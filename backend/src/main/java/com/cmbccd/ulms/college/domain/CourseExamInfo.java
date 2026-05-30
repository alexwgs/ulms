package com.cmbccd.ulms.college.domain;

import com.cmbccd.ulms.edu.domain.QuesBank;

public class CourseExamInfo {
    private String journo;

    private String quesCode;

    private String ploNum;

    private String userAnswer;

    private Short ifCorrect;

    private String handleDate;

    private Short quesOrder;

    private String examCode;

    private QuesBank question;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

    public String getQuesCode() {
        return quesCode;
    }

    public void setQuesCode(String quesCode) {
        this.quesCode = quesCode == null ? null : quesCode.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer == null ? null : userAnswer.trim();
    }

    public Short getIfCorrect() {
        return ifCorrect;
    }

    public void setIfCorrect(Short ifCorrect) {
        this.ifCorrect = ifCorrect;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate == null ? null : handleDate.trim();
    }

    public Short getQuesOrder() {
        return quesOrder;
    }

    public void setQuesOrder(Short quesOrder) {
        this.quesOrder = quesOrder;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode == null ? null : examCode.trim();
    }

    public void setQuestion(QuesBank question) {
        this.question = question;
    }

    public QuesBank getQuestion() {
        return question;
    }
}