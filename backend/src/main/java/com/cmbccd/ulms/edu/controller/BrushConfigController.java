package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.BrushConfigExample;
import com.cmbccd.ulms.edu.domain.BrushConfigExample.Criteria;
import com.cmbccd.ulms.edu.domain.DailyGroup;
import com.cmbccd.ulms.edu.service.BrushConfigService;
import com.cmbccd.ulms.edu.service.BrushScoreService;
import com.cmbccd.ulms.edu.service.DailyGroupService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("edu/brush/config")
public class BrushConfigController {

	@Resource
	private BrushConfigService brushConfigService;
	
	@Resource
	private BrushScoreService brushScoreService;
	
	@Resource
	private DailyGroupService dailyGroupService;
	
	@GetMapping("/list")
	public Msg listBrushConfig(@RequestParam Map<String, String> params) {
		return Msg.success(brushConfigService.listBrushConfigByQuery(params));
	}
	
	@PostMapping("/add")
	@SaCheckPermission("edu:brush:config:add")
	public Msg addNewBrushConfig(@RequestBody BrushConfig record) {
		int count = brushConfigService.create(record);
		if (count<1) return Msg.error();
		return Msg.success();
	}
	
	@PutMapping("/update")
	@SaCheckPermission("edu:brush:config:update")
	@MyLog(title = "[edu-brush]刷题管理")
	public Msg updateBrushConfig(@RequestBody BrushConfig record) {
		int count = brushConfigService.update(record);
		if (count<1) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/delete/{brushCode}")
	@SaCheckPermission("edu:brush:config:delete")
	@MyLog(title = "[edu-brush]刷题管理")
	public Msg updateBrushConfig(@PathVariable("brushCode") String brushCode) {
		int count = brushConfigService.delete(brushCode);
		if (count<1) return Msg.error();
		return Msg.success();
	}
	
	@GetMapping("")
	public Msg getMyBrushConfig() {
		String userId = Util.userIdByShiro();
		DailyGroup group = dailyGroupService.get(userId);
		if(Util.isNullorEmpty(group)) return Msg.error("您没有在任何学霸答题分组里！");
		BrushConfigExample example = new BrushConfigExample();
		Criteria criteria = example.createCriteria();
		criteria.andGroupIdEqualTo(group.getGroupId());
		List<BrushConfig> list = brushConfigService.list(example);
		if (list.size() < 1) return Msg.error("您所在的分组目前没有配置刷题规则！");
		BrushConfig brushConfig = list.get(0);
		int dayCount = brushScoreService.dayBrushCount(Util.getDateToday(), userId);
		int monthCount = brushScoreService.monthBrushCount(Util.getDateToday().substring(0, 7), userId);
		brushConfig.setDayCount(dayCount);
		brushConfig.setMonthCount(monthCount);
		return Msg.success(brushConfig);
	}
	
}
