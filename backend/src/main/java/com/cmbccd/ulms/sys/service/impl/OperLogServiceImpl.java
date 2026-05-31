package com.cmbccd.ulms.sys.service.impl;


import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.OperLogMapper;
import com.cmbccd.ulms.sys.domain.OperLog;
import com.cmbccd.ulms.sys.domain.OperLogExample;
import com.cmbccd.ulms.sys.service.OperLogService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OperLogServiceImpl implements OperLogService {

    @Resource
    private OperLogMapper operLogMapper;

    @Override
    public int insert(OperLog operLog) {
        operLog.setId(Util.getUUID());
        operLog.setOperTime(Util.currentDateTime()); // 时间
        operLog.setPloNum(Util.userIdByShiro()); // 获取用户名（真实环境中，肯定有工具类获取当前登录者的账号或ID的，或者从token中解析而来）
        if(Util.isNullorEmpty(operLog.getContent()) && !Util.isNullorEmpty(operLog.getRequestMethod())) {
            String method = operLog.getRequestMethod();
            operLog.setContent("GET".equals(method) ? "查询操作" : "PUT".equals(method) ? "更新操作" :
                    "POST".equals(method) ? "新增操作" :
                            "DELETE".equals(method) ?"删除操作" : "未知操作");
        }
        return operLogMapper.insert(operLog);
    }

    @Override
    public List<OperLog> list(OperLogExample example) {
        return operLogMapper.selectByExample(example);
    }

    // ==================== Dashboard 统计方法实现 ====================

    @Override
    public Map<String, Object> getTodayStats() {
        String today = Util.getDateToday();
        Map<String, Object> raw = operLogMapper.statsToday(today);
        // 处理 null 值
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("uv", raw != null && raw.get("uv") != null ? raw.get("uv") : 0);
        result.put("pv", raw != null && raw.get("pv") != null ? raw.get("pv") : 0);
        result.put("exceptionCount", raw != null && raw.get("exceptionCount") != null ? raw.get("exceptionCount") : 0);
        return result;
    }

    @Override
    public List<Map<String, Object>> getUvPvTrend(int days) {
        String begDate = Util.getPastDate(days - 1);
        String endDate = Util.getDateToday();
        return toCamelKeys(operLogMapper.uvPvTrend(begDate, endDate));
    }

    @Override
    public List<Map<String, Object>> getMethodStatsTrend(int days) {
        String begDate = Util.getPastDate(days - 1);
        String endDate = Util.getDateToday();
        return toCamelKeys(operLogMapper.methodStatsTrend(begDate, endDate));
    }

    @Override
    public List<OperLog> getTodayErrorList() {
        return operLogMapper.errorListToday(Util.getDateToday());
    }

    /**
     * 将 Oracle 返回的 HashMap key（带引号的小写别名）统一转换为小写 key，
     * 确保前端接收到的 JSON key 为小写格式
     */
    private List<Map<String, Object>> toCamelKeys(List<Map<String, Object>> list) {
        if (list == null) return List.of();
        return list.stream().map(row -> {
            Map<String, Object> clean = new LinkedHashMap<>();
            row.forEach((k, v) -> clean.put(k.toLowerCase(), v));
            return clean;
        }).collect(Collectors.toList());
    }
}
