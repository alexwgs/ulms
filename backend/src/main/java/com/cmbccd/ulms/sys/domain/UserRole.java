package com.cmbccd.ulms.sys.domain;

public class UserRole {
    private String userId;

    private String roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }

	@Override
	public String toString() {
		return "UserRole [userId=" + userId + ", roles=" + roles + "]";
	}
    
    
}