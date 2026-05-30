package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.dao.StatusJourMapper;
import com.cmbccd.ulms.oht.domain.StatusJour;
import com.cmbccd.ulms.oht.domain.StatusJourExample;
import com.cmbccd.ulms.oht.domain.StatusJourExample.Criteria;
import com.cmbccd.ulms.oht.service.StatusJourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
* <p>Title: StatusJourServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class StatusJourServiceImpl implements StatusJourService {

	private static final Logger log = LoggerFactory.getLogger(StatusJourServiceImpl.class);

	@Resource
	private StatusJourMapper statusJourMapper;

	@Override
	public StatusJour getUserLatestStatusJour(String date, String userId) {
		StatusJourExample example = new StatusJourExample();
		Criteria criteria = example.createCriteria();
		criteria.andDataDateEqualTo(date);
		criteria.andUserIdEqualTo(userId);
		example.setOrderByClause(" id desc");
		List<StatusJour> jours = statusJourMapper.selectByExample(example);
		if (jours.isEmpty()) {
			return null;
		}
		return jours.get(0);
	}

	@Override
	public int selectNewId() {
		return statusJourMapper.selectNewId();
	}

	@Override
	public int insertNewStatusJour(StatusJour record) {
		// 若有前次状态则需结算上次状态,首先查看是否存在
		if (getCrossDayRecord(record.getUserId())) {
			record.setBegTime("00:00:00");
		}
		statusJourMapper.updateLastStatusJour(record.getUserId(), record.getBegTime());

		record.setId(statusJourMapper.selectNewId());
		int count = statusJourMapper.insertSelective(record);
		return count;
	}

	@Override
	public int updateLastStatusJour(String userId, String endTime) {
		return statusJourMapper.updateLastStatusJour(userId, endTime);
	}

	@Override
	public List<StatusJour> selectStatusJourList(String userId, String dataDate) {
		StatusJourExample example = new StatusJourExample();
		Criteria criteria = example.createCriteria();
		criteria.andDataDateEqualTo(dataDate);
		criteria.andUserIdEqualTo(userId);
		example.setOrderByClause(" id ");
		return statusJourMapper.selectByExample(example);
	}

	@Override
	public List<StatusJour> getUserStatusTotal(String userId, String begDate, String endDate) {
		return statusJourMapper.getUserStatusTotal(userId, begDate, endDate);
	}

	@Override
	public int getUserStatusIdTotal(String userId, String begDate, String endDate, Integer statusId) {
		StatusJourExample example = new StatusJourExample();
		Criteria criteria = example.createCriteria();
		criteria.andDataDateBetween(begDate, endDate);
		criteria.andStatusIdEqualTo(statusId);
		criteria.andUserIdEqualTo(userId);
		int count = statusJourMapper.countByExample(example);
		if (count == 0) {
			return 0;
		}
		return statusJourMapper.getUserStatusIdTotal(userId, begDate, endDate, statusId);
	}

	@Override
	public List<Map<String, String>> getStatusDetailByUserId(String userId, String dataDate) {
		return statusJourMapper.getStatusDetailByUserId(userId, dataDate);
	}

	@Override
	public List<Map<String, String>> getStatusDetailByUserIds(String[] userId, String dataDate) {
		StatusJourExample example = new StatusJourExample();
		Criteria criteria = example.createCriteria();
		criteria.andDataDateEqualTo(dataDate);
		List<String> userList = new ArrayList<>(Arrays.asList(userId));
		criteria.andUserIdIn(userList);
		example.setOrderByClause(" id ");
		return statusJourMapper.getStatusDetailByUserIds(example);
	}

	public boolean getCrossDayRecord(String userId) {
		StatusJourExample example = new StatusJourExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		String yesterday = Util.getPastDate(1);
		criteria.andDataDateEqualTo(yesterday);
		criteria.andEndTimeIsNull();
		example.setOrderByClause(" id desc");
		List<StatusJour> records = statusJourMapper.selectByExample(example);

		if (records == null || records.size() == 0) {
			return false;
		}
		StatusJour record = records.get(0);
		record.setEndTime("23:59:59");
		try {
			record.setDuration((int) Util.diffDateTime(yesterday + " " + record.getBegTime(), yesterday + " 23:59:59"));
		} catch (ParseException e) {
			record.setDuration(0);
			log.warn("计算duration失败", e);
		}
		statusJourMapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public int getAvailableStatusTimeByUserId(String userId, String begDate, String endDate) {
		return statusJourMapper.getAvailableStatusTimeByUserId(userId, begDate, endDate);
	}
}
