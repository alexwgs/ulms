package com.cmbccd.ulms.flow.domain;

import com.cmbccd.ulms.sys.domain.Employee;

public class FlowProxy {
    private String id;

    private String ploNum;

    private String proxyNum;

    private String begDate;

    private String endDate;

    private String handlePlo;

    private String handleDate;

    private Employee ploUser;

    private Employee proxyUser;

    private Employee handleUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getProxyNum() {
        return proxyNum;
    }

    public void setProxyNum(String proxyNum) {
        this.proxyNum = proxyNum == null ? null : proxyNum.trim();
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

    public Employee getPloUser() {
        return ploUser;
    }

    public void setPloUser(Employee ploUser) {
        this.ploUser = ploUser;
    }

    public Employee getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(Employee proxyUser) {
        this.proxyUser = proxyUser;
    }

    public Employee getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(Employee handleUser) {
        this.handleUser = handleUser;
    }
}