package com.cmbccd.ulms.edu.domain;

public class BrushScore {
    private String journo;

    private String quesCode;

    private String libCode;

    private String ploNum;

    private String ploName;

    private String deptNum;

    private String deptGroup;

    private String answer;

    private Integer passFlag;

    private String dataDate;

    private String dataTime;

    private String answerDate;

    private String answerTime;
    
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

    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode == null ? null : libCode.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getPloName() {
        return ploName;
    }

    public void setPloName(String ploName) {
        this.ploName = ploName == null ? null : ploName.trim();
    }

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum == null ? null : deptNum.trim();
    }

    public String getDeptGroup() {
        return deptGroup;
    }

    public void setDeptGroup(String deptGroup) {
        this.deptGroup = deptGroup == null ? null : deptGroup.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Integer getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(Integer passFlag) {
        this.passFlag = passFlag;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime == null ? null : dataTime.trim();
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate == null ? null : answerDate.trim();
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime == null ? null : answerTime.trim();
    }

	public QuesBank getQuestion() {
		return question;
	}

	public void setQuestion(QuesBank question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "BrushScore [journo=" + journo + ", quesCode=" + quesCode + ", libCode=" + libCode + ", ploNum=" + ploNum
				+ ", ploName=" + ploName + ", deptNum=" + deptNum + ", deptGroup=" + deptGroup + ", answer=" + answer
				+ ", passFlag=" + passFlag + ", dataDate=" + dataDate + ", dataTime=" + dataTime + ", answerDate="
				+ answerDate + ", answerTime=" + answerTime + ", question=" + question + "]";
	}
    
}