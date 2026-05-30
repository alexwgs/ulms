package com.cmbccd.ulms.hr.dao;

import com.cmbccd.ulms.hr.domain.PunchJour;
import com.cmbccd.ulms.hr.domain.PunchJourExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PunchJourMapper {
    long countByExample(PunchJourExample example);

    int deleteByExample(PunchJourExample example);

    int deleteByPrimaryKey(String journo);

    int insert(PunchJour record);

    int insertSelective(PunchJour record);

    List<PunchJour> selectByExample(PunchJourExample example);

    PunchJour selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") PunchJour record, @Param("example") PunchJourExample example);

    int updateByExample(@Param("record") PunchJour record, @Param("example") PunchJourExample example);

    int updateByPrimaryKeySelective(PunchJour record);

    int updateByPrimaryKey(PunchJour record);
}