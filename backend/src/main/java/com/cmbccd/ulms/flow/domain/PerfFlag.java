package com.cmbccd.ulms.flow.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.cmbccd.ulms.sys.domain.Employee;

public class PerfFlag {


    @ExcelIgnore
    private String id;
    @ExcelProperty("申请单号")
    private String caseId;
    @ExcelProperty("员编")
    private String ploNum;
    @ExcelProperty("计入小组")
    private Short groupFlag;
    @ExcelProperty("计入科室")
    private Short deptFlag;
    @ExcelProperty("开始月份")
    @DateTimeFormat("yyyy-MM")
    private String begMonth;
    @ExcelProperty("结束月份")
    @DateTimeFormat("yyyy-MM")
    private String endMonth;
    @ExcelProperty("备注")
    private String memo;
    @ExcelProperty("状态")
    private Short flowStatus;

    private String modifyTime;

    private String modifyId;

    private Employee user;

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

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public Short getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Short groupFlag) {
        this.groupFlag = groupFlag;
    }

    public Short getDeptFlag() {
        return deptFlag;
    }

    public void setDeptFlag(Short deptFlag) {
        this.deptFlag = deptFlag;
    }

    public String getBegMonth() {
        return begMonth;
    }

    public void setBegMonth(String begMonth) {
        this.begMonth = begMonth == null ? null : begMonth.trim();
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth == null ? null : endMonth.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Short getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Short flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime == null ? null : modifyTime.trim();
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId == null ? null : modifyId.trim();
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PerfFlag{" +
                "id='" + id + '\'' +
                ", caseId='" + caseId + '\'' +
                ", ploNum='" + ploNum + '\'' +
                ", groupFlag=" + groupFlag +
                ", deptFlag=" + deptFlag +
                ", begMonth='" + begMonth + '\'' +
                ", endMonth='" + endMonth + '\'' +
                ", memo='" + memo + '\'' +
                ", flowStatus=" + flowStatus +
                ", modifyTime='" + modifyTime + '\'' +
                ", modifyId='" + modifyId + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}