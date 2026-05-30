package com.cmbccd.ulms.oht.domain;

public class StatusJour {
    private Integer id;

    private String userId;

    private String dataDate;

    private String begTime;

    private String endTime;

    private Integer statusId;

    private String identity;

    private String ip;

    private Integer duration;

    private String memo;

    private Integer ohtFlag;
    
    private String statusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime == null ? null : begTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getOhtFlag() {
        return ohtFlag;
    }

    public void setOhtFlag(Integer ohtFlag) {
        this.ohtFlag = ohtFlag;
    }

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "StatusJour [id=" + id + ", userId=" + userId + ", dataDate=" + dataDate + ", begTime=" + begTime
				+ ", endTime=" + endTime + ", statusId=" + statusId + ", identity=" + identity + ", ip=" + ip
				+ ", duration=" + duration + ", memo=" + memo + ", ohtFlag=" + ohtFlag + ", statusName=" + statusName
				+ "]";
	}
    
	
}