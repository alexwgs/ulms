package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.BrushConfigMapper;
import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.BrushConfigExample;
import com.cmbccd.ulms.edu.domain.BrushConfigExample.Criteria;
import com.cmbccd.ulms.edu.service.BrushConfigService;
import com.cmbccd.ulms.sys.service.PublicService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BrushConfigServiceImpl implements BrushConfigService {

	@Resource
	private BrushConfigMapper brushConfigMapper;
	
	@Resource
	private PublicService publicService;
	
	@Override
	public List<BrushConfig> list(BrushConfigExample example) {
		return brushConfigMapper.selectByExample(example);
	}

	@Override
	public DataPage<BrushConfig> listBrushConfigByQuery(Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		BrushConfigExample example = new BrushConfigExample();
		String queryType = params.get("queryType");
		String query = params.get("query");
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		if (!Util.isNullorEmpty(queryType) && !Util.isNullorEmpty(query)) {
			if ("groupName".equals(queryType)) {
				criteria.andGroupNameLike("%" + query + "%");
			}
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<BrushConfig> list = brushConfigMapper.selectByExample(example);
		return new DataPage<BrushConfig>(list);
	}

	@Override
	public int create(BrushConfig record) {
		record.setBrushCode(publicService.getJourno());
		return brushConfigMapper.insert(record);
	}

	@Override
	public int delete(String brushCode) {
		
		return brushConfigMapper.deleteByPrimaryKey(brushCode);
	}

	@Override
	public int update(BrushConfig record) {
		
		return brushConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public BrushConfig get(String brushCode) {
		
		return brushConfigMapper.selectByPrimaryKey(brushCode);
	}

	@Override
	public BrushConfig getBrushConfig(String ploNum) {
		List<BrushConfig> list = brushConfigMapper.getBrushConfig(ploNum);
		if(list.size()>0) return list.get(0);
		return null;
	}

}
