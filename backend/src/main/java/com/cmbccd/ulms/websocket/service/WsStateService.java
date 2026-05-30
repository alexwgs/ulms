package com.cmbccd.ulms.websocket.service;

import com.cmbccd.ulms.oht.domain.Case;
import com.cmbccd.ulms.oht.domain.InitUser;
import com.cmbccd.ulms.websocket.domain.MsgTemplate;

import jakarta.websocket.Session;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * WebSocket 状态管理抽象接口。
 * 单机模式：LocalWsStateService（ConcurrentHashMap，无外部依赖）
 * 多实例模式：RedisWsStateService（Redis 存储状态 + Pub/Sub 消息中继）
 */
public interface WsStateService {

    // ========== Session 管理（永远本机） ==========
    void putSession(String userId, Session session);
    void removeSession(String userId);
    boolean hasSession(String userId);

    // ========== 房间管理 ==========
    void userJoinRoom(String userId, String roomName);
    void userLeaveRoom(String userId);
    void roomChange(String userId, String newRoomName);
    String getUserRoom(String userId);
    Set<String> getRoomMembers(String roomName);
    boolean isRoomExist(String roomName);

    // ========== 用户 Map ==========
    void putUser(String userId, InitUser user);
    InitUser getUser(String userId);
    void removeUser(String userId);
    Collection<InitUser> getAllUsers();
    /** 获取轻量级在线用户列表，仅包含前端在线名单所需字段，减少网络传输量 */
    Collection<Map<String, Object>> getAllUsersLightweight();

    // ========== 业务主任列表 ==========
    void addDirector(String userId);
    void removeDirector(String userId);
    boolean isDirector(String userId);
    Set<String> getDirectors();

    // ========== 待接案件缓存 ==========
    List<Case> getWaittingCase();
    void setWaittingCase(List<Case> cases);

    // ========== 消息发送 ==========
    void sendMessage(MsgTemplate message, String targetUserId);
    void sendMessageByRoom(MsgTemplate message, String roomName, String excludeUserId);
    void boardcast(MsgTemplate message, String excludeUserId);
    void boardcastToDirectors(MsgTemplate message, String excludeUserId);
    void ohtSendMessage(MsgTemplate message, String targetUserId);

    // ========== 生命周期 ==========
    void shutdown();
}
