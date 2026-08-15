/**
 * 
 */
package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.dao.CaseMapper;
import com.cmbccd.ulms.oht.dao.StatusJourMapper;
import com.cmbccd.ulms.oht.domain.*;
import com.cmbccd.ulms.oht.domain.CaseExample.Criteria;
import com.cmbccd.ulms.oht.service.CaseService;
import com.cmbccd.ulms.sys.service.DictionaryService;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author WeiGenSheng
 * @Time 2020年5月7日 下午4:32:25
 * @Version 1.0
 * @Description:
 */
@Service
public class CaseServiceImpl implements CaseService {

//	Map<String, Map<String, String>> DICT = DataCache.DICT;

	@Resource
	private CaseMapper caseMapper;

	@Resource
	private StatusJourMapper statusJourMapper;

	@Resource
	private DictionaryService dictionaryService;

	@Override
	public List<Case> getCaseList(CaseExample example) {
		return caseMapper.selectByExample(example);
	}

	@Override
	public Case getUnfinishCaseByHelper(String userId) {
		List<Integer> caseSataus = new ArrayList<Integer>();
		caseSataus.add(0);
		caseSataus.add(2);
		caseSataus.add(3);

		CaseExample example = new CaseExample();
		Criteria criteria = example.createCriteria();
		criteria.andBcompTypeIsNull();
		criteria.andCaseStatusIn(caseSataus);
		criteria.andBuildIdEqualTo(userId);
		Case record = null;
		List<Case> cases = caseMapper.selectByExample(example);
		if (cases.size() > 0) {
			record = cases.get(0);
			if (record.getPickId() != null) {
				record.setPickUser(DataCache.EMPLOYEE.get(record.getPickId()));
			}
		}
		return record;
	}

	@Override
	public Case getUnfinishCaseByListener(String userId) {
		List<Integer> caseSataus = new ArrayList<Integer>();
		caseSataus.add(2);
		caseSataus.add(3);

		CaseExample example = new CaseExample();
		Criteria criteria = example.createCriteria();
		criteria.andPcompTypeIsNull();
		criteria.andCaseStatusIn(caseSataus);
		criteria.andPickIdEqualTo(userId);
		Case record = null;
		List<Case> cases = caseMapper.selectByExample(example);
		if (cases.size() > 0) {
			record = cases.get(0);
			if (record.getBuildId() != null) {
				record.setBuildUser(DataCache.EMPLOYEE.get(record.getBuildId()));
			}
		}
		return record;
	}

	@Override
	public Case insertNewCase(String userId, Integer caseType, String extnNum) {
		Case record = new Case();
		Employee user = DataCache.EMPLOYEE.get(userId);
		record.setCaseId(Util.getShortUuid());
		record.setDataTime(Util.currentDateTime());
		record.setBuildId(userId);
		record.setBuildDept(user.getDeptNum());
		record.setBuildGroup(user.getDeptGroup());
		record.setBuildTime(Integer.parseInt(Util.getCurrentTimestamp()));
		record.setBuildExtn(extnNum);
		record.setCaseType(caseType);
		record.setCaseStatus(0);
		int count = caseMapper.insert(record);
		if (count > 0) {
			return record;
		}
		return null;
	}

	@Override
	public Case getCaseByCaseId(String caseId) {
		return caseMapper.selectByPrimaryKey(caseId);
	}

