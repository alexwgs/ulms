package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.CourseExamInfo;
import com.cmbccd.ulms.college.domain.CourseExamInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseExamInfoMapper {
    long countByExample(CourseExamInfoExample example);

    int deleteByExample(CourseExamInfoExample example);

    int deleteByPrimaryKey(String journo);

    int insert(CourseExamInfo record);

    int insertSelective(CourseExamInfo record);

    List<CourseExamInfo> selectByExample(CourseExamInfoExample example);

    CourseExamInfo selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") CourseExamInfo record, @Param("example") CourseExamInfoExample example);

    int updateByExample(@Param("record") CourseExamInfo record, @Param("example") CourseExamInfoExample example);

    int updateByPrimaryKeySelective(CourseExamInfo record);

    int updateByPrimaryKey(CourseExamInfo record);

    int insertNewExam(@Param("examCode") String examCode,@Param("libCode") String libCode,@Param("ploNum") String ploNum, @Param("quesNum") int quesNum);
}