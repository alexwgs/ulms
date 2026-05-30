package com.cmbccd.ulms.flow.dao;

import com.cmbccd.ulms.flow.domain.FlowApproveTemp;
import com.cmbccd.ulms.flow.domain.FlowApproveTempExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowApproveTempMapper {
    long countByExample(FlowApproveTempExample example);

    int deleteByExample(FlowApproveTempExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlowApproveTemp record);

    int insertSelective(FlowApproveTemp record);

    List<FlowApproveTemp> selectByExample(FlowApproveTempExample example);

    FlowApproveTemp selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlowApproveTemp record, @Param("example") FlowApproveTempExample example);

    int updateByExample(@Param("record") FlowApproveTemp record, @Param("example") FlowApproveTempExample example);

    int updateByPrimaryKeySelective(FlowApproveTemp record);

    int updateByPrimaryKey(FlowApproveTemp record);
}