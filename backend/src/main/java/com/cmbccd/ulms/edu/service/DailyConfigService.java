package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.domain.DailyConfigExample;

import java.util.List;

public interface DailyConfigService {

	List<DailyConfig> list(DailyConfigExample example);
	
	int update(DailyConfig record);
	
	int delete(String id);
	
	int create(DailyConfig record);

	DailyConfig getDailyConfigByPloNum(String quesDate);

	List<DailyConfig> getDailyConfigCalendar(String dataMonth);

//
//	Integer dailyQuseCompleteFlag();
//
//	int createDailyScore(String quesCode);
//
//	int submitDailyScore(int flag, String userAnswer,String quesCode);
}
