package com.cmbccd.ulms.hr.service;


import com.cmbccd.ulms.hr.domain.RuleData;

import java.util.List;

public interface RuleDataService
{
    List<RuleData> findAllRuleData(String ruleType, String dataType, int status);

    RuleData getRuleDataById(String journo);

    int create(RuleData ruleData);

    int update(RuleData ruleData);

    int deleteRuleData(String journo);
}
