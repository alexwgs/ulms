package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.QuesScoreMapper;
import com.cmbccd.ulms.edu.domain.QuesScore;
import com.cmbccd.ulms.edu.domain.QuesScoreExample;
import com.cmbccd.ulms.edu.domain.QuesScoreExample.Criteria;
import com.cmbccd.ulms.edu.domain.report.QuesScoreHum;
import com.cmbccd.ulms.edu.service.QuesScoreService;
import com.cmbccd.ulms.sys.service.PublicService;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class QuesScoreServiceImpl implements QuesScoreService {

	@Resource
	private QuesScoreMapper quesScoreMapper;
	
	@Resource
	private PublicService publicService;
	
	@Override
	public int create(QuesScore record) {
		Employee user = DataCache.getEmployees().get(record.getPloNum());
		if(Util.isNullorEmpty(user)) return 0;
		record.setDeptNum(user.getDeptNum());
		record.setDeptGroup(user.getDeptGroup());
		if(Util.isNullorEmpty(record.getCompStat())) record.setCompStat(0);
		record.setJourno(publicService.getJourno());
		return quesScoreMapper.insert(record);
	}

	@Override
	public int update(QuesScore record) {
		
		return quesScoreMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(String journo) {
		
		return quesScoreMapper.deleteByPrimaryKey(journo);
	}

	@Override
	public List<QuesScore> list(QuesScoreExample example) {
		
		 List<QuesScore> list = quesScoreMapper.selectByExample(example);
		 for(QuesScore item : list) {
			 item.setUser(DataCache.getEmployees().get(item.getPloNum()));
		 }
		return list;
	}

	@Override
	public QuesScore getUserQuesScore(String examCode, String userId) {
		QuesScoreExample example = new QuesScoreExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		criteria.andPloNumEqualTo(userId);
		List<QuesScore> list = quesScoreMapper.selectByExample(example);
		if(list.size()>0) return list.get(0);
		return null;
	}

	@Override
	public int updateUserScore(String examCode) {
		
		return quesScoreMapper.updateUserScore(examCode);
	}

	@Override
	public int updateCompleteStatus(String examCode) {
		
		return quesScoreMapper.completeScoreStatus(examCode);
	}

	@Override
	public List<QuesScoreHum> reportExamScoreHum(String examCode) {
		List<QuesScoreHum> list = quesScoreMapper.reportQuesScoreHum(examCode);

		for (QuesScoreHum record : list) {
			Employee user = DataCache.getEmployees().get(record.getPloNum());
			if (!Util.isNullorEmpty(user)) {
				record.setDeptNum(user.getDeptName());
				record.setDeptGroup(user.getGroupName());
				record.setBatchGroup(user.getBatchGroup());
				record.setPloName(user.getPloName());
			}
		}
		return list;
	}

	@Override
	public int deleteByExamCode(String quesCode) {
		
		return quesScoreMapper.deleteByExamCode(quesCode);
	}

	@Override
	public int resetExam(QuesScore quesScore) {
		quesScore.setUserScore((double) 0);
		quesScore.setCompStat(0);
		quesScore.setBegDate("");
		quesScore.setEndDate("");
		quesScore.setHandleIp("");
		return quesScoreMapper.updateByPrimaryKeySelective(quesScore);
	}


}
