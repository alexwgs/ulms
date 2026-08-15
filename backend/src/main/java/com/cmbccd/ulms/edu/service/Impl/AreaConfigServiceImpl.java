package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.AreaConfigMapper;
import com.cmbccd.ulms.edu.domain.AreaConfig;
import com.cmbccd.ulms.edu.domain.AreaConfigExample;
import com.cmbccd.ulms.edu.service.AreaConfigService;
import com.cmbccd.ulms.sys.service.PublicService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class AreaConfigServiceImpl implements AreaConfigService {

	@Resource
	private AreaConfigMapper areaConfigMapper;
	
	@Resource
	private PublicService publicService;
	
	@Override
	public List<AreaConfig> listAreaConfig(AreaConfigExample example) {
		return areaConfigMapper.selectByExample(example);
	}

	@Override
	public int create(AreaConfig record) {
		String userId = Util.userIdByShiro();
		if(Util.isNullorEmpty(userId)) return 0;
		record.setHandlePlo(userId);
		record.setHandleDate(Util.currentDateTime());
		record.setAreaCode(publicService.getJourno());
		return areaConfigMapper.insertSelective(record);
	}

	@Override
	public int update(AreaConfig record) {
		String userId = Util.userIdByShiro();
		if(Util.isNullorEmpty(userId)) return 0;
		record.setHandlePlo(userId);
		record.setHandleDate(Util.currentDateTime());
		return areaConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(String areaCode) {
		
		return areaConfigMapper.deleteByPrimaryKey(areaCode);
	}
	
	public AreaConfig get(String areaCode) {
		if(Util.isNullorEmpty(areaCode)) return null;
		return areaConfigMapper.selectByPrimaryKey(areaCode);
	}
	
}
