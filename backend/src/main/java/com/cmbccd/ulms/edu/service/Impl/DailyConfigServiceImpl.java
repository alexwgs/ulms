package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.DailyConfigMapper;
import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.domain.DailyConfigExample;
import com.cmbccd.ulms.edu.service.DailyConfigService;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class DailyConfigServiceImpl implements DailyConfigService {

	@Resource
	private DailyConfigMapper dailyConfigMapper;
	@Resource
	private PublicMapper publicMapper;
	@Override
	public List<DailyConfig> list(DailyConfigExample example) {
		
		return dailyConfigMapper.selectByExample(example);
	}

	@Override
	public int update(DailyConfig record) {
		
		return dailyConfigMapper.updateByPrimaryKey(record);
	}

	@Override
	public int delete(String id) {
		
		return dailyConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int create(DailyConfig record) {
		record.setId(publicMapper.selectNewJourno());
		return dailyConfigMapper.insert(record);
	}

	@Override
	public DailyConfig getDailyConfigByPloNum(String quesDate) {
		String userId = Util.userIdByShiro();
		return dailyConfigMapper.selectDailyConfigByPloNum(userId, quesDate);
	}

	@Override
	public List<DailyConfig> getDailyConfigCalendar(String dataMonth) {
		String userId = Util.userIdByShiro();
		return dailyConfigMapper.selectDailyConfigCalendar(dataMonth, userId);
	}

}
