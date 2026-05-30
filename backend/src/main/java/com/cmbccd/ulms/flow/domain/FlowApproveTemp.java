package com.cmbccd.ulms.flow.domain;

public class FlowApproveTemp {
    private String id;

    private String name;

    private Short approveLevel;

    private Short model;

    private String modelInfo;

    private String flowId;

    private Short status;

    private Short require;

    private String requireInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getApproveLevel() {
        return approveLevel;
    }

    public void setApproveLevel(Short approveLevel) {
        this.approveLevel = approveLevel;
    }

    public Short getModel() {
        return model;
    }

    public void setModel(Short model) {
        this.model = model;
    }

    public String getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(String modelInfo) {
        this.modelInfo = modelInfo == null ? null : modelInfo.trim();
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId == null ? null : flowId.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getRequire() {
        return require;
    }

    public void setRequire(Short require) {
        this.require = require;
    }

    public String getRequireInfo() {
        return requireInfo;
    }

    public void setRequireInfo(String requireInfo) {
        this.requireInfo = requireInfo == null ? null : requireInfo.trim();
    }
}