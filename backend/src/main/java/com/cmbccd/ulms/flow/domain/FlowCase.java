package com.cmbccd.ulms.flow.domain;

import com.cmbccd.ulms.sys.domain.Employee;

import java.util.List;

public class FlowCase {


    private String id;

    private String flowId;

    private String dataTime;

    private String applyNum;

    private Short caseStatus;

    private Short approveStatus;

    private String modifyDate;

    private String endDate;

    private Short approveLevel;

    private FlowInfo flowInfo;

    private Employee applyUser;

    private List<FlowApproveLog> flowApproveLogs;

    private Object data;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId == null ? null : flowId.trim();
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime == null ? null : dataTime.trim();
    }

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum == null ? null : applyNum.trim();
    }

    public Short getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(Short caseStatus) {
        this.caseStatus = caseStatus;
    }

    public Short getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Short approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate == null ? null : modifyDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public Short getApproveLevel() {
        return approveLevel;
    }

    public FlowInfo getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(FlowInfo flowInfo) {
        this.flowInfo = flowInfo;
    }

    public void setApproveLevel(Short approveLevel) {
        this.approveLevel = approveLevel;
    }

    public Employee getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Employee applyUser) {
        this.applyUser = applyUser;
    }


    public List<FlowApproveLog> getFlowApproveLogs() {
        return flowApproveLogs;
    }

    public void setFlowApproveLogs(List<FlowApproveLog> flowApproveLogs) {
        this.flowApproveLogs = flowApproveLogs;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FlowCase{" +
                "id='" + id + '\'' +
                ", flowId='" + flowId + '\'' +
                ", dataTime='" + dataTime + '\'' +
                ", applyNum='" + applyNum + '\'' +
                ", caseStatus=" + caseStatus +
                ", approveStatus=" + approveStatus +
                ", modifyDate='" + modifyDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", approveLevel=" + approveLevel +
                ", flowInfo=" + flowInfo +
                ", applyUser=" + applyUser +
                ", flowApproveLogs=" + flowApproveLogs +
                ", data=" + data +
                '}';
    }
}