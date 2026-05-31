package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.OperateLogMapper;
import com.cmbccd.ulms.sys.domain.OperateLog;
import com.cmbccd.ulms.sys.service.OperateLogService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Override
    public int add(String module, String operate) {
        String userId = Util.userIdByShiro();
        OperateLog record = new OperateLog();
        record.setDataDate(Util.getDateToday());
        record.setDataTime(Util.currentDateTime());
        record.setPloNum(userId);
        record.setModule(module.toUpperCase());
        record.setOperate(operate);
        return operateLogMapper.insert(record);
    }

    // ==================== Dashboard 统计方法实现 ====================

    @Override
    public List<Map<String, Object>> getSubsysVisitTrend(int days) {
        String begDate = Util.getPastDate(days - 1);
        String endDate = Util.getDateToday();
        List<Map<String, Object>> rawList = operateLogMapper.subsysVisitTrend(begDate, endDate);
        if (rawList == null) return List.of();
        return rawList.stream().map(row -> {
            Map<String, Object> clean = new LinkedHashMap<>();
            row.forEach((k, v) -> clean.put(k.toLowerCase(), v));
            return clean;
        }).collect(Collectors.toList());
    }
}
