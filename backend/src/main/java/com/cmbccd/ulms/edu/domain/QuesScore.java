package com.cmbccd.ulms.edu.domain;

import com.cmbccd.ulms.sys.domain.Employee;

public class QuesScore {
    private String journo;

    private String examCode;

    private String ploNum;

    private String begDate;

    private String endDate;

    private Double userScore;

    private Integer compStat;

    private String handleIp;

    private String deptNum;

    private String deptGroup;

    private Employee user;
    
    ExamInfo examInfo;

	public String getJourno() {
		return journo;
	}

	public void setJourno(String journo) {
		this.journo = journo;
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public String getPloNum() {
		return ploNum;
	}

	public void setPloNum(String ploNum) {
		this.ploNum = ploNum;
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

	public Double getUserScore() {
		return userScore;
	}

	public void setUserScore(Double userScore) {
		this.userScore = userScore;
	}

	public Integer getCompStat() {
		return compStat;
	}

	public void setCompStat(Integer compStat) {
		this.compStat = compStat;
	}

	public String getHandleIp() {
		return handleIp;
	}

	public void setHandleIp(String handleIp) {
		this.handleIp = handleIp;
	}

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

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public ExamInfo getExamInfo() {
		return examInfo;
	}

	public void setExamInfo(ExamInfo examInfo) {
		this.examInfo = examInfo;
	}
    
    
    
}