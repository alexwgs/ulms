package com.cmbccd.ulms.flow.dao;

import com.cmbccd.ulms.flow.domain.PerfUnit;
import com.cmbccd.ulms.flow.domain.PerfUnitExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerfUnitMapper {
    long countByExample(PerfUnitExample example);

    int deleteByExample(PerfUnitExample example);

    int deleteByPrimaryKey(String id);

    int insert(PerfUnit record);

    int insertSelective(PerfUnit record);

    List<PerfUnit> selectByExample(PerfUnitExample example);

    PerfUnit selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PerfUnit record, @Param("example") PerfUnitExample example);

    int updateByExample(@Param("record") PerfUnit record, @Param("example") PerfUnitExample example);

    int updateByPrimaryKeySelective(PerfUnit record);

    int updateByPrimaryKey(PerfUnit record);

    PerfUnit check(String userId, String begMonth, String endMonth);

}