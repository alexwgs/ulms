package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Message;
import com.cmbccd.ulms.youngTalk.domain.MessageExample;

import java.util.List;

public interface MessageService {

	public int newId();
	
	public int insertMessage (Message record);
	
	public int setReadById (int id );
	
	public List<Message> getMessageList(MessageExample example);
	
	public int updateSelectivaById(Message record);
	
	public int getUnreadMessageCount(String userId);
	
	public int updateUnreadMessage(String userId);
}
