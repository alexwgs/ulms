package com.cmbccd.ulms.oht.domain;

public class OhtRole {
    private Integer roleCode;

    private String roleName;

    private Integer roleType;

    private Integer roleStat;

    private String roleRelat;

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getRoleStat() {
        return roleStat;
    }

    public void setRoleStat(Integer roleStat) {
        this.roleStat = roleStat;
    }

    public String getRoleRelat() {
        return roleRelat;
    }

    public void setRoleRelat(String roleRelat) {
        this.roleRelat = roleRelat == null ? null : roleRelat.trim();
    }
}