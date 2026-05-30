package com.cmbccd.ulms.sys.domain;

public class Station {
    private String floorNum;

    private String poi;

    private String extnNum;

    private String extnIp;

    private String pcIp;

    private String ploNum;

    private String ploStatus;

    private String poiStatus;

    private String memo;

    private String updateTime;

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum == null ? null : floorNum.trim();
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi == null ? null : poi.trim();
    }

    public String getExtnNum() {
        return extnNum;
    }

    public void setExtnNum(String extnNum) {
        this.extnNum = extnNum == null ? null : extnNum.trim();
    }

    public String getExtnIp() {
        return extnIp;
    }

    public void setExtnIp(String extnIp) {
        this.extnIp = extnIp == null ? null : extnIp.trim();
    }

    public String getPcIp() {
        return pcIp;
    }

    public void setPcIp(String pcIp) {
        this.pcIp = pcIp == null ? null : pcIp.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getPloStatus() {
        return ploStatus;
    }

    public void setPloStatus(String ploStatus) {
        this.ploStatus = ploStatus == null ? null : ploStatus.trim();
    }

    public String getPoiStatus() {
        return poiStatus;
    }

    public void setPoiStatus(String poiStatus) {
        this.poiStatus = poiStatus == null ? null : poiStatus.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}