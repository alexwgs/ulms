package com.cmbccd.ulms.flow.service.Impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.PerfFlagMapper;
import com.cmbccd.ulms.flow.domain.PerfFlag;
import com.cmbccd.ulms.flow.domain.PerfFlagExample;
import com.cmbccd.ulms.flow.service.PerfFlagFlowService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PerfFlagFlowServiceImpl<PerfUnitFlowMapper> implements PerfFlagFlowService {
    @Resource
    private PerfFlagMapper perfFlagMapper;


    @Override
    public int insert(PerfFlag record, String caseId) {
        record.setId(Util.getUUID());
        record.setModifyId(Util.userIdByShiro());
        record.setModifyTime(Util.currentDateTime());
        record.setCaseId(caseId);
        return perfFlagMapper.insert(record);
    }

    @Override
    public Msg check(List<PerfFlag> list) {
        List<PerfFlag> errList = new ArrayList<>();
        list.forEach( item -> {
            PerfFlag perfUnit = perfFlagMapper.check(item.getPloNum(), item.getBegMonth(), item.getEndMonth());
            if(!Util.isNullorEmpty(perfUnit)){
                errList.add(item);
            }
        });
        if (errList.size()> 0) return Msg.success("部分人员存在已提交的不计入小组/科室的表单，请仔细复核下列名单，调整无误后再提交！").put("data", errList).put("type", "confirm");

        return Msg.success().put("type", "pass");
    }

    @Override
    public Msg batchInsert(List<PerfFlag> list, String caseId) {
        String userId = Util.userIdByShiro();
        String dateTime = Util.currentDateTime();
        list.forEach( item -> {
            item.setCaseId(caseId);
            item.setId(Util.getUUID());
            item.setModifyTime(dateTime);
            item.setModifyId(userId);
            item.setFlowStatus((short) 0);
            perfFlagMapper.insert(item);
        });
        return Msg.success();
    }

    @Override
    public int update(PerfFlag record) {
        return perfFlagMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delete(String id) {
        return perfFlagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PerfFlag> list(Map<String, String> params) {
        PerfFlagExample example = new PerfFlagExample();
        // PerfFlagExample.Criteria criteria = example.createCriteria();
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        // String begDate = Util.isNullorEmpty(params.get("begDate"))?Util.getDateToday():params.get("begDate");
        // String endDate = Util.isNullorEmpty(params.get("endDate"))?Util.getPastDate(180):params.get("begDate");

        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<PerfFlag> list = perfFlagMapper.selectByExample(example);
        return list;
    }
}
