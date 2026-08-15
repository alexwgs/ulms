package com.cmbccd.ulms.youngTalk.domain;

import com.cmbccd.ulms.sys.domain.Employee;

public class Article {
    private Integer id;

    private Integer articleType;

    private Integer category;

    private String title;

    private Integer status;

    private String respDept;

    private String pubUser;

    private String pubDate;

    private Integer compType;

    private String compDate;

    private Integer topFlag;

    private Integer eliteFlag;

    private Integer onStage;

    private Integer viewNum;

    private Integer collectNum;

    private Integer replyNum;

    private Integer likeNum;

    private String cover;

    private String files;

    private Integer compGrade;

    private Integer respStatus;

    private Integer anonFlag;

    private String content;

    private Employee user;

    private Integer isLike;

    private Integer isCollect;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRespDept() {
        return respDept;
    }

    public void setRespDept(String respDept) {
        this.respDept = respDept == null ? null : respDept.trim();
    }

    public String getPubUser() {
        return pubUser;
    }

    public void setPubUser(String pubUser) {
        this.pubUser = pubUser == null ? null : pubUser.trim();
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate == null ? null : pubDate.trim();
    }

    public Integer getCompType() {
        return compType;
    }

    public void setCompType(Integer compType) {
        this.compType = compType;
    }

    public String getCompDate() {
        return compDate;
    }

    public void setCompDate(String compDate) {
        this.compDate = compDate == null ? null : compDate.trim();
    }

    public Integer getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(Integer topFlag) {
        this.topFlag = topFlag;
    }

    public Integer getEliteFlag() {
        return eliteFlag;
    }

    public void setEliteFlag(Integer eliteFlag) {
        this.eliteFlag = eliteFlag;
    }

    public Integer getOnStage() {
        return onStage;
    }

    public void setOnStage(Integer onStage) {
        this.onStage = onStage;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files == null ? null : files.trim();
    }

    public Integer getCompGrade() {
        return compGrade;
    }

    public void setCompGrade(Integer compGrade) {
        this.compGrade = compGrade;
    }

    public Integer getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(Integer respStatus) {
        this.respStatus = respStatus;
    }

    public Integer getAnonFlag() {
        return anonFlag;
    }

    public void setAnonFlag(Integer anonFlag) {
        this.anonFlag = anonFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", articleType=" + articleType +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", respDept='" + respDept + '\'' +
                ", pubUser='" + pubUser + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", compType=" + compType +
                ", compDate='" + compDate + '\'' +
                ", topFlag=" + topFlag +
                ", eliteFlag=" + eliteFlag +
                ", onStage=" + onStage +
                ", viewNum=" + viewNum +
                ", collectNum=" + collectNum +
                ", replyNum=" + replyNum +
                ", likeNum=" + likeNum +
                ", cover='" + cover + '\'' +
                ", files='" + files + '\'' +
                ", compGrade=" + compGrade +
                ", respStatus=" + respStatus +
                ", anonFlag=" + anonFlag +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", isLike=" + isLike +
                ", isCollect=" + isCollect +
                '}';
    }
}