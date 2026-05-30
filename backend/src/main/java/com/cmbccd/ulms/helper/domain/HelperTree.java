package com.cmbccd.ulms.helper.domain;

import java.util.List;

public class HelperTree {
    private Integer id;

    private Integer pid;

    private Short grade;

    private String name;

    private Short status;

    private Short sort;

    List<HelperTree> children;

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

    public Short getGrade() {
        return grade;
    }

    public void setGrade(Short grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public List<HelperTree> getChildren() {
        return children;
    }

    public void setChildren(List<HelperTree> children) {
        this.children = children;
    }
}