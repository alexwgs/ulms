package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.QuesDisputeMapper;
import com.cmbccd.ulms.edu.domain.DisputeQuestion;
import com.cmbccd.ulms.edu.domain.QuesDispute;
import com.cmbccd.ulms.edu.domain.QuesDisputeExample;
import com.cmbccd.ulms.edu.domain.QuesDisputeExample.Criteria;
import com.cmbccd.ulms.edu.service.QuesDisputeService;
import com.cmbccd.ulms.sys.service.PublicService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class QuesDisputeServiceImpl implements QuesDisputeService {

	@Resource
	private QuesDisputeMapper quesDisputeMapper;
	
	@Resource
	private PublicService publicService;
	
	@Override
	public int create(QuesDispute record) {
		String userId = Util.userIdByShiro();
		record.setJourno(publicService.getJourno());
		record.setDataDate(Util.currentDateTime());
		record.setPloNum(userId);
		record.setDisputeResult((short)2);
		return quesDisputeMapper.insertSelective(record);
	}

	@Override
	public int update(QuesDispute record) {
		return quesDisputeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int update(Short disputeResult,QuesDisputeExample example) {
		String userId = Util.userIdByShiro();
		QuesDispute record = new QuesDispute();
		record.setDisputeResult(disputeResult);
		record.setHandleDate(Util.currentDateTime());
		record.setHandlePlo(userId);
		if (disputeResult == 1) record.setReplyAnswer("[任意选项均正确]");
		else if (disputeResult == 0 )record.setReplyAnswer("[维持原答案]");
		return quesDisputeMapper.updateByExampleSelective(record, example);
	}

	@Override
	public List<QuesDispute> list(QuesDisputeExample example) {
		return quesDisputeMapper.selectByExample(example);
	}

	@Override
	public QuesDispute get(String quesCode, String userId) {
		QuesDisputeExample example = new QuesDisputeExample();
		Criteria criteria = example.createCriteria();
		criteria.andPloNumEqualTo(userId);
		criteria.andQuesCodeEqualTo(quesCode);
		List<QuesDispute> list =  quesDisputeMapper.selectByExample(example);
		if(list.size()<1) return null;
		return list.get(0);
	}

	@Override
	public List<QuesDispute> list(String examCode, String userId) {
		QuesDisputeExample example = new QuesDisputeExample();
		Criteria criteria = example.createCriteria();
		criteria.andPloNumEqualTo(userId);
		criteria.andExamCodeEqualTo(examCode);
		return quesDisputeMapper.selectByExample(example);
	}
	
	public List<QuesDispute> listDisputeDetail(String quesCode, short disputeResult) {
		QuesDisputeExample example = new QuesDisputeExample();
		Criteria criteria = example.createCriteria();
		criteria.andQuesCodeEqualTo(quesCode);
		criteria.andDisputeResultEqualTo(disputeResult);
		example.setOrderByClause(" data_date desc");
		return quesDisputeMapper.selectByExample(example);
	}

	@Override
	public List<DisputeQuestion> listDisputeQuestion(QuesDisputeExample example) {
		List<DisputeQuestion> list = quesDisputeMapper.selectDisputeQuesionByExample(example);
		
		for(DisputeQuestion item : list) {
			item.setQuesDisputes(this.listDisputeDetail(item.getQuesCode(), item.getDisputeResult()));
		}
		return list;
	}
	
}
