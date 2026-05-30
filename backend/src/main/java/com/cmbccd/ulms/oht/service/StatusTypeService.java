package com.cmbccd.ulms.oht.service;

import com.cmbccd.ulms.oht.domain.StatusType;
import com.cmbccd.ulms.oht.domain.StatusTypeExample;

import java.util.List;
import java.util.Map;
/**
 * 
* <p>Title: StatusTypeService.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public interface StatusTypeService {
	/**
	 * 获取新的ID
	 * @return int
	 */
    int selectNewId();
	
	public List<StatusType> getStatusByTree();
	
	public List<StatusType> getIdentity();
	
	public List<StatusType> getStatusTypeList();
	
	public int identityNameOhtFlag(String[] statusNames);
	
	public Map<String, String> getUserStatusAndOhtFlag (int id);
	
	public List<StatusType> getAllStatusType();
	
	public List<StatusType> getStatusTypeByExample(StatusTypeExample example);
	
	public int updateStatusTypeByPrimaryId(StatusType record);
	
	public int insertStatusType(StatusType record);
}
