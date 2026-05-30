package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.MessageMapper;
import com.cmbccd.ulms.youngTalk.domain.Message;
import com.cmbccd.ulms.youngTalk.domain.MessageExample;
import com.cmbccd.ulms.youngTalk.domain.MessageExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.MessageService;

import jakarta.annotation.Resource;

@Service
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageMapper messageMapper;

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
