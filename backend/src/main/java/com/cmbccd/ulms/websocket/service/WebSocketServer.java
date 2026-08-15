package com.cmbccd.ulms.websocket.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.dev33.satoken.stp.StpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.Case;
import com.cmbccd.ulms.oht.domain.InitUser;
import com.cmbccd.ulms.websocket.controller.ConnectInitController;
import com.cmbccd.ulms.websocket.domain.MsgTemplate;

/**
 * WebSocket 服务端。
 * 共享状态通过 WsStateService 管理，支持单机（JVM内存）和多实例（Redis）两种模式。
 */
@Component
@ServerEndpoint(value = "/ws/{userId}", configurator = WebSocketServer.IpConfigurator.class)
public class WebSocketServer {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);
    private static final String DEFAULT_ROOM = "default";

    /** 状态管理服务 —— 由 Spring 根据 ulms.ws.multi-instance 配置自动注入对应实现 */
    private static volatile WsStateService state;

    @Resource
    public void setState(WsStateService service) {
        WebSocketServer.state = service;
    }

    /** 状态管理服务访问器（静态桥接，供 @ServerEndpoint 实例与其他组件读取共享状态） */
    public static WsStateService getState() {
        return state;
    }

    // ========== IP 配置器 ==========

    public static class IpConfigurator extends ServerEndpointConfig.Configurator {
        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
            // 鉴权：校验 token 与 URL userId 一致，防止任意人伪装任意用户连接
            String token = getQueryParam(request, "token");
            String uriUserId = extractUserIdFromUri(request.getRequestURI());
            if (Util.isNullorEmpty(token) || Util.isNullorEmpty(uriUserId)) {
                throw new RuntimeException("WebSocket 鉴权失败：缺少 token 或 userId");
            }
            String loginId;
            try {
                loginId = StpUtil.getLoginIdByToken(token).toString();
            } catch (Exception e) {
                LOG.error("WebSocket 握手 token 校验失败", e);
                throw new RuntimeException("WebSocket 鉴权失败：token 无效或已过期");
            }
            if (!loginId.equals(uriUserId)) {
                throw new RuntimeException("WebSocket 鉴权失败：token 与 userId 不匹配");
            }

            String ip = null;
            List<String> forwarded = request.getHeaders().get("x-forwarded-for");
            if (forwarded != null && !forwarded.isEmpty()) {
                ip = forwarded.get(0).split(",")[0].trim();
            }
            if (ip == null || ip.isEmpty()) {
                List<String> realIp = request.getHeaders().get("x-real-ip");
                if (realIp != null && !realIp.isEmpty()) {
                    ip = realIp.get(0).trim();
                }
            }
            if (ip == null || ip.isEmpty()) {
                try {
                    Field f = request.getClass().getDeclaredField("request");
                    f.setAccessible(true);
                    HttpServletRequest req = (HttpServletRequest) f.get(request);
                    ip = req.getRemoteAddr();
                } catch (Exception ignored) {
                }
            }
            sec.getUserProperties().put("client.ip", ip);
        }

        private static String getQueryParam(HandshakeRequest request, String name) {
            Map<String, List<String>> params = request.getParameterMap();
            if (params == null) {
                return null;
            }
            List<String> values = params.get(name);
            return (values != null && !values.isEmpty()) ? values.get(0) : null;
        }

        private static String extractUserIdFromUri(URI uri) {
            if (uri == null || uri.getPath() == null) {
                return null;
            }
            String path = uri.getPath();
            int idx = path.lastIndexOf('/');
            return idx >= 0 ? path.substring(idx + 1) : null;
        }
    }

    // ========== 连接实例字段 ==========

    private String userId;
    private Session session;

    // ========== WebSocket 生命周期 ==========

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        if (Util.isNullorEmpty(userId)) {
            LOG.error("WebSocket 连接缺少 userId，关闭连接");
            try { session.close(); } catch (IOException ignored) {}
            return;
        }
        // 如果用户已在本机连接，先踢掉旧连接
        if (state.hasSession(userId)) {
            state.sendMessage(MsgTemplate.success("sys", "system", "logoff"), userId);
            state.boardcast(MsgTemplate.success("oht", "system", "leave").add("data", userId), userId);
        }

        // 设置消息缓冲区上限，防止大消息被截断（512KB）
        session.setMaxTextMessageBufferSize(512 * 1024);
        session.setMaxBinaryMessageBufferSize(512 * 1024);

        String ip = getClientIp(session);
        LOG.info("Client IP: {}", ip);
        InitUser user = ConnectInitController.staticInit.initUser(userId, ip);
        this.userId = userId;
        this.session = session;

        // 确定房间名称
        String roomName = DEFAULT_ROOM;
        try {
            if (user.getUnfinishCase() != null) {
                roomName = user.getUnfinishCase().getCaseId();
            }
        } catch (Exception e) {
            LOG.error("初始化用户信息后获取未结案数据时异常：{}", e.getMessage());
        }

        state.putSession(userId, session);
        state.userJoinRoom(userId, roomName);
        state.putUser(userId, user);

        // 使用轻量级在线名单，仅包含前端显示所需字段，大幅减少数据传输量
        var allUsers = state.getAllUsersLightweight();

        // 初始化消息：业务主任能看到待接案件，普通用户看不到
        if (state.isDirector(userId)) {
            state.sendMessage(
                    MsgTemplate.success("oht", "init", "websocke初始化成功！")
                            .add("data", user).add("cases", state.getWaittingCase()),
                    userId);
        } else {
            state.sendMessage(
                    MsgTemplate.success("oht", "init", "websocke初始化成功！")
                            .add("data", user).add("cases", null),
                    userId);
        }

        // 在线用户列表
        state.sendMessage(
                MsgTemplate.success("oht", "listInit", "成功！").add("data", allUsers),
                userId);

        // 广播加入通知
        state.boardcast(
                MsgTemplate.success("oht", "system", "join").add("data", user),
                userId);

        // 房间内重连通知
        if (!DEFAULT_ROOM.equals(roomName) && state.getRoomMembers(roomName).size() > 1) {
            state.sendMessageByRoom(
                    MsgTemplate.success("oht", "command", "reconnect"),
                    roomName, null);
        }
    }

    @OnClose
    public void onClose() {
        String roomName = state.getUserRoom(userId);
        if (roomName != null && !DEFAULT_ROOM.equals(roomName)) {
            state.sendMessageByRoom(
                    MsgTemplate.success("oht", "command", "disconnect").add("data", userId),
                    roomName, userId);
        }

        state.boardcast(
                MsgTemplate.success("oht", "system", "leave").add("data", userId),
                userId);

        logoff(userId, session);
    }

    @OnMessage
    public void onMessage(String chatmsg, Session session) {
        if (MessageHandle.handle != null) {
            MessageHandle.handle.messageHandle(chatmsg, userId);
        } else {
            LOG.error("MessageHandle 未初始化，丢弃消息, userId: {}", userId);
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        LOG.error("WebSocket onError, userId: {}", userId, t);
        state.boardcast(
                MsgTemplate.success("oht", "system", "leave").add("data", userId),
                userId);
        logoff(userId, session);
    }

    // ========== 登出逻辑（实例方法，操作 state） ==========

    private void logoff(String userId, Session session) {
        if (Util.isNullorEmpty(userId) || state.getUser(userId) == null) {
            // 通过 session 反查 userId
            // 由于 session 已移至 state 内部管理，此处仅做安全兜底
            LOG.warn("logoff: 无法确定 userId, session 可能已失效");
        }

        ConnectInitController.staticInit.logOff(state.getUser(userId));

        if (session != null && state.hasSession(userId)) {
            state.removeSession(userId);
            state.userLeaveRoom(userId);
            state.removeUser(userId);
        }

        if (state.isDirector(userId)) {
            state.removeDirector(userId);
        }
    }

    // ========== 客户端 IP ==========

    private static String getClientIp(Session session) {
        Object ipObj = session.getUserProperties().get("client.ip");
        if (ipObj != null) {
            String ip = ipObj.toString().trim();
            if (!ip.isEmpty()) {
                return ip;
            }
        }
        return "unknown";
    }

    // ========== 静态消息方法（对外 API，委托给 WsStateService） ==========

    /**
     * 点对点发送消息（同步）
     */
    public static void sendMessage(MsgTemplate message, String userId) {
        state.sendMessage(message, userId);
    }

    /**
     * 点对点发送消息（异步 —— 单机模式下与同步相同，多实例模式下亦相同）
     */
    public static void sendMessageAsync(MsgTemplate message, String userId) {
        state.sendMessage(message, userId);
    }

    /**
     * 房间内发送消息（同步）
     */
    public static void sendMessageByRoom(MsgTemplate message, String roomName, String userId) {
        state.sendMessageByRoom(message, roomName, userId);
    }

    /**
     * 房间内发送消息（异步）
     */
    public static void sendMessageByRoomAsync(MsgTemplate message, String roomName, String userId) {
        state.sendMessageByRoom(message, roomName, userId);
    }

    /**
     * 全员广播（同步）
     */
    public static void boardcast(MsgTemplate message, String userId) {
        state.boardcast(message, userId);
    }

    /**
     * 全员广播（异步）
     */
    public static void boardcastAsync(MsgTemplate message, String userId) {
        state.boardcast(message, userId);
    }

    /**
     * 广播给所有业务主任
     */
    public static void boardcastToDirectors(MsgTemplate message, String userId) {
        state.boardcastToDirectors(message, userId);
    }

    /**
     * OHT 点对点消息（仅本机投递）
     */
    public static void ohtSendMessage(MsgTemplate message, String revId) {
        state.ohtSendMessage(message, revId);
    }

    /**
     * 房间是否还存在
     */
    public static boolean isRoomExist(String roomName) {
        return state.isRoomExist(roomName);
    }

    /**
     * 用户更换房间
     */
    public static void roomChange(String userId, String newRoomName) {
        state.roomChange(userId, newRoomName);
    }
}
