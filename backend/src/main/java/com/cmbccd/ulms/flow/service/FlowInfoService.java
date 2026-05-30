package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.flow.domain.FlowInfo;

import java.util.List;
import java.util.Map;

public interface FlowInfoService {

    List<FlowInfo> list(Map<String, String> params);

    List<FlowInfo> list(Short status);

    int insert(FlowInfo record);

    FlowInfo get(String id);

    int update(FlowInfo record);

    int delete(String id);

    // 根据流程配置的明细表，更新审批结果
    int updateDetailStatus(String flowId, String caseId, Short approveResult);

    /**
     * 根据流程配置的明细表，产出明细表数据
     * @param flowId 电子流ID
     * @param caseId 案件ID
     * @return
     */
    List<Map<String, Object>> listFlowData(String flowId, String caseId);
}
