package com.cmbccd.ulms.db.dao;

import com.cmbccd.ulms.db.domain.Column;
import com.cmbccd.ulms.db.domain.ColumnExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ColumnMapper {
    long countByExample(ColumnExample example);

    int deleteByExample(ColumnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Column record);

    int insertSelective(Column record);

    List<Column> selectByExample(ColumnExample example);

    Column selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Column record, @Param("example") ColumnExample example);

    int updateByExample(@Param("record") Column record, @Param("example") ColumnExample example);

    int updateByPrimaryKeySelective(Column record);

    int updateByPrimaryKey(Column record);
    
    int selectNewId();

    List<Column> selectColumnByTableName(String tableName);

    // 获取目前已存在的表字段数据
    List<Column> selectExistColumn();
    // 获取目前尚未同步的表字段数据
    List<Column> selectNotExistColumn();
}