package com.cmbccd.ulms.youngTalk.domain;

public class Collect {
    private Integer id;

    private Integer articleId;

    private String userId;

    private Integer status;

    private String dateTime;
    
    private Article article;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public String toString() {
		return "Collect [id=" + id + ", articleId=" + articleId + ", userId=" + userId + ", status=" + status
				+ ", dateTime=" + dateTime + ", article=" + article + "]";
	}
	
}