package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.TeachGroupMapper;
import com.cmbccd.ulms.college.domain.TeachGroup;
import com.cmbccd.ulms.college.domain.TeachGroupExample;
import com.cmbccd.ulms.college.domain.TeachGroupExample.Criteria;
import com.cmbccd.ulms.college.service.TeachGroupService;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Employee;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeachGroupServiceImpl implements TeachGroupService {
    @Resource
    private TeachGroupMapper teachGroupMapper;

    @Override
    public List<TeachGroup> listGroupName() {
        return teachGroupMapper.listGroupName();
    }

    @Override
    public List<TeachGroup> listUserByGroup(TeachGroupExample example) {
        List<TeachGroup> list = teachGroupMapper.selectByExample(example);
        list.forEach(item -> {
            item.setUser(DataCache.getEmployees().get(item.getPloNum()));
        });
        return list;
    }

    @Override
    public DataPage<TeachGroup> listUserByGroupQuery(Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String groupName = params.get("groupName");
        String query = params.get("query");
        String queryType = params.get("queryType");
        if (Util.isNullorEmpty(groupName)) return null;
        TeachGroupExample example = new TeachGroupExample();
        Criteria criteria = example.createCriteria();
        criteria.andGroupNameEqualTo(groupName);
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        if (!Util.isNullorEmpty(query)) {
            if ("ploNum".equals(queryType)) {
                criteria.andPloNumEqualTo(query);
            } else if ("ploName".equals(queryType)) {
                List<String> ploNums = DataCache.getEmployees().values().stream()
                        .filter(e -> e.getPloName().indexOf(query) > -1)
                        .map(Employee::getPloNum).collect(Collectors.toList());
                if (ploNums.size() < 1) criteria.andPloNumIsNull();
                else criteria.andPloNumIn(ploNums);
            }
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<TeachGroup> list = listUserByGroup(example);
        return new DataPage<TeachGroup>(list);
    }

    @Override
    public int delete(TeachGroup teachGroup) {
        TeachGroupExample example = new TeachGroupExample();
        TeachGroupExample.Criteria criteria = example.createCriteria();
        criteria.andGroupNameEqualTo(teachGroup.getGroupName());
        criteria.andPloNumEqualTo(teachGroup.getPloNum());
        return teachGroupMapper.deleteByExample(example);
    }

    @Override
    public int add(TeachGroup teachGroup) {
        return teachGroupMapper.insert(teachGroup);
    }

    @Override
    public List<String> listGroupNameByPloNum(String ploNum) {
        List<String> list = teachGroupMapper.listGroupNameByPloNum(ploNum);
        list.add("全体员工");
        return list;
    }

    @Override
    public void deleteAll() {
        teachGroupMapper.deleteAll();
    }

    @Override
    public int batchInsert(List<TeachGroup> list) {
        return teachGroupMapper.batchInsert(list);
    }
}
