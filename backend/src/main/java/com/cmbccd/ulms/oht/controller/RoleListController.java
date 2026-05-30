package com.cmbccd.ulms.oht.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.RoleList;
import com.cmbccd.ulms.oht.domain.RoleListExample;
import com.cmbccd.ulms.oht.domain.RoleListExample.Criteria;
import com.cmbccd.ulms.oht.service.RoleListService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("oht/userRole")
public class RoleListController {

	@Resource
	private RoleListService roleListService;

	/**
	 * 根据角色类型获取用户角色列表
	 * @param params 查询参数，包括角色类型、角色状态和员工编号等
	 * @return 包含用户角色列表的分页数据
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Msg getUserRoleListByRoleType(@RequestParam Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		RoleListExample example = new RoleListExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("roleType"))) {
			criteria.andRoleTypeEqualTo(Integer.parseInt(params.get("roleType")));
		}
		if (!Util.isNullorEmpty(params.get("roleStat"))) {
			criteria.andRoleStatEqualTo(Integer.parseInt(params.get("roleStat")));
		}
		if (!Util.isNullorEmpty(params.get("ploNum"))) {
			criteria.andPloNumEqualTo(params.get("ploNum"));
		}

		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		List<RoleList> roleList = roleListService.getRoleListByExample(example);
		return Msg.success( new DataPage<RoleList>(roleList));
	}

	/**
	 * 删除用户角色
	 * @param ploNum 员工编号
	 * @return 操作结果信息
	 */
	@DeleteMapping(value = "/delete/{ploNum}")
	@MyLog(title = "[oht-role]角色管理")
	public Msg deleteUserRole(@PathVariable("ploNum") String ploNum) {
		int count = roleListService.deleteUserRole(ploNum);
		if (count < 1) {
			return Msg.error("删除失败！");
		}
		return Msg.success();
	}

	/**
	 * 更新用户角色
	 * @param record 用户角色对象
	 * @return 操作结果信息
	 */
	@PutMapping(value = "/update")
	@MyLog(title = "[oht-role]角色管理")
	public Msg updateUserRole(@RequestBody RoleList record) {
		int count = roleListService.updateUserRole(record);
		if (count < 1) {
			return Msg.error("修改失败！");
		}
		return Msg.success();
	}

	/**
	 * 新增用户角色
	 * @param record 用户角色对象
	 * @return 操作结果信息
	 */
	@PostMapping(value = "/insert")
	@MyLog(title = "[oht-role]角色管理")
	public Msg insertUserRole(@RequestBody RoleList record) {
		int count = roleListService.insertUserRole(record);
		if (count < 1) {
			return Msg.error("新增失败！");
		}
		return Msg.success();
	}
}
