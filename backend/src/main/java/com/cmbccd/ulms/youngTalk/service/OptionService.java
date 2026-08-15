package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Option;

import java.util.List;
import java.util.Map;

public interface OptionService {

	int getNewId();
	
	int insertNewOption(Option record);
	
	List<Option> getOptionsByQuestionId(int articleId,int questionId);
	
	List<Map<String,Object>> selectRadioNumGroupByQuestionId(Integer articleId,Integer questionId);
	
	int selectCheckboxNumGroupByOptionId(Integer articleId,Integer questionId,String option);
}
