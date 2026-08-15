package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.LikeMapper;
import com.cmbccd.ulms.youngTalk.domain.Like;
import com.cmbccd.ulms.youngTalk.domain.LikeExample;
import com.cmbccd.ulms.youngTalk.domain.LikeExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.LikeService;

import jakarta.annotation.Resource;

@Service
public class LikeServiceImpl implements LikeService {

	@Resource
	private LikeMapper likeMapper;

	@Override
	public int isUserLikeByArticleId(int articleId , String userId) {
		LikeExample example = new LikeExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(articleId);
		criteria.andUserIdEqualTo(userId);
		criteria.andLikeTypeEqualTo(1);
		criteria.andStatusEqualTo(1);
		return likeMapper.countByExample(example);
	}

	@Override
	public int newId() {
		return likeMapper.creatNewId();
	}

	@Override
	public int countByExample(LikeExample example) {
		return likeMapper.countByExample(example);
	}

	@Override
	public int insert(Like record) {
		return likeMapper.insert(record);
	}

	@Override
	public List<Like> getCommentLikeById(int commentId, String userId) {
		LikeExample example = new LikeExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(commentId);
		criteria.andUserIdEqualTo(userId);
		criteria.andLikeTypeEqualTo(2);
		criteria.andStatusEqualTo(1);
		return likeMapper.selectByExample(example);
	}

}
