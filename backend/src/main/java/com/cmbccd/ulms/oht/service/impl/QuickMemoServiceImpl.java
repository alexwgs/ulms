/**
 * 
 */
package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.oht.dao.QuickMemoMapper;
import com.cmbccd.ulms.oht.domain.QuickMemo;
import com.cmbccd.ulms.oht.domain.QuickMemoExample;
import com.cmbccd.ulms.oht.service.QuickMemoService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 
* <p>Title: QuickMemoServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class QuickMemoServiceImpl implements QuickMemoService {

	@Resource
	private QuickMemoMapper quickMemoMapper;

	@Override
	public List<QuickMemo> getQuickMemoList(QuickMemoExample example) {
		return quickMemoMapper.selectByExample(example);
	}

	@Override
	public List<QuickMemo> getQuickMemoByRoleType(String roleType) {
		return quickMemoMapper.selectByRoleType(roleType);
	}

	@Override
	public int addQuickMemo(QuickMemo record) {
		record.setJourno(quickMemoMapper.selectNewJourno());
		return quickMemoMapper.insert(record);
	}

	@Override
	public int updateQuickMemoByJourno(QuickMemo record) {
		return quickMemoMapper.updateByJourno(record);
	}

	@Override
	public int deleteQuickMemoByJourno(String journo) {
		return quickMemoMapper.deleteByJourno(journo);
	}

	@Override
	public String selectNewJourno() {
		return quickMemoMapper.selectNewJourno();
	}

}
