package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.OperLogService;
import com.cmbccd.ulms.sys.service.OperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Dashboard 仪表盘数据接口
 * 提供当日统计、趋势图表等聚合数据
 */
@RestController
@RequestMapping("sys/dashboard")
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Resource
    private OperLogService operLogService;

    @Resource
    private OperateLogService operateLogService;

    /**
     * 今日统计数据（UV、PV、异常数）+ 今日异常列表
     */
    @GetMapping("todayStats")
    public Msg todayStats() {
        try {
            Map<String, Object> stats = operLogService.getTodayStats();
            List<?> errorList = operLogService.getTodayErrorList();
            return Msg.success()
                    .put("todayStats", stats)
                    .put("errorList", errorList);
        } catch (Exception e) {
            log.error("获取今日统计数据失败", e);
            return Msg.error("获取今日统计数据失败");
        }
    }

    /**
     * 系统 UV/PV 趋势
     * @param days 统计天数，默认 7
     */
    @GetMapping("uvPvTrend")
    public Msg uvPvTrend(@RequestParam(defaultValue = "7") int days) {
        try {
            List<Map<String, Object>> data = operLogService.getUvPvTrend(days);
            return Msg.success(data);
        } catch (Exception e) {
            log.error("获取UV/PV趋势数据失败", e);
            return Msg.error("获取UV/PV趋势数据失败");
        }
    }

    /**
     * 请求方法统计趋势（POST/PUT/DELETE 次数、异常数、平均响应时间）
     * @param days 统计天数，默认 7
     */
    @GetMapping("methodStatsTrend")
    public Msg methodStatsTrend(@RequestParam(defaultValue = "7") int days) {
        try {
            List<Map<String, Object>> data = operLogService.getMethodStatsTrend(days);
            return Msg.success(data);
        } catch (Exception e) {
            log.error("获取请求统计趋势数据失败", e);
            return Msg.error("获取请求统计趋势数据失败");
        }
    }

    /**
     * 子系统访问趋势（按模块统计 UV/PV）
     * @param days 统计天数，默认 7
     */
    @GetMapping("subsysVisitTrend")
    public Msg subsysVisitTrend(@RequestParam(defaultValue = "7") int days) {
        try {
            List<Map<String, Object>> data = operateLogService.getSubsysVisitTrend(days);
            return Msg.success(data);
        } catch (Exception e) {
            log.error("获取子系统访问趋势数据失败", e);
            return Msg.error("获取子系统访问趋势数据失败");
        }
    }
}
