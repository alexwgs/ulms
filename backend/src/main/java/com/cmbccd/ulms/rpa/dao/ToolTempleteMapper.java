package com.cmbccd.ulms.rpa.dao;

import com.cmbccd.ulms.rpa.domain.ToolTemplete;
import com.cmbccd.ulms.rpa.domain.ToolTempleteExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ToolTempleteMapper {
    long countByExample(ToolTempleteExample example);

    int deleteByExample(ToolTempleteExample example);

    int deleteByPrimaryKey(String id);

    int insert(ToolTemplete row);

    int insertSelective(ToolTemplete row);

    List<ToolTemplete> selectByExample(ToolTempleteExample example);

    ToolTemplete selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") ToolTemplete row, @Param("example") ToolTempleteExample example);

    int updateByExample(@Param("row") ToolTemplete row, @Param("example") ToolTempleteExample example);

    int updateByPrimaryKeySelective(ToolTemplete row);

    int updateByPrimaryKey(ToolTemplete row);
}