package com.cmbccd.ulms.flow.dao;

import com.cmbccd.ulms.flow.domain.FlowInfo;
import com.cmbccd.ulms.flow.domain.FlowInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FlowInfoMapper {
    long countByExample(FlowInfoExample example);

    int deleteByExample(FlowInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlowInfo record);

    int insertSelective(FlowInfo record);

    List<FlowInfo> selectByExample(FlowInfoExample example);

    FlowInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlowInfo record, @Param("example") FlowInfoExample example);

    int updateByExample(@Param("record") FlowInfo record, @Param("example") FlowInfoExample example);

    int updateByPrimaryKeySelective(FlowInfo record);

    int updateByPrimaryKey(FlowInfo record);
    int countTableColume(String tableName, List<String> columnNames);

    int updateDetailStatus(@Param("tableName") String tableName, @Param("statusColumn") String statusColumn,
                           @Param("idColumn") String idColumn, @Param("status") Short status,
                           @Param("caseId") String caseId);

    List<Map<String, Object>> selectFlowData(@Param("sqlColumn") String sqlColumn,
                                             @Param("tableName") String tableName,
                                             @Param("idColumn") String idColumn,
                                             @Param("caseId") String caseId);

    List<Map<String, Object>> selectFlowDataByIds(@Param("sqlColumn") String sqlColumn,
                                                   @Param("tableName") String tableName,
                                                   @Param("idColumn") String idColumn,
                                                   @Param("ids") List<String> ids);
}