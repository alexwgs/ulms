package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.CourseProgress;
import com.cmbccd.ulms.college.domain.CourseProgressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseProgressMapper {
    long countByExample(CourseProgressExample example);

    int deleteByExample(CourseProgressExample example);

    int deleteByPrimaryKey(String journo);

    int insert(CourseProgress record);

    int insertSelective(CourseProgress record);

    List<CourseProgress> selectByExample(CourseProgressExample example);

    CourseProgress selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") CourseProgress record, @Param("example") CourseProgressExample example);

    int updateByExample(@Param("record") CourseProgress record, @Param("example") CourseProgressExample example);

    int updateByPrimaryKeySelective(CourseProgress record);

    int updateByPrimaryKey(CourseProgress record);

    List<CourseProgress> selectMyLearnLog(CourseProgressExample example);
}