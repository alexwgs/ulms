package com.cmbccd.ulms.websocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.Case;
import com.cmbccd.ulms.oht.domain.InitUser;
import com.cmbccd.ulms.websocket.domain.MsgTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单机模式 WebSocket 状态管理。
 * 行为与原先 WebSocketServer 中的静态 ConcurrentHashMap 完全一致。
 */
public class LocalWsStateService implements WsStateService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalWsStateService.class);

    private final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> userRoom = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<String>> roomMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, InitUser> userMap = new ConcurrentHashMap<>();
    private final Set<String> directorSet = ConcurrentHashMap.newKeySet();
    private volatile List<Case> waittingCase = new ArrayList<>();

    // ========== Session 管理 ==========

    @Override
    public void putSession(String userId, Session session) {
        sessionMap.put(userId, session);
    }

    @Override
    public void removeSession(String userId) {
        sessionMap.remove(userId);
    }

    @Override
    public boolean hasSession(String userId) {
        Session s = sessionMap.get(userId);
        return s != null && s.isOpen();
    }

    // ========== 房间管理 ==========

    @Override
    public void userJoinRoom(String userId, String roomName) {
        userRoom.put(userId, roomName);
        roomMap.computeIfAbsent(roomName, k -> ConcurrentHashMap.newKeySet()).add(userId);
    }

    @Override
    public void userLeaveRoom(String userId) {
        String room = userRoom.remove(userId);
        if (room != null && roomMap.containsKey(room)) {
            roomMap.get(room).remove(userId);
            if (roomMap.get(room).isEmpty()) {
                roomMap.remove(room);
            }
        }
    }

    @Override
    public void roomChange(String userId, String newRoomName) {
        String oldRoom = userRoom.get(userId);
        if (oldRoom != null && roomMap.containsKey(oldRoom)) {
            roomMap.get(oldRoom).remove(userId);
        }
        userRoom.put(userId, newRoomName);
        roomMap.computeIfAbsent(newRoomName, k -> ConcurrentHashMap.newKeySet()).add(userId);
    }

    @Override
    public String getUserRoom(String userId) {
        return userRoom.get(userId);
    }

    @Override
    public Set<String> getRoomMembers(String roomName) {
        Set<String> members = roomMap.get(roomName);
        return members != null ? members : Collections.emptySet();
    }

    @Override
    public boolean isRoomExist(String roomName) {
        if (!roomMap.containsKey(roomName)) {
            return false;
        }
        Set<String> room = roomMap.get(roomName);
        if (room != null && !room.isEmpty()) {
            return true;
        }
        roomMap.remove(roomName);
        return false;
    }

    // ========== 用户 Map ==========

    @Override
    public void putUser(String userId, InitUser user) {
        userMap.put(userId, user);
    }

    @Override
    public InitUser getUser(String userId) {
        return userMap.get(userId);
    }

    @Override
    public void removeUser(String userId) {
        userMap.remove(userId);
    }

    @Override
    public Collection<InitUser> getAllUsers() {
        return userMap.values();
    }

    @Override
    public Collection<Map<String, Object>> getAllUsersLightweight() {
        List<Map<String, Object>> result = new ArrayList<>(userMap.size());
        for (InitUser u : userMap.values()) {
            Map<String, Object> item = new HashMap<>(20);
            item.put("userId", u.getUserId());
            item.put("ohtStatus", u.getOhtStatus());
            item.put("identity", u.getIdentity());
            item.put("userStatus", u.getUserStatus());
            item.put("statusName", u.getStatusName());
            item.put("statusTime", u.getStatusTime());
            if (u.getOhtRole() != null) {
                Map<String, Object> role = new HashMap<>(4);
                role.put("ploNum", u.getOhtRole().getPloNum());
                role.put("roleCode", u.getOhtRole().getRoleCode());
                role.put("roleStat", u.getOhtRole().getRoleStat());
                role.put("roleType", u.getOhtRole().getRoleType());
                item.put("ohtRole", role);
            }
            if (u.getUser() != null) {
                Map<String, Object> user = new HashMap<>(8);
                user.put("ploNum", u.getUser().getPloNum());
                user.put("ploName", u.getUser().getPloName());
                user.put("deptNum", u.getUser().getDeptNum());
                user.put("deptName", u.getUser().getDeptName());
                user.put("deptGroup", u.getUser().getDeptGroup());
                user.put("groupName", u.getUser().getGroupName());
                user.put("jobLevel", u.getUser().getJobLevel());
                user.put("avatar", u.getUser().getAvatar());
                item.put("user", user);
            }
            if (u.getStation() != null) {
                Map<String, String> station = new HashMap<>(5);
                station.put("extnNum", u.getStation().getExtnNum());
                station.put("floorNum", u.getStation().getFloorNum());
                station.put("pcIp", u.getStation().getPcIp());
                station.put("extnIp", u.getStation().getExtnIp());
                item.put("station", station);
            }
            if (u.getUnfinishCase() != null) {
                Map<String, Object> ucase = new HashMap<>(6);
                ucase.put("caseId", u.getUnfinishCase().getCaseId());
                ucase.put("caseType", u.getUnfinishCase().getCaseType());
                ucase.put("caseStatus", u.getUnfinishCase().getCaseStatus());
                ucase.put("buildTime", u.getUnfinishCase().getBuildTime());
                ucase.put("pickTime", u.getUnfinishCase().getPickTime());
                ucase.put("waitTime", u.getUnfinishCase().getWaitTime());
                if (u.getUnfinishCase().getBuildUser() != null) {
                    ucase.put("buildUserName", u.getUnfinishCase().getBuildUser().getPloName());
                }
                if (u.getUnfinishCase().getPickUser() != null) {
                    ucase.put("pickUserName", u.getUnfinishCase().getPickUser().getPloName());
                }
                item.put("unfinishCase", ucase);
            }
            result.add(item);
        }
        return result;
    }

    // ========== 业务主任列表 ==========

    @Override
    public void addDirector(String userId) {
        directorSet.add(userId);
    }

    @Override
    public void removeDirector(String userId) {
        directorSet.remove(userId);
    }

    @Override
    public boolean isDirector(String userId) {
        return directorSet.contains(userId);
    }

    @Override
    public Set<String> getDirectors() {
        return directorSet;
    }

    // ========== 待接案件缓存 ==========

    @Override
    public List<Case> getWaittingCase() {
        return waittingCase;
    }

    @Override
    public void setWaittingCase(List<Case> cases) {
        this.waittingCase = cases;
    }

    // ========== 消息发送（单机模式：只发本机） ==========

    /**
     * 预序列化消息为 JSON 字符串，避免在循环中重复序列化。
     */
    private String toJson(MsgTemplate message) {
        return JSON.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 同步发送（点对点关键消息，需保证送达）。
     */
    private void sendSync(Session s, String json) {
        if (s != null && s.isOpen()) {
            try {
                s.getBasicRemote().sendText(json);
            } catch (IOException e) {
                LOG.error("sendSync error", e);
            }
        }
    }

    /**
     * 异步发送（广播/房间消息，不阻塞发送线程）。
     */
    private void sendAsync(Session s, String json) {
        if (s != null && s.isOpen()) {
            s.getAsyncRemote().sendText(json);
        }
    }

    @Override
    public void sendMessage(MsgTemplate message, String targetUserId) {
        if (Util.isNullorEmpty(targetUserId)) {
            return;
        }
        sendSync(sessionMap.get(targetUserId), toJson(message));
    }

    @Override
    public void sendMessageByRoom(MsgTemplate message, String roomName, String excludeUserId) {
        Set<String> members = roomMap.get(roomName);
        if (members == null || members.isEmpty()) {
            return;
        }
        // 预先序列化一次，循环中复用
        String json = toJson(message);
        for (String userId : members) {
            if (excludeUserId != null && excludeUserId.equals(userId)) {
                continue;
            }
            sendAsync(sessionMap.get(userId), json);
        }
    }

    @Override
    public void boardcast(MsgTemplate message, String excludeUserId) {
        // 预先序列化，避免800次重复序列化
        String json = toJson(message);
        for (String userId : sessionMap.keySet()) {
            if (excludeUserId != null && excludeUserId.equals(userId)) {
                continue;
            }
            sendAsync(sessionMap.get(userId), json);
        }
    }

    @Override
    public void boardcastToDirectors(MsgTemplate message, String excludeUserId) {
        String json = toJson(message);
        for (String userId : directorSet) {
            if (excludeUserId != null && excludeUserId.equals(userId)) {
                continue;
            }
            sendAsync(sessionMap.get(userId), json);
        }
    }

    @Override
    public void ohtSendMessage(MsgTemplate message, String targetUserId) {
        if (Util.isNullorEmpty(targetUserId)) {
            return;
        }
        sendSync(sessionMap.get(targetUserId), toJson(message));
    }

    @Override
    public void shutdown() {
        // 单机模式无需清理
    }
}
