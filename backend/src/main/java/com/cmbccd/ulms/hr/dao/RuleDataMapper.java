package com.cmbccd.ulms.hr.dao;

import com.cmbccd.ulms.hr.domain.RuleData;
import com.cmbccd.ulms.hr.domain.RuleDataExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RuleDataMapper {
    long countByExample(RuleDataExample example);

    int deleteByExample(RuleDataExample example);

    int deleteByPrimaryKey(String journo);

    int insert(RuleData row);

    int insertSelective(RuleData row);

    List<RuleData> selectByExample(RuleDataExample example);

    RuleData selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("row") RuleData row, @Param("example") RuleDataExample example);

    int updateByExample(@Param("row") RuleData row, @Param("example") RuleDataExample example);

    int updateByPrimaryKeySelective(RuleData row);

    int updateByPrimaryKey(RuleData row);
}