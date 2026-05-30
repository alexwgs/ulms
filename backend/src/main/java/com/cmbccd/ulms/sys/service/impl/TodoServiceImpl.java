package com.cmbccd.ulms.sys.service.impl;

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
		return todoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateTodoList(Todo record) {
		return todoMapper.updateByPrimaryKeySelective(record);
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
