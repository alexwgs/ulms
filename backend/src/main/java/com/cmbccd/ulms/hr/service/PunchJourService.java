package com.cmbccd.ulms.hr.service;

import com.cmbccd.ulms.hr.domain.PunchJour;
import com.cmbccd.ulms.hr.domain.PunchJourExample;

import java.util.List;

public interface PunchJourService {

	List<PunchJour> list(PunchJourExample example);
	
	PunchJour get(String journo);
	
	int create(PunchJour record);
	
	int update (PunchJour record);
	
	int delete(String journo);
}
