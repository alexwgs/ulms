package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.youngTalk.dao.MessageMapper;
import com.cmbccd.ulms.youngTalk.domain.Message;
import com.cmbccd.ulms.youngTalk.domain.MessageExample;
import com.cmbccd.ulms.youngTalk.domain.MessageExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.ArticalService;
import com.cmbccd.ulms.youngTalk.service.MessageService;
import com.github.pagehelper.PageHelper;

import jakarta.annotation.Resource;

@Service
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageMapper messageMapper;

	@Resource
	private ArticalService articalService;

	@Override
	public int insertMessage(Message record) {
		
		return messageMapper.insert(record);
	}

	@Override
	public int newId() {
		
		return messageMapper.creatNewId();
	}

	@Override
	public int setReadById(int id) {
		
		return messageMapper.setReadById(id);
	}

	@Override
	public List<Message> getMessageList(MessageExample example) {
		return messageMapper.selectByExample(example);
	}

	@Override
	public DataPage<Message> listMessageByQuery(Map<String, String> params, String userId) {
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
		List<Message> messages = messageMapper.selectByExample(example);
		for (Message message : messages) {
			message.setArtical(articalService.getArticalByIdWithNoContent(message.getArticalId()));
			message.setfUser(DataCache.getEmployees().get(message.getFromUser()));
		}
		return new DataPage<Message>(messages);
	}

	@Override
	public int updateSelectivaById(Message record) {
		
		return messageMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int getUnreadMessageCount(String userId) {
		MessageExample example = new MessageExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsReadEqualTo(0);
		criteria.andToUserEqualTo(userId);
		return messageMapper.countByExample(example);
	}

	@Override
	public int updateUnreadMessage(String userId) {
		MessageExample example = new MessageExample();
		Criteria criteria = example.createCriteria();
		criteria.andToUserEqualTo(userId);
		criteria.andIsReadEqualTo(0);
		Message record = new Message();
		record.setIsRead(1);
		return messageMapper.updateByExampleSelective(record, example);
	}

}
