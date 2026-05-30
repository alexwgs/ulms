package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.flow.domain.FlowCase;
import com.cmbccd.ulms.flow.excel.FlowCaseExcel;

import java.util.List;
import java.util.Map;

public interface FlowCaseService {
    /**
     *
     * @param type 设置类型 0-我的草稿 1-我发起的  2-我处理的 3-我的待办
     * @param params
     * @return
     */
    List<FlowCase> list(String type, Map<String, String> params);

    /**
     * 导出excel
     * @param params 查询参数
     * @return
     */
    List<FlowCaseExcel> report(Map<String, String> params);

    /**
     * 新增流程
     * @param flowId 流程ID
     * @param caseStatus 流程状态 0-草稿  2-待审批
     * @return 返回流程审批结果
     */
    FlowCase insert(String flowId, Short caseStatus);

    int update(FlowCase flowCase);

    int draftToSubmit(String caseId);

    FlowCase get(String caseId);
    FlowCase getFullCase(String caseId);

    int completeCase(String caseId, Short caseStatus, Short approveStatus);

    boolean caseCancel(FlowCase flowCase);

    /**
     * 免审批直接通过
     */
    boolean caseNoApprovePass(String flowId,String caseId);
}
