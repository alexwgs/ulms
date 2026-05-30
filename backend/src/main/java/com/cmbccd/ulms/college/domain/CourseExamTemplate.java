package com.cmbccd.ulms.college.domain;

public class CourseExamTemplate {
    private String courseId;

    private String libCode;

    private Short quesNum;

    private Short passNum;

    private Short examTime;

    private Short repeatFlag;

    private Short optionRandom;

    private String handlePlo;

    private String handleDate;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode == null ? null : libCode.trim();
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

    public Short getExamTime() {
        return examTime;
    }

    public void setExamTime(Short examTime) {
        this.examTime = examTime;
    }

    public Short getRepeatFlag() {
        return repeatFlag;
    }

    public void setRepeatFlag(Short repeatFlag) {
        this.repeatFlag = repeatFlag;
    }

    public Short getOptionRandom() {
        return optionRandom;
    }

    public void setOptionRandom(Short optionRandom) {
        this.optionRandom = optionRandom;
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
}