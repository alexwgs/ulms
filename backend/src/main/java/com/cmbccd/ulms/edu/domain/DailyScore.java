package com.cmbccd.ulms.edu.domain;

public class DailyScore {
    private String quesDate;

    private String ploNum;

    private String ploName;

    private String deptNum;

    private String deptGroup;

    private String quesCode;

    private Short mistakes;

    private Short passFlag;

    private String firstAnswer;

    private Short articalStatus;

    private String studyBeg;

    private String studyEnd;

    private String moodType;

    private String moodMemo;

    private String moodDate;

    private Short valid;

    public String getQuesDate() {
        return quesDate;
    }

    public void setQuesDate(String quesDate) {
        this.quesDate = quesDate == null ? null : quesDate.trim();
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

    public String getQuesCode() {
        return quesCode;
    }

    public void setQuesCode(String quesCode) {
        this.quesCode = quesCode == null ? null : quesCode.trim();
    }

    public Short getMistakes() {
        return mistakes;
    }

    public void setMistakes(Short mistakes) {
        this.mistakes = mistakes;
    }

    public Short getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(Short passFlag) {
        this.passFlag = passFlag;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer == null ? null : firstAnswer.trim();
    }

    public Short getArticalStatus() {
        return articalStatus;
    }

    public void setArticalStatus(Short articalStatus) {
        this.articalStatus = articalStatus;
    }

    public String getStudyBeg() {
        return studyBeg;
    }

    public void setStudyBeg(String studyBeg) {
        this.studyBeg = studyBeg == null ? null : studyBeg.trim();
    }

    public String getStudyEnd() {
        return studyEnd;
    }

    public void setStudyEnd(String studyEnd) {
        this.studyEnd = studyEnd == null ? null : studyEnd.trim();
    }

    public String getMoodType() {
        return moodType;
    }

    public void setMoodType(String moodType) {
        this.moodType = moodType == null ? null : moodType.trim();
    }

    public String getMoodMemo() {
        return moodMemo;
    }

    public void setMoodMemo(String moodMemo) {
        this.moodMemo = moodMemo == null ? null : moodMemo.trim();
    }

    public String getMoodDate() {
        return moodDate;
    }

    public void setMoodDate(String moodDate) {
        this.moodDate = moodDate == null ? null : moodDate.trim();
    }

    public Short getValid() {
        return valid;
    }

    public void setValid(Short valid) {
        this.valid = valid;
    }

    public DailyScore() {
    }

    public DailyScore(String quesDate, String ploNum, String ploName, String deptNum, String deptGroup, String quesCode, Short mistakes, Short passFlag, Short articalStatus, Short valid) {
        this.quesDate = quesDate;
        this.ploNum = ploNum;
        this.ploName = ploName;
        this.deptNum = deptNum;
        this.deptGroup = deptGroup;
        this.quesCode = quesCode;
        this.mistakes = mistakes;
        this.passFlag = passFlag;
        this.articalStatus = articalStatus;
        this.valid = valid;
    }
}