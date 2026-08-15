package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.*;
import com.cmbccd.ulms.sys.domain.MenuExample.Criteria;
import com.cmbccd.ulms.sys.service.MenuService;
import com.cmbccd.ulms.sys.service.RoleService;
import com.cmbccd.ulms.sys.service.UserRoleService;
import com.cmbccd.ulms.sys.service.UserService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月25日 下午6:37:35
 * @Version 1.0 Description:
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

	@Resource
	private MenuService menuService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private RoleService roleService;
    @Resource
    private UserService userService;
	/**
	 * 后台管理菜单，不返回失效菜单及无权限菜单
	 *
	 * @return Msg
	 */

	@GetMapping(value = "bsAuthMenu")
	public Msg getBsAuthMenu() {
        String userId = Util.userIdByShiro();
        if (Util.isNullorEmpty(userId)) {
            return Msg.error("用户未登录！");
        }
        User user = userService.getWithMenuList(userId);
        if (Util.isNullorEmpty(user)) {
            return Msg.error("用户不存在！");
        }
		Set<Integer> authMenuList = user.getMenuList();
		List<Menu> menuList = menuService.getMenuWithAuth("a6squre",(short)0, authMenuList);
		return Msg.success(menuList);
	}


	/**
	 * 返回前台菜单，不返回前台失效无权限菜单
	 *
	 * @return Msg
	 */

	@GetMapping(value = "/AuthMenu")
	public Msg getAuthMenu() {
        String userId = Util.userIdByShiro();
        if (Util.isNullorEmpty(userId)) {
            return Msg.error("用户未登录！");
        }
        User user = userService.getWithMenuList(userId);
        if (Util.isNullorEmpty(user)) {
            return Msg.error("用户不存在！");
        }
		Set<Integer> authMenuIds = user.getMenuList();

		String permissions = "";
		// 获取前台菜单的权限列表
		UserRole userRole = userRoleService.getUserRolesByPrimaryKey(user.getCzyCode());
		if (userRole == null) {
			return Msg.error("当前账号暂无用户角色，请联系魏根生[655012]分配角色！");
		}

		String[] roleArray = userRole.getRoles().split(",");

		for (String id : roleArray) {
			permissions += "0," + roleService.getRoleById(Integer.parseInt(id)).getPermissions() + ",";
		}
		List<String> permissionList = menuService.selectFontPermissionByIds(permissions.split(","));
		List<Menu> menuList = menuService.getMenuWithAuth("a6squre",(short)1, authMenuIds);
		return Msg.success().put("menu", menuList).put("permissions", permissionList);
	}

	/**
	 * 管理员后台菜单管理（显示全部，含失效菜单）
	 * @return Msg
	 */

	@GetMapping(value = "/menu")
//	@SaCheckPermission("menu:list")
	public Msg getfontMenu(@RequestParam Map<String, String> params) {
		String system = params.get("system");
		if(Util.isNullorEmpty(system))	system = "a6squre";
		// useage 不再作为强制过滤条件：不传则返回全部菜单（前台+后台统一管理）
		Short useage = null;
		String useageStr = params.get("useage");
		if(!Util.isNullorEmpty(useageStr)) {
			useage = Short.parseShort(useageStr);
		}
		List<Menu> menuList = menuService.getMenu(system,useage);
		return Msg.success( menuList);
	}


	/**
	 * 返回前台有菜单，包含失效菜单
	 *
	 * @return Msg
	 */
	@GetMapping(value = "/menus")
	@SaCheckPermission("menu:list")
	public Msg getMenus(@RequestParam Map<String, String> params) {
		String system = params.get("system");
		if(Util.isNullorEmpty(system))	system = null;
		List<Menu> menuList = menuService.getMenu(system,null);
		return Msg.success(menuList);
	}

	/**
	 * 菜单新增
	 *
	 * @param menu
	 * @return
	 */

	@PostMapping(value = "/menus")
	@SaCheckPermission("menu:add")
	@MyLog(title = "[sys-menu]菜单管理")
	public Msg menuAdd(@RequestBody Menu menu) {
		Integer count = menuService.addMenu(menu);
		if (count == 0) {
			return Msg.error("操作失败，没有新增数据！");
		}
		return Msg.success("成功添加 " + count + " 条菜单记录！");
	}

	/**
	 * 菜单更新
	 *
	 * @param menu
	 * @return
	 */

	@PutMapping(value = "{id}")
	@SaCheckPermission("menu:update")
	@MyLog(title = "[sys-menu]菜单管理")
	public Msg menuUpdage(@RequestBody Menu menu, @PathVariable(value = "id") Integer id) {
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(menu.getId());
		Integer count = menuService.updateMenu(menu, example);
		if (count == 0) {
			return Msg.error("操作失败，没有修改数据！");
		}
		return Msg.success("成功修改 " + count + " 条菜单记录！");
	}

	/**
	 * 菜单删除
	 *
	 * @param id,pid
	 * @return
	 */

	@DeleteMapping(value = "{id}/{pid}")
	@SaCheckPermission("menu:delete")
	@MyLog(title = "[sys-menu]菜单管理")
	public Msg menuDelete(@PathVariable(value = "id") Integer id, @PathVariable(value = "pid") Integer pid) {

		if (id == null) {
			return Msg.error("没有获取到菜单数据！");
		}
		if (pid == null) {
			return Msg.error("没有获取到菜单数据！");
		}

		MenuExample example = new MenuExample();
		Criteria crieria = example.createCriteria();
		crieria.andIdEqualTo(id);
		Integer count = menuService.deleteMenu(example);

		if (pid == 0) {
			example.clear();
			crieria = example.createCriteria();
			crieria.andPidEqualTo(id);
			count += menuService.deleteMenu(example);
		}

		if (count == 0) {
			return Msg.error("操作失败，没有删除数据！");
		}
		return Msg.success("成功删除 " + count + " 条菜单记录！");
	}

	@GetMapping(value = "/{system}")
	public Msg getMenus(@PathVariable ("system") String system) {
        String userId = Util.userIdByShiro();
        if (Util.isNullorEmpty(userId)) {
            return Msg.error("用户未登录！");
        }
        User user = userService.getWithMenuList(userId);
        if (Util.isNullorEmpty(user)) {
            return Msg.error("用户不存在！");
        }
		Set<Integer> authMenuList = user.getMenuList();
		List<Menu> menuList = menuService.getMenuWithAuth(system,(short)1, authMenuList);
		return Msg.success(menuList);
	}
}
