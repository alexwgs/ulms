package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.AdMapper;
import com.cmbccd.ulms.sys.domain.Ad;
import com.cmbccd.ulms.sys.domain.AdExample;
import com.cmbccd.ulms.sys.domain.AdExample.Criteria;
import com.cmbccd.ulms.sys.service.AdService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AdServiceImpl implements AdService {

	@Resource
	private AdMapper adMapper;

	@Override
	public List<Ad> listAds(AdExample example) {
		return adMapper.selectByExample(example);
	}

	@Override
	public DataPage<Ad> listAdsByQuery(Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		AdExample example = new AdExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("status"))) {
			if (!params.get("status").equals("-1")) {
				criteria.andStatusEqualTo(Integer.parseInt(params.get("status")));
			}
		}
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Ad> list = adMapper.selectByExample(example);
		return new DataPage<Ad>(list);
	}

	@Override
	public Integer addAd(Ad record) {
		int id = adMapper.selectNewId();
		record.setId(id);
		return adMapper.insert(record);
	}
	@Override
	public Integer updateAd(Ad record) {
		return adMapper.updateByPrimaryKey(record);
	}

	@Override
	public Integer deleteAd(int id) {
		return adMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Ad> ListIndexAd() {
		return adMapper.ListIndexAd();
	}
	
}
