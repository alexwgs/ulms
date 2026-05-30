package com.cmbccd.ulms.youngTalk.domain;

public class Collect {
    private Integer id;

    private Integer articalId;

    private String userId;

    private Integer status;

    private String dateTime;
    
    private Artical artical;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticalId() {
        return articalId;
    }

    public void setArticalId(Integer articalId) {
        this.articalId = articalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.trim();
    }

	public Artical getArtical() {
		return artical;
	}

	public void setArtical(Artical artical) {
		this.artical = artical;
	}

	@Override
	public String toString() {
		return "Collect [id=" + id + ", articalId=" + articalId + ", userId=" + userId + ", status=" + status
				+ ", dateTime=" + dateTime + ", artical=" + artical + "]";
	}
	
}