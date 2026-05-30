package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.edu.dao.DailyGroupMapper;
import com.cmbccd.ulms.edu.domain.DailyGroup;
import com.cmbccd.ulms.edu.domain.DailyGroupExample;
import com.cmbccd.ulms.edu.service.DailyGroupService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class DailyGroupServiceImpl implements DailyGroupService {

	@Resource
	private DailyGroupMapper dailyGroupMapper;

	@Override
	public List<DailyGroup> list(DailyGroupExample example) {
		return dailyGroupMapper.selectByExample(example);
	}

	@Override
	public int delete(String ploNum) {
		
		return dailyGroupMapper.deleteByPrimaryKey(ploNum);
	}

	@Override
	public int update(DailyGroup record) {
		
		return dailyGroupMapper.updateByPrimaryKey(record);
	}

	@Override
	public int create(DailyGroup record) {
		
		return dailyGroupMapper.insert(record);
	}

	@Override
	public DailyGroup get(String ploNum) {
		return dailyGroupMapper.selectByPrimaryKey(ploNum);
	}

	@Override
	public int batchInsert(List<DailyGroup> list) {
		return  dailyGroupMapper.batchInsert(list);
	}

	@Override
	public int deleteAll() {
		
		return dailyGroupMapper.deleteAll();
	}
	
	
	
}
