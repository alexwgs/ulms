package com.cmbccd.ulms.oht.dao;

import com.cmbccd.ulms.oht.domain.QuickMemo;
import com.cmbccd.ulms.oht.domain.QuickMemoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface QuickMemoMapper {
    long countByExample(QuickMemoExample example);

    int deleteByExample(QuickMemoExample example);

    int insert(QuickMemo record);

    int insertSelective(QuickMemo record);

    List<QuickMemo> selectByExample(QuickMemoExample example);

    int updateByExampleSelective(@Param("record") QuickMemo record, @Param("example") QuickMemoExample example);

    int updateByExample(@Param("record") QuickMemo record, @Param("example") QuickMemoExample example);
    
    List<QuickMemo> selectByRoleType(String roleType);
    
    int updateByJourno(@Param("record") QuickMemo record);
    
    int deleteByJourno(String journo);
    
    String selectNewJourno();
}