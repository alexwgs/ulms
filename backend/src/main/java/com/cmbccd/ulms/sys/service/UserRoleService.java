/**
 * 
 */
package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.UserRole;

/**
 * 
* <p>Title: UserRoleService.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public interface UserRoleService {
	UserRole getUserRolesByPrimaryKey(String userId);
	//插入新的用户角色
	Integer insertNewUserRole(UserRole userRole);
	
	//根据主键修改角色信息
	Integer updateUserRoleByPrimaryKey(UserRole userRole);
}
