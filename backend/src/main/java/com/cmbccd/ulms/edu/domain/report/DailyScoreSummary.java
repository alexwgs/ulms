package com.cmbccd.ulms.edu.domain.report;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;

public class DailyScoreSummary {

    @ExcelProperty("科室")
    private String deptNum;
    @ExcelProperty("组别")
    private String deptGroup;
    @ExcelProperty("工号")
    private String ploNum;
    @ExcelProperty("姓名")
    private String ploName;
    @ExcelProperty("总天数")
    private int totalDay;
    @ExcelProperty("打卡总天数")
    private int punchDays;
    @ExcelProperty("打卡率")
    private BigDecimal punchRate;
    @ExcelProperty("当天签到数")
    private int signDays;
    @ExcelProperty("签到率")
    private BigDecimal signRate;
    @ExcelProperty("首对题数")
    private int firstRightNum;
    @ExcelProperty("首对率")
    private BigDecimal firstRightRate;
    @ExcelProperty("公布栏数量")
    private int studyNum;
    @ExcelProperty("平均学习时长")
    private BigDecimal studyTime;

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

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public int getPunchDays() {
        return punchDays;
    }

    public void setPunchDays(int punchDays) {
        this.punchDays = punchDays;
    }

    public BigDecimal getPunchRate() {
        return punchRate;
    }

    public void setPunchRate(BigDecimal punchRate) {
        this.punchRate = punchRate;
    }

    public int getSignDays() {
        return signDays;
    }

    public void setSignDays(int signDays) {
        this.signDays = signDays;
    }

    public BigDecimal getSignRate() {
        return signRate;
    }

    public void setSignRate(BigDecimal signRate) {
        this.signRate = signRate;
    }

    public int getFirstRightNum() {
        return firstRightNum;
    }

    public void setFirstRightNum(int firstRightNum) {
        this.firstRightNum = firstRightNum;
    }

    public BigDecimal getFirstRightRate() {
        return firstRightRate;
    }

    public void setFirstRightRate(BigDecimal firstRightRate) {
        this.firstRightRate = firstRightRate;
    }

    public int getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(int studyNum) {
        this.studyNum = studyNum;
    }

    public BigDecimal getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(BigDecimal studyTime) {
        this.studyTime = studyTime;
    }

    @Override
    public String toString() {
        return "DailyScoreSummary{" +
                "deptNum='" + deptNum + '\'' +
                ", deptGroup='" + deptGroup + '\'' +
                ", ploNum='" + ploNum + '\'' +
                ", ploName='" + ploName + '\'' +
                ", totalDay=" + totalDay +
                ", punchDays=" + punchDays +
                ", punchRate=" + punchRate +
                ", signDays=" + signDays +
                ", signRate=" + signRate +
                ", firstRightNum=" + firstRightNum +
                ", firstRightRate=" + firstRightRate +
                ", studyNum=" + studyNum +
                ", studyTime=" + studyTime +
                '}';
    }
}
