package com.cmbccd.ulms.oht.domain;

import com.alibaba.excel.annotation.ExcelProperty;

public class DirTakeCaseSumarry {
	@ExcelProperty("科室")
	private String deptName;
	
	@ExcelProperty("组别")
	private String groupName;
	
	@ExcelProperty("工号")
	private String userId;

	@ExcelProperty("姓名")
	private String userName;
	
	@ExcelProperty("可接单时间")
	private Double avaliableTime;
	
	@ExcelProperty("通话中时间")
	private Double onCallTime;
	
	@ExcelProperty("离开时间")
	private Double leaveTime;
	
	@ExcelProperty("业务放行(1级)")
	private Integer type1Level1;
	
	@ExcelProperty("业务放行(2级)")
	private Integer type1Level2;
	
	@ExcelProperty("业务放行(3级)")
	private Integer type1Level3;
	
	@ExcelProperty("业务放行时长(s)")
	private Double type1ProcessTime;
	
	@ExcelProperty("投诉案件(1级)")
	private Integer type2Level1;
	
	@ExcelProperty("投诉案件(2级)")
	private Integer type2Level2;
	
	@ExcelProperty("投诉案件(3级)")
	private Integer type2Level3;
	
	@ExcelProperty("投诉处理时长(s)")
	private Double type2ProcessTime;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

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

	

	public Double getAvaliableTime() {
		return avaliableTime;
	}

	public void setAvaliableTime(Double avaliableTime) {
		this.avaliableTime = avaliableTime;
	}

	public Double getOnCallTime() {
		return onCallTime;
	}

	public void setOnCallTime(Double onCallTime) {
		this.onCallTime = onCallTime;
	}

	public Double getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Double leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Integer getType1Level1() {
		return type1Level1;
	}

	public void setType1Level1(Integer type1Level1) {
		this.type1Level1 = type1Level1;
	}

	public Integer getType1Level2() {
		return type1Level2;
	}

	public void setType1Level2(Integer type1Level2) {
		this.type1Level2 = type1Level2;
	}

	public Integer getType1Level3() {
		return type1Level3;
	}

	public void setType1Level3(Integer type1Level3) {
		this.type1Level3 = type1Level3;
	}

	public Double getType1ProcessTime() {
		return type1ProcessTime;
	}

	public void setType1ProcessTime(Double type1ProcessTime) {
		this.type1ProcessTime = type1ProcessTime;
	}

	public Integer getType2Level1() {
		return type2Level1;
	}

	public void setType2Level1(Integer type2Level1) {
		this.type2Level1 = type2Level1;
	}

	public Integer getType2Level2() {
		return type2Level2;
	}

	public void setType2Level2(Integer type2Level2) {
		this.type2Level2 = type2Level2;
	}

	public Integer getType2Level3() {
		return type2Level3;
	}

	public void setType2Level3(Integer type2Level3) {
		this.type2Level3 = type2Level3;
	}

	public Double getType2ProcessTime() {
		return type2ProcessTime;
	}

	public void setType2ProcessTime(Double type2ProcessTime) {
		this.type2ProcessTime = type2ProcessTime;
	}


}
