package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.domain.DailyConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DailyConfigMapper {
    long countByExample(DailyConfigExample example);

    int deleteByExample(DailyConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(DailyConfig record);

    int insertSelective(DailyConfig record);

    List<DailyConfig> selectByExample(DailyConfigExample example);

    DailyConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DailyConfig record, @Param("example") DailyConfigExample example);

    int updateByExample(@Param("record") DailyConfig record, @Param("example") DailyConfigExample example);

    int updateByPrimaryKeySelective(DailyConfig record);

    int updateByPrimaryKey(DailyConfig record);

    DailyConfig selectDailyConfigByPloNum(String ploNum, String quesDate);

    List<DailyConfig> selectDailyConfigCalendar(String dataMonth, String ploNum);

    int countQuesByDate(String begDate,String endDate);
}