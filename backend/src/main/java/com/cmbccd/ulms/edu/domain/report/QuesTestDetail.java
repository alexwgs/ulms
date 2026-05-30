package com.cmbccd.ulms.edu.domain.report;

import com.alibaba.excel.annotation.ExcelProperty;

public class QuesTestDetail {
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
    @ExcelProperty("试题编号")
    private String quesCode;
    @ExcelProperty("答题序号")
    private String quesOrder;
    @ExcelProperty("题目类型")
    private String quesType;
    @ExcelProperty("题干")
    private String quesStem;
    @ExcelProperty("选项")
    private String options;
    @ExcelProperty("答案")
    private String answer;
    @ExcelProperty("用户答案")
    private String userAnswer;
    @ExcelProperty("答题结果")
    private String ifCorrect;
    @ExcelProperty("该题分值")
    private String score;
    @ExcelProperty("答题时间")
    private String handleDate;
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
	public String getQuesCode() {
		return quesCode;
	}
	public void setQuesCode(String quesCode) {
		this.quesCode = quesCode;
	}
	public String getQuesOrder() {
		return quesOrder;
	}
	public void setQuesOrder(String quesOrder) {
		this.quesOrder = quesOrder;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public String getQuesStem() {
		return quesStem;
	}
	public void setQuesStem(String quesStem) {
		this.quesStem = quesStem;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public String getIfCorrect() {
		return ifCorrect;
	}
	public void setIfCorrect(String ifCorrect) {
		this.ifCorrect = ifCorrect;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}
    
    
    
}
