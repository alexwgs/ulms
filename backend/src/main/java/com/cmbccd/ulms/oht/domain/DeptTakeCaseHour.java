package com.cmbccd.ulms.oht.domain;

import com.alibaba.excel.annotation.ExcelProperty;

public class DeptTakeCaseHour {
	@ExcelProperty("日期")
	private String dataDate;
	
	@ExcelProperty("时点")
	private Integer hour;
	
	@ExcelProperty("发起案件数")
	private Integer type1CaseNum;
	
	@ExcelProperty("业务放行(1级)")
	private Integer type1Level1;
	
	@ExcelProperty("业务放行(2级)")
	private Integer type1Level2;
	
	@ExcelProperty("业务放行(3级)")
	private Integer type1Level3;
	
	@ExcelProperty("业务放行等待时长(s)")
	private Double type1WaitTime;
	
	@ExcelProperty("发起案件数")
	private Integer type2CaseNum;
	
	@ExcelProperty("投诉案件(1级)")
	private Integer type2Level1;
	
	@ExcelProperty("投诉案件(2级)")
	private Integer type2Level2;
	
	@ExcelProperty("投诉案件(3级)")
	private Integer type2Level3;
	
	@ExcelProperty("投诉处等待理时长(s)")
	private Double type2WaitTime;

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getType1CaseNum() {
		return type1CaseNum;
	}

	public void setType1CaseNum(Integer type1CaseNum) {
		this.type1CaseNum = type1CaseNum;
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

	public Double getType1WaitTime() {
		return type1WaitTime;
	}

	public void setType1WaitTime(Double type1WaitTime) {
		this.type1WaitTime = type1WaitTime;
	}

	public Integer getType2CaseNum() {
		return type2CaseNum;
	}

	public void setType2CaseNum(Integer type2CaseNum) {
		this.type2CaseNum = type2CaseNum;
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

	public Double getType2WaitTime() {
		return type2WaitTime;
	}

	public void setType2WaitTime(Double type2WaitTime) {
		this.type2WaitTime = type2WaitTime;
	}

	@Override
	public String toString() {
		return "DeptTakeCaseHour [dataDate=" + dataDate + ", hour=" + hour + ", type1CaseNum=" + type1CaseNum
				+ ", type1Level1=" + type1Level1 + ", type1Level2=" + type1Level2 + ", type1Level3=" + type1Level3
				+ ", type1WaitTime=" + type1WaitTime + ", type2CaseNum=" + type2CaseNum + ", type2Level1=" + type2Level1
				+ ", type2Level2=" + type2Level2 + ", type2Level3=" + type2Level3 + ", type2WaitTime=" + type2WaitTime
				+ "]";
	}

}
