package com.cmbccd.ulms.oht.domain;

import java.math.BigDecimal;

public class QuickMemo {
    private String journo;

    private String roleType;

    private String memo;

    private BigDecimal status;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType == null ? null : roleType.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "QuickMemo [journo=" + journo + ", roleType=" + roleType + ", memo=" + memo + ", status=" + status + "]";
	}
    
    
}