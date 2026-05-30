package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.QuickUrlMapper;
import com.cmbccd.ulms.sys.domain.QuickUrl;
import com.cmbccd.ulms.sys.domain.QuickUrlExample;
import com.cmbccd.ulms.sys.service.QuickUrlService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class QuickUrlServiceImpl implements QuickUrlService {

	@Resource
	private QuickUrlMapper quickUrlMapper;

	@Override
	public List<QuickUrl> listQuickUrl(QuickUrlExample example) {		
		return quickUrlMapper.selectByExample(example);
	}

	@Override
	public QuickUrl getQuickUrlById(Integer id) {
		return quickUrlMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int insertQuickUrl(QuickUrl record) {
		record.setId(quickUrlMapper.getNewId());
		return quickUrlMapper.insert(record);
	}
	
	@Override
	public int updateQuickUrlById(QuickUrl record) {
		return quickUrlMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteQuickUrlById(Integer id) {
		return quickUrlMapper.deleteByPrimaryKey(id);
	}


	
}
