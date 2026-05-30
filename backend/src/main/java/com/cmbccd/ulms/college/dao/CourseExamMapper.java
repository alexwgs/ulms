package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.CourseExam;
import com.cmbccd.ulms.college.domain.CourseExamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseExamMapper {
    long countByExample(CourseExamExample example);

    int deleteByExample(CourseExamExample example);

    int deleteByPrimaryKey(String journo);

    int insert(CourseExam record);

    int insertSelective(CourseExam record);

    List<CourseExam> selectByExample(CourseExamExample example);

    CourseExam selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") CourseExam record, @Param("example") CourseExamExample example);

    int updateByExample(@Param("record") CourseExam record, @Param("example") CourseExamExample example);

    int updateByPrimaryKeySelective(CourseExam record);

    int updateByPrimaryKey(CourseExam record);

    int updateExamScore(@Param("examCode") String examCode);
}