package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.Role;
import com.cmbccd.ulms.sys.domain.RoleExample;
import com.cmbccd.ulms.sys.service.RoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author WeiGenSheng
 * @Time 2020年5月6日 上午9:56:02
 * @Version 1.0
 * @Description:
 */
@RestController
@RequestMapping("role")
public class RoleController {

	@Resource
	private RoleService roleService;

	@GetMapping("list")
	public Msg getRoleList(@RequestParam Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		RoleExample example = new RoleExample();
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
		}
		List<Role> roleList = roleService.getRolePermissionTree(example);
		return Msg.success(new DataPage<Role>(roleList));
	}

	@GetMapping("")
	public Msg getRole() {
		RoleExample example = new RoleExample();
		example.setOrderByClause(" ID ASC ");
		List<Role> roleList = roleService.getRolePermissionTree(example);
		return Msg.success(roleList);
	}

	@PutMapping(value = "permission/{roleId}")
	@SaCheckPermission("role:permission:update")
	@MyLog(title = "[sys-role]角色管理")
	public Msg updateRolePermission(@RequestBody Map<String, String> map, @PathVariable("roleId") Integer roleId) {
		Role role = new Role();
		role.setPermissions(map.get("permissionIds"));
		int count = roleService.updatePermissionByRoleId(role, roleId);
		if (count == 0) {
			return Msg.error("操作失败，没有删除数据！");
		}
		return Msg.success("成功分配" + count + "个角色的权限！");
	}

	@DeleteMapping(value = "{roleId}")
	@SaCheckPermission("role:delete")
	@MyLog(title = "[sys-role]角色管理")
	public Msg updateRolePermission(@PathVariable("roleId") Integer roleId) {
		int count = roleService.deleteRoleByRoleId(roleId);
		return Msg.success("成功删除" + count + "个角色！");
	}

	@PutMapping(value = "update")
	@SaCheckPermission("role:update")
	@MyLog(title = "[sys-role]角色管理")
	public Msg updateRole(@RequestBody Role role) {
		int count = roleService.updateRoleByRoleId(role);
		if (count == 0) {
			return Msg.error("操作失败，没有更新数据！");
		}
		return Msg.success("成功更新" + count + "个角色！");
	}

	@PostMapping(value = "add")
	@SaCheckPermission("role:add")
	@MyLog(title = "[sys-role]角色管理")
	public Msg addRole(@RequestBody Role role) {
		int count = roleService.addRole(role);
		if (count == 0) {
			return Msg.error("操作失败，没有新增数据！");
		}
		return Msg.success("成功新增" + count + "个角色！");
	}
}
