package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.QuickUrl;
import com.cmbccd.ulms.sys.domain.QuickUrlExample;
import com.cmbccd.ulms.sys.domain.QuickUrlExample.Criteria;
import com.cmbccd.ulms.sys.service.QuickUrlService;
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
		return Msg.success(quickUrlService.listQuickUrlByAdmin(params));
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
		int count = quickUrlService.deleteQuickUrlById(record.getId());
		return Msg.success("成功完成[ " + count + " ]条信息的删除！");
	}
}
