package com.cmbccd.ulms.edu.domain.report;

import com.alibaba.excel.annotation.ExcelProperty;

public class QuesScoreHum {
    @ExcelProperty("科室")
	private String deptNum;
    @ExcelProperty("组别")
	private String deptGroup;
    @ExcelProperty("员工编号")
	private String ploNum;
    @ExcelProperty("员工姓名")
	private String ploName;
    @ExcelProperty("期数")
	private String batchGroup;
    @ExcelProperty("开考时间")
	private String begDate;
    @ExcelProperty("交卷时间")
	private String endDate;
    @ExcelProperty("考试得分")
	private String userScore;
    @ExcelProperty("考试状态")
	private String compStat;
    @ExcelProperty("考试IP")
	private String handle_ip;
    @ExcelProperty("预约日期")
	private String bookDate;
    @ExcelProperty("预约开考时间")
	private String begTime;
    @ExcelProperty("预约结束时间")
	private String endTime;

	public String getDeptNum() {
		return deptNum;
	}

	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}

	public String getDeptGroup() {
		return deptGroup;
	}

	public void setDeptGroup(String deptGroup) {
		this.deptGroup = deptGroup;
	}

	public String getPloNum() {
		return ploNum;
	}

	public void setPloNum(String ploNum) {
		this.ploNum = ploNum;
	}

	public String getPloName() {
		return ploName;
	}

	public void setPloName(String ploName) {
		this.ploName = ploName;
	}

	public String getBatchGroup() {
		return batchGroup;
	}

	public void setBatchGroup(String batchGroup) {
		this.batchGroup = batchGroup;
	}

	public String getBegDate() {
		return begDate;
	}

	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserScore() {
		return userScore;
	}

	public void setUserScore(String userScore) {
		this.userScore = userScore;
	}

	public String getCompStat() {
		return compStat;
	}

	public void setCompStat(String compStat) {
		this.compStat = compStat;
	}

	public String getHandle_ip() {
		return handle_ip;
	}

	public void setHandle_ip(String handle_ip) {
		this.handle_ip = handle_ip;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getBegTime() {
		return begTime;
	}

	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
