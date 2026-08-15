package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.sys.domain.ErrorLog;
import com.cmbccd.ulms.sys.domain.ErrorLogExample;

import java.util.List;
import java.util.Map;

public interface ErrorLogService {

	public int getNewId();
	
	public List<ErrorLog> getErrorLogListByExample(ErrorLogExample example);

	public DataPage<ErrorLog> getErrorLogListByQuery(Map<String, String> params);
	
	public int deleteErrorLogById(int id);
	
	public int insertNewErrorLog(ErrorLog record);
} 