	@Override
	public int updateCaseBySelective(Case record) {
		return caseMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Case> getHelpWaitCase() {
		CaseExample example = new CaseExample();
		Criteria criteria = example.createCriteria();
		criteria.andCaseStatusEqualTo(0);
		return caseMapper.selectByExample(example);
	}

	@Override
	public int updateDisconnectCaseStatus(String buildId) {
		return caseMapper.updateDisconnectCaseStatus(buildId);
	}

	@Override
	public List<DirTakeCaseSumarry> getCaseCompleteCountByDir(String begDate, String endDate) {
		List<DirTakeCaseSumarry> list = new ArrayList<>();
		// 从前使用的是有接单的人员名单，改为根据岗位获取人员名单
//		List<String> pickIds = caseMapper.selectPickIdByDateTime(begDate + " 00:00:00", endDate + " 23:59:59");
		List<String> pickIds = DataCache.EMPLOYEE.values().stream().filter(e -> "101".equals(e.getJobLevel()) && "00".equals(e.getPloStatus())).map(Employee::getPloNum).collect(Collectors.toList());
		for (String pickId : pickIds) {
			DirTakeCaseSumarry pickUser = creatNewCaseCompleteUser(pickId);
			List<Map<String, Object>> level1Data = caseMapper.selectCaseCompleteCountByCaseType(begDate + " 00:00:00",
					endDate + " 23:59:59", "1", pickId);
			if (level1Data.size() > 0) {
				pickUser.setType1Level1(((BigDecimal) level1Data.get(0).get("level1")).intValue());
				pickUser.setType1Level2(((BigDecimal) level1Data.get(0).get("level2")).intValue());
				pickUser.setType1Level3(((BigDecimal) level1Data.get(0).get("level3")).intValue());
				pickUser.setType1ProcessTime(Util.retainTwoDecimal((BigDecimal) level1Data.get(0).get("takeTime")));
			}
			List<Map<String, Object>> level2Data = caseMapper.selectCaseCompleteCountByCaseType(begDate + " 00:00:00",
					endDate + " 23:59:59", "2", pickId);
			if (level2Data.size() > 0) {
				pickUser.setType2Level1(((BigDecimal) level2Data.get(0).get("level1")).intValue());
				pickUser.setType2Level2(((BigDecimal) level2Data.get(0).get("level2")).intValue());
				pickUser.setType2Level3(((BigDecimal) level2Data.get(0).get("level3")).intValue());
				pickUser.setType2ProcessTime(Util.retainTwoDecimal((BigDecimal) level2Data.get(0).get("takeTime")));
			}
			Integer avaliableTime = 0, onCallTime = 0, leaveTime = 0;
			avaliableTime = statusJourMapper.getStatusDurationByOhtFlag(1, pickId, begDate, endDate);
			onCallTime = statusJourMapper.getOnCallDurationByOhtFlag(pickId, begDate, endDate);
			leaveTime = statusJourMapper.getStatusDurationByOhtFlag(0, pickId, begDate, endDate);
			// 写入当前可接单时间
			if (Util.isNullorEmpty(avaliableTime)) {
				pickUser.setAvaliableTime(0.0);
			} else {
				pickUser.setAvaliableTime(Double.valueOf(String.format("%.2f", avaliableTime / 3600.0)));
			}
			if (Util.isNullorEmpty(onCallTime)) {
				pickUser.setOnCallTime(0.0);
			} else {
				pickUser.setOnCallTime(Double.valueOf(String.format("%.2f", onCallTime / 3600.0)));
			}
			if (Util.isNullorEmpty(leaveTime)) {
				pickUser.setLeaveTime(0.0);
			} else {
				pickUser.setLeaveTime(Double.valueOf(String.format("%.2f", leaveTime / 3600.0)));
			}

			list.add(pickUser);
		}
		return list;
	}

	public DirTakeCaseSumarry creatNewCaseCompleteUser(String userId) {
		DirTakeCaseSumarry dir = new DirTakeCaseSumarry();
		Employee user = DataCache.EMPLOYEE.get(userId);
		dir.setUserId(userId);
		dir.setUserName(user.getPloName());
		dir.setDeptName(user.getDeptName());
		dir.setGroupName(user.getGroupName() == null ? "" : user.getGroupName());
		dir.setType1Level1(0);
		dir.setType1Level2(0);
		dir.setType1Level3(0);
		dir.setType1ProcessTime(0.0);
		dir.setType2Level1(0);
		dir.setType2Level2(0);
		dir.setType2Level3(0);
		dir.setType2ProcessTime(0.0);
		return dir;
	}

	public DeptTakeCaseHour creatNewCaseCompleteCenter() {
		DeptTakeCaseHour dept = new DeptTakeCaseHour();
		dept.setDataDate("");
		dept.setHour(0);
		dept.setType1CaseNum(0);
		dept.setType1Level1(0);
		dept.setType1Level2(0);
		dept.setType1Level3(0);
		dept.setType1WaitTime(0.0);
		dept.setType2CaseNum(0);
		dept.setType2Level1(0);
		dept.setType2Level2(0);
		dept.setType2Level3(0);
		dept.setType2WaitTime(0.0);
		return dept;
	}

	@Override
	public List<DeptTakeCaseHour> getStartCaseCenterCountByDept(String begDate, String endDate) {
		List<DeptTakeCaseHour> list = new ArrayList<>();
		String dataDate = "";
		if (begDate.substring(0, 10).equals(endDate.substring(0, 10))) {
			dataDate = begDate.substring(0, 10);
		} else {
			dataDate = begDate.substring(0, 10) + "~" + endDate.substring(0, 10);
		}

		for (int i = 0; i < 24; i++) {
			DeptTakeCaseHour data = creatNewCaseCompleteCenter();
			data.setHour(i);
			data.setDataDate(dataDate);
			list.add(data);
		}

		List<Map<String, Object>> level1Data = caseMapper.selectStartCaseCenterByCaseType(begDate + " 00:00:00",
				endDate + " 23:59:59", "1");
		for (Map<String, Object> item : level1Data) {
			int index = ((BigDecimal) item.get("hour")).intValue();
			list.get(index).setType1Level1(((BigDecimal) item.get("level1")).intValue());
			list.get(index).setType1Level2(((BigDecimal) item.get("level2")).intValue());
			list.get(index).setType1Level3(((BigDecimal) item.get("level3")).intValue());
			list.get(index).setType1CaseNum(((BigDecimal) item.get("holdNum")).intValue());
			list.get(index).setType1WaitTime(Util.retainTwoDecimal((BigDecimal) item.get("waitTime")));
		}

		List<Map<String, Object>> level2Data = caseMapper.selectStartCaseCenterByCaseType(begDate + " 00:00:00",
				endDate + " 23:59:59", "2");
		for (Map<String, Object> item : level2Data) {
			int index = ((BigDecimal) item.get("hour")).intValue();
			list.get(index).setType2Level1(((BigDecimal) item.get("level1")).intValue());
			list.get(index).setType2Level2(((BigDecimal) item.get("level2")).intValue());
			list.get(index).setType2Level3(((BigDecimal) item.get("level3")).intValue());
			list.get(index).setType2CaseNum(((BigDecimal) item.get("holdNum")).intValue());
			list.get(index).setType2WaitTime(Util.retainTwoDecimal((BigDecimal) item.get("waitTime")));
		}
		return list;
	}

	@Override
	public List<CaseReport> getCaseReport(CaseExample example) {
		List<CaseReport> cases = caseMapper.selectByExampleReport(example);
		Map<String, Dictionary> status = dictionaryService.getDictionaryMapByName("oht_case_status");
		Map<String, Dictionary> type = dictionaryService.getDictionaryMapByName("oht_case_type");
		Map<String, Dictionary> bcompType = dictionaryService.getDictionaryMapByName("oht_case_bcompType");
		Map<String, Dictionary> pcompType = dictionaryService.getDictionaryMapByName("oht_case_pcompType");

		for (CaseReport item : cases) {
			if (Util.isNullorEmpty(item.getPickTime())) {
				item.setPickTime("");
			} else {
				item.setPickTime(Util.timeStampToDate(item.getPickTime(), null));
			}

			if (Util.isNullorEmpty(item.getBuildId())) {
				item.setBuildName("");
				item.setBuildDept("");
				item.setBuildGroup("");
			} else {
				Employee emp = DataCache.EMPLOYEE.get(item.getBuildId());
				item.setBuildName(emp.getPloName());
				item.setBuildDept(emp.getDeptName());
				item.setBuildGroup(emp.getGroupName());
			}

			if (Util.isNullorEmpty(item.getPickId())) {
				item.setPickName("");
				item.setPickDept("");
				item.setPickGroup("");
			} else {
				Employee emp = DataCache.EMPLOYEE.get(item.getPickId());
				item.setPickName(emp.getPloName());
				item.setPickDept(emp.getDeptName());
				item.setPickGroup(emp.getGroupName());
			}

			if (Util.isNullorEmpty(item.getCaseStatus())) {
				item.setStatusName("");
			} else {
				Dictionary caseStatus = status.get(item.getCaseStatus().toString());
				if (Util.isNullorEmpty(caseStatus)) {
					item.setStatusName("");
				} else {
					item.setStatusName(caseStatus.getCodeval());
				}
			}

			if (Util.isNullorEmpty(item.getCaseType())) {
				item.setTypeName("");
			} else {
				Dictionary caseType = type.get(item.getCaseType().toString());
				if (Util.isNullorEmpty(caseType)) {
					item.setTypeName("");
				} else {
					item.setTypeName(caseType.getCodeval());
				}
			}

			if (Util.isNullorEmpty(item.getBcompType())) {
				item.setBcompName("");
			} else {
				Dictionary bcomp = bcompType.get(item.getBcompType().toString());
				if (Util.isNullorEmpty(bcomp)) {
					item.setBcompName("");
				} else {
					item.setBcompName(bcomp.getCodeval());
				}
			}

			if (Util.isNullorEmpty(item.getPcompType())) {
				item.setPcompName("");
			} else {
				Dictionary pcomp = pcompType.get(item.getPcompType().toString());
				if (Util.isNullorEmpty(pcomp)) {
					item.setPcompName("");
				} else {
					item.setPcompName(pcomp.getCodeval());
				}
			}
		}
		return cases;
	}

}
