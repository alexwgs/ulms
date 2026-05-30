package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.CommentMapper;
import com.cmbccd.ulms.youngTalk.domain.Comment;
import com.cmbccd.ulms.youngTalk.domain.CommentExample;
import com.cmbccd.ulms.youngTalk.domain.CommentExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.CommentService;

import jakarta.annotation.Resource;

@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;

	@Override
	public List<Comment> getCommentByArticalId(int articalId) {
		CommentExample example = new CommentExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticalIdEqualTo(articalId);
		criteria.andStatusEqualTo(1);
		example.setOrderByClause(" DATE_TIME DESC");
		return commentMapper.selectByExample(example);
	}

	@Override
	public int insertNewComment(Comment record) {

		return commentMapper.insert(record);
	}

	@Override
	public int newId() {
		return commentMapper.creatNewId();
	}

	@Override
	public int increaseLikeNum(Integer id) {
		return commentMapper.increaseLikeNum(id);
	}

	@Override
	public List<HashMap<String, Object>> getCommentCountRank() {
		return commentMapper.getCommentCountRank();
	}

	@Override
	public int updateSelectiveCommentById(Comment record) {
		return commentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Comment getCommentById(int id) {
		return commentMapper.selectByPrimaryKey(id);
	}

}
