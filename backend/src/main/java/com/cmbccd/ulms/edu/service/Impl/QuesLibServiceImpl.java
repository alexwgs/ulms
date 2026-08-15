package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.QuesBankMapper;
import com.cmbccd.ulms.edu.dao.QuesLibMapper;
import com.cmbccd.ulms.edu.domain.QuesLib;
import com.cmbccd.ulms.edu.service.QuesLibService;
import com.cmbccd.ulms.sys.service.PublicService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuesLibServiceImpl implements QuesLibService {

	@Resource
	private QuesLibMapper quesLibMapper;
	@Resource
	private QuesBankMapper quesBankMapper;
	@Resource
	private PublicService publicService;


	@Override
	public List<QuesLib> quesTree(Integer status) {
		List<QuesLib> list = quesLibMapper.selectTreeByStat(status);
		
		List<QuesLib> list1 = list.stream().filter(e -> e.getLibLevel() == 0 ).collect(Collectors.toList());
		List<QuesLib> list2 = list.stream().filter(e -> e.getLibLevel() == 1 ).collect(Collectors.toList());
		List<QuesLib> list3 = list.stream().filter(e -> e.getLibLevel() == 2 ).collect(Collectors.toList());
		
		List<QuesLib> treeList = new ArrayList<QuesLib>();
		for (QuesLib item : list1) {
			// 查找二级菜单
			List<QuesLib> level2 =list2.stream().filter(e-> e.getUpCode().equals( item.getLibCode())).collect(Collectors.toList());
			List<QuesLib> treeList2 = new ArrayList<QuesLib>();
			for(QuesLib item2 : level2) {
				List<QuesLib> level3 =list3.stream().filter(e-> e.getUpCode().equals(item2.getLibCode())).collect(Collectors.toList());
				List<QuesLib> treeList3 = new ArrayList<QuesLib>();
				for(QuesLib item3 : level3) {
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
	public int updateByLibCode(QuesLib record) {
		String userId = Util.userIdByShiro();
		int count = quesLibMapper.updateByPrimaryKeySelective(record);
		if (record.getLibStat() == 0) {
			// 设置该题库下的题目全部置空
			updateLibQuesionInvalid(record.getLibCode(), userId);
			quesBankMapper.invalidAllQuestionByQuesLib(userId,record.getLibCode());
		}
		return count;
	}


	@Override
	public int deleteByLibCode(String libCode) {
		return quesLibMapper.deleteByPrimaryKey(libCode);
	}


	@Override
	public int create(QuesLib record) {
		record.setLibCode(publicService.getJourno());
		return quesLibMapper.insert(record);
	}

	@Override
	public int updateLibQuesionInvalid(String libCode, String handlePlo) {
		return quesLibMapper.updateLibQuesionInvalid(libCode, handlePlo);
	}

}
