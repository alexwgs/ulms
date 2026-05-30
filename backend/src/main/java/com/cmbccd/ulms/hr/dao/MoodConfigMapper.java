package com.cmbccd.ulms.hr.dao;

import com.cmbccd.ulms.hr.domain.MoodConfig;
import com.cmbccd.ulms.hr.domain.MoodConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MoodConfigMapper {
    long countByExample(MoodConfigExample example);

    int deleteByExample(MoodConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MoodConfig record);

    int insertSelective(MoodConfig record);

    List<MoodConfig> selectByExampleWithBLOBs(MoodConfigExample example);

    List<MoodConfig> selectByExample(MoodConfigExample example);

    MoodConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MoodConfig record, @Param("example") MoodConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") MoodConfig record, @Param("example") MoodConfigExample example);

    int updateByExample(@Param("record") MoodConfig record, @Param("example") MoodConfigExample example);

    int updateByPrimaryKeySelective(MoodConfig record);

    int updateByPrimaryKeyWithBLOBs(MoodConfig record);

    int updateByPrimaryKey(MoodConfig record);
}