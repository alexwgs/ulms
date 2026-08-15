package com.cmbccd.ulms.flow.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.FlowApproveLogMapper;
import com.cmbccd.ulms.flow.dao.FlowCaseMapper;
import com.cmbccd.ulms.flow.domain.FlowApproveLog;
import com.cmbccd.ulms.flow.domain.FlowApproveLogExample;
import com.cmbccd.ulms.flow.domain.FlowApproveTemp;
import com.cmbccd.ulms.flow.domain.FlowCase;
import com.cmbccd.ulms.flow.service.FlowApproveLogService;
import com.cmbccd.ulms.flow.service.FlowApproveTempService;
import com.cmbccd.ulms.flow.service.FlowProxyService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FlowApproveLogServiceImpl implements FlowApproveLogService {

    @Resource
    private FlowApproveLogMapper flowApproveLogMapper;

    @Resource
    private FlowApproveTempService flowApproveTempService;

    @Resource
    private FlowProxyService flowProxyService;

    @Resource
    private FlowCaseMapper flowCaseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Msg submitApprove(List<FlowApproveLog> flowApproveLogs) {
        // String userId = Util.userIdByShiro();
        List<FlowApproveLog> updateApproveLogs = flowApproveLogs.stream().filter(e-> Util.isNullorEmpty(e.getHandleTime()) && !Util.isNullorEmpty(e.getHandleType())).collect(Collectors.toList());

        // 判定是否存在不同意的情况。则直接结案，更新案件状态
        boolean ifDisagree = updateApproveLogs.stream().filter(e -> "2".equals(String.valueOf(e.getHandleType()))).collect(Collectors.toList()).size() > 0;
        if(ifDisagree) {
            // 更新审批日志
            this.submitApproveLog(updateApproveLogs);

            // 更新案件状态
            this.completeCase(updateApproveLogs.get(0).getCaseId(), (short) 1, (short) 2);
            return Msg.success("[s001]审批不通过！").put("result", "disagree");
        }

        // 如当前级别已经完成审批，则需判定下一级别是否有审批人
        int curApproveLevel = updateApproveLogs.stream().findFirst().get().getApproveLevel();

        // 判定当前级别是否已经完成全部审批
        int count = flowApproveLogs.stream().filter(e -> e.getApproveLevel() == curApproveLevel && Util.isNullorEmpty(e.getHandleType())).collect(Collectors.toList()).size();
        if(count < 1 ) {
            // 获取下级审批列表
            List<FlowApproveLog> nextApproveLogs = flowApproveLogs.stream().filter(e -> e.getApproveLevel() == curApproveLevel + 1 ).collect(Collectors.toList());
            if(nextApproveLogs.size() < 1) {
                // 更新案件状态
                this.completeCase(updateApproveLogs.get(0).getCaseId(), (short) 1, (short) 1);
                // 更新审批日志
                this.submitApproveLog(updateApproveLogs);
                return Msg.success("[s002]审批通过，案件完成！").put("result", "agree");
            } else {
                // 判定下级审批是否有审批人
                List<FlowApproveLog> nextApproveLogs2 = nextApproveLogs.stream().filter(e -> Util.isNullorEmpty(e.getApproveNum())).collect(Collectors.toList());
                if(nextApproveLogs2.size() > 0) return Msg.error("[s003]没有选择下级审批人，无法完成放行。").put("result", "return"); // 没有选择下级审批人，无法完成放行。
                // 更新审批日志2
            this.submitApproveLog(updateApproveLogs);
                nextApproveLogs.forEach( e -> {
                flowApproveLogMapper.updateByPrimaryKeySelective(e);
            });
            FlowCase flowCase = new FlowCase();
            flowCase.setId(updateApproveLogs.get(0).getCaseId());
            flowCase.setApproveLevel((short) (curApproveLevel + 1));
            flowCaseMapper.updateByPrimaryKeySelective(flowCase);
            return Msg.success("[s003]审批通过，案件进入下一级审批！").put("result", "continue");
        }
    } else {
        // 更新审批日志
        this.submitApproveLog(updateApproveLogs);
            return Msg.success("[s004]审批通过，案件进入会签人处理！").put("result", "continue");
        }
    }

    @Override
    public int insert(FlowApproveLog record) {
        record.setId(Util.getUUID());
        return flowApproveLogMapper.insertSelective(record);
    }

    @Override
    public int update(FlowApproveLog record) {
        return flowApproveLogMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<FlowApproveLog> listByCaseId(String caseId) {
        FlowApproveLogExample example = new FlowApproveLogExample();
        FlowApproveLogExample.Criteria criteria = example.createCriteria();
        criteria.andCaseIdEqualTo(caseId);
        example.setOrderByClause("APPROVE_LEVEL asc");
        return flowApproveLogMapper.selectByExample(example);
    }

    @Override
    public List<FlowApproveLog> listByCaseIdWithProxy(String caseId, String userId) {
        return flowApproveLogMapper.selectApproveLogListWithProxy(caseId, userId);
    }

    @Override
    public List<String> listMyApproveCaseIds(String begDate, String endDate) {
        endDate = Util.getDateToday();
        if(Util.isNullorEmpty(begDate) || Util.isNullorEmpty(endDate)) {
            begDate = Util.getPastDate(180);
        }
        FlowApproveLogExample example = new FlowApproveLogExample();
        FlowApproveLogExample.Criteria criteria = example.createCriteria();
        criteria.andHandleNumEqualTo(Util.userIdByShiro());
        criteria.andHandleTimeBetween(begDate+" 00:00:00", endDate+" 23:59:59");
        List<FlowApproveLog> list = flowApproveLogMapper.selectByExample(example);
        return list.stream().map(FlowApproveLog::getCaseId).collect(Collectors.toList());
    }


    @Override
    public List<FlowApproveLog> listApproveLog(String id) {
        List<FlowApproveLog> list = flowApproveLogMapper.selectApproveLogList(id);
        return list;
    }

    @Override
    public List<FlowApproveLog> list(FlowApproveLogExample example) {
        return flowApproveLogMapper.selectByExample(example);
    }

    @Override
    public int submitApproveLog(List<FlowApproveLog> logs) {
        AtomicInteger count = new AtomicInteger();
        logs.forEach(e -> {
            e.setHandleTime(Util.currentDateTime());
            e.setHandleNum(Util.userIdByShiro());
            count.addAndGet(flowApproveLogMapper.updateByPrimaryKeySelective(e));
        });
        return count.get();
    }

    // 首次写入审批日志
    public List<FlowApproveLog> insertFlowApproveLog(String flowId, String CaseId) {
        // 符合条件则返回审批下一层级的数据列表
        List<FlowApproveTemp> list = flowApproveTempService.listByFlowId(flowId, (short) 1);
        List<FlowApproveLog> logList = new ArrayList<>();
        for(FlowApproveTemp item : list) {
            // 0-全部 1-发起人科室筛选 2-发起人组别筛选 3-发起人岗位筛选
            short require = item.getRequire();
            String requireInfo = item.getRequireInfo();
            short model = item.getModel();
            String modelInfo = item.getModelInfo();
            FlowApproveLog log = new FlowApproveLog();


            String userId = Util.userIdByShiro();
            Employee user = DataCache.getEmployees().get(userId);
            if (user == null) continue;
            log.setId(Util.getUUID());
            log.setCaseId(CaseId);
            log.setApproveLevel(item.getApproveLevel());
            log.setModel(model);
            log.setModelInfo(modelInfo);
            log.setName(item.getName());
            if (require == 0 || require == 1 && user.getDeptNum().indexOf(requireInfo) > -1 || require == 2 && user.getDeptGroup().indexOf(requireInfo) > -1 || require == 3 && user.getJobLevel().indexOf(requireInfo) > -1) {
                logList.add(log);
                flowApproveLogMapper.insert(log);
            } else {
                continue;
            }
        }
        return logList;
    }

    // 案件状态更新（原 FlowCaseService.completeCase 逻辑内联，用于打破与 FlowCaseService 的循环依赖）
    private int completeCase(String caseId, short caseStatus, short approveStatus) {
        FlowCase record = new FlowCase();
        record.setId(caseId);
        record.setCaseStatus(caseStatus);
        record.setApproveStatus(approveStatus);
        record.setEndDate(Util.currentDateTime());
        return flowCaseMapper.updateByPrimaryKeySelective(record);
    }

}
