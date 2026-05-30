package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.Role;
import com.cmbccd.ulms.sys.domain.RoleExample;

import java.util.List;

/** 
* @Author WeiGenSheng
* @Time 2020年5月4日 下午7:00:18 
* @Version 1.0
* Description:
*/
public interface RoleService {

	public List<Role> getRoleList(RoleExample example);
	
	public Role getRoleById(Integer id);

	public List<Role> getRoleByIdsForPermission(String ids);
	
	public List<Role> getRolePermissionTree(RoleExample example);
	//使用roleId更新角色权限列表
	public Integer updatePermissionByRoleId(Role role, Integer id);
	//使用roleID删除角色
	public Integer deleteRoleByRoleId(Integer id);
	//新增一个role角色
	public Integer addRole(Role role);
	//修改一个role的信息
	public Integer updateRoleByRoleId(Role role);
	
	
}
