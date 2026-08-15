package com.cmbccd.ulms.websocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.Case;
import com.cmbccd.ulms.oht.domain.InitUser;
import com.cmbccd.ulms.websocket.domain.MsgTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import jakarta.websocket.Session;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多实例模式 WebSocket 状态管理。
 * 共享状态（房间、用户、业务主任、待接案件）存储在 Redis，
 * 跨服务器消息通过 Redis Pub/Sub 中继。
 */
public class RedisWsStateService implements WsStateService {

    private static final Logger LOG = LoggerFactory.getLogger(RedisWsStateService.class);

    // Redis key 前缀
    private static final String KEY_USER_ROOM = "ulms:ws:user:room";
    private static final String KEY_ROOM_PREFIX = "ulms:ws:room:";
    private static final String KEY_USER_MAP = "ulms:ws:user:map";
    private static final String KEY_DIRECTORS = "ulms:ws:directors";
    private static final String KEY_WAITTING = "ulms:ws:waitting";
    private static final String CHANNEL = "ulms:ws:relay";

    private final String serverId = UUID.randomUUID().toString();

    /** 本机 Session 映射 —— 永远存本地，不可跨 JVM */
    private final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    private final StringRedisTemplate redis;
    private final RedisMessageListenerContainer listenerContainer;

    public RedisWsStateService(StringRedisTemplate redis,
                               RedisMessageListenerContainer listenerContainer) {
        this.redis = redis;
        this.listenerContainer = listenerContainer;

        // 注册 Redis Pub/Sub 订阅
        MessageListener listener = (message, pattern) -> {
            try {
                // 消息由 StringRedisTemplate(UTF-8) 发布，必须用 UTF-8 解码，避免 GBK 默认字符集乱码
                onRelayMessage(new String(message.getBody(), StandardCharsets.UTF_8));
            } catch (Exception e) {
                LOG.error("处理中继消息失败", e);
            }
        };
        this.listenerContainer.addMessageListener(listener, new ChannelTopic(CHANNEL));
    }

    // ========== Session 管理（仅本机） ==========

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

    // ========== 房间管理（Redis Hash + Set） ==========

    @Override
    public void userJoinRoom(String userId, String roomName) {
        redis.opsForHash().put(KEY_USER_ROOM, userId, roomName);
        redis.opsForSet().add(KEY_ROOM_PREFIX + roomName, userId);
    }

    @Override
    public void userLeaveRoom(String userId) {
        String room = (String) redis.opsForHash().get(KEY_USER_ROOM, userId);
        if (room != null) {
            redis.opsForSet().remove(KEY_ROOM_PREFIX + room, userId);
        }
        redis.opsForHash().delete(KEY_USER_ROOM, userId);
    }

    @Override
    public void roomChange(String userId, String newRoomName) {
        String oldRoom = (String) redis.opsForHash().get(KEY_USER_ROOM, userId);
        if (oldRoom != null) {
            redis.opsForSet().remove(KEY_ROOM_PREFIX + oldRoom, userId);
        }
        redis.opsForHash().put(KEY_USER_ROOM, userId, newRoomName);
        redis.opsForSet().add(KEY_ROOM_PREFIX + newRoomName, userId);
    }

    @Override
    public String getUserRoom(String userId) {
        return (String) redis.opsForHash().get(KEY_USER_ROOM, userId);
    }

    @Override
    public Set<String> getRoomMembers(String roomName) {
        Set<String> members = redis.opsForSet().members(KEY_ROOM_PREFIX + roomName);
        return members != null ? members : Collections.emptySet();
    }

    @Override
    public boolean isRoomExist(String roomName) {
        Long size = redis.opsForSet().size(KEY_ROOM_PREFIX + roomName);
        if (size != null && size > 0) {
            return true;
        }
        redis.delete(KEY_ROOM_PREFIX + roomName);
        return false;
    }

    // ========== 用户 Map（Redis Hash，JSON 序列化 InitUser） ==========

