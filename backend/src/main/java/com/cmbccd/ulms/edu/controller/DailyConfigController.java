package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.domain.DailyConfigExample;
import com.cmbccd.ulms.edu.domain.DailyScore;
import com.cmbccd.ulms.edu.service.DailyConfigService;
import com.cmbccd.ulms.edu.service.DailyScoreService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("edu/daily")
public class DailyConfigController {

	@Resource
	private DailyConfigService dailyConfigService;
	@Resource
	private DailyScoreService dailyScoreService;

	@GetMapping("/config/list")
	public Msg listDailyConfig(@RequestParam Map<String, String> params) {
		DailyConfigExample example = new DailyConfigExample();
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		// Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<DailyConfig> list = dailyConfigService.list(example);
		return Msg.success(new DataPage<DailyConfig>(list));
	}
	
	@DeleteMapping("/config/{id}")
	@SaCheckPermission("edu:daily:config:delete")
	@MyLog(title = "[edu-daily]每日一学管理")
	public Msg deleteDailyConfig(@PathVariable("id") String id) {
		int count = dailyConfigService.delete(id);
		if(count < 1) return Msg.error();
		return Msg.success();
	}
	
	@PutMapping("/config")
	@SaCheckPermission("edu:daily:config:update")
	@MyLog(title = "[edu-daily]每日一学管理")
	public Msg updateDailyConfig(@RequestBody DailyConfig record) {
		int count = dailyConfigService.update(record);
		if(count < 1) return Msg.error();
		return Msg.success();
	}
	
	@PostMapping("/config")
	@SaCheckPermission("edu:daily:config:add")
	@MyLog(title = "[edu-daily]每日一学管理")
	public Msg createDailyConfig(@RequestBody DailyConfig record) {
		int count = dailyConfigService.update(record);
		// 可设置groupIds为空置，若为空值则表明为全体人员
		if(Util.isNullorEmpty(record.getGroupId())) {
			count += dailyConfigService.create(record);
		}else {
			String[] groupIds = record.getGroupId().split(",");
			for (String groupId : groupIds) {
				record.setGroupId(groupId);
				count += dailyConfigService.create(record);
			}
		}
		if(count < 1) return Msg.error();
		return Msg.success();
	}

	// 获取今日每日一招数据
	@GetMapping("/question/init")
	public Msg getDailyQuestion(@RequestParam Map<String, String> params) {
		String quesDate = params.get("quesDate");
		if(Util.isNullorEmpty(quesDate)) quesDate = Util.getDateToday();
		// 判断是否在答题群组中
		DailyConfig dailyConfig = dailyConfigService.getDailyConfigByPloNum(quesDate);

		if(Util.isNullorEmpty(dailyConfig)) return Msg.success("不在分组中，无需答题！").put("flag", false);
		// 如果需要答题，那么获取答题结果，如果没有记录则新建并返回，若有记录则直接返回记录结果;
		DailyScore dailyScore = dailyScoreService.init(dailyConfig);
		if (Util.isNullorEmpty(dailyScore)) {
			return Msg.error("新建答题数据失败！");
		} else if (dailyScore.getValid() == 1) return Msg.success("每日一学打卡已完成。无需再次提交！").put("flag", true).put("dailyConfig",dailyConfig).put("dailyScore", dailyScore);
		return Msg.success().put("flag", true).put("dailyConfig",dailyConfig).put("dailyScore", dailyScore);
	}


}
