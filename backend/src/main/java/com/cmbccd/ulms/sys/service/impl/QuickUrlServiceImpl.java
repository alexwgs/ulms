package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.QuickUrlMapper;
import com.cmbccd.ulms.sys.domain.QuickUrl;
import com.cmbccd.ulms.sys.domain.QuickUrlExample;
import com.cmbccd.ulms.sys.domain.QuickUrlExample.Criteria;
import com.cmbccd.ulms.sys.service.QuickUrlService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class QuickUrlServiceImpl implements QuickUrlService {

	@Resource
	private QuickUrlMapper quickUrlMapper;

	@Override
	public List<QuickUrl> listQuickUrl(QuickUrlExample example) {		
		return quickUrlMapper.selectByExample(example);
	}

	@Override
	public DataPage<QuickUrl> listQuickUrlByAdmin(Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		QuickUrlExample example = new QuickUrlExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("status"))) {
			if (!params.get("status").equals("-1")) {
				criteria.andStatusEqualTo(Short.parseShort(params.get("status")));
			}
		}
		if (!Util.isNullorEmpty(params.get("area"))) {
			criteria.andAreaEqualTo(params.get("area"));
		}
		if (!Util.isNullorEmpty(params.get("query"))) {
			example.or().andNameLike('%' + params.get("query") + '%');
			example.or().andSysTypeLike('%' + params.get("query") + '%');
		}
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<QuickUrl> list = quickUrlMapper.selectByExample(example);
		return new DataPage<QuickUrl>(list);
	}

	@Override
	public QuickUrl getQuickUrlById(Integer id) {
		return quickUrlMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int insertQuickUrl(QuickUrl record) {
		record.setId(quickUrlMapper.getNewId());
		return quickUrlMapper.insert(record);
	}
	
	@Override
	public int updateQuickUrlById(QuickUrl record) {
		return quickUrlMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteQuickUrlById(Integer id) {
		return quickUrlMapper.deleteByPrimaryKey(id);
	}


	
}
