package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.QuickUrl;
import com.cmbccd.ulms.sys.domain.QuickUrlExample;

import java.util.List;

public interface QuickUrlService {

	List<QuickUrl> listQuickUrl(QuickUrlExample example);
	
	QuickUrl getQuickUrlById(Integer id);
	
	int insertQuickUrl(QuickUrl record);
	
	int updateQuickUrlById(QuickUrl record);
	
	int deleteQuickUrlById(Integer id);
}
