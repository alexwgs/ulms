package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.BookTime;
import com.cmbccd.ulms.edu.domain.BookTimeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookTimeMapper {
    long countByExample(BookTimeExample example);

    int deleteByExample(BookTimeExample example);

    int deleteByPrimaryKey(String journo);

    int insert(BookTime record);

    int insertSelective(BookTime record);

    List<BookTime> selectByExample(BookTimeExample example);

    BookTime selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") BookTime record, @Param("example") BookTimeExample example);

    int updateByExample(@Param("record") BookTime record, @Param("example") BookTimeExample example);

    int updateByPrimaryKeySelective(BookTime record);

    int updateByPrimaryKey(BookTime record);

}