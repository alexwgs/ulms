package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.BookConfig;
import com.cmbccd.ulms.edu.domain.BookConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookConfigMapper {
    long countByExample(BookConfigExample example);

    int deleteByExample(BookConfigExample example);

    int deleteByPrimaryKey(String bookCode);

    int insert(BookConfig record);

    int insertSelective(BookConfig record);

    List<BookConfig> selectByExample(BookConfigExample example);

    BookConfig selectByPrimaryKey(String bookCode);

    int updateByExampleSelective(@Param("record") BookConfig record, @Param("example") BookConfigExample example);

    int updateByExample(@Param("record") BookConfig record, @Param("example") BookConfigExample example);

    int updateByPrimaryKeySelective(BookConfig record);

    int updateByPrimaryKey(BookConfig record);

}