package com.cmbccd.ulms.college.service.Impl;

import com.cmbccd.ulms.college.dao.TeachGroupMapper;
import com.cmbccd.ulms.college.domain.TeachGroup;
import com.cmbccd.ulms.college.domain.TeachGroupExample;
import com.cmbccd.ulms.college.service.TeachGroupService;
import com.cmbccd.ulms.common.controller.DataCache;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.List;

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
            item.setUser(DataCache.EMPLOYEE.get(item.getPloNum()));
        });
        return list;
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
