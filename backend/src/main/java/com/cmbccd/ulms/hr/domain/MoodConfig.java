package com.cmbccd.ulms.hr.domain;

public class MoodConfig {
    private Integer id;

    private String moodInfo;

    private String moodType;

    private Short status;

    private String moodPic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodInfo() {
        return moodInfo;
    }

    public void setMoodInfo(String moodInfo) {
        this.moodInfo = moodInfo == null ? null : moodInfo.trim();
    }

    public String getMoodType() {
        return moodType;
    }

    public void setMoodType(String moodType) {
        this.moodType = moodType == null ? null : moodType.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getMoodPic() {
        return moodPic;
    }

    public void setMoodPic(String moodPic) {
        this.moodPic = moodPic == null ? null : moodPic.trim();
    }
}