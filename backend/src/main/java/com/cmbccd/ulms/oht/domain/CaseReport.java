package com.cmbccd.ulms.oht.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

public class CaseReport {

    @ExcelProperty({"案件信息","案件ID"})
    private String caseId;
    @ExcelProperty({"案件信息","案件时间"})
    private String dataTime;
    @ExcelProperty({"案件信息","案件状态"})
    private String statusName;
    @ExcelIgnore
    private Integer caseStatus;
    @ExcelProperty({"案件信息","案件类别"})
    private String typeName;
    @ExcelIgnore
    private Integer caseType;
    @ExcelProperty({"案件信息","等待时长(s)"})
    private Integer waitTime;
    @ExcelProperty({"案件信息","客户信息"})
    private String custId;

    @ExcelProperty({"建案人","工号"})
    private String buildId;
    @ExcelProperty({"建案人","姓名"})
    private String buildName;
    @ExcelProperty({"建案人","科室"})
    private String buildDept;
    @ExcelProperty({"建案人","组别"})
    private String buildGroup;
    @ExcelProperty({"建案人","分机"})
    private String buildExtn;
//    @ExcelProperty({"建案人","建案时间"})
    @ExcelIgnore
    private String buildTime;
    @ExcelProperty({"建案人","结案类型"})
    private String bcompName;
    @ExcelIgnore
    private Integer bcompType;
    @ExcelProperty({"建案人","结案备注"})
    private String bcompMemo;
    @ExcelProperty({"建案人","结案时间"})
    private String bcompTime;

    @ExcelProperty({"受理人","工号"})
    private String pickId;
    @ExcelProperty({"受理人","姓名"})
    private String pickName;
    @ExcelProperty({"受理人","科室"})
    private String pickDept;
    @ExcelProperty({"受理人","组别"})
    private String pickGroup;
    @ExcelProperty({"受理人","分机"})
    private String pickExtn;
    @ExcelProperty({"受理人","接单时间"})
    private String pickTime;
    @ExcelProperty({"受理人","结案类型"})
    private String pcompName;
    @ExcelIgnore
    private Integer pcompType;
    @ExcelProperty({"受理人","结案备注"})
    private String pcompMemo;
    @ExcelProperty({"受理人","结案时间"})
    private String pcompTime;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	public Integer getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(Integer caseStatus) {
		this.caseStatus = caseStatus;
	}
	public Integer getCaseType() {
		return caseType;
	}
	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}
	public Integer getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getBuildId() {
		return buildId;
	}
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getBuildDept() {
		return buildDept;
	}
	public void setBuildDept(String buildDept) {
		this.buildDept = buildDept;
	}
	public String getBuildGroup() {
		return buildGroup;
	}
	public void setBuildGroup(String buildGroup) {
		this.buildGroup = buildGroup;
	}
	public String getBuildExtn() {
		return buildExtn;
	}
	public void setBuildExtn(String buildExtn) {
		this.buildExtn = buildExtn;
	}
	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
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
		this.bcompMemo = bcompMemo;
	}
	public String getBcompTime() {
		return bcompTime;
	}
	public void setBcompTime(String bcompTime) {
		this.bcompTime = bcompTime;
	}
	public String getPickId() {
		return pickId;
	}
	public void setPickId(String pickId) {
		this.pickId = pickId;
	}
	public String getPickName() {
		return pickName;
	}
	public void setPickName(String pickName) {
		this.pickName = pickName;
	}
	public String getPickDept() {
		return pickDept;
	}
	public void setPickDept(String pickDept) {
		this.pickDept = pickDept;
	}
	public String getPickGroup() {
		return pickGroup;
	}
	public void setPickGroup(String pickGroup) {
		this.pickGroup = pickGroup;
	}
	public String getPickExtn() {
		return pickExtn;
	}
	public void setPickExtn(String pickExtn) {
		this.pickExtn = pickExtn;
	}
	public String getPickTime() {
		return pickTime;
	}
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
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
		this.pcompMemo = pcompMemo;
	}
	public String getPcompTime() {
		return pcompTime;
	}
	public void setPcompTime(String pcompTime) {
		this.pcompTime = pcompTime;
	}
	
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getBcompName() {
		return bcompName;
	}
	public void setBcompName(String bcompName) {
		this.bcompName = bcompName;
	}
	public String getPcompName() {
		return pcompName;
	}
	public void setPcompName(String pcompName) {
		this.pcompName = pcompName;
	}
	@Override
	public String toString() {
		return "CaseReport [caseId=" + caseId + ", dataTime=" + dataTime + ", statusName=" + statusName
				+ ", caseStatus=" + caseStatus + ", typeName=" + typeName + ", caseType=" + caseType + ", waitTime="
				+ waitTime + ", custId=" + custId + ", buildId=" + buildId + ", buildName=" + buildName + ", buildDept="
				+ buildDept + ", buildGroup=" + buildGroup + ", buildExtn=" + buildExtn + ", buildTime=" + buildTime
				+ ", bcompName=" + bcompName + ", bcompType=" + bcompType + ", bcompMemo=" + bcompMemo + ", bcompTime="
				+ bcompTime + ", pickId=" + pickId + ", pickName=" + pickName + ", pickDept=" + pickDept
				+ ", pickGroup=" + pickGroup + ", pickExtn=" + pickExtn + ", pickTime=" + pickTime + ", pcompName="
				+ pcompName + ", pcompType=" + pcompType + ", pcompMemo=" + pcompMemo + ", pcompTime=" + pcompTime
				+ "]";
	}
    
}
