package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.ExamInfoExample;

import java.util.List;
import java.util.Map;

public interface ExamInfoService {
	int create(ExamInfo record);
	
	int update(ExamInfo record);
	
	int delete(String examCode);
	
	List<ExamInfo> list(ExamInfoExample example);

	DataPage<ExamInfo> listExamInfoQuery(Map<String, String> params, boolean selfOnly);
	
	ExamInfo getRecord(String examCode);
	
	List<ExamInfo> listCurrentExam();
}
