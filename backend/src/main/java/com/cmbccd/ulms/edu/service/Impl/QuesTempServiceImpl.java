package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.edu.dao.QuesTempMapper;
import com.cmbccd.ulms.edu.domain.QuesTemp;
import com.cmbccd.ulms.edu.domain.QuesTempExample;
import com.cmbccd.ulms.edu.domain.QuesTempExample.Criteria;
import com.cmbccd.ulms.edu.service.QuesTempService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class QuesTempServiceImpl implements QuesTempService {

	@Resource
	private QuesTempMapper quesTempMapper;

	@Override
	public List<QuesTemp> list(String examCode) {
		QuesTempExample example = new QuesTempExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		return quesTempMapper.selectByExample(example);
	}

	@Override
	public int create(QuesTemp record) {
		return quesTempMapper.insertSelective(record);
	}

	@Override
	public int delete(String examCode) {
		QuesTempExample example = new QuesTempExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		return quesTempMapper.deleteByExample(example);
	}

	@Override
	public int create(List<QuesTemp> list) {
		int quesNum = 0;
		for(QuesTemp item : list) {
			quesTempMapper.insertSelective(item);
			quesNum +=item.getQuesNum();
		}
		return quesNum;
	}
}
