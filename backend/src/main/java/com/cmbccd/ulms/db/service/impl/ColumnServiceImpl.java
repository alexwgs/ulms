package com.cmbccd.ulms.db.service.impl;

import com.cmbccd.ulms.db.dao.ColumnMapper;
import com.cmbccd.ulms.db.domain.Column;
import com.cmbccd.ulms.db.domain.ColumnExample;
import com.cmbccd.ulms.db.domain.ColumnExample.Criteria;
import com.cmbccd.ulms.db.service.ColumnService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class  ColumnServiceImpl implements ColumnService {

	@Resource
	private ColumnMapper columnMapper;

	@Override
	public List<Column> listColumnsByTid(int tid) {
		ColumnExample example = new ColumnExample();
		Criteria criteria = example.createCriteria();
		criteria.andTidEqualTo(tid);
		example.setOrderByClause(" sort ");
		List<Column> list = columnMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Column> listColumnsByTableName(String tableName) {
		return columnMapper.selectColumnByTableName(tableName);
	}


	@Override
	public int update(Column record) {
		return columnMapper.updateByPrimaryKey(record);
	}

}
