package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.sys.domain.QuickUrl;
import com.cmbccd.ulms.sys.domain.QuickUrlExample;

import java.util.List;
import java.util.Map;

public interface QuickUrlService {

	List<QuickUrl> listQuickUrl(QuickUrlExample example);

	DataPage<QuickUrl> listQuickUrlByAdmin(Map<String, String> params);
	
	QuickUrl getQuickUrlById(Integer id);
	
	int insertQuickUrl(QuickUrl record);
	
	int updateQuickUrlById(QuickUrl record);
	
	int deleteQuickUrlById(Integer id);
}
