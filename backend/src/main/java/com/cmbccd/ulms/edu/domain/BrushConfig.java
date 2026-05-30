package com.cmbccd.ulms.edu.domain;

public class BrushConfig {
    private String brushCode;

    private String groupId;

    private Integer monthLimit;

    private Integer dayLimit;

    private Integer audioRepeat;

    private Integer videoRepeat;

    private Integer optionRand;

    private Integer mistakeFirst;

    private Integer answerTime;
    
    private Integer dayCount;
    
    private Integer monthCount;
    
    private String libCode;

    private Short wrongDay;

    private Short wrongLimit;

    private Short collectDay;

    private Short collectLimit;

    private String groupName;

    public String getBrushCode() {
        return brushCode;
    }

    public void setBrushCode(String brushCode) {
        this.brushCode = brushCode == null ? null : brushCode.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Integer getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(Integer monthLimit) {
        this.monthLimit = monthLimit;
    }

    public Integer getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Integer dayLimit) {
        this.dayLimit = dayLimit;
    }

    public Integer getAudioRepeat() {
        return audioRepeat;
    }

    public void setAudioRepeat(Integer audioRepeat) {
        this.audioRepeat = audioRepeat;
    }

    public Integer getVideoRepeat() {
        return videoRepeat;
    }

    public void setVideoRepeat(Integer videoRepeat) {
        this.videoRepeat = videoRepeat;
    }

    public Integer getOptionRand() {
        return optionRand;
    }

    public void setOptionRand(Integer optionRand) {
        this.optionRand = optionRand;
    }

    public Integer getMistakeFirst() {
        return mistakeFirst;
    }

    public void setMistakeFirst(Integer mistakeFirst) {
        this.mistakeFirst = mistakeFirst;
    }

    public Integer getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Integer answerTime) {
        this.answerTime = answerTime;
    }

    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode == null ? null : libCode.trim();
    }

    public Short getWrongDay() {
        return wrongDay;
    }

    public void setWrongDay(Short wrongDay) {
        this.wrongDay = wrongDay;
    }

    public Short getWrongLimit() {
        return wrongLimit;
    }

    public void setWrongLimit(Short wrongLimit) {
        this.wrongLimit = wrongLimit;
    }

    public Short getCollectDay() {
        return collectDay;
    }

    public void setCollectDay(Short collectDay) {
        this.collectDay = collectDay;
    }

    public Short getCollectLimit() {
        return collectLimit;
    }

    public void setCollectLimit(Short collectLimit) {
        this.collectLimit = collectLimit;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

	public Integer getDayCount() {
		return dayCount;
	}

	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}

	public Integer getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}
    
    
}