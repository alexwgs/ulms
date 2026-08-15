package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.BrushConfigExample;

import java.util.List;
import java.util.Map;

public interface BrushConfigService {

	List<BrushConfig> list(BrushConfigExample example);

	DataPage<BrushConfig> listBrushConfigByQuery(Map<String, String> params);
	
	BrushConfig get(String brushCode);
	
	int create(BrushConfig record);
	
	int delete(String brushCode);
	
	int update(BrushConfig record);
	
	BrushConfig getBrushConfig(String ploNum);
}
