package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.QuesTest;
import com.cmbccd.ulms.edu.domain.report.QuesTestDetail;

import java.util.List;

public interface QuesTestService {
	int create(QuesTest record);
	
	int delete(String journo);
	
	int update(QuesTest record);

	int deleteUserTest(String examCode, String userId);
	
	List<QuesTest> listMyExamTest(String examCode, String userId);
	
	List<QuesTest> createMyExamTest(ExamInfo examInfo, String ip);
	
	int submitAnswer(QuesTest record);
	
	Double getUserScore(String examCode);
	
	int updateQuestionCorrect(String examCode, String quesCode, Integer disputReasult);
	
	// 报表
    List<QuesTestDetail> reportQuesTestDetail(String examCode);
}
