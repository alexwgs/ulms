package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.QuesCollectMapper;
import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.QuesBank;
import com.cmbccd.ulms.edu.domain.QuesCollect;
import com.cmbccd.ulms.edu.domain.QuesCollectExample;
import com.cmbccd.ulms.edu.domain.QuesCollectExample.Criteria;
import com.cmbccd.ulms.edu.service.BrushConfigService;
import com.cmbccd.ulms.edu.service.QuesBankService;
import com.cmbccd.ulms.edu.service.QuesCollectService;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
	public class QuesCollectServiceImpl implements QuesCollectService {

	@Resource
	private QuesCollectMapper quesCollectMapper;
	@Resource
	private BrushConfigService brushConfigService;
	@Resource
	private QuesBankService quesBankService;
	@Resource
	private PublicMapper publicMapper;
	
	@Override
	public int collect(String quesCode) {
		QuesCollect record = new QuesCollect();
		String userId = Util.userIdByShiro();
		// 需检测当题目数量
		BrushConfig brushConfig = brushConfigService.getBrushConfig(userId);
		Integer collectNumber = 0;
		Integer collectDay = (int)brushConfig.getCollectDay();
		Integer collectLimit = (int)brushConfig.getCollectLimit();
		if(quesCollectMapper.getHaveCollect(userId, collectLimit == 0 ? null:collectLimit, quesCode) > 0 ) {
			return -1;
		}
		if(collectDay == 0) {
			collectNumber = quesCollectMapper.getCollectNumber(userId, null);
		}
		else  {
			collectNumber = quesCollectMapper.getCollectNumber(userId, collectDay);
		}
		if(collectLimit != 0)  {
			if(collectNumber >= collectLimit) return 0;
		} 
		record.setQuesCode(quesCode);
		record.setDataDate(Util.currentDateTime());
		record.setPloNum(userId);
		record.setStatus((short)1);
		record.setJourno(publicMapper.selectNewJourno());
		quesCollectMapper.insert(record);
		return collectNumber+1;
	}

	@Override
	public int uncollect(String journo) {
		QuesCollect record = new QuesCollect();
		record.setJourno(journo);
		record.setUpdateDate(Util.currentDateTime());
		record.setStatus((short)0);
		return quesCollectMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int update(QuesCollect record) {
		
		return quesCollectMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(String journo) {
		return quesCollectMapper.deleteByPrimaryKey(journo);
	}

	@Override
	public List<QuesCollect> listMyCollect() {
		// 需检测当题目数量
		String userId = Util.userIdByShiro();
		QuesCollectExample example = new QuesCollectExample();
		Criteria criteria = example.createCriteria();
		BrushConfig brushConfig = brushConfigService.getBrushConfig(userId);
		criteria.andPloNumEqualTo(userId);
		
		Integer collectDay = (int)brushConfig.getCollectDay();
		Integer collectLimit = (int)brushConfig.getCollectLimit();
		
		if(collectDay != 0)  criteria.andDataDateBetween(Util.getPastDate(collectDay) + " 00:00:00", Util.getDateToday()+ " 23:59:59");
		criteria.andStatusEqualTo((short)1);
		List<QuesCollect> list = quesCollectMapper.selectByExample(example);
		if(list.size() > collectLimit) list = list.subList(0, collectLimit);
		for (QuesCollect record : list) {
			QuesBank question = quesBankService.get(record.getQuesCode());
			record.setQuestion(question);
		}
		return list;
	}

}
