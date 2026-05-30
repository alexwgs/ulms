package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.Todo;
import com.cmbccd.ulms.sys.domain.TodoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TodoMapper {
    Integer countByExample(TodoExample example);

    int deleteByExample(TodoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Todo record);

    int insertSelective(Todo record);

    List<Todo> selectByExample(TodoExample example);

    Todo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Todo record, @Param("example") TodoExample example);

    int updateByExample(@Param("record") Todo record, @Param("example") TodoExample example);

    int updateByPrimaryKeySelective(Todo record);

    int updateByPrimaryKey(Todo record);
    
    int selectNewId();
}