package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.ExamInfoExample;

import java.util.List;

public interface ExamInfoService {
	int create(ExamInfo record);
	
	int update(ExamInfo record);
	
	int delete(String examCode);
	
	List<ExamInfo> list(ExamInfoExample example);
	
	ExamInfo getRecord(String examCode);
	
	List<ExamInfo> listCurrentExam();
}
