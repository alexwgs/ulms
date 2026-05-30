package com.cmbccd.ulms.db.domain;

import java.util.List;

public class TableTree {
    private Integer id;

    private Integer pid;

    private Short treeLevel;

    private String name;

    private Integer sort;

    private Short status;

    List<TableTree> children;

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

    public Short getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Short treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public List<TableTree> getChildren() {
        return children;
    }

    public void setChildren(List<TableTree> children) {
        this.children = children;
    }
}