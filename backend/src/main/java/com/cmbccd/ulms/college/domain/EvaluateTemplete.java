package com.cmbccd.ulms.college.domain;


public class EvaluateTemplete {

    private String tempId;
    private String tempName;
    private Integer tempNo;
    private Integer quesType;
    private Integer tempStat;
    private String quesCont;
    private String cont;

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public Integer getTempNo() {
        return tempNo;
    }

    public void setTempNo(Integer tempNo) {
        this.tempNo = tempNo;
    }

    public Integer getQuesType() {
        return quesType;
    }

    public void setQuesType(Integer quesType) {
        this.quesType = quesType;
    }

    public Integer getTempStat() {
        return tempStat;
    }

    public void setTempStat(Integer tempStat) {
        this.tempStat = tempStat;
    }

    public String getQuesCont() {
        return quesCont;
    }

    public void setQuesCont(String quesCont) {
        this.quesCont = quesCont;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    @Override
    public String toString() {
        return "EvaluateTemplete{" +
                "tempId='" + tempId + '\'' +
                ", tempName='" + tempName + '\'' +
                ", tempNo=" + tempNo +
                ", quesType=" + quesType +
                ", tempStat=" + tempStat +
                ", quesCont='" + quesCont + '\'' +
                ", cont='" + cont + '\'' +
                '}';
    }
}
