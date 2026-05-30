package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.UserMapper;
import com.cmbccd.ulms.sys.domain.Menu;
import com.cmbccd.ulms.sys.domain.Role;
import com.cmbccd.ulms.sys.domain.User;
import com.cmbccd.ulms.sys.domain.UserRole;
import com.cmbccd.ulms.sys.service.RoleService;
import com.cmbccd.ulms.sys.service.UserRoleService;
import com.cmbccd.ulms.sys.service.UserService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月25日 下午12:44:13
 * @Version 1.0 Description:
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private RoleService roleService;

	@Resource
	private UserRoleService userRoleService;

	@Override
	public User login(User user) {
		User oUser = userMapper.selectByPrimaryKey(user.getCzyCode());

		if (oUser == null) {
			return null;
		}
		return oUser;
	}

	@Override
	public User selectByCzyCode(String czyCode) {
		return userMapper.selectByPrimaryKey(czyCode);
	}

	@Override
	public int updatePasswordByCzyCode(User user) {
		return userMapper.updatePasswordByPrimaryKey(user);
	}

	@Override
	public User getWithMenuList(String czyCode) {
		User user = selectByCzyCode(czyCode);
		if (user == null) {
			return null; // 防止 NPE
		}
		UserRole userRole = userRoleService.getUserRolesByPrimaryKey(user.getCzyCode());
		if (userRole == null) {
			return user; // 如果没有用户角色，直接返回用户对象
		}
		Set<String> permissionList = new HashSet<>();
		Set<Integer> menuList = new HashSet<>();
		List<Role> roles = roleService.getRoleByIdsForPermission(userRole.getRoles());
		for (Role role : roles) {
			role.getMenus().stream()
					.filter(menu -> Util.isNullorEmpty(menu.getResourse()))
					.map(Menu::getId)
					.forEach(menuList::add);

			role.getMenus().stream()
					.filter(menu -> !Util.isNullorEmpty(menu.getResourse()))
					.map(Menu::getResourse)
					.forEach(permissionList::add);
		}
		user.setMenuList(menuList);
		return user;
	}

}
