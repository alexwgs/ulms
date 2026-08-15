package com.cmbccd.ulms.flow.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.FlowInfoMapper;
import com.cmbccd.ulms.flow.domain.FlowInfo;
import com.cmbccd.ulms.flow.domain.FlowInfoExample;
import com.cmbccd.ulms.flow.service.FlowApproveTempService;
import com.cmbccd.ulms.flow.service.FlowInfoService;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.Employee;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlowInfoServiceImpl implements FlowInfoService {

    @Resource
    private FlowInfoMapper flowInfoMapper;

    @Resource
    private FlowApproveTempService flowApproveTempService;

    @Override
    public List<FlowInfo> list(Map<String, String> params) {
        FlowInfoExample example = new FlowInfoExample();
        FlowInfoExample.Criteria criteria = example.createCriteria();
        Map<String, Integer> pageParams = Util.innitTablePages(params);

        if (!Util.isNullorEmpty(params.get("status"))) {
            criteria.andStatusEqualTo(Short.parseShort(params.get("status")));
        }
        example.setOrderByClause(" SORT ");
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<FlowInfo> list = flowInfoMapper.selectByExample(example);
        return list;
    }

    public List<FlowInfo> list(Short status) {
        FlowInfoExample example = new FlowInfoExample();
        FlowInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(status);
        List<FlowInfo> list = flowInfoMapper.selectByExample(example);
        list.forEach(e -> {
            e.setFlowApproveTemp(flowApproveTempService.listRequireByFlowId(e.getId(),(short)1));
        });
        return list;
    }

    @Override
    public int insert(FlowInfo record) {
        record.setId(Util.getUUID());
        return flowInfoMapper.insert(record);
    }

    @Override
    public FlowInfo get(String id) {
        return flowInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(FlowInfo record) {
        return flowInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delete(String id) {
        return flowInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateDetailStatus(String flowId, String caseId, Short approveResult) {
        FlowInfo flowInfo = flowInfoMapper.selectByPrimaryKey(flowId);
        String tableName = flowInfo.getTableName();
        // 动态表名白名单校验，防止 SQL 注入
        if (!Util.isValidSqlIdentifier(tableName)) {
            return 0;
        }
        tableName = tableName.toUpperCase();
        List<String> columnNames = Arrays.asList("CASE_ID", "FLOW_STATUS");
        int columnCount = flowInfoMapper.countTableColume(tableName, columnNames);
        String statusColumn;
        String idColumn;
        if (columnCount > 1) {
            statusColumn = "FLOW_STATUS";
            idColumn = "CASE_ID";
        } else {
            statusColumn = "EAA_STATUS";
            idColumn = "BATCH_NUM";
        }
        return flowInfoMapper.updateDetailStatus(tableName, statusColumn, idColumn, approveResult, caseId);
    }

    @Override
    public List<Map<String, Object>> listFlowData(String flowId, String caseId) {
        FlowInfo flowInfo = flowInfoMapper.selectByPrimaryKey(flowId);
        JSONArray json = JSONArray.parseArray(flowInfo.getTableInfo());
        String tableName = flowInfo.getTableName();
        // 动态表名白名单校验，防止 SQL 注入
        if (!Util.isValidSqlIdentifier(tableName)) {
            return null;
        }
        // 判断ID列名
        String idColumn = tableName.equals("CUS_FLOW_GQSQ") ? "BATCH_NUM" : "CASE_ID";
        StringBuilder sqlColumnBuilder = new StringBuilder();
        List<JSONObject> getDictColumn = new ArrayList<>();
        List<String> getUserColumn = new ArrayList<>();
        for (Object o : json) {
            JSONObject obj = (JSONObject) o;
            if ("0".equals(obj.get("status").toString())) continue;
            String colName = obj.get("colName") != null ? obj.get("colName").toString() : "";
            // 动态列名白名单校验，防止 SQL 注入
            if (!Util.isValidSqlIdentifier(colName)) {
                continue;
            }
            sqlColumnBuilder.append(colName).append(",");

            if ("1".equals(obj.get("dictType").toString())) {
                getUserColumn.add(colName);
            }
            if ("2".equals(obj.get("dictType").toString())) {
                getDictColumn.add(obj);
            }
        }
        if (sqlColumnBuilder.length() < 2) return null;
        String sqlColumn = sqlColumnBuilder.substring(0, sqlColumnBuilder.length() - 1);
        List<Map<String, Object>> baseList = flowInfoMapper.selectFlowData(sqlColumn, tableName, idColumn, caseId);
        List<Map<String, Object>> list = new ArrayList<>();
        baseList.forEach(e -> {
            Map<String, Object> b = new HashMap<>();
            if (getUserColumn.size() > 0) {
                getUserColumn.forEach(u -> {
                    String userId = e.get(u) != null ? e.get(u).toString() : "";
                    Employee user = DataCache.EMPLOYEE.get(userId);
                    if (user != null) {
                        e.put(u + "_plo_num", user.getPloNum());
                        e.put(u + "_plo_name", user.getPloName());
                        e.put(u + "_Dept_name", user.getDeptName());
                        e.put(u + "_Group_name", user.getGroupName());
                    }
                });
            }
            if (getDictColumn.size() > 0) {
                getDictColumn.forEach(d -> {
                    String dictName = d.get("dictName").toString();
                    String colName = d.get("colName").toString();
                    String codeVal = Util.isNullorEmpty(e.get(colName)) ? "异常-N/A" : e.get(colName).toString();
                    List<Dictionary> dicts = DataCache.Dict.stream().filter(item -> item.getName().equals(dictName) && item.getCode().equals(codeVal)).collect(Collectors.toList());
                    if (dicts.size() > 0)
                        e.put(colName, dicts.get(0).getCodeval());
                    else e.put(colName, "异常-N/A");
                });
            }
            e.forEach((k, v) -> b.put(Util.underlineToCamel(k), v));
            list.add(b);
        });
        return list;
    }






}
