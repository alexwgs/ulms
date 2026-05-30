package com.cmbccd.ulms.edu.domain;

public class ExamInfo {
    private String examCode;

    private String examName;

    private String begDate;

    private String endDate;

    private Integer examTime;

    private Integer quesTime;

    private Integer ifBreak;

    private Integer ifAgain;

    private Integer audioAgain;

    private Integer videoAgain;

    private Integer scoreMethod;

    private Integer unitScore;

    private Integer quesRandom;

    private Integer optionRandom;

    private Integer optionOrder;

    private Integer wrongBreak;

    private Integer examUse;

    private Integer examStat;

    private String handlePlo;

    private String handleDate;

    private Integer examCondition;

    private String perfDate;

    private Integer quesNum;

    private Integer score;

    private Integer ifRead;

    private String disputeDate;

    private String areaCode;

    private Integer skipQues;

    private Integer bookExam;

    private String quesTemplate;
    
    private String bookTime;
    
    private String areaInfo;

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode == null ? null : examCode.trim();
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
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

    public Integer getExamTime() {
        return examTime;
    }

    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }

    public Integer getQuesTime() {
        return quesTime;
    }

    public void setQuesTime(Integer quesTime) {
        this.quesTime = quesTime;
    }

    public Integer getIfBreak() {
        return ifBreak;
    }

    public void setIfBreak(Integer ifBreak) {
        this.ifBreak = ifBreak;
    }

    public Integer getIfAgain() {
        return ifAgain;
    }

    public void setIfAgain(Integer ifAgain) {
        this.ifAgain = ifAgain;
    }

    public Integer getAudioAgain() {
        return audioAgain;
    }

    public void setAudioAgain(Integer audioAgain) {
        this.audioAgain = audioAgain;
    }

    public Integer getVideoAgain() {
        return videoAgain;
    }

    public void setVideoAgain(Integer videoAgain) {
        this.videoAgain = videoAgain;
    }

    public Integer getScoreMethod() {
        return scoreMethod;
    }

    public void setScoreMethod(Integer scoreMethod) {
        this.scoreMethod = scoreMethod;
    }

    public Integer getUnitScore() {
        return unitScore;
    }

    public void setUnitScore(Integer unitScore) {
        this.unitScore = unitScore;
    }

    public Integer getQuesRandom() {
        return quesRandom;
    }

    public void setQuesRandom(Integer quesRandom) {
        this.quesRandom = quesRandom;
    }

    public Integer getOptionRandom() {
        return optionRandom;
    }

    public void setOptionRandom(Integer optionRandom) {
        this.optionRandom = optionRandom;
    }

    public Integer getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(Integer optionOrder) {
        this.optionOrder = optionOrder;
    }

    public Integer getWrongBreak() {
        return wrongBreak;
    }

    public void setWrongBreak(Integer wrongBreak) {
        this.wrongBreak = wrongBreak;
    }

    public Integer getExamUse() {
        return examUse;
    }

    public void setExamUse(Integer examUse) {
        this.examUse = examUse;
    }

    public Integer getExamStat() {
        return examStat;
    }

    public void setExamStat(Integer examStat) {
        this.examStat = examStat;
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

    public Integer getExamCondition() {
        return examCondition;
    }

    public void setExamCondition(Integer examCondition) {
        this.examCondition = examCondition;
    }

    public String getPerfDate() {
        return perfDate;
    }

    public void setPerfDate(String perfDate) {
        this.perfDate = perfDate == null ? null : perfDate.trim();
    }

    public Integer getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(Integer quesNum) {
        this.quesNum = quesNum;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIfRead() {
        return ifRead;
    }

    public void setIfRead(Integer ifRead) {
        this.ifRead = ifRead;
    }

    public String getDisputeDate() {
        return disputeDate;
    }

    public void setDisputeDate(String disputeDate) {
        this.disputeDate = disputeDate == null ? null : disputeDate.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public Integer getSkipQues() {
        return skipQues;
    }

    public void setSkipQues(Integer skipQues) {
        this.skipQues = skipQues;
    }

    public Integer getBookExam() {
        return bookExam;
    }

    public void setBookExam(Integer bookExam) {
        this.bookExam = bookExam;
    }

    public String getQuesTemplate() {
        return quesTemplate;
    }

    public void setQuesTemplate(String quesTemplate) {
        this.quesTemplate = quesTemplate == null ? null : quesTemplate.trim();
    }

	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}

	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}
    
    
}