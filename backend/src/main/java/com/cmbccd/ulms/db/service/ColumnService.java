package com.cmbccd.ulms.db.service;

import com.cmbccd.ulms.db.domain.Column;

import java.util.List;

public interface ColumnService {

	List<Column> listColumnsByTid(int tid);

	List<Column> listColumnsByTableName(String tableName);
	
	int update(Column record);
}
