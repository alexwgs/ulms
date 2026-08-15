package com.cmbccd.ulms.flow.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.FlowApproveTempMapper;
import com.cmbccd.ulms.flow.domain.FlowApproveTemp;
import com.cmbccd.ulms.flow.domain.FlowApproveTempExample;
import com.cmbccd.ulms.flow.service.FlowApproveTempService;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlowApproveTempServiceImpl implements FlowApproveTempService {
    @Resource
    private FlowApproveTempMapper flowApproveTempMapper;

    @Override
    public int insert(FlowApproveTemp record) {
        record.setId(Util.getUUID());
        return flowApproveTempMapper.insert(record);
    }

    @Override
    public int delete(String id) {
        return flowApproveTempMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(FlowApproveTemp record) {
        return flowApproveTempMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<FlowApproveTemp> listByFlowId(String flowId, Short status) {
        FlowApproveTempExample example = new FlowApproveTempExample();
        FlowApproveTempExample.Criteria criteria = example.createCriteria();
        criteria.andFlowIdEqualTo(flowId);
        criteria.andStatusEqualTo(status);
        example.setOrderByClause("APPROVE_LEVEL asc");
        return flowApproveTempMapper.selectByExample(example);
    }

    @Override
    public List<FlowApproveTemp> listRequireByFlowId(String flowId, Short status) {
        FlowApproveTempExample example = new FlowApproveTempExample();
        FlowApproveTempExample.Criteria criteria = example.createCriteria();
        criteria.andFlowIdEqualTo(flowId);
        criteria.andStatusEqualTo(status);
        example.setOrderByClause("APPROVE_LEVEL asc");
        List<FlowApproveTemp> base = flowApproveTempMapper.selectByExample(example);
        List<FlowApproveTemp> list = new ArrayList<>();
        String userId = Util.userIdByShiro();

        Employee user = DataCache.getEmployees().get(userId);
        if (user == null) return list;
        base.forEach(item -> {
            if (item.getRequire() == 0) {
                list.add(item);
            }
            if (Util.isNullorEmpty(item.getRequireInfo())) return;
            if (item.getRequire() == 1 && item.getRequireInfo().contains(user.getDeptNum())) {
                list.add(item);
            }else if (item.getRequire() == 2 && item.getRequireInfo().contains(user.getDeptGroup())) {
                list.add(item);
            }else if (item.getRequire() == 3 && item.getRequireInfo().contains(user.getJobLevel())) {
                list.add(item);
            }
        });
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setStatusOff(String flowId) {
        FlowApproveTempExample example = new FlowApproveTempExample();
        FlowApproveTempExample.Criteria criteria = example.createCriteria();
        criteria.andFlowIdEqualTo(flowId);
        criteria.andStatusEqualTo((short)1);
        List<FlowApproveTemp> list = flowApproveTempMapper.selectByExample(example);
        for (FlowApproveTemp item: list) {
            item.setStatus((short)0);
            flowApproveTempMapper.updateByPrimaryKey(item);
        }
    }
}
