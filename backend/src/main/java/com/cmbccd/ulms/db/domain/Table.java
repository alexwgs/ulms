package com.cmbccd.ulms.db.domain;

public class Table {
    private Integer id;

    private String name;

    private String tableName;

    private Long rowNum;

    private String lastAnalyzed;

    private String tableIndex;

    private String memo;

    private Integer clickNum;

    private Short status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public String getLastAnalyzed() {
        return lastAnalyzed;
    }

    public void setLastAnalyzed(String lastAnalyzed) {
        this.lastAnalyzed = lastAnalyzed == null ? null : lastAnalyzed.trim();
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex == null ? null : tableIndex.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}