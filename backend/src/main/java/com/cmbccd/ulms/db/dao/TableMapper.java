package com.cmbccd.ulms.db.dao;

import com.cmbccd.ulms.db.domain.Table;
import com.cmbccd.ulms.db.domain.TableExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TableMapper {
    long countByExample(TableExample example);

    int deleteByExample(TableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Table record);

    int insertSelective(Table record);

    List<Table> selectByExample(TableExample example);

    Table selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Table record, @Param("example") TableExample example);

    int updateByExample(@Param("record") Table record, @Param("example") TableExample example);

    int updateByPrimaryKeySelective(Table record);

    int updateByPrimaryKey(Table record);

    void updateClickNumIncreaseById(Integer id);

    int selectNewId();
    // 没有被加入菜单的表
    List<Table> selectTablesNotSorted(String keyWord);
    // 已经被加入菜单的表 memo为菜单名称
    List<Table> selectTablesSorted(String keyWord);

    // 更新已存入数据库中的表数据
    int updateExistTables();

    // 获取目前尚未同步的表数据
    List<Table> selectNotExistTables();
}