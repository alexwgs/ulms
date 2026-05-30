package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.CourseFile;
import com.cmbccd.ulms.college.domain.CourseFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseFileMapper {
    long countByExample(CourseFileExample example);

    int deleteByExample(CourseFileExample example);

    int insert(CourseFile record);

    int insertSelective(CourseFile record);

    List<CourseFile> selectByExample(CourseFileExample example);

    int updateByExampleSelective(@Param("record") CourseFile record, @Param("example") CourseFileExample example);

    int updateByExample(@Param("record") CourseFile record, @Param("example") CourseFileExample example);
}