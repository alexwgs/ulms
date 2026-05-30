package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.edu.dao.BrushConfigMapper;
import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.BrushConfigExample;
import com.cmbccd.ulms.edu.service.BrushConfigService;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class BrushConfigServiceImpl implements BrushConfigService {

	@Resource
	private BrushConfigMapper brushConfigMapper;
	
	@Resource
	private PublicMapper publicMapper;
	
	@Override
	public List<BrushConfig> list(BrushConfigExample example) {
		return brushConfigMapper.selectByExample(example);
	}

	@Override
	public int create(BrushConfig record) {
		record.setBrushCode(publicMapper.selectNewJourno());
		return brushConfigMapper.insert(record);
	}

	@Override
	public int delete(String brushCode) {
		
		return brushConfigMapper.deleteByPrimaryKey(brushCode);
	}

	@Override
	public int update(BrushConfig record) {
		
		return brushConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public BrushConfig get(String brushCode) {
		
		return brushConfigMapper.selectByPrimaryKey(brushCode);
	}

	@Override
	public BrushConfig getBrushConfig(String ploNum) {
		List<BrushConfig> list = brushConfigMapper.getBrushConfig(ploNum);
		if(list.size()>0) return list.get(0);
		return null;
	}

}
