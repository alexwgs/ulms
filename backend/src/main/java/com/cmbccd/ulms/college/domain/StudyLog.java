package com.cmbccd.ulms.college.domain;

import java.math.BigDecimal;

public class StudyLog {
    private String courseId;

    private String ploNum;

    private String studyDate;

    private String lecturer;

    private BigDecimal coin;

    private BigDecimal hours;

    private Short teachMethod;

    private Short studyComp;

    private Short ifExam;

    private String examId;

    private Short examComp;

    private Short ifEval;

    private String evalDate;

    private Short evalComp;

    private Short coursePass;

    private String compDate;

    private String tempId;

    private Course course;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate == null ? null : studyDate.trim();
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer == null ? null : lecturer.trim();
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

    public Short getTeachMethod() {
        return teachMethod;
    }

    public void setTeachMethod(Short teachMethod) {
        this.teachMethod = teachMethod;
    }

    public Short getStudyComp() {
        return studyComp;
    }

    public void setStudyComp(Short studyComp) {
        this.studyComp = studyComp;
    }

    public Short getIfExam() {
        return ifExam;
    }

    public void setIfExam(Short ifExam) {
        this.ifExam = ifExam;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId == null ? null : examId.trim();
    }

    public Short getExamComp() {
        return examComp;
    }

    public void setExamComp(Short examComp) {
        this.examComp = examComp;
    }

    public Short getIfEval() {
        return ifEval;
    }

    public void setIfEval(Short ifEval) {
        this.ifEval = ifEval;
    }

    public String getEvalDate() {
        return evalDate;
    }

    public void setEvalDate(String evalDate) {
        this.evalDate = evalDate == null ? null : evalDate.trim();
    }

    public Short getEvalComp() {
        return evalComp;
    }

    public void setEvalComp(Short evalComp) {
        this.evalComp = evalComp;
    }

    public Short getCoursePass() {
        return coursePass;
    }

    public void setCoursePass(Short coursePass) {
        this.coursePass = coursePass;
    }

    public String getCompDate() {
        return compDate;
    }

    public void setCompDate(String compDate) {
        this.compDate = compDate == null ? null : compDate.trim();
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId == null ? null : tempId.trim();
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "StudyLog{" +
                "courseId='" + courseId + '\'' +
                ", ploNum='" + ploNum + '\'' +
                ", studyDate='" + studyDate + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", coin=" + coin +
                ", hours=" + hours +
                ", teachMethod=" + teachMethod +
                ", studyComp=" + studyComp +
                ", ifExam=" + ifExam +
                ", examId='" + examId + '\'' +
                ", examComp=" + examComp +
                ", ifEval=" + ifEval +
                ", evalDate='" + evalDate + '\'' +
                ", evalComp=" + evalComp +
                ", coursePass=" + coursePass +
                ", compDate='" + compDate + '\'' +
                ", tempId='" + tempId + '\'' +
                ", course=" + course +
                '}';
    }
}