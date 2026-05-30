package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.MenuMapper;
import com.cmbccd.ulms.sys.domain.Menu;
import com.cmbccd.ulms.sys.domain.MenuExample;
import com.cmbccd.ulms.sys.domain.MenuExample.Criteria;
import com.cmbccd.ulms.sys.domain.MenuMeta;
import com.cmbccd.ulms.sys.service.MenuService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月25日 下午6:15:18
 * @Version 1.0 Description:
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuMapper menuMapper;

	@Override
	public List<Menu> getMenuWithAuth(String system ,Short useage, Set<Integer> authMenuList) {
		// 设置过滤逻辑 useage=0后台 1前台菜单
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andUseageEqualTo(useage);
		criteria.andMenuTypeEqualTo((short)0);
		criteria.andSystemEqualTo(system);
		// 获取全部菜单列表
		List<Menu> menus = menuMapper.selectByExample(example);
		// 剔除不再权限列表authMenuList中的菜单编号
		menus = menus.stream().filter(e -> authMenuList.contains(e.getId())).collect(Collectors.toList());

		// 由于PID为0时为一级菜单，首先将所有一级菜单获取
		List<Menu> rootMenus = menus.stream().filter(e -> e.getPid() == 0 && e.getStatus() != 0)
				.sorted(Comparator.comparing(Menu::getSort)).collect(Collectors.toList());

		// 查找二级菜单
		for (Menu rootMenu : rootMenus) {
			rootMenu.setMeta(new MenuMeta(rootMenu.getName(), rootMenu.getIcon()));
			rootMenu.setChildren(findMenuChild(rootMenu.getId(), menus, false));
		}
		return rootMenus;
	}

	@Override
	public List<Menu> getMenu(String system , Short useage) {
		MenuExample example = new MenuExample();
		example.setOrderByClause(" USEAGE , SORT ");

		Criteria cirteria = example.createCriteria();
		if (!Util.isNullorEmpty(useage)) {
			cirteria.andUseageEqualTo(useage);
		}
		if (!Util.isNullorEmpty(system)) {
			cirteria.andSystemEqualTo(system);
		}
		List<Menu> menus = menuMapper.selectByExample(example);
		// 由于PID为0时为一级菜单，首先将所有一级菜单获取
//		List<Menu> rootMenus = menus.stream().filter(e -> e.getPid()==0 ).sorted(Comparator.comparing(Menu::getSort)).collect(Collectors.toList());
		List<Menu> rootMenus = menus.stream().filter(e -> e.getPid() == 0).collect(Collectors.toList());

		// 查找二级菜单
		for (Menu rootMenu : rootMenus) {
			rootMenu.setMeta(new MenuMeta(rootMenu.getName(), rootMenu.getIcon()));
			rootMenu.setChildren(findMenuChild(rootMenu.getId(), menus, false));
		}
		return rootMenus;
	}

	public List<Menu> findMenuChild(Integer id, List<Menu> rootMenus, boolean authFlag) {
		boolean authCireria = true;
		List<Menu> childList = new ArrayList<>();
		// 遍历所有节点，将父菜单id与传过来的id比较
		for (Menu menu : rootMenus) {
			if (authFlag) {
				authCireria = menu.getStatus() != 0 ? true : false;
			}
			if (id.equals(menu.getPid())  && authCireria) {
				menu.setMeta(new MenuMeta(menu.getName(), menu.getIcon()));
				childList.add(menu);
			}
		}
//		List<Menu> permitionMenus = new ArrayList<>();
//		if (!authFlag) {
//			MenuExample example = new MenuExample();
//			Criteria cirteria = example.createCriteria();
//			cirteria.andMenuTypeEqualTo(1);
//			permitionMenus = menuMapper.selectByExample(example);
//		}
		childList.sort(Comparator.comparing(Menu::getSort));
		// 把子菜单的子菜单再循环一遍

		// 二级菜单
		for (Menu menu : childList) {
			List<Menu> pList = new ArrayList<>();
			 // 三级权限菜单
			for (Menu pmenu : rootMenus) {
				if (pmenu.getPid().equals(menu.getId())) {
					pmenu.setMeta(new MenuMeta(pmenu.getName(), pmenu.getIcon()));
					pList.add(pmenu);
				}
			}

			if (pList != null && pList.size() != 0) {

				pList.sort(Comparator.comparing(Menu::getSort));
				menu.setChildren(pList);
			}
		}
		return childList;
	}

	@Override
	public int addMenu(Menu menu) {
		// 获取新的ID
		menu.setId(menuMapper.creatNewMenuId());
		return menuMapper.insert(menu);
	}

	@Override
	public int updateMenu(Menu menu, MenuExample menuExampe) {

		return menuMapper.updateByExample(menu, menuExampe);
	}

	@Override
	public int deleteMenu(MenuExample menuExample) {
		return menuMapper.deleteByExample(menuExample);
	}

	@Override
	public int selectNewId() {
		return menuMapper.creatNewMenuId();
	}

	@Override
	public Menu getMenuById(Integer id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Menu> getMenuByPermission(List<Integer> permissions, Short useage) {
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(permissions);
		if (!Util.isNullorEmpty(useage)) {
			criteria.andUseageEqualTo(useage);
		}
		example.setOrderByClause(" id asc ");
//		criteria.andMenuTypeEqualTo(0);
		List<Menu> menus = menuMapper.selectByExample(example);
		List<Menu> permissionMenus = menus.stream().filter(e -> e.getPid() == 0 && e.getStatus() != 0)
				.sorted(Comparator.comparing(Menu::getSort)).collect(Collectors.toList());
		// 查找二级菜单
		for (Menu rootMenu : permissionMenus) {
			rootMenu.setChildren(findMenuPermissionChild(rootMenu.getId(), menus, false));
		}
		return permissionMenus;
	}

	/**
	 * @Description:设置二级及三级菜单内容，三级菜单设置为权限列表
	 * @param
	 * @return : List<Menu>
	 */
	public List<Menu> findMenuPermissionChild(Integer id, List<Menu> rootMenus, boolean authFlag) {
		boolean authCireria = true;
		List<Menu> childList = new ArrayList<>();
		// 遍历所有节点，将父菜单id与传过来的id比较
		for (Menu menu : rootMenus) {
			if (authFlag) {
				authCireria = menu.getStatus() != 0 ? true : false;
			}
			if (id == menu.getPid() && authCireria) {
				childList.add(menu);
			}
		}
		childList.sort(Comparator.comparing(Menu::getSort));
		// 把子菜单的子菜单再循环一遍
		// 二级菜单
		for (Menu menu : childList) {
			List<Menu> pList = new ArrayList<>();
			 // 三级权限菜单
			for (Menu pmenu : rootMenus) {
				if (menu.getId() == pmenu.getPid() && pmenu.getMenuType() == 1) {
					pList.add(pmenu);
				}
			}

			if (pList != null && pList.size() != 0) {

				pList.sort(Comparator.comparing(Menu::getSort));
				menu.setChildren(pList);
			}
		}
		return childList;
	}

	@Override
	public List<String> selectFontPermissionByIds(String[] authMenuList) {
		return menuMapper.selectFontPermissionByIds(authMenuList);
	}

	@Override
	public List<Menu> getPermissionByRoleIds(String[] roleIds) {
		return menuMapper.selectByRoleId(roleIds);
	}

}
