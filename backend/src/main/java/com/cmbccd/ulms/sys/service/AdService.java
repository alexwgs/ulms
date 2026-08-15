package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.sys.domain.Ad;
import com.cmbccd.ulms.sys.domain.AdExample;

import java.util.List;
import java.util.Map;

public interface AdService {

	public List<Ad> ListIndexAd();
	
	public List<Ad> listAds(AdExample example);

	public DataPage<Ad> listAdsByQuery(Map<String, String> params);
	
	public Integer addAd(Ad record);
	
	public Integer deleteAd(int id) ;	
	
	public Integer updateAd(Ad reord);

}
