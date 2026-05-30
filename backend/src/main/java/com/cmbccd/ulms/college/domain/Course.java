package com.cmbccd.ulms.college.domain;

import java.math.BigDecimal;
import java.util.List;

public class Course {
    private String courseId;

    private String courseName;

    private String lecturer;

    private BigDecimal coin;

    private BigDecimal hours;

    private Short courseType;

    private Short teachMethod;

    private String teachObject;

    private Short studyType;

    private String begDate;

    private String endDate;

    private Short ifExam;

    private String examId;

    private Short ifEval;

    private String tempId;

    private String coverImg;

    private Short status;

    private Short evalDate;

    private BigDecimal courseScore;

    private String handlePlo;

    private String handleDate;

    private Integer studyNum;

    private Short topFlag;

    private BigDecimal recommendFlag;

    private String recommend;

    private String courseDes;

    private List<CourseFile> courseFile;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
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

    public Short getCourseType() {
        return courseType;
    }

    public void setCourseType(Short courseType) {
        this.courseType = courseType;
    }

    public Short getTeachMethod() {
        return teachMethod;
    }

    public void setTeachMethod(Short teachMethod) {
        this.teachMethod = teachMethod;
    }

    public String getTeachObject() {
        return teachObject;
    }

    public void setTeachObject(String teachObject) {
        this.teachObject = teachObject == null ? null : teachObject.trim();
    }

    public Short getStudyType() {
        return studyType;
    }

    public void setStudyType(Short studyType) {
        this.studyType = studyType;
    }

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate == null ? null : begDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
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

    public Short getIfEval() {
        return ifEval;
    }

    public void setIfEval(Short ifEval) {
        this.ifEval = ifEval;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId == null ? null : tempId.trim();
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    public String getHandlePlo() {
        return handlePlo;
    }

    public void setHandlePlo(String handlePlo) {
        this.handlePlo = handlePlo == null ? null : handlePlo.trim();
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate == null ? null : handleDate.trim();
    }

    public Integer getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(Integer studyNum) {
        this.studyNum = studyNum;
    }

    public Short getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(Short topFlag) {
        this.topFlag = topFlag;
    }

    public BigDecimal getRecommendFlag() {
        return recommendFlag;
    }

    public void setRecommendFlag(BigDecimal recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend == null ? null : recommend.trim();
    }

    public String getCourseDes() {
        return courseDes;
    }

    public void setCourseDes(String courseDes) {
        this.courseDes = courseDes == null ? null : courseDes.trim();
    }

    public List<CourseFile> getCourseFile() {
        return courseFile;
    }

    public void setCourseFile(List<CourseFile> courseFile) {
        this.courseFile = courseFile;
    }
}