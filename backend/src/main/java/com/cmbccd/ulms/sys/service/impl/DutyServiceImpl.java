package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.DutyMapper;
import com.cmbccd.ulms.sys.domain.Duty;
import com.cmbccd.ulms.sys.domain.DutyExample;
import com.cmbccd.ulms.sys.domain.DutyExample.Criteria;
import com.cmbccd.ulms.sys.service.DutyService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class DutyServiceImpl implements DutyService {
	@Resource
	private DutyMapper dutymapper;

	@Override
	public List<Duty> getDutyByPloNum(String date, String ploNum) {
		DutyExample example = new DutyExample();
		Criteria criteria = example.createCriteria();
		criteria.andDutyDateEqualTo(date);
		criteria.andPloNumEqualTo(ploNum);
		example.setOrderByClause(" DUTY_TYPE ASC ");
		
		return dutymapper.selectByExample(example);
	}
	
	
}
