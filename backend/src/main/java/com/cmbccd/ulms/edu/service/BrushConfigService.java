package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.BrushConfigExample;

import java.util.List;

public interface BrushConfigService {

	List<BrushConfig> list(BrushConfigExample example);
	
	BrushConfig get(String brushCode);
	
	int create(BrushConfig record);
	
	int delete(String brushCode);
	
	int update(BrushConfig record);
	
	BrushConfig getBrushConfig(String ploNum);
}
