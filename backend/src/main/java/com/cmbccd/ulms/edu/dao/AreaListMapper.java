package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.AreaList;
import com.cmbccd.ulms.edu.domain.AreaListExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaListMapper {
    long countByExample(AreaListExample example);

    int deleteByExample(AreaListExample example);

    int deleteByPrimaryKey(String journo);

    int insert(AreaList record);

    int insertSelective(AreaList record);

    List<AreaList> selectByExample(AreaListExample example);

    AreaList selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") AreaList record, @Param("example") AreaListExample example);

    int updateByExample(@Param("record") AreaList record, @Param("example") AreaListExample example);

    int updateByPrimaryKeySelective(AreaList record);

    int updateByPrimaryKey(AreaList record);
    
}