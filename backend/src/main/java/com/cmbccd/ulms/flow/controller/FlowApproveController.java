package com.cmbccd.ulms.flow.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.flow.domain.FlowApproveLog;
import com.cmbccd.ulms.flow.domain.FlowCase;
import com.cmbccd.ulms.flow.service.FlowApproveLogService;
import com.cmbccd.ulms.flow.service.FlowCaseService;
import com.cmbccd.ulms.flow.service.FlowInfoService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/flow/approve")
public class FlowApproveController {
    @Resource
    private FlowApproveLogService flowApproveLogService;
    @Resource
    private FlowCaseService flowCaseService;
    @Resource
    private FlowInfoService flowInfoService;
    @GetMapping("/list/{id}")
    public Msg getApproveList(@PathVariable("id") String id) {
        List<FlowApproveLog> list = flowApproveLogService.listApproveLog(id);
        return Msg.success(list);
    }

    @PostMapping("/submit")
    @MyLog(title = "[flow-approve]审批管理", content = "放行列表")
    public Msg submitApprove(@RequestBody List<FlowApproveLog> flowApproveLogs){
        Msg msg = flowApproveLogService.submitApprove(flowApproveLogs);
        String result = msg.get("result").toString();
        if ("agree".equals(result)) {
            String caseId = flowApproveLogs.stream().findFirst().get().getCaseId();
            FlowCase flowCase = flowCaseService.get(caseId);
            // 需要更新各个电子流的案件明细状态
            int count = flowInfoService.updateDetailStatus(flowCase.getFlowId(), flowCase.getId(), flowCase.getApproveStatus());
            msg.get("msg").toString().concat("，更新了" + count + "条电子流明细状态。");
        }
        return msg;
    }
}
