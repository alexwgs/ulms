package com.cmbccd.ulms.rpa.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.rpa.dao.ToolListMapper;
import com.cmbccd.ulms.rpa.dao.ToolReportMapper;
import com.cmbccd.ulms.rpa.domain.ToolList;
import com.cmbccd.ulms.rpa.domain.ToolReport;
import com.cmbccd.ulms.rpa.domain.ToolReportExample;
import com.cmbccd.ulms.rpa.domain.ToolReportVO;
import com.cmbccd.ulms.rpa.service.ToolReportService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ToolReportServiceImpl implements ToolReportService {

    private final ToolReportMapper toolReportMapper;

    private final ToolListMapper toolListMapper;

    public ToolReportServiceImpl(ToolReportMapper toolReportMapper, ToolListMapper toolListMapper) {
        this.toolReportMapper = toolReportMapper;
        this.toolListMapper = toolListMapper;
    }

    @Override
    public List<ToolReport> list(@RequestParam Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        ToolReportExample example = new ToolReportExample();
        ToolReportExample.Criteria criteria = example.createCriteria();

        if(Util.isNullorEmpty(params.get("begDate")) || Util.isNullorEmpty(params.get("endDate"))) {
            criteria.andRunTimeBetween(Util.getDateToday()+" 00:00:00",Util.getDateToday()+" 23:59:59");
        } else {
            criteria.andRunTimeBetween(params.get("begDate")+" 00:00:00",params.get("endDate")+" 23:59:59");
        }

        if (!Util.isNullorEmpty(params.get("query"))) {
            criteria.andToolNameEqualTo(params.get("query"));
        }

        if (!Util.isNullorEmpty(params.get("runUser"))) {
            criteria.andUserIdEqualTo(params.get("runUser"));
        }

        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<ToolReport> list = toolReportMapper.selectByExample(example);
        list.forEach( item -> item.setUser(DataCache.EMPLOYEE.get(item.getUserId())));
        return list;
    }

    @Override
    public int add(ToolReport record) {
        record.setUserId(Util.userIdByShiro());
        record.setReturnTime(Util.currentDateTime());
        if(!Util.isNullorEmpty(record.getRunParam()) && record.getRunParam().length() > 2000) {
            record.setRunParam(record.getRunParam().substring(0,2000));
        }
        return toolReportMapper.insert(record);
    }

    @Override
    public Long getToolCoolDown(String toolId) throws ParseException {
        ToolList tool = toolListMapper.selectByPrimaryKey(toolId);
        int runTimes = tool.getRunTimes();
        String endDate = Util.getCurrentTimestamp();
        String begDate = Util.timeStampToDate(String.valueOf(Long.parseLong(Util.getCurrentTimestamp()) -runTimes), "yyyy-MM-dd HH:mm:ss");
        ToolReportExample example = new ToolReportExample();
        ToolReportExample.Criteria criteria = example.createCriteria();
        criteria.andToolIdEqualTo(toolId);
        criteria.andRunTimeBetween(begDate,endDate);
//        criteria.andReturnCodeNotEqualTo("ERROR");
        example.setOrderByClause("run_time desc");
        List<ToolReport> list = toolReportMapper.selectByExample(example);
        if(!list.isEmpty()) {
            return runTimes + Util.dateTimeToUnixTIme(list.get(0).getRunTime()) - Long.parseLong(Util.getCurrentTimestamp());
        }
        else return 0L;
    }

    @Override
    public List<ToolReportVO> download(Map<String, String> params) {
        ToolReportExample example = new ToolReportExample();
        ToolReportExample.Criteria criteria = example.createCriteria();

        if(Util.isNullorEmpty(params.get("begDate")) || Util.isNullorEmpty(params.get("endDate"))) {
            criteria.andRunTimeBetween(Util.getDateToday()+" 00:00:00",Util.getDateToday()+" 23:59:59");
        } else {
            criteria.andRunTimeBetween(params.get("begDate")+" 00:00:00",params.get("endDate")+" 23:59:59");
        }

        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        List<ToolReport> listResource = toolReportMapper.selectByExample(example);
        List<ToolReportVO> list = new ArrayList<>();
        listResource.forEach( item -> {
            ToolReportVO toolReportVO = new ToolReportVO();
            Employee user = DataCache.EMPLOYEE.get(item.getUserId());
            toolReportVO.setDeptName(user.getDeptName());
            toolReportVO.setGroupName(user.getGroupName());
            toolReportVO.setUserName(user.getPloName());
            BeanUtils.copyProperties(item, toolReportVO);
            list.add(toolReportVO);
        });
        return list;
    }

    @Override
    public Map<String,Map<String, Object>> chart(Map<String, String> params) {
        ToolReportExample example = new ToolReportExample();
        ToolReportExample.Criteria criteria = example.createCriteria();
        if(Util.isNullorEmpty(params.get("begDate")) || Util.isNullorEmpty(params.get("endDate"))) {
            criteria.andRunTimeBetween(Util.getDateToday()+" 00:00:00",Util.getDateToday()+" 23:59:59");
        } else {
            criteria.andRunTimeBetween(params.get("begDate")+" 00:00:00",params.get("endDate")+" 23:59:59");
        }

        if (!Util.isNullorEmpty(params.get("query"))) {
            criteria.andToolNameEqualTo(params.get("query"));
        }

        if (!Util.isNullorEmpty(params.get("runUser"))) {
            criteria.andUserIdEqualTo(params.get("runUser"));
        }

        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        List<ToolReport> list = toolReportMapper.selectByExample(example);

        Map<String,Map<String, Object>> chartData = new HashMap<>();
        // 按照list中的runTime的前10位进行分组和统计
        Map<String, Object> map1 = new HashMap<>();
        Map<String, List<ToolReport>> groupByDate = new TreeMap<>(list.stream()
                .collect(Collectors.groupingBy(toolReport -> (toolReport.getRunTime()).substring(0,10))));

        List<Object> row1 = new ArrayList<>();
        List<Object> row2 = new ArrayList<>();
        List<Object> row3 = new ArrayList<>();
        groupByDate.forEach((date, toolReports) -> {
            row1.add(date);
            row2.add(toolReports.stream().count());
            row3.add(toolReports.stream().map(ToolReport::getUserId).distinct().count());
        });
        map1.put("coloum", row1);
        map1.put("series1", row2);
        map1.put("series2", row3);
        chartData.put("chartDataUse", map1);

        Map<String, Object> map2 = new HashMap<>();
        Map<String, List<ToolReport>> groupByProuduct = list.stream()
                .collect(Collectors.groupingBy(ToolReport::getToolName));
        List<Object> row4 = new ArrayList<>();
        List<Object> row5 = new ArrayList<>();
        List<Object> row6 = new ArrayList<>();
        groupByProuduct.forEach((product, toolReports) -> {
            row4.add(product);
            row5.add(toolReports.stream().count());
            row6.add(toolReports.stream().map(ToolReport::getUserId).distinct().count());
        });
        map2.put("coloum", row4);
        map2.put("series1", row5);
        map2.put("series2", row6);
        chartData.put("chartDataProduct", map2);

        // 经办调用次数
        Map<String, Object> map3 = new HashMap<>();
        List<Object> row7 = new ArrayList<>();
        List<Object> row8 = new ArrayList<>();
        Map<String, Long> userUsageCount = list.stream()
                .collect(Collectors.groupingBy(
                        ToolReport::getUserId,
                        Collectors.counting()
                ));

        // 按计数数量从大到小排序，并取前20个
        List<Map.Entry<String, Long>> sortedUserUsageCount = userUsageCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Collections.reverseOrder()))
                .limit(20)
                .collect(Collectors.toList());
        sortedUserUsageCount.forEach(item -> {
            row7.add(DataCache.EMPLOYEE.get(item.getKey()).getPloName());
            row8.add(item.getValue());
        });
        map3.put("coloum", row7);
        map3.put("series", row8);
        chartData.put("chartDataUser", map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("size", userUsageCount.size());
        chartData.put("totalUserSize", map4);
        return chartData;
    }
}