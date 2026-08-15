
package com.cmbccd.ulms.oht.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.QuickMemo;
import com.cmbccd.ulms.oht.service.QuickMemoService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("oht/quickMemo")
public class QuickMemoController {

	@Resource
	private QuickMemoService quickMemoService;

	/**
	 * 获取快捷消息列表
	 * @param params 查询参数
	 * @return 包含快捷消息列表的分页数据
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@SaCheckPermission("oht:quickMemo:list")
	public Msg getQuickMemoList(@RequestParam Map<String, String> params) {
		return Msg.success(quickMemoService.getQuickMemoListByQuery(params));
	}

	/**
	 * 根据角色类型获取快捷消息列表
	 * @param roleType 角色类型
	 * @return 快捷消息列表
	 */
	@GetMapping(value = "list/{roleType}")
	public Msg getQuickMemoListByRoleType(@PathVariable("roleType") String roleType) {
		List<QuickMemo> quickMemoList = quickMemoService.getQuickMemoByRoleType(roleType);
		return Msg.success( quickMemoList);
	}

	/**
	 * 更新快捷消息
	 * @param record 快捷消息对象
	 * @return 操作结果信息
	 */
	@PutMapping(value = "update")
	@SaCheckPermission("oht:quickMemo:update")
	@MyLog(title = "[oht-role]快捷消息管理")
	public Msg updateQuickMemo(@RequestBody QuickMemo record) {
		if (Util.isNullorEmpty(record.getJourno())) {
			return Msg.error("修改失败，没有获取到关键值!");
		}
		int count = quickMemoService.updateQuickMemoByJourno(record);
		return Msg.success("成功修改" + count + "条记录");
	}

	/**
	 * 删除快捷消息
	 * @param journo 记录编号
	 * @return 操作结果信息
	 */
	@DeleteMapping(value = "delete/{journo}")
	@SaCheckPermission("oht:quickMemo:delete")
	@MyLog(title = "[oht-memo]快捷消息管理")
	public Msg deleteQuickMemo(@PathVariable("journo") String journo) {
		int count = quickMemoService.deleteQuickMemoByJourno(journo);
		return Msg.success("成功删除" + count + "条记录");
	}

	/**
	 * 新增快捷消息
	 * @param record 快捷消息对象
	 * @return 操作结果信息
	 */
	@PostMapping(value = "add")
	@SaCheckPermission("oht:quickMemo:add")
	@MyLog(title = "[oht-role]快捷消息管理")
	public Msg addQuickMemo(@RequestBody QuickMemo record) {
		int count = quickMemoService.addQuickMemo(record);
		return Msg.success("成功新增" + count + "条记录");
	}

}
