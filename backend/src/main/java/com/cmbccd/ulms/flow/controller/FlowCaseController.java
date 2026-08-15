package com.cmbccd.ulms.flow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.domain.FlowCase;
import com.cmbccd.ulms.flow.domain.FlowInfo;
import com.cmbccd.ulms.flow.service.FlowApproveLogService;
import com.cmbccd.ulms.flow.service.FlowCaseService;
import com.cmbccd.ulms.flow.service.FlowInfoService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flow")
public class FlowCaseController {
    @Resource
    private FlowCaseService flowCaseService;
    @Resource
    private FlowInfoService flowInfoService;
    @Resource
    private FlowApproveLogService flowApproveLogService;

    @GetMapping("/list")
    public Msg getFlowList() {
        List<FlowInfo> list = flowInfoService.list((short)1);
        return Msg.success(list);
    }

    /*
    * 通过获取列表类型
    *       <a-radio value="all">全部</a-radio>
            <a-radio value="apply">我的发起</a-radio>
            <a-radio value="process">我办理的</a-radio>
            <a-radio value="draft">我的草稿</a-radio>
            <a-radio value="proxy">代理案件</a-radio>
     */
    @GetMapping("/case/my/{type}")
    public Msg myList(@PathVariable("type") String type, @RequestParam Map<String, String> params) {
        List<FlowCase> flowCaseList = flowCaseService.list(type, params);
        flowCaseList.forEach(item -> {
            item.setFlowInfo(flowInfoService.get(item.getFlowId()));
            item.setApplyUser(DataCache.getEmployees().get(item.getApplyNum()));
        });
        return Msg.success(new DataPage<FlowCase>(flowCaseList));
    }


    @GetMapping("/case/list")
    public Msg myList(@RequestParam Map<String, String> params) {
        List<FlowCase> flowCaseList = flowCaseService.list(null, params);
        flowCaseList.forEach(item -> {
            item.setFlowInfo(flowInfoService.get(item.getFlowId()));
            item.setApplyUser(DataCache.getEmployees().get(item.getApplyNum()));
        });
        return Msg.success(new DataPage<FlowCase>(flowCaseList));
    }

    @GetMapping("/case/{id}")
    public Msg get(@PathVariable("id") String id) {
        FlowCase flowCase = flowCaseService.getFullCase(id);
//        flowCase.setFlowApproveLogs(flowApproveLogService.listByCaseId(flowCase.getId()));

        return Msg.success(flowCase);
    }

    @PutMapping ("/case/cancel/{id}")
    @SaCheckPermission("flow:case:cancel")
    @MyLog(title = "[flow-case]电子流系统", content = "案件撤销")
    public Msg cancelCase(@PathVariable("id") String id) throws ParseException {
        FlowCase flowCase = flowCaseService.get(id);
        Short caseStatus = flowCase.getCaseStatus();
        Short approveStatus = flowCase.getApproveStatus();
        if(caseStatus == 2) return Msg.error("当前案件已经被撤销！");
        if(approveStatus == 2) return Msg.error("审批失败的案件无法撤销！");
        // 是否需判断是否已经超过一定周期
        String buildDate = flowCase.getDataTime();
        int days = Math.toIntExact(Util.diffDateTime(buildDate, Util.currentDateTime())/3600/24);
        if(days >= 30) return Msg.error("该申请表单举例申请时间已经超过30天，无法申请撤销！");
        boolean result = flowCaseService.caseCancel(flowCase);
        if(!result) return Msg.error("申请撤销失败，可能原因如下：表单不允许撤销或撤销数据写入失败！");
        return Msg.success();
    }
}
