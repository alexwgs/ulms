package com.cmbccd.ulms.edu.domain.report;

import com.alibaba.excel.annotation.ExcelProperty;

public class CategoryInfo {

	@ExcelProperty("业务")
	private String category;
	@ExcelProperty("答题总量")
	private Integer total;
	@ExcelProperty("正确数量")
	private Integer rightNum;
	@ExcelProperty("正确率")
	private Double rightRate;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

}
