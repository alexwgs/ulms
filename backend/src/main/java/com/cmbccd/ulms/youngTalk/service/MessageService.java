package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.youngTalk.domain.Message;
import com.cmbccd.ulms.youngTalk.domain.MessageExample;

import java.util.List;
import java.util.Map;

public interface MessageService {

	public int newId();
	
	public int insertMessage (Message record);
	
	public int setReadById (int id );
	
	public List<Message> getMessageList(MessageExample example);

	public DataPage<Message> listMessageByQuery(Map<String, String> params, String userId);
	
	public int updateSelectivaById(Message record);
	
	public int getUnreadMessageCount(String userId);
	
	public int updateUnreadMessage(String userId);
}
