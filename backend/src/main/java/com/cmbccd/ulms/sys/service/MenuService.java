package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.Menu;
import com.cmbccd.ulms.sys.domain.MenuExample;

import java.util.List;
import java.util.Set;


/** 
* @Author WeiGenSheng
* @Time 2020年4月25日 下午6:14:00 
* @Version 1.0
* Description:
*/
public interface MenuService {
	List<Menu> getMenu(String system ,Short useage);
	List<Menu> getMenuWithAuth(String system , Short useage,Set<Integer> authMenuList);
	int addMenu(Menu menu);
	int updateMenu(Menu menu , MenuExample menuExample);
	int deleteMenu(MenuExample menuExample);
	//新生成一个菜单ID
	int selectNewId();
	
	Menu getMenuById(Integer id);
	
	List<Menu> getMenuByPermission(List<Integer> permissions , Short usage);

	List<String> selectFontPermissionByIds(String [] authMenuList);

	List<Menu> getPermissionByRoleIds(String[] roleIds);
}
