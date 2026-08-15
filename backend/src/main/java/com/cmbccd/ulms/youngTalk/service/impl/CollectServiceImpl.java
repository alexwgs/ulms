package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.youngTalk.dao.CollectMapper;
import com.cmbccd.ulms.youngTalk.domain.Collect;
import com.cmbccd.ulms.youngTalk.domain.CollectExample;
import com.cmbccd.ulms.youngTalk.domain.CollectExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.ArticleService;
import com.cmbccd.ulms.youngTalk.service.CollectService;
import com.github.pagehelper.PageHelper;

import jakarta.annotation.Resource;


@Service
public class CollectServiceImpl implements CollectService {

	@Resource
	private CollectMapper collectMapper;

	@Resource
	private ArticleService articleService;

	@Override
	public int isUserCollectByArticleId(Integer articleId, String userId) {
		CollectExample example = new CollectExample();
		Criteria crtieria = example.createCriteria();
		crtieria.andArticleIdEqualTo(articleId);
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
	public int updateStatusByUserAndArticle(Collect record) {
		return collectMapper.updateStatusByUserAndArticle(record);
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

	@Override
	public DataPage<Collect> listCollectByQuery(Map<String, String> params, String userId) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Collect> collects = getCollectListByUserId(userId);
		for (Collect collect : collects) {
			collect.setArticle(articleService.getArticleByIdWithNoContent(collect.getArticleId()));
		}
		return new DataPage<Collect>(collects);
	}

}
