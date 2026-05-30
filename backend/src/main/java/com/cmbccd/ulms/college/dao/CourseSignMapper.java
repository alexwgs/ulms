package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.CourseSign;
import com.cmbccd.ulms.college.domain.CourseSignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseSignMapper {
    long countByExample(CourseSignExample example);

    int deleteByExample(CourseSignExample example);

    int insert(CourseSign record);

    int insertSelective(CourseSign record);

    List<CourseSign> selectByExample(CourseSignExample example);

    int updateByExampleSelective(@Param("record") CourseSign record, @Param("example") CourseSignExample example);

    int updateByExample(@Param("record") CourseSign record, @Param("example") CourseSignExample example);

    int updateCourseStudyNum(String courseId);
}