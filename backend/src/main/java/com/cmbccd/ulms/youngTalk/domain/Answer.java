package com.cmbccd.ulms.youngTalk.domain;

import com.cmbccd.ulms.sys.domain.Employee;

public class Answer {
    private Integer id;

    private Integer articleId;

    private Integer questionId;

    private String answer;

    private String userId;

    private String dataDate;
    
    private Employee User;

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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
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

	public Employee getUser() {
		return User;
	}

	public void setUser(Employee user) {
		User = user;
	}
    
}