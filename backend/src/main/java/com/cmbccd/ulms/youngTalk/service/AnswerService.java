package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Answer;

import java.util.List;

public interface AnswerService {

	int getNewId();
	
	List<Answer> getAnswerByUserId(int articalId,String userId);
	
	int submitAnswer(Answer answer);
	
//	List<Answer> getAnswers(String userId,int articalId);
	
	List<Answer> selectSurveyUserIds(int articalId);
}
