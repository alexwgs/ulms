package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.DailyGroup;
import com.cmbccd.ulms.edu.domain.DailyGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DailyGroupMapper {
    long countByExample(DailyGroupExample example);

    int deleteByExample(DailyGroupExample example);

    int deleteByPrimaryKey(String ploNum);

    int insert(DailyGroup record);

    int insertSelective(DailyGroup record);

    List<DailyGroup> selectByExample(DailyGroupExample example);

    DailyGroup selectByPrimaryKey(String ploNum);

    int updateByExampleSelective(@Param("record") DailyGroup record, @Param("example") DailyGroupExample example);

    int updateByExample(@Param("record") DailyGroup record, @Param("example") DailyGroupExample example);

    int updateByPrimaryKeySelective(DailyGroup record);

    int updateByPrimaryKey(DailyGroup record);
    
    int batchInsert(List<DailyGroup> list);
    
    int deleteAll();
}