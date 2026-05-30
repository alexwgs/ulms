/**
 * 
 */
package com.cmbccd.ulms.sys.service.impl;
/** 
* @Author WeiGenSheng
* @Time 2020年5月7日 上午10:51:08 
* @Version 1.0
* @Description:
*/

import com.cmbccd.ulms.sys.dao.UserRoleMapper;
import com.cmbccd.ulms.sys.domain.UserRole;
import com.cmbccd.ulms.sys.service.UserRoleService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/** 
* @Description: （对类进行功能描述）
* @date: datedate{time} （日期）
*/
@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Resource
	private UserRoleMapper userRoleMapper;
	@Override
	public UserRole getUserRolesByPrimaryKey(String userId) {
		return userRoleMapper.selectByPrimaryKey(userId);
	}

	@Override
	public Integer insertNewUserRole(UserRole userRole) {
		return userRoleMapper.insert(userRole);
	}

	@Override
	public Integer updateUserRoleByPrimaryKey(UserRole userRole) {
		return userRoleMapper.updateByPrimaryKey(userRole);
	}

}
