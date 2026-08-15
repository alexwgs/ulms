package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.OptionMapper;
import com.cmbccd.ulms.youngTalk.domain.Option;
import com.cmbccd.ulms.youngTalk.domain.OptionExample;
import com.cmbccd.ulms.youngTalk.domain.OptionExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.OptionService;

import jakarta.annotation.Resource;

@Service
public class OptionServiceImpl implements OptionService {

	@Resource
	private OptionMapper optionMapper;
	@Override
	public int getNewId() {
		return optionMapper.creatNewId();
	}
	@Override
	public int insertNewOption(Option record) {
		return optionMapper.insert(record);
	}
	@Override
	public List<Option> getOptionsByQuestionId(int articleId,int questionId) {
		OptionExample example = new OptionExample();
		Criteria criteria = example.createCriteria();
		criteria.andQuestionIdEqualTo(questionId);
		criteria.andArticleIdEqualTo(articleId);
		example.setOrderByClause(" SORT ASC ");
		return optionMapper.selectByExample(example);
	}
	@Override
	public List<Map<String, Object>> selectRadioNumGroupByQuestionId(Integer articleId, Integer questionId) {
		return optionMapper.selectRadioNumGroupByQuestionId(articleId, questionId);
	}
	@Override
	public int selectCheckboxNumGroupByOptionId(Integer articleId, Integer questionId, String option) {
		
		return optionMapper.selectCheckboxNumGroupByOptionId(articleId, questionId, option);
	}

}
