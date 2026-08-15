package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.dao.CaseTaskMapper;
import com.cmbccd.ulms.oht.domain.CaseTask;
import com.cmbccd.ulms.oht.domain.CaseTaskReport;
import com.cmbccd.ulms.oht.service.CaseTaskService;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* <p>Title: CaseTaskServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class CaseTaskServiceImpl implements CaseTaskService {

	@Resource
	private CaseTaskMapper caseTaskMapper;

	@Override
	public int insertNewTask(String caseId, String userId, String buildId, int taskStatus) {
		int count = caseTaskMapper.countTaskByUserIdAndCaseId(userId, caseId);
		if (count > 0) {
			return 0;
		}
		Employee user = DataCache.getEmployees().get(userId);
		Employee buildUser = DataCache.getEmployees().get(buildId);
		CaseTask record = new CaseTask();
		record.setId(Util.getShortUuid());
		record.setCaseId(caseId);
		record.setUserId(userId);
		record.setDeptNum(user.getDeptNum());
		record.setGroupNum(user.getDeptGroup());
		record.setTaskStatus(taskStatus);
		if (user.getDeptGroup() != null && buildUser.getDeptGroup() != null
				&& user.getDeptGroup().equals(buildUser.getDeptGroup())) {
			record.setTaskLevel(1);
		} else if (user.getDeptNum().equals(buildUser.getDeptNum())) {
			record.setTaskLevel(2);
		} else {
			record.setTaskLevel(3);
		}
		record.setDataTime(Integer.parseInt(Util.getCurrentTimestamp()));
		return caseTaskMapper.insertSelective(record);
	}

	@Override
	public int updateTaskCompleteByUserIdAndCaseId(String userId, String caseId) {
		if (Util.isNullorEmpty(userId) || Util.isNullorEmpty(caseId)) {
			return 0;
		}
		return caseTaskMapper.updateTaskCompleteByUserIdAndCaseId(userId, caseId);
	}

	@Override
	public int updateTaskRefuseByUserIdAndCaseId(String userId, String caseId, String memo) {
		if (Util.isNullorEmpty(userId) || Util.isNullorEmpty(caseId)) {
			return 0;
		}
		return caseTaskMapper.updateTaskRefuseByUserIdAndCaseId(userId, caseId, memo);
	}

	@Override
	public Map<String, Integer> selectTsakCountTodayByUserId(String userId) {
		List<Map<String, Object>> taskData = caseTaskMapper.selectTsakCountTodayByUserId(userId);
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("takeNum", 0);
		data.put("ignoreNum", 0);
		data.put("refuseNum", 0);
		if (taskData == null) {
		} else {
			for (Map<String, Object> item : taskData) {
				int type = ((BigDecimal) item.get("taskStatus")).intValue();
				int val = ((BigDecimal) item.get("taskCount")).intValue();
				if (type == 1) {
					data.put("takeNum", val);
				} else if (type == 0) {
					data.put("ignoreNum", val);
				} else if (type == 2) {
					data.put("refuseNum", val);
				}
			}
		}
		return data;
	}

	@Override
	public List<CaseTask> selectTaskByCaseId(String caseId) {
		List<CaseTask> list = caseTaskMapper.selectTsakByCaseId(caseId);
		for (CaseTask item : list) {
			Employee emp = DataCache.getEmployees().get(item.getUserId());
			item.setUserId(emp.getPloName() + "/" + item.getUserId());
			item.setDeptNum(emp.getDeptName());
			item.setGroupNum(emp.getGroupName());
		}
		return list;
	}

	@Override
	public List<CaseTaskReport> selectTsaksByDate(String begDate, String endDate) {
		List<CaseTaskReport> list = caseTaskMapper.selectTsaksByDate(begDate, endDate);
		for (CaseTaskReport item : list) {
			Employee emp = DataCache.getEmployees().get(item.getUserId());
			item.setUserName(emp.getPloName());
			item.setDeptNum(emp.getDeptName());
			item.setGroupNum(emp.getGroupName());
		}
		return list;
	}

}
