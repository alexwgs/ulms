package com.cmbccd.ulms.oht.service;

import com.cmbccd.ulms.oht.domain.RoleList;
import com.cmbccd.ulms.oht.domain.RoleListExample;

import java.util.List;
/**
 * 
* <p>Title: RoleListService.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public interface RoleListService {

	public List<RoleList> getRoleListByExample(RoleListExample example);
	
	public List<RoleList> getUserRoleListByRoleType(Integer roleType ,Integer status);

	public List<RoleList> getUserRoleListByUserId(String userId ,Integer status);
	
	public int deleteUserRole(String ploNum);
	
	public int updateUserRole(RoleList record);
	
	public int insertUserRole(RoleList record);
}
