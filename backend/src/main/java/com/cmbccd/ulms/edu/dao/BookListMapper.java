package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.BookList;
import com.cmbccd.ulms.edu.domain.BookListExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookListMapper {
    long countByExample(BookListExample example);

    int deleteByExample(BookListExample example);

    int deleteByPrimaryKey(String examCode);

    int insert(BookList record);

    int insertSelective(BookList record);

    List<BookList> selectByExample(BookListExample example);

    BookList selectByPrimaryKey(String examCode);

    int updateByExampleSelective(@Param("record") BookList record, @Param("example") BookListExample example);

    int updateByExample(@Param("record") BookList record, @Param("example") BookListExample example);

    int updateByPrimaryKeySelective(BookList record);

    int updateByPrimaryKey(BookList record);
    
    List<String> selectBookTime(String examCode, String ploNum);
    
    List<BookList> selectByExampleWithDateTime(BookListExample example);

}