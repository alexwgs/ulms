package com.cmbccd.ulms.db.dao;

import com.cmbccd.ulms.db.domain.TableTree;
import com.cmbccd.ulms.db.domain.TableTreeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TableTreeMapper {
    long countByExample(TableTreeExample example);

    int deleteByExample(TableTreeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TableTree record);

    int insertSelective(TableTree record);

    List<TableTree> selectByExample(TableTreeExample example);

    TableTree selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TableTree record, @Param("example") TableTreeExample example);

    int updateByExample(@Param("record") TableTree record, @Param("example") TableTreeExample example);

    int updateByPrimaryKeySelective(TableTree record);

    int updateByPrimaryKey(TableTree record);

    List<TableTree> selectTableNameTreeByExample(TableTreeExample example);

    int selectNewId();
}