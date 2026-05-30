package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.Duty;

import java.util.List;


public interface DutyService {

	List<Duty> getDutyByPloNum(String date, String ploNum);
	
}
