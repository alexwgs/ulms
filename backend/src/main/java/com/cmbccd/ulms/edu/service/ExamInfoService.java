package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.ExamInfoExample;
import com.cmbccd.ulms.edu.domain.QuesTemp;

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

	/**
	 * 重建考试抽题配置（先删后建并更新题数，事务在 Service 层）
	 */
	int createExamTemp(String examCode, List<QuesTemp> list);
}
