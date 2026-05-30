package com.cmbccd.ulms.flow.domain;

public class FlowTable {
    String colName;
    String formatType;
    String dataIndex;
    String describe;
    String title;
    String dictType;
    String dictName;
    String status;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FlowTable{" +
                "colName='" + colName + '\'' +
                ", formatType='" + formatType + '\'' +
                ", dataIndex='" + dataIndex + '\'' +
                ", describe='" + describe + '\'' +
                ", title='" + title + '\'' +
                ", dictType='" + dictType + '\'' +
                ", dictName='" + dictName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
