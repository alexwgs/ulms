package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.UserFiles;
import com.cmbccd.ulms.sys.domain.UserFilesExample;

import java.util.List;

public interface UserFilesService {
	
	int selectNewId();
	
	List<UserFiles> getUserFilesList(UserFilesExample example);
	
	int insertNewUserFiles(UserFiles record);
	
	int deleteUserFileById(int id);
	
	List<String> getFileSuffix();
}
