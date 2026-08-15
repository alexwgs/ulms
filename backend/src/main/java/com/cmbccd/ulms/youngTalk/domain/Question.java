package com.cmbccd.ulms.youngTalk.domain;

import java.util.List;

public class Question {
    private Integer id;

    private Integer articleId;

    private String questionType;

    private String content;

    private Integer sort;
    
    private List<Option> options;
    
    private String answer;
    
    private String[] checkboxs = new String[0] ;
    
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

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType == null ? null : questionType.trim();
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

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String[] getCheckboxs() {
		return checkboxs;
	}

	public void setCheckboxs(String[] checkboxs) {
		this.checkboxs = checkboxs;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", articleId=" + articleId + ", questionType=" + questionType + ", content="
				+ content + ", sort=" + sort + ", options=" + options + ", answer=" + answer + ", checkboxs="
				+ checkboxs + "]";
	}

}