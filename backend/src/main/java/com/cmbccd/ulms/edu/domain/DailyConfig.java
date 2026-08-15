package com.cmbccd.ulms.edu.domain;

public class DailyConfig {
    private String id;

    private String quesCode;

    private String quesDate;

    private String groupId;

    private Short optionRand;

    private Long articleId;

    private String valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getQuesCode() {
        return quesCode;
    }

    public void setQuesCode(String quesCode) {
        this.quesCode = quesCode == null ? null : quesCode.trim();
    }

    public String getQuesDate() {
        return quesDate;
    }

    public void setQuesDate(String quesDate) {
        this.quesDate = quesDate == null ? null : quesDate.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Short getOptionRand() {
        return optionRand;
    }

    public void setOptionRand(Short optionRand) {
        this.optionRand = optionRand;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}