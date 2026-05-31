package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Reply;

import java.util.List;

public interface ReplyService {
	public List<Reply> getReplyByCommentId(int commentId);
	
	public int newId();

	public int insertNewReply(Reply record);
	
	public int updateSelectiveReplyById(Reply record);
	
	public Reply getReplyById(int id);

	public int increaseLikeNum(Integer id);

}
