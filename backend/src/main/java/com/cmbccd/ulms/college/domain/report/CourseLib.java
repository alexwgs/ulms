package com.cmbccd.ulms.college.domain.report;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;

public class CourseLib {
    @ExcelProperty("状态")
    private String status;
    @ExcelProperty("课程ID")
    private Long courseId;
    @ExcelProperty("课程名称")
    private String courseName;
    @ExcelProperty("讲师")
    private String lecturer;
    @ExcelProperty("金币")
    private BigDecimal coin;
    @ExcelProperty("学习时长")
    private BigDecimal hours;
    @ExcelProperty("在线学习时长")
    private BigDecimal duration;
    @ExcelProperty("课程类型")
    private String courseType;
    @ExcelProperty("课程类型")
    private String teachMethod;
    @ExcelProperty("授课对象")
    private String teachObject;
    @ExcelProperty("学习类型")
    private String studyType;
    @ExcelProperty("开始时间")
    private String begDate;
    @ExcelProperty("结束时间")
    private String endDate;
    @ExcelProperty("考试")
    private String ifExam;
    @ExcelProperty("评价")
    private String ifEval;
    @ExcelProperty("最晚评价时间")
    private Short evalDate;
    @ExcelProperty("课程评分")
    private BigDecimal courseScore;
    @ExcelProperty("学习人数")
    private Integer studyNum;
    @ExcelProperty("首页")
    private String topFlag;
    @ExcelProperty("推荐")
    private String recommend;
    @ExcelProperty("操作人工号")
    private String handlePlo;
    @ExcelProperty("操作时间")
    private String handleDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public BigDecimal getCoin() {
        return coin;
    }

    public void setCoin(BigDecimal coin) {
        this.coin = coin;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getTeachMethod() {
        return teachMethod;
    }

    public void setTeachMethod(String teachMethod) {
        this.teachMethod = teachMethod;
    }

    public String getTeachObject() {
        return teachObject;
    }

    public void setTeachObject(String teachObject) {
        this.teachObject = teachObject;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
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

    public String getIfExam() {
        return ifExam;
    }

    public void setIfExam(String ifExam) {
        this.ifExam = ifExam;
    }

    public String getIfEval() {
        return ifEval;
    }

    public void setIfEval(String ifEval) {
        this.ifEval = ifEval;
    }

    public Short getEvalDate() {
        return evalDate;
    }

    public void setEvalDate(Short evalDate) {
        this.evalDate = evalDate;
    }

    public BigDecimal getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(BigDecimal courseScore) {
        this.courseScore = courseScore;
    }

    public Integer getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(Integer studyNum) {
        this.studyNum = studyNum;
    }

    public String getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(String topFlag) {
        this.topFlag = topFlag;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getHandlePlo() {
        return handlePlo;
    }

    public void setHandlePlo(String handlePlo) {
        this.handlePlo = handlePlo;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }
}
