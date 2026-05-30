package com.cmbccd.ulms.db.service;

import com.cmbccd.ulms.db.domain.TableTree;

import java.util.List;

public interface TableTreeService {

	public List<TableTree> getTableTree(short status);
	
	public int updateById(TableTree record);
	
	public int deleteById(Integer id);
	
	public int create(TableTree record);
	
	public int createWithIdByBatch(List<TableTree> record);
	
}
