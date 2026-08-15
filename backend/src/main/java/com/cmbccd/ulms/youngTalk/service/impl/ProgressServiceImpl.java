package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.ProgressMapper;
import com.cmbccd.ulms.youngTalk.domain.Progress;
import com.cmbccd.ulms.youngTalk.domain.ProgressExample;
import com.cmbccd.ulms.youngTalk.domain.ProgressExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.ProgressService;

import jakarta.annotation.Resource;

@Service
public class ProgressServiceImpl implements ProgressService {

	@Resource
	private ProgressMapper progressMapper;

	@Override
	public int newId() {
		return progressMapper.creatNewId();
	}

	@Override
	public List<Progress> getProgressByArticleId(int articleId) {
		ProgressExample example = new ProgressExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(articleId);
		criteria.andStatusEqualTo(1);
		example.setOrderByClause(" ID DESC ");
		return progressMapper.selectByExample(example);
	}

	@Override
	public int insertNewProgress(Progress record) {
		int id = progressMapper.creatNewId();
		record.setId(id);
		return progressMapper.insert(record);
	}

	@Override
	public int updateProgressById(Progress record) {
		return progressMapper.updateByPrimaryKeySelective(record);
	}
	
	
}
