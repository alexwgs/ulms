package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.OperLog;
import com.cmbccd.ulms.sys.domain.OperLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OperLogMapper {
    long countByExample(OperLogExample example);

    int deleteByExample(OperLogExample example);

    int deleteByPrimaryKey(String id);

    int insert(OperLog record);

    int insertSelective(OperLog record);

    List<OperLog> selectByExample(OperLogExample example);

    OperLog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OperLog record, @Param("example") OperLogExample example);

    int updateByExample(@Param("record") OperLog record, @Param("example") OperLogExample example);

    int updateByPrimaryKeySelective(OperLog record);

    int updateByPrimaryKey(OperLog record);
}