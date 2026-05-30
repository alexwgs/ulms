package com.cmbccd.ulms.edu.domain;

public class AreaList {
    private String journo;

    private String areaCode;

    private String seatIp;

    private String seatMac;

    private String seatDesc;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getSeatIp() {
        return seatIp;
    }

    public void setSeatIp(String seatIp) {
        this.seatIp = seatIp == null ? null : seatIp.trim();
    }

    public String getSeatMac() {
        return seatMac;
    }

    public void setSeatMac(String seatMac) {
        this.seatMac = seatMac == null ? null : seatMac.trim();
    }

    public String getSeatDesc() {
        return seatDesc;
    }

    public void setSeatDesc(String seatDesc) {
        this.seatDesc = seatDesc == null ? null : seatDesc.trim();
    }
}