package com.cmbccd.ulms.flow.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.FlowInfoMapper;
import com.cmbccd.ulms.flow.domain.FlowInfo;
import com.cmbccd.ulms.flow.domain.FlowTable;
import com.cmbccd.ulms.flow.service.FlowReportService;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class FlowReportServiceImpl implements FlowReportService {
    @Resource
    private FlowInfoMapper flowInfoMapper;

    public List<List<Object>> reportFlowData(FlowInfo flowInfo, List<String> caseIds) {
        String json = flowInfo.getTableInfo();
        List<FlowTable> tableConfig = JSONArray.parseArray(json, FlowTable.class);
        String tableName = flowInfo.getTableName();
        // 动态表名白名单校验，防止 SQL 注入
        if (!Util.isValidSqlIdentifier(tableName)) {
            return new ArrayList<>();
        }
        StringBuilder sqlColumnBuilder = new StringBuilder();
        Boolean isGqsq = tableName.equals("CUS_FLOW_GQSQ");
        List<List<Object>> result = new ArrayList<>();
        List<Object> header = new ArrayList<>();
        List<FlowTable> cols = tableConfig.stream().filter(item -> item.getStatus().equals("1")).collect(Collectors.toList());
        header.add("CASE_ID");
        header.add("状态");
        if (isGqsq) sqlColumnBuilder.append("BATCH_NUM,JOUR_STATUS,EAA_STATUS,");
        else sqlColumnBuilder.append("CASE_ID,FLOW_STATUS,");
        for (FlowTable item : cols) {
            if ("1".equals(item.getDictType())) {
                header.add("工号");
                header.add("姓名");
                header.add("科室");
                header.add("组别");
            } else {
                header.add(item.getTitle());
            }
            // 动态列名白名单校验，防止 SQL 注入
            if (item.getColName() == null || !Util.isValidSqlIdentifier(item.getColName())) {
                continue;
            }
            sqlColumnBuilder.append(item.getColName()).append(",");
        }
        result.add(header);
        String sqlColumn = sqlColumnBuilder.substring(0, sqlColumnBuilder.length() - 1);
        if (caseIds == null || caseIds.isEmpty()) return result;
        String idColumn = isGqsq ? "BATCH_NUM" : "CASE_ID";
        List<Map<String, Object>> baseList = flowInfoMapper.selectFlowDataByIds(sqlColumn, tableName, idColumn, caseIds);
        baseList.forEach(e -> {
            List<Object> row = new ArrayList<>();
            row.add(e.get("CASE_ID"));
            if (isGqsq)
                row.add(e.get("JOUR_STATUS").toString().equals("0") ? e.get("EAA_STATUS").toString().equals("1") ? "正常" : "审批中" : "撤销");
            else row.add(e.get("FLOW_STATUS").toString().equals("1") ? "正常" : "无效");
            for (FlowTable item : cols) {
                if ("1".equals(item.getDictType())) {
                    String userId = e.get("PLO_NUM") != null ? e.get("PLO_NUM").toString() : "";
                    Employee user = DataCache.EMPLOYEE.get(userId);
                    if (user != null) {
                        row.add(user.getPloNum());
                        row.add(user.getPloName());
                        row.add(user.getDeptName());
                        row.add(user.getGroupName());
                    } else {
                        row.add("-");
                        row.add("-");
                        row.add("-");
                        row.add("-");
                    }
                } else if ("2".equals(item.getDictType())) {
                    String code = e.get(item.getColName()) != null ? e.get(item.getColName()).toString() : "";
                    List<Dictionary> dicts = DataCache.Dict.stream().filter(t -> t.getName().equals(item.getDictName()) && t.getCode().equals(code)).collect(Collectors.toList());
                    if (dicts.size() > 0) {
                        row.add(dicts.get(0).getCodeval());
                    } else {
                        row.add("N/A");
                    }
                } else {
                    row.add(e.get(item.getColName()));
                }
            }
            result.add(row);
        });
        return result;
    }
}
