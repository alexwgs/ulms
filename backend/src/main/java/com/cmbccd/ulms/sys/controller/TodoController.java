package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.Todo;
import com.cmbccd.ulms.sys.domain.TodoExample;
import com.cmbccd.ulms.sys.domain.TodoExample.Criteria;
import com.cmbccd.ulms.sys.service.TodoService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class TodoController {

	@Resource
	private TodoService todoService;

	@PostMapping(value = "/todo/add")
	public Msg createNewTodo(@RequestBody Todo record) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取账户信息！请重新登录在尝试");
		}
		record.setUserId(userId);
		int count = todoService.insertNewTodo(record);
		if (count < 1) {
			return Msg.error("新增日程失败！");
		}
		return Msg.success();
	}

	@GetMapping(value = "/todo/{dataDate}")
	public Msg getTodoList(@PathVariable("dataDate") String dataDate) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取账户信息！请重新登录在尝试");
		}
		if (Util.isNullorEmpty(dataDate)) {
			dataDate = Util.getDateToday();
		}
		List<Todo> list = todoService.getUserTodoListByDate(userId, dataDate);
		return Msg.success( list);
	}

	@PutMapping(value = "/todo")
	public Msg updateTodo(@RequestBody Todo record) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取账户信息！请重新登录在尝试");
		}
		int count = todoService.updateTodoList(record);
		if (count < 1) {
			return Msg.error("日程修改失败！");
		}
		return Msg.success();
	}

	@DeleteMapping(value = "/todo/{id}")
	@MyLog(title = "[sys-todo]待办管理")
	public Msg deleteMyTodo(@PathVariable("id") Integer id) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取账户信息！请重新登录在尝试");
		}
		int count = todoService.deleteTodoList(id);
		if (count < 1) {
			return Msg.error("日程删除失败！");
		}
		return Msg.success();
	}

	@PostMapping(value = "/todo/admin/add")
	@MyLog(title = "[sys-todo]待办管理")
	public Msg createNewTodoByAdmin(@RequestBody Todo record) {
		String[] userIds = record.getUserId().split(",");

		int count = 0;
		for (String userId : userIds) {
			record.setUserId(userId);
			todoService.insertNewTodo(record);
			count++;
		}
		if (count < 1) {
			return Msg.error("新增日程失败！");
		}
		return Msg.success("成功新增" + count + "条记录！");
	}

	@RequestMapping(value = "/todo/list", method = RequestMethod.GET)
	@MyLog(title = "[sys-todo]待办管理")
	public Msg getDayStatusJourList(@RequestParam Map<String, String> params) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取账户信息！请重新登录在尝试");
		}

		TodoExample example = new TodoExample();
		Criteria criteria = example.createCriteria();

		if (Util.isNullorEmpty(params.get("begDate")) || Util.isNullorEmpty(params.get("endDate"))) {
			criteria.andDataDateEqualTo(Util.getDateToday());
		} else {
			criteria.andDataDateBetween(params.get("begDate"), params.get("endDate"));
		}
		if (!Util.isNullorEmpty(params.get("status"))) {
			criteria.andStatusEqualTo(Integer.parseInt(params.get("status")));
		}
		if (!Util.isNullorEmpty(params.get("userId"))) {
			criteria.andUserIdEqualTo(params.get("userId"));
		}

		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Todo> list = todoService.getTodoListByExample(example);
		return Msg.success(new DataPage<Todo>(list));
	}
}
