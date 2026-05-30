package com.cmbccd.ulms.youngTalk.domain;

import java.util.List;

import com.cmbccd.ulms.sys.domain.Employee;

public class Comment {
    private Integer id;

    private Integer articalId;

    private String content;

    private Integer likeNum;

    private String userid;

    private String dateTime;

    private Integer status;

    private Integer anonFlag;
    
    private String toUser;
    
    private Employee user;
    
    private List<Like> likes;
    
    private List<Reply> replys;
    
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAnonFlag() {
        return anonFlag;
    }

    public void setAnonFlag(Integer anonFlag) {
        this.anonFlag = anonFlag;
    }

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Reply> getReplys() {
		return replys;
	}

	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articalId=" + articalId +
                ", content='" + content + '\'' +
                ", likeNum=" + likeNum +
                ", userid='" + userid + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", status=" + status +
                ", anonFlag=" + anonFlag +
                ", toUser='" + toUser + '\'' +
                ", user=" + user +
                ", likes=" + likes +
                ", replys=" + replys +
                '}';
    }
}