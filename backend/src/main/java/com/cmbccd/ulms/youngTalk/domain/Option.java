package com.cmbccd.ulms.youngTalk.domain;

public class Option {
    private Integer id;

    private Integer articleId;

    private Integer questionId;

    private String content;

    private Integer sort;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

	@Override
	public String toString() {
		return "Option [id=" + id + ", articleId=" + articleId + ", questionId=" + questionId + ", content=" + content
				+ ", sort=" + sort + "]";
	}
    
    
}