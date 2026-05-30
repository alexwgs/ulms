package com.cmbccd.ulms.flow.service.Impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.FlowGqsqMapper;
import com.cmbccd.ulms.flow.domain.FlowGqsq;
import com.cmbccd.ulms.flow.domain.FlowGqsqExample;
import com.cmbccd.ulms.flow.service.FLowGqsqService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.PublicService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FlowGqsqServiceImpl implements FLowGqsqService {
    @Resource
    private FlowGqsqMapper flowGqsqMapper;
    @Resource
    private PublicService publicService;


    @Override
    public int insert(FlowGqsq record, String caseId) {
        record.setBatchNum(caseId);
        record.setJourStatus("0");
        String journo = publicService.getJourno();
        record.setJourno(journo);
        record.setHandlePlo(Util.userIdByShiro());
        record.setInDate(Util.currentDateTime());
        // 格式化开始时间和结束时间
        record.setBegTime(Util.fourDigiToTime(record.getBegTime()));
        record.setEndTime(Util.fourDigiToTime(record.getEndTime()));
        // 获取详情
        Employee user = DataCache.EMPLOYEE.get(record.getPloNum());
        record.setDeptNum(user.getDeptNum());
        record.setDeptGroup(user.getDeptGroup());
        return flowGqsqMapper.insert(record);
    }

    @Override
    public Msg batchInsert(List<FlowGqsq> list, String caseId) {
        int count = 0;
        for (FlowGqsq flowGqsq : list) {
            String journo = publicService.getJourno();
            flowGqsq.setBatchNum(caseId);
            flowGqsq.setJourStatus("0");
            flowGqsq.setJourno(journo);
            flowGqsq.setHandlePlo(Util.userIdByShiro());
            flowGqsq.setInDate(Util.currentDateTime());
            flowGqsq.setBegTime(Util.fourDigiToTime(flowGqsq.getBegTime()));
            flowGqsq.setEndTime(Util.fourDigiToTime(flowGqsq.getEndTime()));
            Employee user = DataCache.EMPLOYEE.get(flowGqsq.getPloNum());
            flowGqsq.setDeptNum(user.getDeptNum());
            flowGqsq.setDeptGroup(user.getDeptGroup());
            count += flowGqsqMapper.insert(flowGqsq);
        }
        return Msg.success("成功申请"+count+"条记录！");
    }

    @Override
    public Msg check(List<FlowGqsq> list) {
        List<FlowGqsq> errList = new ArrayList<>();
        list.forEach( item -> {
            // 格式化开始时间和结束时间
//            item.setBegTime(Util.fourDigiToTime(item.getBegTime()));
//            item.setEndTime(Util.fourDigiToTime(item.getEndTime()));
            FlowGqsq flowGqsqs = flowGqsqMapper.check(item.getPloNum(), item.getDataDate(), Util.fourDigiToTime(item.getBegTime()), Util.fourDigiToTime(item.getEndTime()));
            if(!Util.isNullorEmpty(flowGqsqs)){
                errList.add(item);
            }
        });
        if (errList.size()> 0) return Msg.success("部分人员存在已提交的不计入小组/科室的表单，请认证复核下列名单！").put("data", errList).put("type", "confirm");

        return Msg.success().put("type", "pass");
    }

    @Override
    public int update(FlowGqsq record) {
        return flowGqsqMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delete(String id) {
        return flowGqsqMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<FlowGqsq> list(Map<String, String> params) {
        FlowGqsqExample example = new FlowGqsqExample();
        FlowGqsqExample.Criteria criteria = example.createCriteria();
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String begDate = Util.isNullorEmpty(params.get("begDate"))?Util.getDateToday():params.get("begDate");
        String endDate = Util.isNullorEmpty(params.get("endDate"))?Util.getPastDate(180):params.get("endDate");
        criteria.andBegTimeGreaterThanOrEqualTo(begDate+" 00:00:00");
        criteria.andEndTimeLessThanOrEqualTo(endDate+" 23:59:59");
        
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<FlowGqsq> list = flowGqsqMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<Map<String, String>> authSeq() {
        String userId = Util.userIdByShiro();
        List<Map<String, String>> maps = flowGqsqMapper.listAuthSeq(userId);
        return maps;
    }
}
