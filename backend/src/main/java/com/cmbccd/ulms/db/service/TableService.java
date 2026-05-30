package com.cmbccd.ulms.db.service;

import com.cmbccd.ulms.db.domain.Table;

import java.util.List;

public interface TableService {
	
	public Table getTableInfoById(int id);
	
	public void increaseClick(int id);
	
	public List<Table> selectTablesSorted(boolean sortedFlag, String keyWord);
	
	public String syncTableInfo();
	
	int update(Table record);
	
	int updateSelective(Table record);
}
