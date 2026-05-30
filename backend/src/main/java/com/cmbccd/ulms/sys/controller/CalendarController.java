package com.cmbccd.ulms.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.service.DailyConfigService;
import com.cmbccd.ulms.sys.domain.Duty;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.Todo;
import com.cmbccd.ulms.sys.service.DutyService;
import com.cmbccd.ulms.sys.service.TodoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/calendar")
@RestController
public class CalendarController {

	@Resource
	private DutyService dutyService;
	@Resource
	private DailyConfigService dailyConfigService;
	@Resource
	private TodoService todoService;

	@RequestMapping(value = "/duty/{dateDate}", method = RequestMethod.GET)
	public Msg getDutyByPloNumOnCalendar(@PathVariable("dateDate") String dateDate) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取到用户信息！");
		}
		if (Util.isNullorEmpty(dateDate)) {
			dateDate = Util.getDateToday().substring(0, 7);
		}
		String priority = "";
		List<Duty> dutyList = dutyService.getDutyByPloNum(dateDate, userId);
		List<Map<String, Object>> duty = new ArrayList<>();
		List<Map<String, Object>> task = new ArrayList<>();
		List<Map<String, Object>> todolist = new ArrayList<>();
		String month = dateDate.substring(5);
		if (dutyList.size() > 0) {
			JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(dutyList.get(0)));
			String day = "";
			for (int i = 1; i <= 31; i++) {
				Map<String, Object> data = new HashMap<String, Object>();
				if (i < 10) {
					day = "0" + i;
				} else {
					day = i + "";
				}

				String dutyFlag = (String) jsonObject.get("dutyDay" + day);
				if (Util.isNullorEmpty(dutyFlag)) {
					continue;
				}else {
					if(dutyFlag.indexOf('休')>-1) {
						priority="info";
					}else if(dutyFlag.indexOf('请')>-1){
						priority="danger";
					}else{
						priority="";
					}
				}
				data.put("date", dateDate +'-'+ day);
				data.put("event", dutyFlag);
				data.put("type", "duty");
				data.put("priority", priority);
				data.put("id", 20 * 1000 + Integer.parseInt(month) * 100 + i);
				duty.add(data);
			}
		}

		List<Todo> todos = todoService.getUserTodoByMonth(userId, dateDate);
		for (Todo todo : todos) {
			String priorityStr = todo.getPriority();
			if(Util.isNullorEmpty(priorityStr)) {
				priority="";
			} else if(priorityStr.indexOf("紧急")>-1){
				priority="danger";
			} else if(priorityStr.indexOf("重要")>-1) {
				priority="warning";
			}else {
				priority="primary";
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("date", todo.getDataDate());
			data.put("type","todolist");
			data.put("event", todo.getTitle());
			data.put("priority",priority);
			data.put("id", todo.getId());
			todolist.add(data);
		}
		// 预先获取公布栏数据
		List<DailyConfig> dailyConfigs = dailyConfigService.getDailyConfigCalendar(dateDate);
		for(DailyConfig item: dailyConfigs){
			String flag = item.getGroupId();
			String event = "";
			if(item.getQuesDate().equals(Util.getDateToday()) && Util.isNullorEmpty(flag)){
				event= "";
				priority="danger";
			} else if("0".equals(flag)) {
				event= "每日一学";
				priority="danger";
			} else if("1".equals(flag)) {
				event="每日一学";
				priority="success";
			} else if("2".equals(flag)) {
				event="每日一学";
				priority="warning";
			} else {
				event= "每日一学";
				priority="danger";
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("date", item.getQuesDate());
			data.put("type", "task");
			data.put("event", event);
			data.put("priority", priority);
			data.put("id", item.getId());
			task.add(data);
		}
		return Msg.success().put("duty", duty).put("todolist",todolist).put("task", task);
	}
}
