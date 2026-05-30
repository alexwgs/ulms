package com.cmbccd.ulms.edu.domain.report;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

public class BrushScoreDept {
    @ExcelProperty("科室")
	private String deptNum;
    
    @ExcelProperty("人力")
	private Double humAct;
    
    @ExcelProperty("答题天数")
	private Integer answerDays;
    
    @ExcelProperty("答题总量")
	private Integer total;
    
    @ExcelProperty("正确数量")
	private Integer rightNum;
    
    @ExcelProperty("正确率")
	private Double rightRate;
    
    @ExcelProperty("薄弱业务一")
	private String weakPoint1;
    
    @ExcelProperty("薄弱业务二")
	private String weakPoint2;
    
    @ExcelProperty("薄弱业务三")
	private String weakPoint3;
    
    @ExcelIgnore
	private Integer rank;

	public String getDeptNum() {
		return deptNum;
	}

	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}

	public Double getHumAct() {
		return humAct;
	}

	public void setHumAct(Double humAct) {
		this.humAct = humAct;
	}

	public Integer getAnswerDays() {
		return answerDays;
	}

	public void setAnswerDays(Integer answerDays) {
		this.answerDays = answerDays;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRightNum() {
		return rightNum;
	}

	public void setRightNum(Integer rightNum) {
		this.rightNum = rightNum;
	}

	public Double getRightRate() {
		return rightRate;
	}

	public void setRightRate(Double rightRate) {
		this.rightRate = rightRate;
	}

	public String getWeakPoint1() {
		return weakPoint1;
	}

	public void setWeakPoint1(String weakPoint1) {
		this.weakPoint1 = weakPoint1;
	}

	public String getWeakPoint2() {
		return weakPoint2;
	}

	public void setWeakPoint2(String weakPoint2) {
		this.weakPoint2 = weakPoint2;
	}

	public String getWeakPoint3() {
		return weakPoint3;
	}

	public void setWeakPoint3(String weakPoint3) {
		this.weakPoint3 = weakPoint3;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
    
}
