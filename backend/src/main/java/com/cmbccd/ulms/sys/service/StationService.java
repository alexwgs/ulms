package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.Station;

import java.util.List;
import java.util.Map;

/** 
* @Author WeiGenSheng
* @Time 2020年4月29日 下午3:16:39 
* @Version 1.0
* @Description:
*/
public interface StationService {

	public List<Station> getStationList(Map<String, String> params);
	
	public int updateStationByPcIp(Station record);
	
	public int deleteStationByIp(String pcIp);
	
	public int insertStation(Station record);

	public int insertOrUpdate(Station record);
	
	public Station getStationByIp(String pcIp);
	
	public Station selectByIpNoMemo(String pcIp);
	
}
