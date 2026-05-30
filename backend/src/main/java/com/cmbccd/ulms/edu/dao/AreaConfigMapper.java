package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.AreaConfig;
import com.cmbccd.ulms.edu.domain.AreaConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaConfigMapper {
    long countByExample(AreaConfigExample example);

    int deleteByExample(AreaConfigExample example);

    int deleteByPrimaryKey(String areaCode);

    int insert(AreaConfig record);

    int insertSelective(AreaConfig record);

    List<AreaConfig> selectByExample(AreaConfigExample example);

    AreaConfig selectByPrimaryKey(String areaCode);

    int updateByExampleSelective(@Param("record") AreaConfig record, @Param("example") AreaConfigExample example);

    int updateByExample(@Param("record") AreaConfig record, @Param("example") AreaConfigExample example);

    int updateByPrimaryKeySelective(AreaConfig record);

    int updateByPrimaryKey(AreaConfig record);
   
}