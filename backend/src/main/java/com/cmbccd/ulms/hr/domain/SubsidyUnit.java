package com.cmbccd.ulms.hr.domain;

public class SubsidyUnit {
    private String id;

    private String perfUnit;

    private String subsidyName;

    private Integer sort;

    private Short status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPerfUnit() {
        return perfUnit;
    }

    public void setPerfUnit(String perfUnit) {
        this.perfUnit = perfUnit == null ? null : perfUnit.trim();
    }

    public String getSubsidyName() {
        return subsidyName;
    }

    public void setSubsidyName(String subsidyName) {
        this.subsidyName = subsidyName == null ? null : subsidyName.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}