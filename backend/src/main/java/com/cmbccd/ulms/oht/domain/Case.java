package com.cmbccd.ulms.oht.domain;

import com.cmbccd.ulms.sys.domain.Employee;

public class Case {
	
    private String caseId;

    private String dataTime;
    
    private String buildId;

    private String buildDept;

    private String buildGroup;

    private String buildExtn;

    private Integer buildTime;

    private Integer bcompType;

    private String bcompMemo;

    private String bcompTime;

    private Integer waitTime;

    private Integer caseType;

    private String pickId;

    private String pickDept;

    private String pickGroup;

    private String pickExtn;

    private Integer pickTime;

    private String custId;

    private Integer pcompType;

    private String pcompMemo;

    private String pcompTime;

    private Integer caseStatus;

    private Employee buildUser;
    
    private Employee pickUser;
    
    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime == null ? null : dataTime.trim();
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId == null ? null : buildId.trim();
    }

    public String getBuildDept() {
        return buildDept;
    }

    public void setBuildDept(String buildDept) {
        this.buildDept = buildDept == null ? null : buildDept.trim();
    }

    public String getBuildGroup() {
        return buildGroup;
    }

    public void setBuildGroup(String buildGroup) {
        this.buildGroup = buildGroup == null ? null : buildGroup.trim();
    }

    public String getBuildExtn() {
        return buildExtn;
    }

    public void setBuildExtn(String buildExtn) {
        this.buildExtn = buildExtn == null ? null : buildExtn.trim();
    }

    public Integer getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Integer buildTime) {
        this.buildTime = buildTime;
    }

    public Integer getBcompType() {
        return bcompType;
    }

    public void setBcompType(Integer bcompType) {
        this.bcompType = bcompType;
    }

    public String getBcompMemo() {
        return bcompMemo;
    }

    public void setBcompMemo(String bcompMemo) {
        this.bcompMemo = bcompMemo == null ? null : bcompMemo.trim();
    }

    public String getBcompTime() {
        return bcompTime;
    }

    public void setBcompTime(String bcompTime) {
        this.bcompTime = bcompTime == null ? null : bcompTime.trim();
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    public String getPickId() {
        return pickId;
    }

    public void setPickId(String pickId) {
        this.pickId = pickId == null ? null : pickId.trim();
    }

    public String getPickDept() {
        return pickDept;
    }

    public void setPickDept(String pickDept) {
        this.pickDept = pickDept == null ? null : pickDept.trim();
    }

    public String getPickGroup() {
        return pickGroup;
    }

    public void setPickGroup(String pickGroup) {
        this.pickGroup = pickGroup == null ? null : pickGroup.trim();
    }

    public String getPickExtn() {
        return pickExtn;
    }

    public void setPickExtn(String pickExtn) {
        this.pickExtn = pickExtn == null ? null : pickExtn.trim();
    }

    public Integer getPickTime() {
        return pickTime;
    }

    public void setPickTime(Integer pickTime) {
        this.pickTime = pickTime;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public Integer getPcompType() {
        return pcompType;
    }

    public void setPcompType(Integer pcompType) {
        this.pcompType = pcompType;
    }

    public String getPcompMemo() {
        return pcompMemo;
    }

    public void setPcompMemo(String pcompMemo) {
        this.pcompMemo = pcompMemo == null ? null : pcompMemo.trim();
    }

    public String getPcompTime() {
        return pcompTime;
    }

    public void setPcompTime(String pcompTime) {
        this.pcompTime = pcompTime == null ? null : pcompTime.trim();
    }

    public Integer getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(Integer caseStatus) {
        this.caseStatus = caseStatus;
    }

	public Employee getBuildUser() {
		return buildUser;
	}

	public void setBuildUser(Employee buildUser) {
		this.buildUser = buildUser;
	}

	public Employee getPickUser() {
		return pickUser;
	}

	public void setPickUser(Employee pickUser) {
		this.pickUser = pickUser;
	}
    
    
}