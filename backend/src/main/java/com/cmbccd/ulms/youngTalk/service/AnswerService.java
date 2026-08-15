package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.youngTalk.domain.Answer;

import java.util.List;
import java.util.Map;

public interface AnswerService {

	int getNewId();
	
	List<Answer> getAnswerByUserId(int articleId,String userId);
	
	int submitAnswer(Answer answer);
	
//	List<Answer> getAnswers(String userId,int articleId);
	
	List<Answer> selectSurveyUserIds(int articleId);

	DataPage<Answer> listSurveyAnswerUser(int articleId, Map<String, String> params);
}
