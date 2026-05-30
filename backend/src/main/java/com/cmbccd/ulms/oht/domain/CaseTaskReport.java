package com.cmbccd.ulms.oht.domain;

import com.alibaba.excel.annotation.ExcelProperty;

public class CaseTaskReport {
    @ExcelProperty("员工编号")
    private String userId;
    @ExcelProperty("员工科室")
    private String deptNum;
    @ExcelProperty("员工组别")
    private String groupNum;
    @ExcelProperty("员工姓名")
    private String userName;
    @ExcelProperty("忽略量")
    private Integer miss;
    @ExcelProperty("接起量")
    private Integer take;
    @ExcelProperty("拒绝量")
    private Integer refuse;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptNum() {
		return deptNum;
	}

	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}

	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public Integer getMiss() {
		return miss;
	}

	public void setMiss(Integer miss) {
		this.miss = miss;
	}

	public Integer getTake() {
		return take;
	}

	public void setTake(Integer take) {
		this.take = take;
	}

	public Integer getRefuse() {
		return refuse;
	}

	public void setRefuse(Integer refuse) {
		this.refuse = refuse;
	}
    
    
}
