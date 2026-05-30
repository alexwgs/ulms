package com.cmbccd.ulms.flow.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.domain.FlowApproveTemp;
import com.cmbccd.ulms.flow.domain.FlowInfo;
import com.cmbccd.ulms.flow.service.FlowApproveTempService;
import com.cmbccd.ulms.flow.service.FlowInfoService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/flow/config")
public class FlowConfigController {

    @Resource
    private FlowInfoService flowInfoService;

    @Resource
    private FlowApproveTempService flowApproveTempService;

    @GetMapping("/list")
    public Msg configList(@RequestParam Map<String, String> params){
        List<FlowInfo> list = flowInfoService.list(params);
        list.forEach(flowInfo -> {
            flowInfo.setFlowApproveTemp(flowApproveTempService.listByFlowId(flowInfo.getId(),(short)1));
        });
        return Msg.success(new DataPage<FlowInfo>(list));
    }

    @PostMapping("")
    @MyLog(title = "[flow-config]电子流配置")
    public Msg newFlow(@RequestBody FlowInfo flowInfo){
        int count = flowInfoService.insert(flowInfo);
        if (count < 1) return Msg.error("新建失败");
        return Msg.success();
    }

    @PutMapping("")
    @MyLog(title = "[flow-config]电子流配置")
    public Msg updateFlow(@RequestBody FlowInfo flowInfo){
        int count = flowInfoService.update(flowInfo);
        if (count < 1) return Msg.error("更新失败");
        return Msg.success();
    }

    @GetMapping("/templete/{id}")
    @MyLog(title = "[flow-config]电子流审批配置")
    public Msg getTemplete(@PathVariable("id") String id){
        List<FlowApproveTemp> list = flowApproveTempService.listByFlowId(id,(short)1);
        return Msg.success(list);
    }

    @PostMapping("/templete/{id}")
    @MyLog(title = "[flow-config]电子流审批配置")
    public Msg addTemplete(@PathVariable ("id") String id,@RequestBody List<FlowApproveTemp> flowApproveTemps){
        if(flowApproveTemps.size() == 0){
            return Msg.error("参数错误");
        }
        FlowInfo flowInfo = flowInfoService.get(id);
        flowInfo.setApproveTemp(Util.currentDateTime());
        flowApproveTemps.forEach(flowApproveTemp -> {
            flowApproveTempService.insert(flowApproveTemp);
        });
        flowInfoService.update(flowInfo);
        return Msg.success();
    }

    @PutMapping("/templete/{id}")
    @MyLog(title = "[flow-config]电子流审批配置")
    public Msg editTemplete(@PathVariable ("id") String flowId,@RequestBody List<FlowApproveTemp> flowApproveTemps){
        if(flowApproveTemps.size() == 0){
            return Msg.error("参数错误");
        }
        FlowInfo flowInfo = flowInfoService.get(flowId);
        flowInfo.setApproveTemp(Util.currentDateTime());
        flowApproveTempService.setStatusOff(flowId);
        flowApproveTemps.forEach(flowApproveTemp -> {
            flowApproveTempService.insert(flowApproveTemp);
        });
        flowInfoService.update(flowInfo);
        return Msg.success();
    }


}
