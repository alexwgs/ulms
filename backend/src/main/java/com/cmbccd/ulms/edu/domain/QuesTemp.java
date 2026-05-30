package com.cmbccd.ulms.edu.domain;

public class QuesTemp {
    private String examCode;

    private String libCode;

    private String quesCode;

    private Short quesNum;

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode == null ? null : examCode.trim();
    }

    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode == null ? null : libCode.trim();
    }

    public String getQuesCode() {
        return quesCode;
    }

    public void setQuesCode(String quesCode) {
        this.quesCode = quesCode == null ? null : quesCode.trim();
    }

    public Short getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(Short quesNum) {
        this.quesNum = quesNum;
    }
}