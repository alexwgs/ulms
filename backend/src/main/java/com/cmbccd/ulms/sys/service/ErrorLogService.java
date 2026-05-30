package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.ErrorLog;
import com.cmbccd.ulms.sys.domain.ErrorLogExample;

import java.util.List;

public interface ErrorLogService {

	public int getNewId();
	
	public List<ErrorLog> getErrorLogListByExample(ErrorLogExample example);
	
	public int deleteErrorLogById(int id);
	
	public int insertNewErrorLog(ErrorLog record);
} 
