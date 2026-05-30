package com.cmbccd.ulms.oht.service;

import com.cmbccd.ulms.oht.domain.CaseTask;
import com.cmbccd.ulms.oht.domain.CaseTaskReport;

import java.util.List;
import java.util.Map;

public interface CaseTaskService {
	
	int insertNewTask(String caseId, String userId, String buildId, int taskStatus);
	
    int updateTaskCompleteByUserIdAndCaseId(String userId,String caseId);
    
    int updateTaskRefuseByUserIdAndCaseId(String userId,String caseId, String memo);
    
    Map<String, Integer> selectTsakCountTodayByUserId(String userId);
    
    List<CaseTask> selectTaskByCaseId(String caseId);
    
    List<CaseTaskReport> selectTsaksByDate(String begDate,String endDate);
}
