package com.cmbccd.ulms.websocket.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.websocket.service.PushService;
import com.cmbccd.ulms.websocket.service.WebSocketServer;

@RestController
@RequestMapping("/ws/online")
public class OnlineMonitorController {

    private static final Logger LOG = LoggerFactory.getLogger(OnlineMonitorController.class);

    @Resource
    private PushService pushService;

    /**
     * 获取所有在线用户（轻量级数据）
     */
    @GetMapping("/list")
    public Msg getOnlineUserList() {
        Collection<Map<String, Object>> users = WebSocketServer.state.getAllUsersLightweight();
        return Msg.success(users);
    }

    /**
     * 推送消息给在线用户。
     * 请求体: { userIds: ["u1","u2"], module: "sys", type: "notice", title: "标题", content: "内容" }
     * userIds 为空或 null 时广播给所有在线用户。
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/push")
    public Msg pushMessage(@RequestBody Map<String, Object> body) {
        List<String> userIds = (List<String>) body.get("userIds");
        String module = (String) body.getOrDefault("module", "sys");
        String type = (String) body.getOrDefault("type", "notice");
        String title = (String) body.getOrDefault("title", "系统通知");
        Object content = body.get("content");

        if (userIds == null || userIds.isEmpty()) {
            pushService.pushToAll(module, type, title, content);
            LOG.info("广播推送消息 —— module: {}, type: {}, title: {}", module, type, title);
        } else {
            pushService.pushToUsers(userIds, module, type, title, content);
            LOG.info("定向推送消息 —— 目标用户数: {}, module: {}, type: {}, title: {}", userIds.size(), module, type, title);
        }

        return Msg.success("推送成功");
    }
}
