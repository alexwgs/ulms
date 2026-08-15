package com.cmbccd.ulms.flow.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.FlowCaseMapper;
import com.cmbccd.ulms.flow.domain.*;
import com.cmbccd.ulms.flow.excel.FlowCaseExcel;
import com.cmbccd.ulms.flow.service.*;
import com.cmbccd.ulms.sys.domain.Employee;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlowCaseServiceImpl implements FlowCaseService {

    private static final Set<String> ALLOWED_ORDER_COLUMNS = new HashSet<>(Arrays.asList(
        "dataTime", "caseStatus", "approveStatus", "approveLevel", "endDate", "applyNum", "flowId"
    ));

    @Resource
    private FlowCaseMapper flowCaseMapper;

    @Resource
    private FlowInfoService flowInfoService;
    @Resource
    private FlowApproveLogService flowApproveLogService;

    @Resource
    FlowApproveTempService flowApproveTempService;

    @Resource
    private FlowProxyService flowProxyService;
    @Override

//    @param type 设置类型 null - 全部  1-我发起的  2-我处理的 4- 代理案件
    public List<FlowCase> list(String type, Map<String, String> params) {
        String begDate = params.get("begDate");
        String endDate = params.get("endDate");
        String caseStatus = params.get("caseStatus");
        String approveStatus = params.get("approveStatus");
        String userId = Util.userIdByShiro();

        Map<String, Integer> pageParams = Util.innitTablePages(params);

        if(Util.isNullorEmpty(begDate) || Util.isNullorEmpty(endDate)) {
            begDate = Util.getPastDate(180);
            endDate = Util.getDateToday();
        }
        FlowCaseExample example = new FlowCaseExample();
        FlowCaseExample.Criteria criteria = example.createCriteria();
        criteria.andDataTimeBetween(begDate+" 00:00:00", endDate+" 23:59:59");

        if(!Util.isNullorEmpty(caseStatus)) {
            criteria.andCaseStatusEqualTo(Short.parseShort(caseStatus));
        }
        if(!Util.isNullorEmpty(approveStatus)) {
            criteria.andApproveStatusEqualTo(Short.parseShort(approveStatus));
        }
        if (!Util.isNullorEmpty(params.get("caseStatus"))) {
            criteria.andCaseStatusEqualTo(Short.parseShort(params.get("caseStatus")));
            criteria.andApplyNumEqualTo(userId);
        }
        if (!Util.isNullorEmpty(params.get("approveStatus"))) {
            criteria.andApproveStatusEqualTo(Short.parseShort(params.get("approveStatus")));
        }
        if(Util.isNullorEmpty(type)) {
            // 流程监控
        } else if("draft".equals(type)) {
            // 我的草稿
            criteria.andApplyNumEqualTo(userId);
            criteria.andCaseStatusEqualTo((short) 0);
        } else if("apply".equals(type)) {
            criteria.andApplyNumEqualTo(userId);
        } else if("process".equals(type)) {
            // 我处理的
            List<String> ids = flowApproveLogService.listMyApproveCaseIds(params.get("begDate"), params.get("endDate"));
            ids.add("");
            criteria.andIdIn(ids);
        } else if("todo".equals(type)){
            List<String> ids = flowCaseMapper.getCaseTodoIds(userId);
            ids.add("");
            criteria.andIdIn(ids);
        } else if("proxy".equals(type)) {
            criteria.andApplyNumNotEqualTo(userId);
            criteria.andApproveStatusEqualTo((short) 0);
        } else if ("all".equals(type)) {
            List<String> ids = flowCaseMapper.myCaseId(userId);
            ids.add("");
            criteria.andIdIn(ids);
        } else {
            return null;
        }
        if (Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(" DATA_TIME desc ");
        }else if (ALLOWED_ORDER_COLUMNS.contains(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<FlowCase> list = flowCaseMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<FlowCaseExcel> report(Map<String, String> params) {
        String flowId = params.get("flowId");
        String buildRange = params.get("buildRange");
        String finishRange = params.get("finishRange");
        String flowStatus = params.get("flowStatus");
        String approveStatus = params.get("approveStatus");
        if(Util.isNullorEmpty(flowId)) return null;
        FlowCaseExample example = new FlowCaseExample();
        FlowCaseExample.Criteria criteria = example.createCriteria();
        criteria.andFlowIdEqualTo(flowId);
        if(Util.isNullorEmpty(buildRange)) {
            criteria.andDataTimeBetween(Util.getPastDate(180)+ " 00:00:00", Util.getDateToday() + " 23:59:59" );
        } else {
            criteria.andDataTimeBetween(buildRange.split("~")[0]+ " 00:00:00", buildRange.split("~")[1]+ " 23:59:59" );
        }
        if(!Util.isNullorEmpty(finishRange)) {
            criteria.andEndDateBetween(finishRange.split("~")[0]+ " 00:00:00", finishRange.split("~")[1]+ " 23:59:59" );
        }
        if(!Util.isNullorEmpty(flowStatus)) {
            criteria.andCaseStatusEqualTo(Short.parseShort(flowStatus));
        }
        if(!Util.isNullorEmpty(approveStatus)) {
            criteria.andApproveStatusEqualTo(Short.parseShort(approveStatus));
        }
        List<FlowCase> base = flowCaseMapper.selectByExample(example);
        List<FlowCaseExcel> list = new ArrayList<>();
        base.forEach(flowCase -> {
            FlowCaseExcel excel = new FlowCaseExcel();
            Employee appUser = DataCache.EMPLOYEE.get(flowCase.getApplyNum());
            if (appUser != null) {
                excel.setApplyDeptName(appUser.getDeptName());
                excel.setApplyName(appUser.getPloName());
                excel.setApplyGroupName(appUser.getGroupName());
                excel.setApplyNum(appUser.getPloNum());
            }
            excel.setDataTime(flowCase.getDataTime());
            excel.setApproveLevel(flowCase.getApproveLevel());
            excel.setEndDate(flowCase.getEndDate());
            excel.setId(flowCase.getId());
            excel.setCaseStatus(flowCase.getCaseStatus()==1?"正常":flowCase.getCaseStatus()==2?"撤销":"草稿");
            excel.setApproveStatus(flowCase.getApproveStatus()==0?"待审批":flowCase.getApproveStatus()==1?"通过":"不通过");
            // 获取放行明细
            List<FlowApproveLog> approveLogs = flowApproveLogService.listByCaseId(flowCase.getId());
            StringBuilder operLog = new StringBuilder();
            for (FlowApproveLog item : approveLogs) {
                String handleResult= Util.isNullorEmpty(item.getHandleType())?"-":item.getHandleType()==1?"同意":"不同意";
                operLog.append("审批层级：").append(item.getApproveLevel()).append("；审批人：").append(item.getApproveNum()).append("；放行人： ").append(item.getHandleNum()).append("；放行结果：").append(handleResult).append("；备注：[").append(item.getMemo()).append("] 放行时间：").append(item.getHandleTime()).append("|");
            }
            excel.setDetail(operLog.toString());
            list.add(excel);
        });
        return list;
    }

    // 新建表单 案件状态只能是 0-草稿 1-正常 或者 2- 撤回
    public FlowCase insert(String flowId, Short caseStatus) {
        FlowCase flowCase = new FlowCase();
        flowCase.setFlowId(flowId);
        flowCase.setId(Util.getUUID());
        flowCase.setApplyNum(Util.userIdByShiro());
        flowCase.setDataTime(Util.currentDateTime());
        flowCase.setCaseStatus(caseStatus);
        flowCase.setApproveStatus((short) 0);
        flowCase.setApproveLevel((short)1);

        flowCaseMapper.insert(flowCase);
        return flowCase;
    }

    @Override
    public int update(FlowCase flowCase) {
        return flowCaseMapper.updateByPrimaryKeySelective(flowCase);
    }

    // 案件状态变更 草稿 -》 发送
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int draftToSubmit(String caseId) {
          FlowCase record = flowCaseMapper.selectByPrimaryKey(caseId);
//        List<FlowApproveTemp> temps = flowApproveTempService.listByFlowId(record.getFlowId(), (short) 1);
        flowApproveLogService.insertFlowApproveLog(record.getFlowId(), record.getId());
        record.setDataTime(Util.currentDateTime());
        record.setCaseStatus((short)0);
        record.setApproveStatus((short)0);
        return flowCaseMapper.updateByPrimaryKey(record);
}

    @Override
    public FlowCase get(String caseId) {
        FlowCase flowCase = flowCaseMapper.selectByPrimaryKey(caseId);
        // 设置审批日志数据
        List<FlowApproveLog> logs = flowApproveLogService.listByCaseId(caseId);
        flowCase.setFlowApproveLogs(logs);
        return flowCase;
    }

    @Override
    public FlowCase getFullCase(String caseId) {
        FlowCase flowCase = flowCaseMapper.selectByPrimaryKey(caseId);
        // 设置表单详情数据，包括原始数据表，字段，数据表明细结构JSON
        FlowInfo flowInfo =flowInfoService.get(flowCase.getFlowId());
        flowCase.setFlowInfo(flowInfo);
        flowCase.setFlowApproveLogs(flowApproveLogService.listByCaseIdWithProxy(flowCase.getId(), Util.userIdByShiro()));
        flowCase.setApplyUser(DataCache.EMPLOYEE.get(flowCase.getApplyNum()));
        List<Map<String, Object>> list = flowInfoService.listFlowData(flowCase.getFlowId(), caseId);
        flowCase.setData(list);
        return flowCase;
    }

    @Override
    public int completeCase(String caseId, Short caseStatus, Short approveStatus) {
        FlowCase record = new FlowCase();
        record.setId(caseId);
        record.setCaseStatus(caseStatus);
        record.setApproveStatus(approveStatus);
        record.setEndDate(Util.currentDateTime());
        return flowCaseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean caseCancel(FlowCase flowCase) {
        FlowInfo flowInfo = flowInfoService.get(flowCase.getFlowId());
        if (flowInfo.getCancel() == 0) return false;
        String table = flowInfo.getTableName();
        // 动态表名白名单校验，防止 SQL 注入
        if (!Util.isValidSqlIdentifier(table)) {
            return false;
        }
        String statusColumn;
        String idColumn;
        if ("CUS_FLOW_GQSQ".equals(table)) {
            statusColumn = "JOUR_STATUS";
            idColumn = "BATCH_NUM";
        } else {
            statusColumn = "FLOW_STATUS";
            idColumn = "CASE_ID";
        }
        int updateCount = flowCaseMapper.cancelCaseDetail(table, statusColumn, idColumn, flowCase.getId());
        flowCase.setCaseStatus((short) 2);
        flowCase.setModifyDate(Util.currentDateTime());
        int caseUpdate = flowCaseMapper.updateByPrimaryKeySelective(flowCase);
        if (updateCount == 0 || caseUpdate == 0) return false;
        return true;
    }


    // 案件审批
    @Transactional(rollbackFor = Exception.class)
    public FlowCase approve (FlowApproveLog approveLog) {
        String currentUserId = Util.userIdByShiro();
        String nextApproveNum = approveLog.getNextApproveNum();
        // 获取当前审批level 和最大level
        List<FlowApproveLog> logs = flowApproveLogService.listByCaseId(approveLog.getCaseId());

        FlowCase flowCase = flowCaseMapper.selectByPrimaryKey(approveLog.getCaseId());

        short curLevel = flowCase.getApproveLevel();

        // 确定当前 level 和 审批人及 代理人信息
        int maxLevel = logs.stream().mapToInt(FlowApproveLog::getApproveLevel).max().getAsInt();
        // 当前审批层级的审批人是谁
        List<FlowApproveLog> curFlowApproveLog = logs.stream().filter(e -> e.getApproveLevel() == curLevel).collect(Collectors.toList());

        String userId = flowProxyService.selectByProxyId(Util.userIdByShiro());
        List<FlowApproveLog> updateList = logs.stream().filter(e -> e.getApproveLevel() == curLevel && Util.isNullorEmpty(e.getHandleType()) && (userId.equals(e.getApproveNum()) || currentUserId.equals(e.getApproveNum()))).collect(Collectors.toList());

        // 判断当前层级涉及几个人审批
        int needToApproveLevel = (int)logs.stream().filter(e -> e.getApproveLevel() == curLevel && Util.isNullorEmpty(e.getHandleType())).count();

        if(updateList.size() < 1){
            if(logs.size() == 1) {
                // 当审批层级为1时，且审批模式为0，直接将审批状态改为已通过
                if (logs.get(0).getModel() == 0) {
                    flowCase.setApproveStatus((short) 1);
                    flowCase.setCaseStatus((short) 1);
                    flowCase.setEndDate(Util.getCurrentTimestamp());
                    logs.get(0).setApproveNum(Util.userIdByShiro());
                    logs.get(0).setHandleNum(Util.userIdByShiro());
                    logs.get(0).setHandleTime(Util.currentDateTime());
                    logs.get(0).setHandleType((short) 1);
                    logs.get(0).setMemo("无需放行(自动通过)");
                    flowApproveLogService.update(logs.get(0));
                }
                return flowCase;
            }
            return null;
        }
        // 写入放行情况，如果涉及代理后同一个人同一放行级别则一次完成放行
        short handleType = approveLog.getHandleType();
        short handleLevel = approveLog.getApproveLevel();
        for( FlowApproveLog item : updateList) {
            approveLog.setHandleTime(Util.currentDateTime());
            approveLog.setHandleNum(currentUserId);
            approveLog.setId(item.getId());
            flowApproveLogService.update(approveLog);
        }

        // 如果不同意直接结案
        if (handleType == 2 || (maxLevel == curLevel && needToApproveLevel == updateList.size())) {
            flowCase.setCaseStatus((short) 1); // 审批完成
            flowCase.setApproveStatus(approveLog.getHandleType()); // 与审批结果一直；不同意
            flowCase.setEndDate(Util.currentDateTime());
            flowCaseMapper.updateByPrimaryKey(flowCase);
            return flowCase;
        }
        if (maxLevel == curLevel) {
            // 完成审批
            return flowCase;
        } else {
            Employee user = DataCache.EMPLOYEE.get(currentUserId);
            List<FlowApproveLog> nextList = logs.stream().filter(e -> e.getApproveLevel() == curLevel+1 && Util.isNullorEmpty(e.getHandleType())).collect(Collectors.toList());
            for (FlowApproveLog item: nextList) {
                if(item.getModel() == 4) {
                    // 放行人上级
                    String fonter = "100,102,103,104,105";
                    String dir = "101,106,107,108,109";
                    String erxian = "";
                    String manager = "199,299,301";
                    String leader = "";
                    user.getJobLevel().indexOf("100,102,103,104,105");

                }else if(item.getModel() <= 5){
                    item.setApproveNum(nextApproveNum);
                }
                flowApproveLogService.update(item);
            }
            return flowCase;
        }

    }


    @Override
    public boolean caseNoApprovePass(String flowId, String caseId) {
        FlowApproveLogExample example = new FlowApproveLogExample();
        example.createCriteria().andCaseIdEqualTo(caseId).andHandleTypeIsNull().andModelEqualTo((short)0).andApproveLevelEqualTo((short)1);
        List<FlowApproveLog> logs = flowApproveLogService.list(example);
        if(logs.size() < 1) return false;
        for (FlowApproveLog log : logs) {
            log.setHandleType((short)1);
            log.setApproveNum(Util.userIdByShiro());
            log.setHandleNum(Util.userIdByShiro());
            log.setHandleTime(Util.currentDateTime());
            flowApproveLogService.update(log);
        }
        this.completeCase(logs.get(0).getCaseId(), (short)1, (short)1);
        flowInfoService.updateDetailStatus(flowId, caseId, (short)1);
        return true;
    }
}
