package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.youngTalk.dao.AnswerMapper;
import com.cmbccd.ulms.youngTalk.domain.Answer;
import com.cmbccd.ulms.youngTalk.domain.AnswerExample;
import com.cmbccd.ulms.youngTalk.domain.AnswerExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.AnswerService;
import com.github.pagehelper.PageHelper;

import jakarta.annotation.Resource;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Resource
	private AnswerMapper answerMapper;
	@Override
	
	public int getNewId() {
		return answerMapper.creatNewId();
	}
	
	@Override
	public List<Answer> getAnswerByUserId(int articleId,String userId) {
		AnswerExample example = new AnswerExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(articleId);
		criteria.andUserIdEqualTo(userId);
		example.setOrderByClause(" Question_Id ");
		return answerMapper.selectByExample(example);
	}

	@Override
	public int submitAnswer(Answer answer) {
		int id = answerMapper.creatNewId();
		answer.setId(id);
		return answerMapper.insert(answer);
	}

	@Override
	public List<Answer> selectSurveyUserIds(int articleId) {
		return answerMapper.selectSurveyUserIds(articleId);
	}

	@Override
	public DataPage<Answer> listSurveyAnswerUser(int articleId, Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Answer> users = answerMapper.selectSurveyUserIds(articleId);
		for (Answer user : users) {
			user.setUser(DataCache.getEmployees().get(user.getUserId()));
		}
		return new DataPage<Answer>(users);
	}

}
