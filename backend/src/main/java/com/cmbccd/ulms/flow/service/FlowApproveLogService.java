package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.flow.domain.FlowApproveLog;
import com.cmbccd.ulms.flow.domain.FlowApproveLogExample;
import com.cmbccd.ulms.sys.domain.Msg;

import java.util.List;

public interface FlowApproveLogService {

    Msg submitApprove(List<FlowApproveLog> flowApproveLogs);

    int insert(FlowApproveLog record);

    int update(FlowApproveLog record);

    List<FlowApproveLog> listByCaseId(String caseId);

    /**
     * 获取审批流程。如审批人魏代理人则将处理人的userId放到handleNum
     * @param caseId 案件编号
     * @param userId 审批人编号
     * @return
     */
    List<FlowApproveLog> listByCaseIdWithProxy(String caseId, String userId);

    // 我所审批过的流程ID列表
    List<String> listMyApproveCaseIds(String begDate, String endDate);

    List<FlowApproveLog> listApproveLog(String id);

    List<FlowApproveLog> list(FlowApproveLogExample example);

    int submitApproveLog(List<FlowApproveLog> logs);

    // 首次审批需获取审批日志
    List<FlowApproveLog> insertFlowApproveLog(String flowId, String CaseId);
}
