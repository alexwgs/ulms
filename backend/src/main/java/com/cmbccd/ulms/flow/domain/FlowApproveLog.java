package com.cmbccd.ulms.flow.domain;

public class FlowApproveLog {
    private String id;

    private String caseId;

    private Short approveLevel;

    private String approveNum;

    private String handleNum;

    private String handleTime;

    private Short handleType;

    private String memo;

    private String name;

    private Short model;

    private String modelInfo;

    private String nextApproveNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
    }

    public Short getApproveLevel() {
        return approveLevel;
    }

    public void setApproveLevel(Short approveLevel) {
        this.approveLevel = approveLevel;
    }

    public String getApproveNum() {
        return approveNum;
    }

    public void setApproveNum(String approveNum) {
        this.approveNum = approveNum == null ? null : approveNum.trim();
    }

    public String getHandleNum() {
        return handleNum;
    }

    public void setHandleNum(String handleNum) {
        this.handleNum = handleNum == null ? null : handleNum.trim();
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime == null ? null : handleTime.trim();
    }

    public Short getHandleType() {
        return handleType;
    }

    public void setHandleType(Short handleType) {
        this.handleType = handleType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getNextApproveNum() {
        return nextApproveNum;
    }

    public void setNextApproveNum(String nextApproveNum) {
        this.nextApproveNum = nextApproveNum;
    }

    @Override
    public String toString() {
        return "FlowApproveLog{" +
                "id='" + id + '\'' +
                ", caseId='" + caseId + '\'' +
                ", approveLevel=" + approveLevel +
                ", approveNum='" + approveNum + '\'' +
                ", handleNum='" + handleNum + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", handleType=" + handleType +
                ", memo='" + memo + '\'' +
                ", name='" + name + '\'' +
                ", model=" + model +
                ", modelInfo='" + modelInfo + '\'' +
                ", nextApproveNum='" + nextApproveNum + '\'' +
                '}';
    }
}