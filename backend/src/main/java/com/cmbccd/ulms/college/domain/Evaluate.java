package com.cmbccd.ulms.college.domain;

import java.util.List;

public class Evaluate {
    private String journo;

    private String courseId;

    private String tempId;

    private String ploNum;

    private String handleDate;

    private Integer ifAnon;

    List<EvaluateTemplete> evaluate;

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

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId == null ? null : tempId.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate == null ? null : handleDate.trim();
    }

    public Integer getIfAnon() {
        return ifAnon;
    }

    public void setIfAnon(Integer ifAnon) {
        this.ifAnon = ifAnon;
    }

    public List<EvaluateTemplete> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<EvaluateTemplete> evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "Evaluate{" +
                "journo='" + journo + '\'' +
                ", courseId='" + courseId + '\'' +
                ", tempId='" + tempId + '\'' +
                ", ploNum='" + ploNum + '\'' +
                ", handleDate='" + handleDate + '\'' +
                ", ifAnon=" + ifAnon +
                ", evaluate=" + evaluate +
                '}';
    }
}