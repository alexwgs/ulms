package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.AdMapper;
import com.cmbccd.ulms.sys.domain.Ad;
import com.cmbccd.ulms.sys.domain.AdExample;
import com.cmbccd.ulms.sys.service.AdService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {

	@Resource
	private AdMapper adMapper;

	@Override
	public List<Ad> listAds(AdExample example) {
		return adMapper.selectByExample(example);
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
