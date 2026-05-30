package com.cmbccd.ulms.college.domain.report;

import com.alibaba.excel.annotation.ExcelProperty;

public class CourseStudyDetail {
    @ExcelProperty("科室")
    private String deptNum;
    @ExcelProperty("组别")
    private String deptGroup;
    @ExcelProperty("员工编号")
    private String ploNum;
    @ExcelProperty("员工姓名")
    private String ploName;
    @ExcelProperty("金币")
    private Float coin;
    @ExcelProperty("授课时数")
    private Float hours;
    @ExcelProperty("报名时间")
    private String signDate;
    @ExcelProperty("在线学时总时长")
    private Double totalTime;
    @ExcelProperty("在线完成时长")
    private Double currTime;
    @ExcelProperty("在线学习完成情况")
    private String studyComp;
    @ExcelProperty("是否考试")
    private String ifExam;
    @ExcelProperty("考试通过")
    private String examComp;
    @ExcelProperty("是否评价")
    private String ifEval;
    @ExcelProperty("评价情况")
    private String evalComp;
    @ExcelProperty("评价时间")
    private String evalDate;
    @ExcelProperty("完成情况")
    private String coursePass;
    @ExcelProperty("完成时间")
    private String compDate;

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

    public Float getCoin() {
        return coin;
    }

    public void setCoin(Float coin) {
        this.coin = coin;
    }

    public Float getHours() {
        return hours;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public Double getCurrTime() {
        return currTime;
    }

    public void setCurrTime(Double currTime) {
        this.currTime = currTime;
    }

    public String getStudyComp() {
        return studyComp;
    }

    public void setStudyComp(String studyComp) {
        this.studyComp = studyComp;
    }

    public String getIfExam() {
        return ifExam;
    }

    public void setIfExam(String ifExam) {
        this.ifExam = ifExam;
    }

    public String getExamComp() {
        return examComp;
    }

    public void setExamComp(String examComp) {
        this.examComp = examComp;
    }

    public String getIfEval() {
        return ifEval;
    }

    public void setIfEval(String ifEval) {
        this.ifEval = ifEval;
    }

    public String getEvalComp() {
        return evalComp;
    }

    public void setEvalComp(String evalComp) {
        this.evalComp = evalComp;
    }

    public String getEvalDate() {
        return evalDate;
    }

    public void setEvalDate(String evalDate) {
        this.evalDate = evalDate;
    }

    public String getCoursePass() {
        return coursePass;
    }

    public void setCoursePass(String coursePass) {
        this.coursePass = coursePass;
    }

    public String getCompDate() {
        return compDate;
    }

    public void setCompDate(String compDate) {
        this.compDate = compDate;
    }
}
