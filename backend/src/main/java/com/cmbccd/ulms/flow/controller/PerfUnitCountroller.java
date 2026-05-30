package com.cmbccd.ulms.flow.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.cmbccd.ulms.common.config.UlmsConfig;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.domain.FlowApproveLog;
import com.cmbccd.ulms.flow.domain.FlowCase;
import com.cmbccd.ulms.flow.domain.PerfUnit;
import com.cmbccd.ulms.flow.service.FlowApproveLogService;
import com.cmbccd.ulms.flow.service.FlowCaseService;
import com.cmbccd.ulms.flow.service.PerfUnitFlowService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/perf-unit")
public class PerfUnitCountroller {
    Logger logger = LoggerFactory.getLogger(PerfUnitCountroller.class);
    @Resource
    private PerfUnitFlowService perfUnitFlowService;

    @Resource
    private FlowCaseService flowCaseService;

    @Resource
    private FlowApproveLogService flowApproveLogService;

    @Resource
    private UlmsConfig ulmsConfig;

    @PostMapping("/flow/new")
    public Msg newPerfUnitFlow(@RequestBody FlowCase flowCase) {
        if(Util.isNullorEmpty(flowCase)) return Msg.error("参数错误");
        List<PerfUnit> list = Util.objToList(flowCase.getData(), PerfUnit.class);

        // 校验和名单
        Msg msg = perfUnitFlowService.check(list);
        if(msg.get("type").toString().equals("confirm")) return msg;
        // 先要新建一个Case
        FlowCase rFlowCase = flowCaseService.insert(flowCase.getFlowId(), (short) flowCase.getCaseStatus());
        if(Util.isNullorEmpty(rFlowCase.getId())) return Msg.error("新建流程失败");
        List<FlowApproveLog> applogs = flowCase.getFlowApproveLogs();
        applogs.forEach( item -> {
            item.setCaseId(rFlowCase.getId());
            flowApproveLogService.insert(item);
        });
        msg = perfUnitFlowService.batchInsert(list, rFlowCase.getId());
        boolean noApprove = flowCaseService.caseNoApprovePass(rFlowCase.getFlowId(), rFlowCase.getId());
        if(noApprove == true) {
            Msg.success("无需审批，案件完成");
        }
        return Msg.success();
    }

    @GetMapping("/batch/process")
    public Msg ProcessBatchImport(@RequestParam String filePath) {
        String fileName = ulmsConfig.getUploadPath() + filePath;
        List<PerfUnit> list = new ArrayList<>();
        EasyExcel.read(fileName, PerfUnit.class, new PageReadListener<PerfUnit>(dataList ->{
            for(PerfUnit item: dataList) {
                item.setUser(DataCache.EMPLOYEE.get(item.getPloNum()));
                list.add(item);
                logger.info("读取到数据:{}", JSON.toJSONString(item));
            }
        })).sheet().doRead();
        return Msg.success(list);
    }

}
