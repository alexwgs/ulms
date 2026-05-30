package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.CourseExamTemplate;
import com.cmbccd.ulms.college.domain.CourseExamTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseExamTemplateMapper {
    long countByExample(CourseExamTemplateExample example);

    int deleteByExample(CourseExamTemplateExample example);

    int deleteByPrimaryKey(String courseId);

    int insert(CourseExamTemplate record);

    int insertSelective(CourseExamTemplate record);

    List<CourseExamTemplate> selectByExample(CourseExamTemplateExample example);

    CourseExamTemplate selectByPrimaryKey(String courseId);

    int updateByExampleSelective(@Param("record") CourseExamTemplate record, @Param("example") CourseExamTemplateExample example);

    int updateByExample(@Param("record") CourseExamTemplate record, @Param("example") CourseExamTemplateExample example);

    int updateByPrimaryKeySelective(CourseExamTemplate record);

    int updateByPrimaryKey(CourseExamTemplate record);
}