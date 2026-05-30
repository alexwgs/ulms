package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.ErrorLog;
import com.cmbccd.ulms.sys.domain.ErrorLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ErrorLogMapper {
    long countByExample(ErrorLogExample example);

    int deleteByExample(ErrorLogExample example);

    int insert(ErrorLog record);

    int insertSelective(ErrorLog record);

    List<ErrorLog> selectByExample(ErrorLogExample example);

    int updateByExampleSelective(@Param("record") ErrorLog record, @Param("example") ErrorLogExample example);

    int updateByExample(@Param("record") ErrorLog record, @Param("example") ErrorLogExample example);
    
    int getNewId();
}