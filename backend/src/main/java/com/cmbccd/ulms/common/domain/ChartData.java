package com.cmbccd.ulms.common.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 
* <p>Title: ChartData.java</p>  
* <p>Description:图标数据方法 </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public class ChartData {
	List<String> columns;
	List<Map<String, Object>> rows;
	
	public ChartData question () {
		ChartData question=new ChartData();
		List<String> cols = new ArrayList<>();
		cols.add("选项");
		cols.add("选择人数");
		question.setColumns(cols);
		return question;
	}
	
	public List<String> getColumns() {
		return columns;
	}
	public List<Map<String, Object>> getRows() {
		return rows;
	}
	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

}
