package com.cmbccd.ulms.hr.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.dao.RuleDataMapper;
import com.cmbccd.ulms.hr.domain.RuleData;
import com.cmbccd.ulms.hr.domain.RuleDataExample;
import com.cmbccd.ulms.hr.service.RuleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleDataServiceImpl implements RuleDataService {

    @Autowired
    private RuleDataMapper ruleDataMapper;

    @Override
    public List<RuleData> findAllRuleData(String ruleType, String dataType, int status) {
        RuleDataExample example = new RuleDataExample();
        RuleDataExample.Criteria criteria = example.createCriteria();
        criteria.andRuleTypeEqualTo(ruleType);
        criteria.andDataTypeEqualTo(dataType);
        if(!Util.isNullorEmpty(status)) {
            criteria.andStatusEqualTo(status);
        }
        return ruleDataMapper.selectByExample(example);
    }

    @Override
    public RuleData getRuleDataById(String journo) {
        return ruleDataMapper.selectByPrimaryKey(journo);
    }

    @Override
    public int create(RuleData ruleData) {
        return ruleDataMapper.insertSelective(ruleData);
    }

    @Override
    public int update(RuleData ruleData) {
        return ruleDataMapper.updateByPrimaryKey(ruleData);
    }

    @Override
    public int deleteRuleData(String journo) {
        return ruleDataMapper.deleteByPrimaryKey(journo);
    }
}
