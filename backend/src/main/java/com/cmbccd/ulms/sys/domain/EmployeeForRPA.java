package com.cmbccd.ulms.sys.domain;

import com.alibaba.excel.annotation.ExcelProperty;

public class EmployeeForRPA {
    @ExcelProperty("员编")
    private String ploNum;
    @ExcelProperty("姓名")
    private String ploName;
    @ExcelProperty("科室")
    private String deptName;
    @ExcelProperty("组别")
    private String groupName;
    private String deptNum;
    private String deptGroup;
    private String batchGroup;
    private String jobLevel;
    private String ploStatus;
    private String inDate;
    private String outDate;
    private String ploSap;

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum;
    }

    public String getPloName() {
        return ploName;
    }

    public void setPloName(String ploName) {
        this.ploName = ploName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum;
    }

    public String getDeptGroup() {
        return deptGroup;
    }

    public void setDeptGroup(String deptGroup) {
        this.deptGroup = deptGroup;
    }

    public String getBatchGroup() {
        return batchGroup;
    }

    public void setBatchGroup(String batchGroup) {
        this.batchGroup = batchGroup;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getPloStatus() {
        return ploStatus;
    }

    public void setPloStatus(String ploStatus) {
        this.ploStatus = ploStatus;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getPloSap() {
        return ploSap;
    }

    public void setPloSap(String ploSap) {
        this.ploSap = ploSap;
    }

    @Override
    public String toString() {
        return "EmployeeForRPA{" +
                "ploNum='" + ploNum + '\'' +
                ", ploName='" + ploName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", deptNum='" + deptNum + '\'' +
                ", deptGroup='" + deptGroup + '\'' +
                ", batchGroup='" + batchGroup + '\'' +
                ", jobLevel='" + jobLevel + '\'' +
                ", ploStatus='" + ploStatus + '\'' +
                ", inDate='" + inDate + '\'' +
                ", outDate='" + outDate + '\'' +
                ", ploSap='" + ploSap + '\'' +
                '}';
    }
}
