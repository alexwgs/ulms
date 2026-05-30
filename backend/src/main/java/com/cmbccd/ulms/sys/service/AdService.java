package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.Ad;
import com.cmbccd.ulms.sys.domain.AdExample;

import java.util.List;

public interface AdService {

	public List<Ad> ListIndexAd();
	
	public List<Ad> listAds(AdExample example);
	
	public Integer addAd(Ad record);
	
	public Integer deleteAd(int id) ;	
	
	public Integer updateAd(Ad reord);

}
