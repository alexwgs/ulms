package com.cmbccd.ulms.rpa.domain;

import com.alibaba.excel.annotation.ExcelProperty;

public class ToolReportVO {
    @ExcelProperty("工具ID")
    private String toolId;
    @ExcelProperty("科室")
    private String deptName;
    @ExcelProperty("组别")
    private String groupName;
    @ExcelProperty("调用用户工号")
    private String userId;
    @ExcelProperty("调用用户姓名")
    private String userName;
    @ExcelProperty("调用时间")
    private String runTime;
    @ExcelProperty("工具名称")
    private String toolName;
    @ExcelProperty("返回码")
    private String returnCode;
    @ExcelProperty("返回信息")
    private String returnMessage;
    @ExcelProperty("返回时间")
    private String returnTime;
    @ExcelProperty("调用参数")
    private String runParam;

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getRunParam() {
        return runParam;
    }

    public void setRunParam(String runParam) {
        this.runParam = runParam;
    }
}
