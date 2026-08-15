/**
 * 
 */
package com.cmbccd.ulms.oht.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.OhtRole;
import com.cmbccd.ulms.oht.domain.OhtRoleExample;
import com.cmbccd.ulms.oht.service.OhtRoleService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("oht/role")
public class OhtRoleController {

	@Resource
	private OhtRoleService ohtRoleService;
	
	/**
	 * 获取求助系统角色列表
	 * @param params 查询参数
	 * @return 包含角色列表的分页数据
	 */
	@GetMapping( value = "list")
	@SaCheckPermission("oht:role:list")
	public Msg getOhtRoleList(@RequestParam Map<String, String> params) {
		
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		
		OhtRoleExample example = new OhtRoleExample();
		if(!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause( Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
	
		
		List<OhtRole> ohtRoles = ohtRoleService.getOhtRoleList(example);
		
		return Msg.success(new DataPage<OhtRole>(ohtRoles));
	}
	
	/**
	 * 根据角色代码获取角色信息
	 * @param roleCode 角色代码
	 * @return 角色信息
	 */
	@GetMapping( value = "get/{roleCode}")
	public Msg getOhtRoleList(@PathVariable ("roleCode") Integer roleCode) {
		OhtRole ohtRole = ohtRoleService.selectOhtRoleByPrimaryId(roleCode);
		return Msg.success(ohtRole);
	}
	
	/**
	 * 新增求助系统角色
	 * @param ohtRole 角色对象
	 * @return 操作结果信息
	 */
	@PostMapping(value = "add")
	@SaCheckPermission("oht:role:add")
	@MyLog(title = "[oht-role]角色管理")
	public Msg addOhtRole(@RequestBody OhtRole ohtRole) {
		int recordCount = ohtRoleService.addSelectiveOhtRole(ohtRole);
		return Msg.success("成功新增"+recordCount+"条记录！");
	}
	
	/**
	 * 更新求助系统角色信息
	 * @param ohtRole 角色对象
	 * @return 操作结果信息
	 */
	@PutMapping(value="update")
	@SaCheckPermission("oht:role:update")
	@MyLog(title = "[oht-role]角色管理")
	public Msg updateOhtRole(@RequestBody OhtRole ohtRole){
		int recordCount = ohtRoleService.updateOhtRoleByPrimaryId(ohtRole);
		return Msg.success("成功修改"+recordCount+"条记录！");
	}
	
	/**
	 * 删除求助系统角色
	 * @param roleCode 角色代码
	 * @return 操作结果信息
	 */
	@DeleteMapping(value="delete/{roleCode}")
	@SaCheckPermission("oht:role:delete")
	@MyLog(title = "[oht-role]角色管理")
	public Msg deleteOhtRole(@PathVariable("roleCode") Integer roleCode) {
		int recordCount = ohtRoleService.deleteOhtRoleByPrimaryId(roleCode);
		return Msg.success("成功删除"+recordCount+"条记录！");
	}
}
