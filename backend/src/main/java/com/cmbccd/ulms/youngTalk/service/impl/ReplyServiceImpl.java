package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.ReplyMapper;
import com.cmbccd.ulms.youngTalk.domain.Reply;
import com.cmbccd.ulms.youngTalk.domain.ReplyExample;
import com.cmbccd.ulms.youngTalk.domain.ReplyExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.ReplyService;

import jakarta.annotation.Resource;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Resource
	private ReplyMapper replyMapper;
	@Override
	public List<Reply> getReplyByCommentId(int commentId) {

		ReplyExample example = new ReplyExample();
		Criteria criteria = example.createCriteria();
		criteria.andCommentIdEqualTo(commentId);
		criteria.andStatusEqualTo(1);
		example.setOrderByClause(" DATE_TIME DESC ");
		return replyMapper.selectByExample(example);
	}
	@Override
	public int newId() {
		return replyMapper.creatNewId();
	}
	@Override
	public int insertNewReply(Reply record) {
		return replyMapper.insert(record);
	}
	@Override
	public int updateSelectiveReplyById(Reply record) {
		return replyMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public Reply getReplyById(int id) {
		return replyMapper.selectByPrimaryKey(id);
	}

	@Override
	public int increaseLikeNum(Integer id) {
		return replyMapper.increaseLikeNum(id);
	}


}
