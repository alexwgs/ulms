package com.cmbccd.ulms.flow.dao;

import com.cmbccd.ulms.flow.domain.PerfFlag;
import com.cmbccd.ulms.flow.domain.PerfFlagExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerfFlagMapper {
    long countByExample(PerfFlagExample example);

    int deleteByExample(PerfFlagExample example);

    int deleteByPrimaryKey(String id);

    int insert(PerfFlag record);

    int insertSelective(PerfFlag record);

    List<PerfFlag> selectByExample(PerfFlagExample example);

    PerfFlag selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PerfFlag record, @Param("example") PerfFlagExample example);

    int updateByExample(@Param("record") PerfFlag record, @Param("example") PerfFlagExample example);

    int updateByPrimaryKeySelective(PerfFlag record);

    int updateByPrimaryKey(PerfFlag record);

    PerfFlag check(String userId, String begMonth, String endMonth);
}