package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.Todo;
import com.cmbccd.ulms.sys.domain.TodoExample;

import java.util.List;


public interface TodoService {

	public int insertNewTodo(Todo record);	
	
	public List<Todo> getUserTodoListByDate(String userId, String dataDate);
	
	public int deleteTodoList(int id);
	
	public int updateTodoList(Todo record);
	
	public List<Todo> getUserTodoByMonth(String userId, String month);
	
	public List<Todo> getTodoListByExample(TodoExample example);
	
}
