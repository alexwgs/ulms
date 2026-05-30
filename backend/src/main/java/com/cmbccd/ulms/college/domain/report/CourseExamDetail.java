package com.cmbccd.ulms.college.domain.report;

import com.alibaba.excel.annotation.ExcelProperty;

public class CourseExamDetail {
    @ExcelProperty("科室")
    private String deptNum;
    @ExcelProperty("组别")
    private String deptGroup;
    @ExcelProperty("工号")
    private String ploNum;
    @ExcelProperty("姓名")
    private String ploName;
    @ExcelProperty("开始时间")
    private String begDate;
    @ExcelProperty("结束时间")
    private String endDate;
    @ExcelProperty("题目数量")
    private Short quesNum;
    @ExcelProperty("通过需答对")
    private Short passNum;
    @ExcelProperty("答对数量")
    private Short rightNum;
    @ExcelProperty("是否通过")
    private String passFlag;

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

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Short getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(Short quesNum) {
        this.quesNum = quesNum;
    }

    public Short getPassNum() {
        return passNum;
    }

    public void setPassNum(Short passNum) {
        this.passNum = passNum;
    }

    public Short getRightNum() {
        return rightNum;
    }

    public void setRightNum(Short rightNum) {
        this.rightNum = rightNum;
    }

    public String getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(String passFlag) {
        this.passFlag = passFlag;
    }
}
