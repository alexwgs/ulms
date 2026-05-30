package com.cmbccd.ulms.hr.dao;

import com.cmbccd.ulms.hr.domain.MoodRecord;
import com.cmbccd.ulms.hr.domain.MoodRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MoodRecordMapper {
    long countByExample(MoodRecordExample example);

    int deleteByExample(MoodRecordExample example);

    int insert(MoodRecord record);

    int insertSelective(MoodRecord record);

    List<MoodRecord> selectByExample(MoodRecordExample example);

    int updateByExampleSelective(@Param("record") MoodRecord record, @Param("example") MoodRecordExample example);

    int updateByExample(@Param("record") MoodRecord record, @Param("example") MoodRecordExample example);
}