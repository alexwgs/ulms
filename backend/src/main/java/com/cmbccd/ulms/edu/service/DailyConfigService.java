package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.domain.DailyConfigExample;

import java.util.List;
import java.util.Map;

public interface DailyConfigService {

	List<DailyConfig> list(DailyConfigExample example);

	DataPage<DailyConfig> listDailyConfig(Map<String, String> params);
	
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
