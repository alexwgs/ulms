package com.cmbccd.ulms.flow.domain;

import java.util.List;

public class FlowInfo {
    private String id;

    private String categroy;

    private String flowName;

    private String tableName;

    private String approveTemp;

    private Short sort;

    private Short status;

    private Short cancel;

    private Short draft;

    private String path;

    private String tableInfo;
    private List<FlowApproveTemp> flowApproveTemp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCategroy() {
        return categroy;
    }

    public void setCategroy(String categroy) {
        this.categroy = categroy == null ? null : categroy.trim();
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName == null ? null : flowName.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getApproveTemp() {
        return approveTemp;
    }

    public void setApproveTemp(String approveTemp) {
        this.approveTemp = approveTemp == null ? null : approveTemp.trim();
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getCancel() {
        return cancel;
    }

    public void setCancel(Short cancel) {
        this.cancel = cancel;
    }

    public Short getDraft() {
        return draft;
    }

    public void setDraft(Short draft) {
        this.draft = draft;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(String tableInfo) {
        this.tableInfo = tableInfo == null ? null : tableInfo.trim();
    }

    public List<FlowApproveTemp> getFlowApproveTemp() {
        return flowApproveTemp;
    }

    public void setFlowApproveTemp(List<FlowApproveTemp> flowApproveTemp) {
        this.flowApproveTemp = flowApproveTemp;
    }
}