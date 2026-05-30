package com.cmbccd.ulms.college.domain;

import java.util.List;

public class CourseType {
    private Integer id;

    private Integer pid;

    private String name;

    private Short grade;

    private Short status;

    private Short sort;

    List<CourseType> children;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getGrade() {
        return grade;
    }

    public void setGrade(Short grade) {
        this.grade = grade;
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

    public void setChildren(List<CourseType> children) {
        this.children = children;
    }

    public List<CourseType> getChildren() {
        return children;
    }
}