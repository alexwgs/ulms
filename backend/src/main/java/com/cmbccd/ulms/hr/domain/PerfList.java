package com.cmbccd.ulms.hr.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cmbccd.ulms.sys.domain.Employee;

import java.util.List;

public class PerfList {

    @ExcelIgnore
    private String id;
    @ExcelProperty("绩效月份")
    private String perfMonth;
    @ExcelProperty("员编")
    private String ploNum;

    @ExcelProperty("姓名")
    private String ploName;

    @ExcelProperty("科室")
    private String deptNum;
    @ExcelProperty("组别")
    private String deptGroup;
    @ExcelProperty("岗位")
    private String jobLevel;
    @ExcelProperty("SAP号")
    private String sapId;
    @ExcelProperty("考核单元")
    private String perfUnit;
    @ExcelProperty("补贴单元")
    private String subsidyUnit;
    @ExcelProperty("个人考核")
    private Short humFlag;
    @ExcelProperty("小组考核")
    private Short groupFlag;

    @ExcelProperty("科室考核")
    private Short deptFlag;

    @ExcelProperty("备注")
    private String memo;

    @ExcelIgnore
    private String unitCaseId;
    @ExcelProperty("原始考核单元")
    private String oriPerfUnit;
    @ExcelIgnore
    private Employee user;
    @ExcelProperty("标签")
    private String labels;
    @ExcelIgnore
    List<HrLabel> labelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPerfMonth() {
        return perfMonth;
    }

    public void setPerfMonth(String perfMonth) {
        this.perfMonth = perfMonth == null ? null : perfMonth.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
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

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel == null ? null : jobLevel.trim();
    }

    public String getSapId() {
        return sapId;
    }

    public void setSapId(String sapId) {
        this.sapId = sapId == null ? null : sapId.trim();
    }

    public String getPerfUnit() {
        return perfUnit;
    }

    public void setPerfUnit(String perfUnit) {
        this.perfUnit = perfUnit == null ? null : perfUnit.trim();
    }

    public String getSubsidyUnit() {
        return subsidyUnit;
    }

    public void setSubsidyUnit(String subsidyUnit) {
        this.subsidyUnit = subsidyUnit == null ? null : subsidyUnit.trim();
    }

    public Short getHumFlag() {
        return humFlag;
    }

    public void setHumFlag(Short humFlag) {
        this.humFlag = humFlag;
    }

    public Short getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Short groupFlag) {
        this.groupFlag = groupFlag;
    }

    public Short getDeptFlag() {
        return deptFlag;
    }

    public void setDeptFlag(Short deptFlag) {
        this.deptFlag = deptFlag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getUnitCaseId() {
        return unitCaseId;
    }

    public void setUnitCaseId(String unitCaseId) {
        this.unitCaseId = unitCaseId == null ? null : unitCaseId.trim();
    }

    public String getOriPerfUnit() {
        return oriPerfUnit;
    }

    public void setOriPerfUnit(String oriPerfUnit) {
        this.oriPerfUnit = oriPerfUnit == null ? null : oriPerfUnit.trim();
    }

    public String getPloName() {
        return ploName;
    }

    public void setPloName(String ploName) {
        this.ploName = ploName == null ? null : ploName.trim();
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public List<HrLabel> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<HrLabel> labelList) {
        this.labelList = labelList;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }
}