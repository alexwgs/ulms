package com.cmbccd.ulms.college.domain;

import java.math.BigDecimal;

public class CourseProgress {
    private String journo;

    private String courseId;

    private String ploNum;

    private String fileId;

    private BigDecimal currTime;

    private BigDecimal totalTime;

    private String studyDate;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public BigDecimal getCurrTime() {
        return currTime;
    }

    public void setCurrTime(BigDecimal currTime) {
        this.currTime = currTime;
    }

    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate == null ? null : studyDate.trim();
    }
}