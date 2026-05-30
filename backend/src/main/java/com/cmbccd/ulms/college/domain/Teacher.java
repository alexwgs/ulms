package com.cmbccd.ulms.college.domain;

import com.cmbccd.ulms.sys.domain.Employee;

import java.math.BigDecimal;

public class Teacher {
    private String ploNum;

    private Short skillType;

    private Short skillName;

    private String dataDate;

    private String avatar;

    private String introduce;

    private Short courseNum;

    private BigDecimal courseHour;

    private BigDecimal score;

    private Short status;

    private String ploName;

    private String honor;

    private Employee user;

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public Short getSkillType() {
        return skillType;
    }

    public void setSkillType(Short skillType) {
        this.skillType = skillType;
    }

    public Short getSkillName() {
        return skillName;
    }

    public void setSkillName(Short skillName) {
        this.skillName = skillName;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public Short getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Short courseNum) {
        this.courseNum = courseNum;
    }

    public BigDecimal getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(BigDecimal courseHour) {
        this.courseHour = courseHour;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getPloName() {
        return ploName;
    }

    public void setPloName(String ploName) {
        this.ploName = ploName == null ? null : ploName.trim();
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor == null ? null : honor.trim();
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }
}