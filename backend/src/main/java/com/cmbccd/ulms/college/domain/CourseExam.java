package com.cmbccd.ulms.college.domain;

public class CourseExam {
    private String journo;

    private String courseId;

    private String ploNum;

    private String begDate;

    private String endDate;

    private Short quesNum;

    private Short passNum;

    private Short passFlag;

    private Short rightNum;

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

    public Short getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(Short passFlag) {
        this.passFlag = passFlag;
    }

    public Short getRightNum() {
        return rightNum;
    }

    public void setRightNum(Short rightNum) {
        this.rightNum = rightNum;
    }
}