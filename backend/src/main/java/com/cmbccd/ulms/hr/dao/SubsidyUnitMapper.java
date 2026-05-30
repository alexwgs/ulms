package com.cmbccd.ulms.hr.dao;

import com.cmbccd.ulms.hr.domain.SubsidyUnit;
import com.cmbccd.ulms.hr.domain.SubsidyUnitExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubsidyUnitMapper {
    long countByExample(SubsidyUnitExample example);

    int deleteByExample(SubsidyUnitExample example);

    int deleteByPrimaryKey(String id);

    int insert(SubsidyUnit record);

    int insertSelective(SubsidyUnit record);

    List<SubsidyUnit> selectByExample(SubsidyUnitExample example);

    SubsidyUnit selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SubsidyUnit record, @Param("example") SubsidyUnitExample example);

    int updateByExample(@Param("record") SubsidyUnit record, @Param("example") SubsidyUnitExample example);

    int updateByPrimaryKeySelective(SubsidyUnit record);

    int updateByPrimaryKey(SubsidyUnit record);
}