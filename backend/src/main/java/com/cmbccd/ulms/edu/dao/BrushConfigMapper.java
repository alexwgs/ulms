package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.BrushConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrushConfigMapper {
    long countByExample(BrushConfigExample example);

    int deleteByExample(BrushConfigExample example);

    int deleteByPrimaryKey(String brushCode);

    int insert(BrushConfig record);

    int insertSelective(BrushConfig record);

    List<BrushConfig> selectByExample(BrushConfigExample example);

    BrushConfig selectByPrimaryKey(String brushCode);

    int updateByExampleSelective(@Param("record") BrushConfig record, @Param("example") BrushConfigExample example);

    int updateByExample(@Param("record") BrushConfig record, @Param("example") BrushConfigExample example);

    int updateByPrimaryKeySelective(BrushConfig record);

    int updateByPrimaryKey(BrushConfig record);
    
   List<BrushConfig> getBrushConfig(String ploNum);
}