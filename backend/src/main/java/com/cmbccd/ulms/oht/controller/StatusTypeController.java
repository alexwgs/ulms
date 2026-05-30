package com.cmbccd.ulms.oht.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.StatusType;
import com.cmbccd.ulms.oht.domain.StatusTypeExample;
import com.cmbccd.ulms.oht.domain.StatusTypeExample.Criteria;
import com.cmbccd.ulms.oht.service.StatusTypeService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oht")
public class StatusTypeController {

	@Resource
	private StatusTypeService statusTypeService;

	/**
	 * 获取状态类型树
	 * @return 状态类型树形结构数据
	 */
	@GetMapping(value = "/statusTree")
	public Msg getStatusTypeTree() {
		List<StatusType> statusTypeTree = statusTypeService.getStatusByTree();
		return Msg.success( statusTypeTree);
	}

	/**
	 * 获取身份列表
	 * @return 身份列表数据
	 */
	@GetMapping(value = "/identityList")
	public Msg getIdentity() {
		List<StatusType> getIdentity = statusTypeService.getIdentity();
		return Msg.success(getIdentity);
	}

	/**
	 * 获取状态类型列表
	 * @param params 查询参数，包括数据类型、等级和状态
	 * @return 状态类型列表的分页数据
	 */
	@GetMapping(value = "/statusType/list")
	public Msg getStatusTypeList(@RequestParam Map<String, String> params) {
		StatusTypeExample example = new StatusTypeExample();
		Criteria criteria = example.createCriteria();

		if (!Util.isNullorEmpty(params.get("dataType"))) {
			criteria.andDataTypeEqualTo(params.get("dataType"));
		}
		if (!Util.isNullorEmpty(params.get("levelGrade"))) {
			criteria.andLevelGradeEqualTo(Integer.parseInt(params.get("levelGrade")));
		}
		if (!Util.isNullorEmpty(params.get("status"))) {
			criteria.andStatusEqualTo(Integer.parseInt(params.get("status")));
		}

		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<StatusType> statusTypeList = statusTypeService.getStatusTypeByExample(example);
		return Msg.success(new DataPage<StatusType>(statusTypeList));
	}

	/**
	 * 更新状态类型
	 * @param record 状态类型对象
	 * @return 操作结果信息
	 */
	@PutMapping(value = "/statusType/update")
	@MyLog(title = "[oht-status]状态管理")
	public Msg updateStatusType(@RequestBody StatusType record) {
		int count = statusTypeService.updateStatusTypeByPrimaryId(record);
		if (count > 1) {
			return Msg.error("状态修改失败！");
		}
		return Msg.success();
	}

	/**
	 * 新增状态类型
	 * @param record 状态类型对象
	 * @return 操作结果信息
	 */
	@PostMapping(value = "/statusType/add")
	@MyLog(title = "[oht-status]状态管理")
	public Msg insertStatusType(@RequestBody StatusType record) {
		int count = statusTypeService.insertStatusType(record);
		if (count > 1) {
			return Msg.error("状态创建失败！");
		}
		return Msg.success();
	}
}
