package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.RoleMapper;
import com.cmbccd.ulms.sys.domain.Menu;
import com.cmbccd.ulms.sys.domain.Role;
import com.cmbccd.ulms.sys.domain.RoleExample;
import com.cmbccd.ulms.sys.domain.RoleExample.Criteria;
import com.cmbccd.ulms.sys.service.MenuService;
import com.cmbccd.ulms.sys.service.RoleService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author WeiGenSheng
 * @Time 2020年5月4日 下午7:00:40
 * @Version 1.0 Description:
 */
@Service
public class RoleServiceImpl implements RoleService {


	@Resource
	private RoleMapper roleMapper;
	@Resource
	private MenuService menuService;

	@Override
	public List<Role> getRoleList(RoleExample example) {
		
		return roleMapper.selectByExample(example);
	}

	@Override
	public Role getRoleById(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<Role> getRoleByIdsForPermission(String ids) {
		List<Role> roleList = new ArrayList<Role>();

		if (Util.isNullorEmpty(ids)) {
			return roleList;
		}
		String[] roleIdList = ids.split(",");

		for (String roleId : roleIdList) {
			List<Menu> roleMenus = new ArrayList<Menu>();

			Role role = roleMapper.selectByPrimaryKey(Integer.parseInt(roleId));
			String[] permissionList = role.getPermissions().split(",");
			for (String pid : permissionList) {
				if (Util.isNullorEmpty(pid.trim())) continue;
				Menu menu = menuService.getMenuById(Integer.parseInt(pid));
				if (Util.isNullorEmpty(menu)) {
					continue;
				}
//				if(Util.isNullorEmpty(menu.getResourse())) continue;
				roleMenus.add(menu);
			}

			role.setMenus(roleMenus);
			roleList.add(role);
		}

		return roleList;
	}

	@Override
	public List<Role> getRolePermissionTree(RoleExample example) {

		List<Role> roles = roleMapper.selectByExample(example);
		for (Role role : roles) {

			if (Util.isNullorEmpty(role.getPermissions())) {
				continue;
			}
			String[] permissions = role.getPermissions().split(",");

			if (permissions == null) {
				continue;
			}
			List<Integer> permissionList = new ArrayList<Integer>();
			for (String permission : permissions) {
				if (Util.isNullorEmpty(permission.trim())) continue;
				permissionList.add(Integer.parseInt(permission));
			}

//		获取角色的权限信息
			role.setChildren(menuService.getMenuByPermission(permissionList, null));

		}
		// 由于PID为0时为一级菜单，首先将所有一级菜单获取

		return roles;
	}

	/*
	 * 修改权限列表
	 * 
	 */
	@Override
	public Integer updatePermissionByRoleId(Role record, Integer id) {
		RoleExample example = new RoleExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);

		int updateCount = roleMapper.updateByExampleSelective(record, example);
		return updateCount;
	}

	/* 
	 *
	 */
	@Override
	public Integer deleteRoleByRoleId(Integer id) {
		Integer deleteCount = roleMapper.deleteByPrimaryKey(id);
		return deleteCount;
	}

	/* 
	 * 
	 */
	@Override
	public Integer addRole(Role role) {
		Integer roleId = roleMapper.selectNewRoleId();
		role.setId(roleId);
		return roleMapper.insert(role);
	}

	/* 
	 * 
	 */
	@Override
	public Integer updateRoleByRoleId(Role role) {
		return roleMapper.updateByPrimaryKeySelective(role);
	}

}
