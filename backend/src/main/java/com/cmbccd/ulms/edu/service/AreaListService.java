package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.AreaList;
import com.cmbccd.ulms.edu.domain.AreaListExample;

import java.util.List;

public interface AreaListService {
	
	 List<AreaList> listAreaList(AreaListExample example);

	int create(AreaList record);
	
	int update(AreaList record);
	
	int delete(String journo);
	
	boolean checkArea(String areaCode, String ip);
}
