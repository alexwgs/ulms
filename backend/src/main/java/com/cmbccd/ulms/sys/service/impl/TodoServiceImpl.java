package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.TodoMapper;
import com.cmbccd.ulms.sys.domain.Todo;
import com.cmbccd.ulms.sys.domain.TodoExample;
import com.cmbccd.ulms.sys.domain.TodoExample.Criteria;
import com.cmbccd.ulms.sys.service.TodoService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

	@Resource
	private TodoMapper todoMapper;
	
	@Override
	public int insertNewTodo(Todo record) {
		record.setId(todoMapper.selectNewId());
		return todoMapper.insert(record);
	}

	@Override
	public List<Todo> getUserTodoListByDate(String userId, String dataDate) {
		TodoExample example = new TodoExample();
		Criteria criteria = example.createCriteria();
		List<String> users = new ArrayList<String>();
		users.add(userId);
		users.add("admin");
		criteria.andUserIdIn(users);
		criteria.andDataDateEqualTo(dataDate);
		example.setOrderByClause(" priority desc, id asc");
		return todoMapper.selectByExample(example);
	}

	@Override
	public int deleteTodoList(int id) {
		// 审计加固（IDOR）：只能删除自己的日程（按 id+userId 双重条件）
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) return 0;
		TodoExample example = new TodoExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		criteria.andUserIdEqualTo(userId);
		return todoMapper.deleteByExample(example);
	}

	@Override
	public int updateTodoList(Todo record) {
		// 审计加固（IDOR）：只能修改自己的日程（按 id+userId 双重条件）
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId) || record.getId() == null) return 0;
		TodoExample example = new TodoExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(record.getId());
		criteria.andUserIdEqualTo(userId);
		return todoMapper.updateByExampleSelective(record, example);
	}

	@Override
	public List<Todo> getUserTodoByMonth(String userId, String month) {
		TodoExample example = new TodoExample();
		Criteria criteria = example.createCriteria();
		List<String> users = new ArrayList<String>();
		users.add(userId);
		users.add("admin");
		criteria.andUserIdIn(users);
		criteria.andDataDateLike(month+"%");
		criteria.andStatusEqualTo(0);
		example.setOrderByClause(" priority asc, id asc");
		return todoMapper.selectByExample(example);
	}

	@Override
	public List<Todo> getTodoListByExample(TodoExample example) {
		return todoMapper.selectByExample(example);
	}

}
