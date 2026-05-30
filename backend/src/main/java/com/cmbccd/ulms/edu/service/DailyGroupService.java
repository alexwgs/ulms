package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.DailyGroup;
import com.cmbccd.ulms.edu.domain.DailyGroupExample;

import java.util.List;

public interface DailyGroupService {

	List<DailyGroup> list(DailyGroupExample example);
	
	int delete(String ploNum);
	
	int update(DailyGroup record);
	
	int create(DailyGroup record);
	
	DailyGroup get(String ploNum);
	
    int batchInsert(List<DailyGroup> list);
    
    int deleteAll();
}