    @Override
    public void putUser(String userId, InitUser user) {
        redis.opsForHash().put(KEY_USER_MAP, userId,
                JSON.toJSONString(user, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Override
    public InitUser getUser(String userId) {
        String json = (String) redis.opsForHash().get(KEY_USER_MAP, userId);
        if (json == null) {
            return null;
        }
        return JSON.parseObject(json, InitUser.class);
    }

    @Override
    public void removeUser(String userId) {
        redis.opsForHash().delete(KEY_USER_MAP, userId);
    }

    @Override
    public Collection<InitUser> getAllUsers() {
        List<InitUser> users = new ArrayList<>();
        List<Object> values = redis.opsForHash().values(KEY_USER_MAP);
        if (values != null) {
            for (Object v : values) {
                if (v != null) {
                    users.add(JSON.parseObject(v.toString(), InitUser.class));
                }
            }
        }
        return users;
    }

    @Override
    public Collection<Map<String, Object>> getAllUsersLightweight() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Object> values = redis.opsForHash().values(KEY_USER_MAP);
        if (values == null) {
            return result;
        }
        for (Object v : values) {
            if (v == null) continue;
            JSONObject obj = JSON.parseObject(v.toString());
            Map<String, Object> item = new HashMap<>(20);
            item.put("userId", obj.getString("userId"));
            item.put("ohtStatus", obj.getInteger("ohtStatus"));
            item.put("identity", obj.getString("identity"));
            item.put("userStatus", obj.getInteger("userStatus"));
            item.put("statusName", obj.getString("statusName"));
            item.put("statusTime", obj.getString("statusTime"));

            JSONObject ohtRole = obj.getJSONObject("ohtRole");
            if (ohtRole != null) {
                Map<String, Object> role = new HashMap<>(4);
                role.put("ploNum", ohtRole.getString("ploNum"));
                role.put("roleCode", ohtRole.getInteger("roleCode"));
                role.put("roleStat", ohtRole.getInteger("roleStat"));
                role.put("roleType", ohtRole.getInteger("roleType"));
                item.put("ohtRole", role);
            }

            JSONObject user = obj.getJSONObject("user");
            if (user != null) {
                Map<String, Object> um = new HashMap<>(8);
                um.put("ploNum", user.getString("ploNum"));
                um.put("ploName", user.getString("ploName"));
                um.put("deptNum", user.getString("deptNum"));
                um.put("deptName", user.getString("deptName"));
                um.put("deptGroup", user.getString("deptGroup"));
                um.put("groupName", user.getString("groupName"));
                um.put("jobLevel", user.getString("jobLevel"));
                um.put("avatar", user.getString("avatar"));
                item.put("user", um);
            }

            JSONObject station = obj.getJSONObject("station");
            if (station != null) {
                Map<String, String> sm = new HashMap<>(5);
                sm.put("extnNum", station.getString("extnNum"));
                sm.put("floorNum", station.getString("floorNum"));
                sm.put("pcIp", station.getString("pcIp"));
                sm.put("extnIp", station.getString("extnIp"));
                item.put("station", sm);
            }

            JSONObject ucase = obj.getJSONObject("unfinishCase");
            if (ucase != null) {
                Map<String, Object> cm = new HashMap<>(6);
                cm.put("caseId", ucase.getString("caseId"));
                cm.put("caseType", ucase.getInteger("caseType"));
                cm.put("caseStatus", ucase.getInteger("caseStatus"));
                cm.put("buildTime", ucase.getInteger("buildTime"));
                cm.put("pickTime", ucase.getInteger("pickTime"));
                cm.put("waitTime", ucase.getInteger("waitTime"));
                JSONObject buildUser = ucase.getJSONObject("buildUser");
                if (buildUser != null) cm.put("buildUserName", buildUser.getString("ploName"));
                JSONObject pickUser = ucase.getJSONObject("pickUser");
                if (pickUser != null) cm.put("pickUserName", pickUser.getString("ploName"));
                item.put("unfinishCase", cm);
            }
            result.add(item);
        }
        return result;
    }

    // ========== 业务主任列表（Redis Set） ==========

    @Override
    public void addDirector(String userId) {
        redis.opsForSet().add(KEY_DIRECTORS, userId);
    }

    @Override
    public void removeDirector(String userId) {
        redis.opsForSet().remove(KEY_DIRECTORS, userId);
    }

    @Override
    public boolean isDirector(String userId) {
        Boolean result = redis.opsForSet().isMember(KEY_DIRECTORS, userId);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public Set<String> getDirectors() {
        Set<String> directors = redis.opsForSet().members(KEY_DIRECTORS);
        return directors != null ? directors : Collections.emptySet();
    }

    // ========== 待接案件缓存（Redis String，JSON 序列化） ==========

    @Override
    public List<Case> getWaittingCase() {
        String json = redis.opsForValue().get(KEY_WAITTING);
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        return JSON.parseArray(json, Case.class);
    }

    @Override
    public void setWaittingCase(List<Case> cases) {
        if (cases == null) {
            redis.delete(KEY_WAITTING);
        } else {
            redis.opsForValue().set(KEY_WAITTING,
                    JSON.toJSONString(cases, SerializerFeature.DisableCircularReferenceDetect));
        }
    }

    // ========== 消息发送（多实例模式：本地优先 + Redis Pub/Sub 中继） ==========

    private String toJson(MsgTemplate message) {
        return JSON.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public void sendMessage(MsgTemplate message, String targetUserId) {
        if (Util.isNullorEmpty(targetUserId)) {
            return;
        }
        String json = toJson(message);
        if (sendToLocal(targetUserId, json)) {
            return;
        }
        relay("unicast", json, targetUserId, null, null);
    }

    @Override
    public void sendMessageByRoom(MsgTemplate message, String roomName, String excludeUserId) {
        if (Util.isNullorEmpty(roomName)) {
            return;
        }
        String json = toJson(message);
        Set<String> members = getRoomMembers(roomName);
        if (members != null) {
            for (String userId : members) {
                if (userId.equals(excludeUserId)) {
                    continue;
                }
                sendToLocal(userId, json);
            }
        }
        relay("roomcast", json, null, roomName, excludeUserId);
    }

    @Override
    public void boardcast(MsgTemplate message, String excludeUserId) {
        String json = toJson(message);
        for (String userId : sessionMap.keySet()) {
            if (userId.equals(excludeUserId)) {
                continue;
            }
            sendToLocal(userId, json);
        }
        relay("broadcast", json, null, null, excludeUserId);
    }

    @Override
    public void boardcastToDirectors(MsgTemplate message, String excludeUserId) {
        String json = toJson(message);
        Set<String> directors = getDirectors();
        if (directors != null) {
            for (String userId : directors) {
                if (userId.equals(excludeUserId)) {
                    continue;
                }
                sendToLocal(userId, json);
            }
        }
        relay("directorcast", json, null, null, excludeUserId);
    }

    @Override
    public void ohtSendMessage(MsgTemplate message, String targetUserId) {
        if (Util.isNullorEmpty(targetUserId)) {
            return;
        }
        sendToLocal(targetUserId, toJson(message));
    }

    @Override
    public void shutdown() {
        try {
            listenerContainer.stop();
        } catch (Exception e) {
            LOG.warn("关闭 Redis 消息监听容器时出错", e);
        }
    }

    // ========== 内部方法 ==========

    /**
     * 异步投递预序列化的 JSON 到本机 Session。成功返回 true。
     */
    private boolean sendToLocal(String userId, String json) {
        if (Util.isNullorEmpty(userId)) {
            return false;
        }
        Session s = sessionMap.get(userId);
        if (s != null && s.isOpen()) {
            s.getAsyncRemote().sendText(json);
            return true;
        }
        return false;
    }

    /**
     * 发布中继消息到 Redis Pub/Sub 频道。payload 已经是预序列化的 JSON 字符串。
     */
    private void relay(String type, String payload, String targetUserId,
                       String roomName, String excludeUserId) {
        try {
            Map<String, Object> relay = new HashMap<>();
            relay.put("sid", serverId);
            relay.put("type", type);
            relay.put("uid", targetUserId);
            relay.put("room", roomName);
            relay.put("exc", excludeUserId);
            relay.put("payload", payload);  // 已是 JSON 字符串，接收方无需再序列化
            redis.convertAndSend(CHANNEL, JSON.toJSONString(relay));
        } catch (Exception e) {
            LOG.error("发布中继消息失败, type: {}", type, e);
        }
    }

    /**
     * 收到其他服务器的中继消息，投递给本机对应的 Session。
     */
    @SuppressWarnings("unchecked")
    private void onRelayMessage(String raw) {
        Map<String, Object> relay = JSON.parseObject(raw, Map.class);

        if (serverId.equals(relay.get("sid"))) {
            return;
        }

        String type = (String) relay.get("type");
        String payload = (String) relay.get("payload");  // 已是 JSON，直接使用
        if (payload == null) {
            return;
        }

        switch (type) {
            case "unicast" -> {
                String uid = (String) relay.get("uid");
                sendToLocal(uid, payload);
            }
            case "roomcast" -> {
                String roomName = (String) relay.get("room");
                String exc = (String) relay.get("exc");
                Set<String> members = getRoomMembers(roomName);
                if (members != null) {
                    for (String userId : members) {
                        if (userId.equals(exc)) {
                            continue;
                        }
                        sendToLocal(userId, payload);
                    }
                }
            }
            case "broadcast" -> {
                String exc = (String) relay.get("exc");
                for (String userId : sessionMap.keySet()) {
                    if (userId.equals(exc)) {
                        continue;
                    }
                    sendToLocal(userId, payload);
                }
            }
            case "directorcast" -> {
                String exc = (String) relay.get("exc");
                Set<String> directors = getDirectors();
                if (directors != null) {
                    for (String userId : directors) {
                        if (userId.equals(exc)) {
                            continue;
                        }
                        sendToLocal(userId, payload);
                    }
                }
            }
        }
    }
}
