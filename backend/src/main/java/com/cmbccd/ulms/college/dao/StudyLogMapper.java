package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.StudyLog;
import com.cmbccd.ulms.college.domain.StudyLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudyLogMapper {
    long countByExample(StudyLogExample example);

    int deleteByExample(StudyLogExample example);

    int insert(StudyLog record);

    int insertSelective(StudyLog record);

    List<StudyLog> selectByExample(StudyLogExample example);

    int updateByExampleSelective(@Param("record") StudyLog record, @Param("example") StudyLogExample example);

    int updateByExample(@Param("record") StudyLog record, @Param("example") StudyLogExample example);

}