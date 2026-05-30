package com.cmbccd.ulms.flow.dao;

import com.cmbccd.ulms.flow.domain.FlowProxy;
import com.cmbccd.ulms.flow.domain.FlowProxyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowProxyMapper {
    long countByExample(FlowProxyExample example);

    int deleteByExample(FlowProxyExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlowProxy record);

    int insertSelective(FlowProxy record);

    List<FlowProxy> selectByExample(FlowProxyExample example);

    FlowProxy selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlowProxy record, @Param("example") FlowProxyExample example);

    int updateByExample(@Param("record") FlowProxy record, @Param("example") FlowProxyExample example);

    int updateByPrimaryKeySelective(FlowProxy record);

    int updateByPrimaryKey(FlowProxy record);
}