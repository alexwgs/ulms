package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Collect;

import java.util.List;

public interface CollectService {

	public int newId();
	public int isUserCollectByArticalId(Integer articalId , String userId);
	
	public int insertCollect(Collect record);
	
	public int updateByPrimaryKey(Collect record);
	
	public int updateStatusByUserAndArtical (Collect record);
	
	public List<Collect> getCollectListByUserId(String userId);
}
