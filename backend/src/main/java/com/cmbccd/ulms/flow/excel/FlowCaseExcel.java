package com.cmbccd.ulms.flow.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

public class FlowCaseExcel {
    @ExcelProperty("案件编号")
    @ColumnWidth(32)
    private String id;
    @ExcelProperty("申请时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(20)
    private String dataTime;
    @ExcelProperty("申请人科室")
    private String applyDeptName;
    @ExcelProperty("申请人组别")
    private String applyGroupName;
    @ExcelProperty("申请人工号")
    private String applyNum;
    @ExcelProperty("申请人姓名")
    private String applyName;
    @ExcelProperty("案件状态")
    private String caseStatus;
    @ExcelProperty("放行情况")
    private String approveStatus;
    @ExcelProperty("案件结束时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(18)
    private String endDate;
    @ExcelProperty("放行级别")
    private Short approveLevel;
    @ExcelProperty("修改时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(18)
    private String modifyDate;
    @ExcelProperty("放行明细")
    @ColumnWidth(70)
    private String detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
    }

    public String getApplyGroupName() {
        return applyGroupName;
    }

    public void setApplyGroupName(String applyGroupName) {
        this.applyGroupName = applyGroupName;
    }

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Short getApproveLevel() {
        return approveLevel;
    }

    public void setApproveLevel(Short approveLevel) {
        this.approveLevel = approveLevel;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
