package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.AreaConfig;
import com.cmbccd.ulms.edu.domain.AreaConfigExample;

import java.util.List;

public interface AreaConfigService {
	List<AreaConfig> listAreaConfig(AreaConfigExample example);

	AreaConfig get(String areaCode);
	
	int create(AreaConfig record);
	
 	int update(AreaConfig record);
	
	int delete(String areaCode);
}
