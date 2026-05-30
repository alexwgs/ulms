package com.cmbccd.ulms.sys.domain;

public class Todo {
    private Integer id;

    private String dataDate;

    private String userId;

    private String title;

    private String content;

    private String priority;

    private Integer alertFlag;

    private String alertTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority == null ? null : priority.trim();
    }

    public Integer getAlertFlag() {
        return alertFlag;
    }

    public void setAlertFlag(Integer alertFlag) {
        this.alertFlag = alertFlag;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime == null ? null : alertTime.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Todo() {
    }

    public Todo(String dataDate, String userId, String title, String content, String priority, Integer alertFlag, String alertTime, Integer status) {
        this.dataDate = dataDate;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.alertFlag = alertFlag;
        this.alertTime = alertTime;
        this.status = status;
    }
}