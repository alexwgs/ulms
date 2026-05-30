package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Question;

import java.util.List;

public interface QuestionService {

	int getNewId();
	
	int insertNewQuestion(Question record);
	
	List<Question> selectUserQuestionByArticalId (int articalId, String userId);
	
	List<Question> selectQuestionByArticalId(int articalId);

}
