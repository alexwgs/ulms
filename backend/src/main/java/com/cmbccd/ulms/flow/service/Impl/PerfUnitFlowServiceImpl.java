package com.cmbccd.ulms.flow.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.PerfUnitMapper;
import com.cmbccd.ulms.flow.domain.PerfUnit;
import com.cmbccd.ulms.flow.domain.PerfUnitExample;
import com.cmbccd.ulms.flow.service.PerfUnitFlowService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PerfUnitFlowServiceImpl<PerfUnitFlowMapper> implements PerfUnitFlowService {
    @Resource
    private PerfUnitMapper perfUnitMapper;

    @Override
    public int insert(PerfUnit record,String caseId) {
        record.setId(Util.getUUID());
        record.setModifyId(Util.userIdByShiro());
        record.setModifyTime(Util.currentDateTime());
        record.setCaseId(caseId);
        return perfUnitMapper.insert(record);
    }

    @Override
    public Msg check(List<PerfUnit> list) {
        List<PerfUnit> errList = new ArrayList<>();
        list.forEach( item -> {
            PerfUnit perfUnit = perfUnitMapper.check(item.getPloNum(), item.getBegMonth(), item.getEndMonth());
            if(!Util.isNullorEmpty(perfUnit)){
                errList.add(item);
            }
        });
        if (errList.size()> 0) return Msg.success("部分人员存在已提交的特殊单元申请，请仔细复核下列名单，调整无误后再提交！").put("data", errList).put("type", "confirm");

        return Msg.success().put("type", "pass");
    }

    @Override
    public Msg batchInsert(List<PerfUnit> list, String caseId) {
        String userId = Util.userIdByShiro();
        String dateTime = Util.currentDateTime();
        list.forEach( item -> {
            item.setCaseId(caseId);
            item.setId(Util.getUUID());
            item.setModifyTime(dateTime);
            item.setModifyId(userId);
            item.setFlowStatus((short) 0);
            perfUnitMapper.insert(item);
        });
        return Msg.success();
    }

    @Override
    public int update(PerfUnit record) {
        return perfUnitMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delete(String id) {
        return perfUnitMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PerfUnit> list(Map<String, String> params) {
        PerfUnitExample example = new PerfUnitExample();
        // PerfUnitExample.Criteria criteria = example.createCriteria();
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        // String begDate = Util.isNullorEmpty(params.get("begDate"))?Util.getDateToday():params.get("begDate");
        // String endDate = Util.isNullorEmpty(params.get("endDate"))?Util.getPastDate(180):params.get("begDate");

        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<PerfUnit> list = perfUnitMapper.selectByExample(example);
        return list;
    }
}
