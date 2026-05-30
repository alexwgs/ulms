package com.cmbccd.ulms.hr.domain;

public class RuleData {
    private String journo;

    private String ruleName;

    private String ruleType;

    private String fileName;

    private Integer sortNum;

    private String dataDate;

    private String handlePlo;

    private String dataType;

    private Integer status;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType == null ? null : ruleType.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }

    public String getHandlePlo() {
        return handlePlo;
    }

    public void setHandlePlo(String handlePlo) {
        this.handlePlo = handlePlo == null ? null : handlePlo.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}