package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.OperLog;
import com.cmbccd.ulms.sys.domain.OperLogExample;

import java.util.List;
import java.util.Map;

public interface OperLogService {
    int insert(OperLog operLog);

    List<OperLog> list(OperLogExample example);

    // ==================== Dashboard 统计方法 ====================
    Map<String, Object> getTodayStats();

    List<Map<String, Object>> getUvPvTrend(int days);

    List<Map<String, Object>> getMethodStatsTrend(int days);

    List<OperLog> getTodayErrorList();
}
