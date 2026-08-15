package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.DailyConfigMapper;
import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.domain.DailyConfigExample;
import com.cmbccd.ulms.edu.service.DailyConfigService;
import com.cmbccd.ulms.sys.service.PublicService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DailyConfigServiceImpl implements DailyConfigService {

	@Resource
	private DailyConfigMapper dailyConfigMapper;
	@Resource
	private PublicService publicService;
	@Override
	public List<DailyConfig> list(DailyConfigExample example) {
		
		return dailyConfigMapper.selectByExample(example);
	}

	@Override
	public DataPage<DailyConfig> listDailyConfig(Map<String, String> params) {
		DailyConfigExample example = new DailyConfigExample();
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<DailyConfig> list = dailyConfigMapper.selectByExample(example);
		return new DataPage<DailyConfig>(list);
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
		record.setId(publicService.getJourno());
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
