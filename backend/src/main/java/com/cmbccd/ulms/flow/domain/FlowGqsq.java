package com.cmbccd.ulms.flow.domain;

public class FlowGqsq {
    private String journo;

    private String ploNum;

    private String dataDate;

    private String begTime;

    private String endTime;

    private String excTime;

    private String auxReason;

    private String avgTime;

    private String auxMemo;

    private String eaaPlo;

    private String eaaStatus;

    private String deptNum;

    private String deptGroup;

    private String jourStatus;

    private String handlePlo;

    private String inDate;

    private String batchNum;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
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

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime == null ? null : begTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getExcTime() {
        return excTime;
    }

    public void setExcTime(String excTime) {
        this.excTime = excTime == null ? null : excTime.trim();
    }

    public String getAuxReason() {
        return auxReason;
    }

    public void setAuxReason(String auxReason) {
        this.auxReason = auxReason == null ? null : auxReason.trim();
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime == null ? null : avgTime.trim();
    }

    public String getAuxMemo() {
        return auxMemo;
    }

    public void setAuxMemo(String auxMemo) {
        this.auxMemo = auxMemo == null ? null : auxMemo.trim();
    }

    public String getEaaPlo() {
        return eaaPlo;
    }

    public void setEaaPlo(String eaaPlo) {
        this.eaaPlo = eaaPlo == null ? null : eaaPlo.trim();
    }

    public String getEaaStatus() {
        return eaaStatus;
    }

    public void setEaaStatus(String eaaStatus) {
        this.eaaStatus = eaaStatus == null ? null : eaaStatus.trim();
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

    public String getJourStatus() {
        return jourStatus;
    }

    public void setJourStatus(String jourStatus) {
        this.jourStatus = jourStatus == null ? null : jourStatus.trim();
    }

    public String getHandlePlo() {
        return handlePlo;
    }

    public void setHandlePlo(String handlePlo) {
        this.handlePlo = handlePlo == null ? null : handlePlo.trim();
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate == null ? null : inDate.trim();
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum == null ? null : batchNum.trim();
    }


    @Override
    public String toString() {
        return "FlowGqsq{" +
                "journo='" + journo + '\'' +
                ", ploNum='" + ploNum + '\'' +
                ", dataDate='" + dataDate + '\'' +
                ", begTime='" + begTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", excTime='" + excTime + '\'' +
                ", auxReason='" + auxReason + '\'' +
                ", avgTime='" + avgTime + '\'' +
                ", auxMemo='" + auxMemo + '\'' +
                ", eaaPlo='" + eaaPlo + '\'' +
                ", eaaStatus='" + eaaStatus + '\'' +
                ", deptNum='" + deptNum + '\'' +
                ", deptGroup='" + deptGroup + '\'' +
                ", jourStatus='" + jourStatus + '\'' +
                ", handlePlo='" + handlePlo + '\'' +
                ", inDate='" + inDate + '\'' +
                ", batchNum='" + batchNum + '\'' +
                '}';
    }
}