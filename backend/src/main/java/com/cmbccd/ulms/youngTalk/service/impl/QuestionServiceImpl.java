package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.QuestionMapper;
import com.cmbccd.ulms.youngTalk.domain.Question;
import com.cmbccd.ulms.youngTalk.service.QuestionService;

import jakarta.annotation.Resource;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Resource
	private QuestionMapper questionMapper;
	@Override
	public int getNewId() {
		return questionMapper.creatNewId();
	}
	@Override
	public int insertNewQuestion(Question record) {
		return questionMapper.insert(record);
	}
	@Override
	public List<Question> selectUserQuestionByArticalId(int articalId,String userId) {
//		return questionMapper.selectByExample(example);
		return questionMapper.selectUserQuestionByArticalId(articalId, userId);
	}
	@Override
	public List<Question> selectQuestionByArticalId(int articalId) {
		return questionMapper.selectQuestionByArticalId(articalId);
	}

}
