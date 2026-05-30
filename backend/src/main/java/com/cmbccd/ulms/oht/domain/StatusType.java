package com.cmbccd.ulms.oht.domain;

import java.util.List;

public class StatusType {
    private Integer id;

    private Integer pid;

    private String statusName;

    private Integer levelGrade;

    private Integer ohtFlag;

    private Integer sort;

    private Integer status;

    private String dataType;

    private List<StatusType> children;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public Integer getLevelGrade() {
        return levelGrade;
    }

    public void setLevelGrade(Integer levelGrade) {
        this.levelGrade = levelGrade;
    }

    public Integer getOhtFlag() {
        return ohtFlag;
    }

    public void setOhtFlag(Integer ohtFlag) {
        this.ohtFlag = ohtFlag;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

	public List<StatusType> getChildren() {
		return children;
	}

	public void setChildren(List<StatusType> children) {
		this.children = children;
	}
    
    
}