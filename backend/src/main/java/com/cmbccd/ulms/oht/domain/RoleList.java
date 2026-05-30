package com.cmbccd.ulms.oht.domain;

public class RoleList {
    private String ploNum;

    private Integer roleType;

    private Integer roleCode;

    private Integer roleStat;

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getRoleStat() {
        return roleStat;
    }

    public void setRoleStat(Integer roleStat) {
        this.roleStat = roleStat;
    }

	@Override
	public String toString() {
		return "RoleList [ploNum=" + ploNum + ", roleType=" + roleType + ", roleCode=" + roleCode + ", roleStat="
				+ roleStat + "]";
	}
    
    
    
}