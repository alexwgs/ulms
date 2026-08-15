package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.QuesBankMapper;
import com.cmbccd.ulms.edu.dao.QuesTestMapper;
import com.cmbccd.ulms.edu.domain.*;
import com.cmbccd.ulms.edu.domain.QuesTestExample.Criteria;
import com.cmbccd.ulms.edu.domain.report.QuesTestDetail;
import com.cmbccd.ulms.edu.service.QuesBankService;
import com.cmbccd.ulms.edu.service.QuesScoreService;
import com.cmbccd.ulms.edu.service.QuesTempService;
import com.cmbccd.ulms.edu.service.QuesTestService;
import com.cmbccd.ulms.sys.service.PublicService;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuesTestServiceImpl implements QuesTestService {

	@Resource
	private QuesTestMapper quesTestMapper;

	@Resource
	private QuesTempService quesTempService;

	@Resource
	private QuesBankService quesBankService;

	@Resource
	private QuesScoreService examScoreService;

	@Resource
	private PublicService publicService;

	@Resource
	private QuesBankMapper quesBankMapper;

	@Override
	public int create(QuesTest record) {
		record.setJourno(publicService.getJourno());
		return quesTestMapper.insert(record);
	}

	@Override
	public int delete(String journo) {
		return quesTestMapper.deleteByPrimaryKey(journo);
	}

	@Override
	public int update(QuesTest record) {
		return quesTestMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteUserTest(String examCode, String userId) {
		QuesTestExample example = new QuesTestExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		criteria.andPloNumEqualTo(userId);
		return quesTestMapper.deleteByExample(example);
	}

	@Override
	public List<QuesTest> listMyExamTest(String examCode, String userId) {
		QuesTestExample example = new QuesTestExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		criteria.andPloNumEqualTo(userId);
		example.setOrderByClause(" ques_order asc");
		List<QuesTest> list = quesTestMapper.selectByExample(example);
		for(QuesTest item : list) {
			item.setQuestion(quesBankService.get(item.getQuesCode()));
		}
		return list;
	}

	@Override
	public List<QuesTest> createMyExamTest(ExamInfo examInfo, String ip) {
		if(Util.isNullorEmpty(examInfo.getQuesNum()) || examInfo.getQuesNum() == 0) return null;
		String userId = Util.userIdByShiro();
		// 获取当前是否已经有试卷
		List<QuesTest> quesTestList =  this.listMyExamTest(examInfo.getExamCode(), userId);

		QuesScore examScore = examScoreService.getUserQuesScore(examInfo.getExamCode(), userId);

		if(quesTestList.size()>0) {
			for (QuesTest item : quesTestList) {
				item.setQuestion(quesBankService.getNoSensitive(item.getQuesCode()));
			}
		} else {
			List<QuesTemp> tempList = quesTempService.list(examInfo.getExamCode());
			List<QuesBank> questions = new ArrayList<QuesBank>();
			String examCode= examInfo.getExamCode();
			for(QuesTemp item: tempList) {
				QuesBankExample example = new QuesBankExample();
				com.cmbccd.ulms.edu.domain.QuesBankExample.Criteria criteria = example.createCriteria();
				criteria.andLibCodeEqualTo(item.getLibCode());
				criteria.andQuesStatEqualTo(1);
				List<QuesBank> quesList = quesBankMapper.selectByExample(example);
				Collections.shuffle(quesList);
				questions.addAll(quesList.subList(0, item.getQuesNum()));
			}
			// ques_type 1-单选 2-多选 3-判断
			questions.stream().sorted(new Comparator<QuesBank>() {
				public int compare (QuesBank o1, QuesBank o2) {
					if (o1.getQuesType()==3 || o2.getQuesType() == 3) {
						return -1;
					} else {
						return 1;
					}
				}
			}).collect(Collectors.toList());
			BigDecimal score = new BigDecimal(0);
			if(examInfo.getScoreMethod() == 0)	{
				score = new BigDecimal(examInfo.getScore() * 1.0 / examInfo.getQuesNum()).setScale(2, RoundingMode.HALF_DOWN);
			}else {
				score=  new BigDecimal(examInfo.getUnitScore());
			}
			for (int i = 0; i < questions.size(); i++ ) {
				QuesBank item = questions.get(i);
				QuesTest record = new QuesTest();
				record.setQuesCode(item.getQuesCode());
				record.setPloNum(userId);
				record.setTestStat(1);
				record.setQuesOrder(i+1);
				record.setScore(score);
				record.setIfCorrect(0);
				record.setExamCode(examCode);
				this.create(record);
				record.setQuestion(item);
				quesTestList.add(record);
			}
			// 新建立完成试卷后更新IP地址及开始时间
			examScore.setHandleIp(ip);
			examScore.setBegDate(Util.currentDateTime());
		}
		examScoreService.update(examScore);
		return quesTestList;
	}

	@Override
	public int submitAnswer(QuesTest record) {
		QuesBank question = quesBankService.get(record.getQuesCode());
		int passFLag = quesBankService.checkUserAnswer(question, record.getUserAnswer());
		record.setIfCorrect(passFLag);
		record.setHandleDate(Util.currentDateTime());
		int count = this.update(record);
		if(count < 0) return -1;
		return passFLag;
	}

	@Override
	public Double getUserScore(String examCode) {
		String userId = Util.userIdByShiro();
		return quesTestMapper.getUserScore(examCode, userId);
	}

	/*
	 * 若复议通过则所有人员该题目
	 * 若不通过则从新匹配有该题的答案。
	 */
	@Override
	public int updateQuestionCorrect(String examCode, String quesCode, Integer disputeResult) {
		int count = 0;
		QuesTestExample example = new QuesTestExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		criteria.andQuesCodeEqualTo(quesCode);
		criteria.andHandleDateIsNotNull();
		if(disputeResult == 1) {
			QuesTest record = new QuesTest();
			record.setIfCorrect(1);
			count = quesTestMapper.updateByExampleSelective(record, example);
		}else {
			List<QuesTest> quesTestList = quesTestMapper.selectByExample(example);
			QuesBank question = quesBankService.get(quesCode);
			for( QuesTest item : quesTestList) {
				int passFlag = quesBankService.checkUserAnswer(question, item.getUserAnswer());
				if(passFlag != item.getIfCorrect()) {
					item.setIfCorrect(passFlag);
					quesTestMapper.updateByPrimaryKey(item);
				}
			}
		}
		return count;
	}

	@Override
	public List<QuesTestDetail> reportQuesTestDetail(String examCode) {
		List<QuesTestDetail> list = quesTestMapper.reportQuesTestDetail(examCode);

		for (QuesTestDetail record : list) {
			Employee user = DataCache.EMPLOYEE.get(record.getPloNum());
			if (!Util.isNullorEmpty(user)) {
				record.setDeptNum(user.getDeptName());
				record.setDeptGroup(user.getGroupName());
				record.setBatchGroup(user.getBatchGroup());
				record.setPloName(user.getPloName());
			}
		}
		return list;
	}


}