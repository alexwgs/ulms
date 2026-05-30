package com.cmbccd.ulms.edu.domain;

public class QuesDispute {
    private String journo;

    private String examCode;

    private String quesCode;

    private String userAnswer;

    private String replyAnswer;

    private Short disputeResult;

    private String ploNum;

    private String dataDate;

    private String handlePlo;

    private String handleDate;

    private String disputeMemo;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode == null ? null : examCode.trim();
    }

    public String getQuesCode() {
        return quesCode;
    }

    public void setQuesCode(String quesCode) {
        this.quesCode = quesCode == null ? null : quesCode.trim();
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer == null ? null : userAnswer.trim();
    }

    public String getReplyAnswer() {
        return replyAnswer;
    }

    public void setReplyAnswer(String replyAnswer) {
        this.replyAnswer = replyAnswer == null ? null : replyAnswer.trim();
    }

    public Short getDisputeResult() {
        return disputeResult;
    }

    public void setDisputeResult(Short disputeResult) {
        this.disputeResult = disputeResult;
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
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

    public String getDisputeMemo() {
        return disputeMemo;
    }

    public void setDisputeMemo(String disputeMemo) {
        this.disputeMemo = disputeMemo == null ? null : disputeMemo.trim();
    }
}