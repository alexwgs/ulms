package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.CollectMapper;
import com.cmbccd.ulms.youngTalk.domain.Collect;
import com.cmbccd.ulms.youngTalk.domain.CollectExample;
import com.cmbccd.ulms.youngTalk.domain.CollectExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.CollectService;

import jakarta.annotation.Resource;


@Service
public class CollectServiceImpl implements CollectService {

	@Resource
	private CollectMapper collectMapper;

	@Override
	public int isUserCollectByArticalId(Integer articalId, String userId) {
		CollectExample example = new CollectExample();
		Criteria crtieria = example.createCriteria();
		crtieria.andArticalIdEqualTo(articalId);
		crtieria.andUserIdEqualTo(userId);
		crtieria.andStatusEqualTo(1);
		return collectMapper.countByExample(example);
	}

	@Override
	public int newId() {
		return collectMapper.creatNewId();
	}

	@Override
	public int insertCollect(Collect record) {
		return collectMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(Collect record) {
		return collectMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateStatusByUserAndArtical(Collect record) {
		return collectMapper.updateStatusByUserAndArtical(record);
	}

	@Override
	public List<Collect> getCollectListByUserId(String userId) {
		CollectExample example = new CollectExample();
		example.setOrderByClause(" DATE_TIME DESC ");
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andStatusEqualTo(1);
		return collectMapper.selectByExample(example);
	}

}
