package com.cmbccd.ulms.db.service.impl;

import com.cmbccd.ulms.db.dao.ColumnMapper;
import com.cmbccd.ulms.db.dao.TableMapper;
import com.cmbccd.ulms.db.domain.Column;
import com.cmbccd.ulms.db.domain.Table;
import com.cmbccd.ulms.db.service.TableService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {
	@Resource
	private TableMapper tableMapper;
	
	@Resource
	private ColumnMapper columnMapper;

	@Override
	public Table getTableInfoById(int id) {
		// 每次获取表信息均增加一次访问量
		increaseClick(id);
		return tableMapper.selectByPrimaryKey(id);
	}

	@Override
	public  void  increaseClick(int id) {
		tableMapper.updateClickNumIncreaseById(id);
	}

	@Override
	public List<Table> selectTablesSorted(boolean sortedFlag, String keyWord) {
		if(sortedFlag) {
			return tableMapper.selectTablesSorted(keyWord);
		}else {
			return tableMapper.selectTablesNotSorted(keyWord);
		}
	}

	@Override
	public String syncTableInfo() {
		String msg = "";
		int count = 0;
		count = tableMapper.updateExistTables();
		msg = "成功更新表["+count+"]张！";
		List<Table> tlist = tableMapper.selectNotExistTables();
		for(Table record: tlist) {
			record.setId(tableMapper.selectNewId());
			count += tableMapper.insert(record);
		}
		msg += "成功新增加表["+count+"]张！";
		List<Column> clist1 = columnMapper.selectExistColumn();
		for(Column record : clist1) {
			count += columnMapper.updateByPrimaryKeySelective(record);
		}
		msg += "成功更新字段["+count+"]个！";
		List<Column> clist2 = columnMapper.selectNotExistColumn();
		for(Column record : clist2 ) {
			record.setId(columnMapper.selectNewId());
			count += columnMapper.insertSelective(record);
		}
		msg += "成功新增字段["+count+"]个！";
		return msg;
	}

	@Override
	public int update(Table record) {
		return tableMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateSelective(Table record) {
		return tableMapper.updateByPrimaryKeySelective(record);
	}
}
