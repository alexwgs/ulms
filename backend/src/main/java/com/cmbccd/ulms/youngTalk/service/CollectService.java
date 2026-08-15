package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.youngTalk.domain.Collect;

import java.util.List;
import java.util.Map;

public interface CollectService {

	public int newId();
	public int isUserCollectByArticalId(Integer articalId , String userId);
	
	public int insertCollect(Collect record);
	
	public int updateByPrimaryKey(Collect record);
	
	public int updateStatusByUserAndArtical (Collect record);
	
	public List<Collect> getCollectListByUserId(String userId);

	public DataPage<Collect> listCollectByQuery(Map<String, String> params, String userId);
}
