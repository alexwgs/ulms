package com.cmbccd.ulms.flow.controller;


import com.alibaba.excel.EasyExcel;
import com.cmbccd.ulms.common.config.UlmsConfig;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.domain.FlowApproveLog;
import com.cmbccd.ulms.flow.domain.FlowCase;
import com.cmbccd.ulms.flow.domain.FlowGqsq;
import com.cmbccd.ulms.flow.excel.FlowGqsqListener;
import com.cmbccd.ulms.flow.service.FLowGqsqService;
import com.cmbccd.ulms.flow.service.FlowApproveLogService;
import com.cmbccd.ulms.flow.service.FlowCaseService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work-attendance")
public class FlowGqsqController {

    @Resource
    private FLowGqsqService flowGqsqService;

    @Resource
    private FlowCaseService flowCaseService;
    @Resource
    private FlowApproveLogService flowApproveLogService;

    @Resource
    private UlmsConfig ulmsConfig;

    Logger logger = LoggerFactory.getLogger(FlowGqsqController.class);

    @PostMapping("/flow/new")
    public Msg newPerfUnitFlow(@RequestBody FlowCase flowCase) {
        if(Util.isNullorEmpty(flowCase)) return Msg.error("参数错误");
        List<FlowGqsq> list = Util.objToList(flowCase.getData(), FlowGqsq.class);

        // 校验和名单
        Msg msg = flowGqsqService.check(list);
        if(msg.get("type").toString().equals("confirm")) return msg;
        // 先要新建一个Case
        FlowCase rFlowCase = flowCaseService.insert(flowCase.getFlowId(), (short) flowCase.getCaseStatus());
        if(Util.isNullorEmpty(rFlowCase.getId())) return Msg.error("新建流程失败");
        List<FlowApproveLog> applogs = flowCase.getFlowApproveLogs();
        applogs.forEach( item -> {
            item.setCaseId(rFlowCase.getId());
            flowApproveLogService.insert(item);
        });
        msg = flowGqsqService.batchInsert(list, rFlowCase.getId());
        boolean noApprove = flowCaseService.caseNoApprovePass(rFlowCase.getFlowId(), rFlowCase.getId());
        if(noApprove == true) {
            Msg.success("无需审批，案件完成");
        }
        return Msg.success();
    }

    @GetMapping("/flow/seq/auth")
    public Msg getAuthSeq() {
        return Msg.success(flowGqsqService.authSeq());
    }


    @GetMapping("/batch/process")
    public Msg ProcessBatchImport(@RequestParam String filePath) {
        String fileName = ulmsConfig.getUploadPath() + filePath;
        List<Map<String, String>> list = new ArrayList<>();
        FlowGqsqListener listener = new FlowGqsqListener();
        EasyExcel.read(fileName, listener).sheet().doRead();
        list = listener.getList();

        return Msg.success(list);
    }
}
