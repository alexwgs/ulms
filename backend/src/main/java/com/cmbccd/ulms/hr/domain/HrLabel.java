package com.cmbccd.ulms.hr.domain;

public class HrLabel {
    private String id;

    private String perfMonth;

    private String ploNum;

    private String labelName;

    private Short humFlag;

    private Short groupFlag;

    private Short deptFlag;

    private String memo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPerfMonth() {
        return perfMonth;
    }

    public void setPerfMonth(String perfMonth) {
        this.perfMonth = perfMonth == null ? null : perfMonth.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Short getHumFlag() {
        return humFlag;
    }

    public void setHumFlag(Short humFlag) {
        this.humFlag = humFlag;
    }

    public Short getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Short groupFlag) {
        this.groupFlag = groupFlag;
    }

    public Short getDeptFlag() {
        return deptFlag;
    }

    public void setDeptFlag(Short deptFlag) {
        this.deptFlag = deptFlag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

}