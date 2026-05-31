package com.cmbccd.ulms.sys.service;

import java.util.List;
import java.util.Map;

public interface OperateLogService {

    int add(String module, String operate);

    // ==================== Dashboard 统计方法 ====================
    List<Map<String, Object>> getSubsysVisitTrend(int days);
}
