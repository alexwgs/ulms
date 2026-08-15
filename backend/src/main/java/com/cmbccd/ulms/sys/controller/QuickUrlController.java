package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.QuickUrl;
import com.cmbccd.ulms.sys.domain.QuickUrlExample;
import com.cmbccd.ulms.sys.domain.QuickUrlExample.Criteria;
import com.cmbccd.ulms.sys.service.QuickUrlService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quickUrl")
public class QuickUrlController {

	@Resource
	private QuickUrlService quickUrlService;

	@GetMapping(value = "/list")
	@SaCheckPermission("quickUrl:list")
	public Msg getQuickUrlListAdmin(@RequestParam Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);

		QuickUrlExample example = new QuickUrlExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("status"))) {
			if (!params.get("status").equals("-1")) {
				criteria.andStatusEqualTo(Short.parseShort(params.get("status")));
			}
		}
		if (!Util.isNullorEmpty(params.get("area"))) {
			criteria.andAreaEqualTo(params.get("area"));
		}
		if (!Util.isNullorEmpty(params.get("query"))) {
			example.or().andNameLike('%' + params.get("query") + '%');
			example.or().andSysTypeLike('%' + params.get("query") + '%');
		}
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<QuickUrl> list = quickUrlService.listQuickUrl(example);
		return Msg.success(new DataPage<QuickUrl>(list));
	}

	@GetMapping(value = "/records/{area}")
	public Msg getQuickUrlList(@PathVariable String area) {
		QuickUrlExample example = new QuickUrlExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(area)) {
			criteria.andAreaEqualTo(area);
		} else{
			criteria.andAreaEqualTo("default");
		}
		criteria.andStatusEqualTo((short)1);
		example.setOrderByClause(" SORT DESC");
		List<QuickUrl> list = quickUrlService.listQuickUrl(example);
		return Msg.success(list);
	}

	@PutMapping(value = "/record")
	@SaCheckPermission("quickUrl:update")
	@MyLog(title = "[sys-quickurl]快链管理")
	public Msg updateQuickUrlList(@RequestBody QuickUrl record) {
		if (Util.isNullorEmpty(record.getId()) || Util.isNullorEmpty(record.getName())
				|| Util.isNullorEmpty(record.getSort()) || Util.isNullorEmpty(record.getStatus())) {
			return Msg.error("缺失表单信息，请完善后在提交！");
		}
		int count = quickUrlService.updateQuickUrlById(record);
		return Msg.success("成功完成[ " + count + " ]条信息的变更！");
	}

	@PostMapping(value = "/record")
	@SaCheckPermission("quickUrl:insert")
	@MyLog(title = "[sys-quickurl]快链管理")
	public Msg insertQuickUrlList(@RequestBody QuickUrl record) {
		if (Util.isNullorEmpty(record.getName()) || Util.isNullorEmpty(record.getSort())
				|| Util.isNullorEmpty(record.getStatus())) {
			return Msg.error("缺失表单信息，请完善后在提交！");
		}
		int count = quickUrlService.insertQuickUrl(record);
		return Msg.success("成功完成[ " + count + " ]条信息的新增！");
	}

	@DeleteMapping(value = "/record")
	@SaCheckPermission("quickUrl:delete")
	@MyLog(title = "[sys-quickurl]快链管理")
	public Msg deleteQuickUrlList(@RequestBody QuickUrl record) {
		if (Util.isNullorEmpty(record.getId())) {
			return Msg.error("缺失表单信息，请完善后在提交！");
		}
		int count = quickUrlService.insertQuickUrl(record);
		return Msg.success("成功完成[ " + count + " ]条信息的删除！");
	}
}
