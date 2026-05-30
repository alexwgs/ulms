package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.flow.domain.FlowApproveTemp;

import java.util.List;

public interface FlowApproveTempService {

    int insert(FlowApproveTemp record);

    int delete(String id);

    int update(FlowApproveTemp record);

    List<FlowApproveTemp> listByFlowId(String flowId, Short status);

    List<FlowApproveTemp> listRequireByFlowId(String flowId, Short status);

    void setStatusOff(String flowId);
}
