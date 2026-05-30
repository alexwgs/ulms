package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.AreaConfigMapper;
import com.cmbccd.ulms.edu.domain.AreaConfig;
import com.cmbccd.ulms.edu.domain.AreaConfigExample;
import com.cmbccd.ulms.edu.service.AreaConfigService;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class AreaConfigServiceImpl implements AreaConfigService {

	@Resource
	private AreaConfigMapper areaConfigMapper;
	
	@Resource
	private PublicMapper publicMapper;
	
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
		record.setAreaCode(publicMapper.selectNewJourno());
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
