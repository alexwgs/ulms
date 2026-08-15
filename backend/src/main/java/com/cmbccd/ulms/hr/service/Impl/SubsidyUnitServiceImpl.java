package com.cmbccd.ulms.hr.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.dao.SubsidyUnitMapper;
import com.cmbccd.ulms.hr.domain.SubsidyUnit;
import com.cmbccd.ulms.hr.domain.SubsidyUnitExample;
import com.cmbccd.ulms.hr.service.SubsidyUnitService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SubsidyUnitServiceImpl implements SubsidyUnitService {
    @Resource
    private SubsidyUnitMapper subsidyUnitMapper;

    @Override
    public List<SubsidyUnit> list(Map<String, String> params) {
        String status = params.get("status");
        String subsidyName = params.get("subsidyName");
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        SubsidyUnitExample example = new SubsidyUnitExample();
        SubsidyUnitExample.Criteria criteria = example.createCriteria();
        if(!Util.isNullorEmpty(status)) {
            criteria.andStatusEqualTo(Short.parseShort(status));
        }
        if(!Util.isNullorEmpty(subsidyName)) {
            criteria.andSubsidyNameLike("%" + subsidyName + "%");
        }
        if (Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(" sort, subsidy_name ");
        }else {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<SubsidyUnit> list = subsidyUnitMapper.selectByExample(example);
        return list;
    }

    @Override
    public SubsidyUnit get(String id) {
        return subsidyUnitMapper.selectByPrimaryKey(id);
    }

    @Override
    public int add(SubsidyUnit subsidyUnit) {
        subsidyUnit.setId(UUID.randomUUID().toString());
        return subsidyUnitMapper.insert(subsidyUnit);
    }

    @Override
    public int update(SubsidyUnit subsidyUnit) {
        return subsidyUnitMapper.updateByPrimaryKey(subsidyUnit);
    }

    @Override
    public int delete(String id) {
        return subsidyUnitMapper.deleteByPrimaryKey(id);
    }
}
