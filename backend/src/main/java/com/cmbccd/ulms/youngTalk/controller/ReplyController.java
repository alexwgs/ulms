package com.cmbccd.ulms.youngTalk.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.websocket.domain.MsgTemplate;
import com.cmbccd.ulms.websocket.service.WebSocketServer;
import com.cmbccd.ulms.youngTalk.domain.Artical;
import com.cmbccd.ulms.youngTalk.domain.Message;
import com.cmbccd.ulms.youngTalk.domain.Reply;
import com.cmbccd.ulms.youngTalk.service.ArticalService;
import com.cmbccd.ulms.youngTalk.service.MessageService;
import com.cmbccd.ulms.youngTalk.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("cyt")
public class ReplyController {

	@Resource
	private ReplyService replyService;
	@Resource
	private ArticalService articalService;
	@Resource
	private MessageService messageService;

	@PostMapping(value = "/reply/")
	public Msg insertNewReply(@RequestBody Reply reply) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		int replyId = replyService.newId();
		reply.setId(replyId);
		reply.setUserid(userId);
		reply.setStatus(1);
		reply.setLikeNum(0);
		reply.setDateTime(Util.currentDateTime());
		if (Util.isNullorEmpty(reply.getArticalId()) || Util.isNullorEmpty(reply.getContent())
				|| Util.isNullorEmpty(reply.getToUser())) {
			return Msg.error("请正确使用A6有声！");
		}
		int count = replyService.insertNewReply(reply);
		if (count == 0) {
			return Msg.error("评论回复失败！");
		}
		articalService.increaseReplyNum(reply.getArticalId());

		Artical artical = articalService.getArticalByIdWithNoContent(reply.getArticalId());

		// 回复成功则将计入消息
		if (!reply.getToUser().equals(userId) && !artical.getPubUser().equals(userId)) {
			Message message = new Message();
			message.setDateTime(Util.currentDateTime());
			if (reply.getAnonFlag() == 1) {
				message.setFromUser("0" + userId);
			} else {
				message.setFromUser(userId);
			}
			message.setMessageId(replyId);
			message.setToUser(reply.getToUser());
			message.setId(messageService.newId());
			message.setArticalId(reply.getArticalId());
			message.setMessageType(2);// 消息类型 1-帖子 2-评论 ...
			message.setIsRead(0);
			messageService.insertMessage(message);
			WebSocketServer.sendMessage(MsgTemplate.success("cyt", "notice", "您收到了新的A6有声评论回复！请在A6有声-我的消息中查看"),
					artical.getPubUser());
		}
		return Msg.success();
	}

	@PutMapping(value = "reply/delete/{id}")
	@SaCheckPermission("cyt:reply:delete")
	public Msg setCommentDisable(@PathVariable("id") int id) {
		if (Util.isNullorEmpty(id)) {
			return Msg.error("没有获取到回复ID！");
		}
		Reply record = new Reply();
		record.setId(id);
		record.setStatus(0);
		int count = replyService.updateSelectiveReplyById(record);
		if (count == 0) {
			return Msg.error("评论回复删除失败！");
		}
		return Msg.success();
	}

	/**
	 * 通过主键获取评论内容
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "reply/{id}")
	public Msg getReplyById(@PathVariable("id") Integer id) {
		if (Util.isNullorEmpty(id)) {
			return Msg.error("没有获取到评论ID！");
		}
		Reply reply = replyService.getReplyById(id);
		return Msg.success( reply);
	}

}
