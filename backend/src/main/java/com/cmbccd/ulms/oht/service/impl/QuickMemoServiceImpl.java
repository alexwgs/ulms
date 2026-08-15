/**
 * 
 */
package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.dao.QuickMemoMapper;
import com.cmbccd.ulms.oht.domain.QuickMemo;
import com.cmbccd.ulms.oht.domain.QuickMemoExample;
import com.cmbccd.ulms.oht.service.QuickMemoService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	public DataPage<QuickMemo> getQuickMemoListByQuery(Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		QuickMemoExample example = new QuickMemoExample();
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<QuickMemo> quickMemoList = quickMemoMapper.selectByExample(example);
		return new DataPage<QuickMemo>(quickMemoList);
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
