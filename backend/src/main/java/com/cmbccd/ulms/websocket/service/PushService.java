package com.cmbccd.ulms.websocket.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.websocket.domain.MsgTemplate;

/**
 * 通用消息推送服务。
 * 任何后端模块注入此服务后，调用一行代码即可将消息推送到前端。
 */
@Service
public class PushService {

    private static final DateTimeFormatter TIMESTAMP_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** 推送给指定用户 */
    public void pushToUser(String userId, String module, String type, String title, Object data) {
        MsgTemplate msg = buildMessage(module, type, title, data);
        WebSocketServer.sendMessage(msg, userId);
    }

    /** 推送给多个用户 */
    public void pushToUsers(Collection<String> userIds, String module, String type, String title, Object data) {
        MsgTemplate msg = buildMessage(module, type, title, data);
        for (String userId : userIds) {
            WebSocketServer.sendMessage(msg, userId);
        }
    }

    /** 广播给所有在线用户 */
    public void pushToAll(String module, String type, String title, Object data) {
        MsgTemplate msg = buildMessage(module, type, title, data);
        WebSocketServer.boardcast(msg, null);
    }

    /** 广播给所有业务主任 */
    public void pushToDirectors(String module, String type, String title, Object data) {
        MsgTemplate msg = buildMessage(module, type, title, data);
        WebSocketServer.boardcastToDirectors(msg, null);
    }

    private MsgTemplate buildMessage(String module, String type, String title, Object data) {
        return MsgTemplate.success("push", type)
                .add("module", module)
                .add("title", title)
                .add("content", data)
                .add("timestamp", LocalDateTime.now().format(TIMESTAMP_FMT));
    }
}
