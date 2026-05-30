package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.QuesCollect;

import java.util.List;

public interface QuesCollectService {

	int collect(String quesCode);
	
	int update(QuesCollect record);
	
	int uncollect(String journo);
	
	int delete(String journo);
	
	List<QuesCollect> listMyCollect();
	
}
