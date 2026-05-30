package com.cmbccd.ulms.db.service.impl;

import com.cmbccd.ulms.db.dao.TableTreeMapper;
import com.cmbccd.ulms.db.domain.TableTree;
import com.cmbccd.ulms.db.domain.TableTreeExample;
import com.cmbccd.ulms.db.domain.TableTreeExample.Criteria;
import com.cmbccd.ulms.db.service.TableTreeService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TableTreeServiceImpl implements TableTreeService {

	@Resource
	private TableTreeMapper tableTreeMapper;
	
	@Override
	public List<TableTree> getTableTree(short status) {
		List<TableTree> treeList = new ArrayList<TableTree>();
	
		TableTreeExample example = new TableTreeExample();
		Criteria criteria = example.createCriteria();
		if(status != -1) {
			criteria.andStatusEqualTo(status);
		}
		List<TableTree> list = tableTreeMapper.selectTableNameTreeByExample(example);
				
		List<TableTree> list1 = list.stream().filter(e-> e.getTreeLevel() == (short)1).sorted(Comparator.comparing(TableTree::getSort)).collect(Collectors.toList());
		List<TableTree> list2 = list.stream().filter(e-> e.getTreeLevel() == (short)2).sorted(Comparator.comparing(TableTree::getSort)).collect(Collectors.toList());
		List<TableTree> list3 = list.stream().filter(e-> e.getTreeLevel() == (short)3).sorted(Comparator.comparing(TableTree::getSort)).collect(Collectors.toList());
		for (TableTree item : list1) {
			
			// 查找二级菜单
			List<TableTree> level2 =list2.stream().filter(e-> e.getPid().equals( item.getId())).collect(Collectors.toList());
			List<TableTree> treeList2 = new ArrayList<TableTree>();
			for(TableTree item2 : level2) {
				List<TableTree> level3 =list3.stream().filter(e-> e.getPid().equals(item2.getId())).collect(Collectors.toList());
				List<TableTree> treeList3 = new ArrayList<TableTree>();
				for(TableTree item3 : level3) {
					treeList3.add(item3);
				}
				item2.setChildren(treeList3);
				treeList2.add(item2);
			}
			item.setChildren(treeList2);
			treeList.add(item);
		}
		return treeList;
	}

	@Override
	public int updateById(TableTree record) {
		return tableTreeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteById(Integer id) {
		return tableTreeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int create(TableTree record) {
		record.setId(tableTreeMapper.selectNewId());
		return tableTreeMapper.insert(record);
	}

	@Override
	public int createWithIdByBatch(List<TableTree> records) {
		int count=0;
		for(TableTree record : records) {
			count+=tableTreeMapper.insertSelective(record);
		}
		return count;
	}

}
