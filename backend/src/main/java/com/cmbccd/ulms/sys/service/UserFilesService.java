package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.sys.domain.UserFiles;
import com.cmbccd.ulms.sys.domain.UserFilesExample;

import java.util.List;
import java.util.Map;

public interface UserFilesService {
	
	int selectNewId();
	
	List<UserFiles> getUserFilesList(UserFilesExample example);

	DataPage<UserFiles> getUserFilesListByQuery(Map<String, String> params);
	
	int insertNewUserFiles(UserFiles record);
	
	int deleteUserFileById(int id);
	
	List<String> getFileSuffix();
}
