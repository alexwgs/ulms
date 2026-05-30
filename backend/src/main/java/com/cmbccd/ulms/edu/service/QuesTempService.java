package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.QuesTemp;

import java.util.List;

public interface QuesTempService {

	List<QuesTemp> list(String examCode);
	
	int create(QuesTemp record);
	
	int create(List<QuesTemp> list);
	
	int delete(String examCode);
	
}
