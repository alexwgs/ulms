package com.cmbccd.ulms.helper.domain;

public class HelperArticle {
    private String journo;

    private String area;

    private String owner;

    private String title;

    private String keyWord;

    private String insertDate;

    private String insertPlo;

    private Integer status;

    private String updateDate;

    private String updatePlo;

    private String ralateCode;

    private Integer sorting;

    private Integer search;

    private Integer clicks;

    private Integer routeId;

    private String files;

    private String content;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getInsertPlo() {
        return insertPlo;
    }

    public void setInsertPlo(String insertPlo) {
        this.insertPlo = insertPlo == null ? null : insertPlo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatePlo() {
        return updatePlo;
    }

    public void setUpdatePlo(String updatePlo) {
        this.updatePlo = updatePlo;
    }

    public String getRalateCode() {
        return ralateCode;
    }

    public void setRalateCode(String ralateCode) {
        this.ralateCode = ralateCode == null ? null : ralateCode.trim();
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public Integer getSearch() {
        return search;
    }

    public void setSearch(Integer search) {
        this.search = search;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}