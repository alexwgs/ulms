package com.cmbccd.ulms.rpa.dao;

import com.cmbccd.ulms.rpa.domain.ToolList;
import com.cmbccd.ulms.rpa.domain.ToolListExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ToolListMapper {
    long countByExample(ToolListExample example);

    int deleteByExample(ToolListExample example);

    int deleteByPrimaryKey(String id);

    int insert(ToolList row);

    int insertSelective(ToolList row);

    List<ToolList> selectByExample(ToolListExample example);

    ToolList selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") ToolList row, @Param("example") ToolListExample example);

    int updateByExample(@Param("row") ToolList row, @Param("example") ToolListExample example);

    int updateByPrimaryKeySelective(ToolList row);

    int updateByPrimaryKey(ToolList row);

    List<String> getToolListName(String name);
}