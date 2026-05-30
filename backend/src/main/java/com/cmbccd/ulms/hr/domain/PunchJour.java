package com.cmbccd.ulms.hr.domain;

public class PunchJour {
    private String journo;

    private String workDate;

    private String dutyDay;

    private String dutyFlag;

    private String punDate;

    private String ploNum;

    private String punStatus;

    private String jourFlag;

    private String locIp;

    private String punMem;

    private String dyskFlag;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate == null ? null : workDate.trim();
    }

    public String getDutyDay() {
        return dutyDay;
    }

    public void setDutyDay(String dutyDay) {
        this.dutyDay = dutyDay == null ? null : dutyDay.trim();
    }

    public String getDutyFlag() {
        return dutyFlag;
    }

    public void setDutyFlag(String dutyFlag) {
        this.dutyFlag = dutyFlag == null ? null : dutyFlag.trim();
    }

    public String getPunDate() {
        return punDate;
    }

    public void setPunDate(String punDate) {
        this.punDate = punDate == null ? null : punDate.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getPunStatus() {
        return punStatus;
    }

    public void setPunStatus(String punStatus) {
        this.punStatus = punStatus == null ? null : punStatus.trim();
    }

    public String getJourFlag() {
        return jourFlag;
    }

    public void setJourFlag(String jourFlag) {
        this.jourFlag = jourFlag == null ? null : jourFlag.trim();
    }

    public String getLocIp() {
        return locIp;
    }

    public void setLocIp(String locIp) {
        this.locIp = locIp == null ? null : locIp.trim();
    }

    public String getPunMem() {
        return punMem;
    }

    public void setPunMem(String punMem) {
        this.punMem = punMem == null ? null : punMem.trim();
    }

    public String getDyskFlag() {
        return dyskFlag;
    }

    public void setDyskFlag(String dyskFlag) {
        this.dyskFlag = dyskFlag == null ? null : dyskFlag.trim();
    }
}