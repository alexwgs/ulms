package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.AnswerMapper;
import com.cmbccd.ulms.youngTalk.domain.Answer;
import com.cmbccd.ulms.youngTalk.domain.AnswerExample;
import com.cmbccd.ulms.youngTalk.domain.AnswerExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.AnswerService;

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
	public List<Answer> getAnswerByUserId(int articalId,String userId) {
		AnswerExample example = new AnswerExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticalIdEqualTo(articalId);
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
	public List<Answer> selectSurveyUserIds(int articalId) {
		return answerMapper.selectSurveyUserIds(articalId);
	}

}
