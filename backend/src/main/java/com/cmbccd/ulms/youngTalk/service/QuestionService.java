package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Question;

import java.util.List;

public interface QuestionService {

	int getNewId();
	
	int insertNewQuestion(Question record);
	
	List<Question> selectUserQuestionByArticleId (int articleId, String userId);
	
	List<Question> selectQuestionByArticleId(int articleId);

}
