package com.cmbccd.ulms.flow.dao;

import com.cmbccd.ulms.flow.domain.FlowGqsq;
import com.cmbccd.ulms.flow.domain.FlowGqsqExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FlowGqsqMapper {
    long countByExample(FlowGqsqExample example);

    int deleteByExample(FlowGqsqExample example);

    int deleteByPrimaryKey(String journo);

    int insert(FlowGqsq record);

    int insertSelective(FlowGqsq record);

    List<FlowGqsq> selectByExample(FlowGqsqExample example);

    FlowGqsq selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") FlowGqsq record, @Param("example") FlowGqsqExample example);

    int updateByExample(@Param("record") FlowGqsq record, @Param("example") FlowGqsqExample example);

    int updateByPrimaryKeySelective(FlowGqsq record);

    int updateByPrimaryKey(FlowGqsq record);

    FlowGqsq check(String ploNum, String dataDate,String begTime,String endTime);

    List<Map<String,String>> listAuthSeq(String ploNum);
}