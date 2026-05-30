package com.cmbccd.ulms.oht.domain;

import java.math.BigDecimal;

public class LoginLog {
    private String ploNum;

    private String ploName;

    private BigDecimal lineFloor;

    private BigDecimal roleCode;

    private String ip;

    private String extn;

    private BigDecimal status;

    private String dateTime;

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

    public BigDecimal getLineFloor() {
        return lineFloor;
    }

    public void setLineFloor(BigDecimal lineFloor) {
        this.lineFloor = lineFloor;
    }

    public BigDecimal getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(BigDecimal roleCode) {
        this.roleCode = roleCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getExtn() {
        return extn;
    }

    public void setExtn(String extn) {
        this.extn = extn == null ? null : extn.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.trim();
    }
}