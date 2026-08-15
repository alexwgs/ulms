package com.cmbccd.ulms.youngTalk.controller;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.Message;
import com.cmbccd.ulms.youngTalk.domain.MessageExample;
import com.cmbccd.ulms.youngTalk.domain.MessageExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.ArticalService;
import com.cmbccd.ulms.youngTalk.service.MessageService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
* <p>Title: MessageController.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@RestController
@RequestMapping("cyt")
public class MessageController {
	@Resource
	private MessageService messageService;
	@Resource
	private ArticalService articalService;


	@GetMapping("/message/list")
	public Msg getMessageList(@RequestParam Map<String, String> params) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		MessageExample example = new MessageExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("messageType"))) {
			criteria.andMessageTypeEqualTo(Integer.parseInt(params.get("messageType")));
		}
		if (!Util.isNullorEmpty(params.get("isRead"))) {
			criteria.andIsReadEqualTo(Integer.parseInt(params.get("isRead")));
		} else {
			criteria.andIsReadEqualTo(0);
		}
		criteria.andToUserEqualTo(userId);

		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}

		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Message> messages = messageService.getMessageList(example);
		for (Message message : messages) {
			message.setArtical(articalService.getArticalByIdWithNoContent(message.getArticalId()));
			message.setfUser(DataCache.EMPLOYEE.get(message.getFromUser()));
		}
		return Msg.success(new DataPage<Message>(messages));
	}

	/**
	 * 设置已读BY 主键 ID
	 * @param id
	 * @return
	 */
	@PutMapping(value = "message/read/{id}")
	public Msg setMessageRead(@PathVariable("id") Integer id) {
		Message record = new Message();
		record.setId(id);
		record.setIsRead(1);
		int count = messageService.updateSelectivaById(record);
		if (count == 0) {
			return Msg.error();
		}
		return Msg.success();
	}

	@GetMapping(value = "message/unread")
	public Msg getUnReadMessage() {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		int count = messageService.getUnreadMessageCount(userId);
		return Msg.success(count);
	}

	/**
	 * 	设置全部已读BY 主键 ID
	 * @return
	 */
	@PutMapping(value = "message/read/all")
	public Msg setMessageReadAll() {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		int count = messageService.updateUnreadMessage(userId);
		if (count == 0) {
			return Msg.error();
		}
		return Msg.success("成功将[" + count + "]条记录设置为已读！");
	}
}
