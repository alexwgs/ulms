package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.BrushScoreMapper;
import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.BrushScore;
import com.cmbccd.ulms.edu.domain.BrushScoreExample;
import com.cmbccd.ulms.edu.domain.QuesBank;
import com.cmbccd.ulms.edu.domain.report.BrushScoreDept;
import com.cmbccd.ulms.edu.domain.report.BrushScoreGroup;
import com.cmbccd.ulms.edu.domain.report.BrushScoreHum;
import com.cmbccd.ulms.edu.domain.report.CategoryInfo;
import com.cmbccd.ulms.edu.service.BrushConfigService;
import com.cmbccd.ulms.edu.service.BrushScoreService;
import com.cmbccd.ulms.edu.service.QuesBankService;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import com.cmbccd.ulms.sys.domain.Department;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BrushScoreServiceImpl implements BrushScoreService{

	@Resource
	private BrushScoreMapper brushScoreMapper;

	@Resource
	private BrushConfigService brushConfigService;

	@Resource
	private QuesBankService quesBankService;
	
	@Resource
	private PublicMapper publicMapper;
	
	@Override
	public List<BrushScore> list(BrushScoreExample example) {
		return brushScoreMapper.selectByExample(example);
	}

	@Override
	public int delete(String journo) {
		
		return brushScoreMapper.deleteByPrimaryKey(journo);
	}

	@Override
	public int update(BrushScore record) {
		
		return brushScoreMapper.updateByPrimaryKey(record);
	}

	@Override
	public int create(BrushScore record) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return 0;
		}
		String dateTime = Util.currentDateTime();
		record.setJourno(publicMapper.selectNewJourno());
		Employee user = DataCache.EMPLOYEE.get(userId);
		record.setPloNum(userId);
		record.setPassFlag(0);
		record.setPloName(user.getPloName());
		record.setDeptNum(user.getDeptNum());
		record.setDeptGroup(user.getDeptGroup());
		record.setDataDate(dateTime.substring(0, 10));
		record.setDataTime(dateTime.substring(11));
		return brushScoreMapper.insert(record);
	}

	@Override
	public BrushScore get(String journo) {
		return brushScoreMapper.selectByPrimaryKey(journo);
	}

	@Override
	public int dayBrushCount(String dataDate, String ploNum) {
		if(Util.isNullorEmpty(dataDate))	dataDate=Util.getDateToday();
		return brushScoreMapper.dayBrushCount(dataDate, ploNum);
	}

	@Override
	public int monthBrushCount(String month, String ploNum) {
		return brushScoreMapper.monthBrushCount(month, ploNum);
	}

	@Override
	public BrushScore createBrushScore(BrushScore record) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return null;
		}
		String dateTime = Util.currentDateTime();
		record.setJourno(publicMapper.selectNewJourno());
		Employee user = DataCache.EMPLOYEE.get(userId);
		record.setPloNum(userId);
		record.setPassFlag(0);
		record.setPloName(user.getPloName());
		record.setDeptNum(user.getDeptNum());
		record.setDeptGroup(user.getDeptGroup());
		record.setDataDate(dateTime.substring(0, 10));
		record.setDataTime(dateTime.substring(11));
		brushScoreMapper.insert(record);
		return record;
	}

	@Override
	public BrushScore submitAnswer(BrushScore record) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) return null;
		QuesBank question = quesBankService.get(record.getQuesCode());
		String dateTime = Util.currentDateTime();
		record.setAnswerDate(dateTime.substring(0, 10));
		record.setAnswerTime(dateTime.substring(11));
		record.setQuestion(question);
		record.setPassFlag(quesBankService.checkUserAnswer(question, record.getAnswer()));
		int count = brushScoreMapper.updateByPrimaryKeySelective(record);
		if(count < 1) return null;
		return record;
	}

	private static final java.util.Set<String> ALLOWED_ORDER_COLUMNS = new java.util.HashSet<>(
			java.util.Arrays.asList("right_num", "right_rate", "total", "plo_num", "plo_name", "dept_num", "dept_group"));

	@Override
	public List<BrushScoreHum> reportBrushScoreHum(Map<String, String> params) {
		String begDate = params.get("begDate");
		String endDate = params.get("endDate");
		if(Util.isNullorEmpty(begDate) || Util.isNullorEmpty(endDate) ) {
			endDate = Util.getDateToday();
			begDate = Util.getPastDate(7);
		}
		String orderBy = "right_num desc,right_rate desc,total desc";
		if (!Util.isNullorEmpty(params.get("orderBy"))) {
			String col = Util.camel4underline(params.get("orderBy")).trim();
			if (ALLOWED_ORDER_COLUMNS.contains(col)) {
				String dir = "desc";
				if (!Util.isNullorEmpty(params.get("orderType")) && "asc".equalsIgnoreCase(params.get("orderType").trim())) {
					dir = "asc";
				}
				orderBy = col + " " + dir;
			}
		}

		List<BrushScoreHum> list = brushScoreMapper.reportBrushScoreHum(begDate, endDate, orderBy);
		for (BrushScoreHum record : list) {
			Employee user = DataCache.EMPLOYEE.get(record.getPloNum());
			if(Util.isNullorEmpty(user)) {
				record.setPloName("-");
				record.setDeptNum("-");
				record.setDeptGroup("-");
				record.setBatchGroup("-");
			}else {
				record.setPloName(user.getPloName());
				record.setDeptNum(user.getDeptName());
				record.setDeptGroup(user.getGroupName());
				record.setBatchGroup(user.getBatchGroup());
			}
		}
		return list;
	}

	@Override
	public List<BrushScoreGroup> reportBrushScoreGroup(Map<String, String> params) {
		String begDate = params.get("begDate");
		String endDate = params.get("endDate");
		if(Util.isNullorEmpty(begDate) || Util.isNullorEmpty(endDate) ) {
			endDate = Util.getDateToday();
			begDate = Util.getPastDate(7);
		}
		String orderBy = "right_num desc,right_rate desc,total desc";
		if (!Util.isNullorEmpty(params.get("orderBy"))) {
			String col = Util.camel4underline(params.get("orderBy")).trim();
			if (ALLOWED_ORDER_COLUMNS.contains(col)) {
				String dir = "desc";
				if (!Util.isNullorEmpty(params.get("orderType")) && "asc".equalsIgnoreCase(params.get("orderType").trim())) {
					dir = "asc";
				}
				orderBy = col + " " + dir;
			}
		}
		List<BrushScoreGroup> list = brushScoreMapper.reportBrushScoreGroup(begDate, endDate, orderBy);
		for (BrushScoreGroup record :list) {
			String dept_name = Optional.ofNullable(DataCache.DEPARTMENT.get(record.getDeptNum())).map(Department::getDeptName).orElse("-");
			String group_name = Optional.ofNullable(DataCache.DEPARTMENT.get(record.getDeptGroup())).map(Department::getDeptName).orElse("-");
			record.setDeptNum(dept_name);
			record.setDeptGroup(group_name);
		}
		return list;
	}
	
	@Override
	public List<BrushScoreDept> reportBrushScoreDept(Map<String, String> params) {
		String begDate = params.get("begDate");
		String endDate = params.get("endDate");
		String orderBy = "right_num desc,right_rate desc,total desc";
		if (!Util.isNullorEmpty(params.get("orderBy"))) {
			String col = Util.camel4underline(params.get("orderBy")).trim();
			if (ALLOWED_ORDER_COLUMNS.contains(col)) {
				String dir = "desc";
				if (!Util.isNullorEmpty(params.get("orderType")) && "asc".equalsIgnoreCase(params.get("orderType").trim())) {
					dir = "asc";
				}
				orderBy = col + " " + dir;
			}
		}
		List<BrushScoreDept> list = brushScoreMapper.reportBrushScoreDept(begDate, endDate, orderBy);
		for (BrushScoreDept record :list) {
			String dept_name = Optional.ofNullable(DataCache.DEPARTMENT.get(record.getDeptNum())).map(Department::getDeptName).orElse("-");
			record.setDeptNum(dept_name);
		}
		return list;
	}
	
	@Override
	public List<CategoryInfo> reportCategoryInfo(Map<String, String> params) {
		String begDate = params.get("begDate");
		String endDate = params.get("endDate");
        return brushScoreMapper.reportCategoryInfo(begDate, endDate);
	}	
	
	@Override
	public List<BrushScore> listWrongQuestion(String userId) {
		BrushConfig brushConfig = brushConfigService.getBrushConfig(userId);
		if(Util.isNullorEmpty(brushConfig)) return null;
		List<BrushScore> list = brushScoreMapper.selectWrongQuestion(userId, brushConfig.getWrongDay()==0?null:brushConfig.getWrongDay(), brushConfig.getWrongLimit());
		for(BrushScore record: list) {
			record.setQuestion(quesBankService.get(record.getQuesCode()));
		}
		return list;
	}

	@Override
	public List<BrushScore> centerWrongMostQuestion() {
		return brushScoreMapper.centerWrongMostQuestion();
	}





}
