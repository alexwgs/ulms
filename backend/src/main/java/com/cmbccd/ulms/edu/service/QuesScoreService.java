package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.QuesScore;
import com.cmbccd.ulms.edu.domain.QuesScoreExample;
import com.cmbccd.ulms.edu.domain.report.QuesScoreHum;

import java.util.List;

public interface QuesScoreService {
	int create(QuesScore record);
	
	int update(QuesScore record);
	
	int delete(String journo);
	
	List<QuesScore> list(QuesScoreExample example);
	
	QuesScore getUserQuesScore(String examCode,String userId);
	
	int updateUserScore(String examCode);
	
	int  updateCompleteStatus(String examCode);
	
	// 报表
	List<QuesScoreHum>reportExamScoreHum(String examCode);
	
	// 根据考试编号删除用户评分
	int deleteByExamCode(String examCode);
	
	// 重置考试
	int resetExam(QuesScore quesScore);
}
