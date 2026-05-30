package com.cmbccd.ulms.flow.dao;

import com.cmbccd.ulms.flow.domain.FlowApproveLog;
import com.cmbccd.ulms.flow.domain.FlowApproveLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowApproveLogMapper {
    long countByExample(FlowApproveLogExample example);

    int deleteByExample(FlowApproveLogExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlowApproveLog record);

    int insertSelective(FlowApproveLog record);

    List<FlowApproveLog> selectByExample(FlowApproveLogExample example);

    FlowApproveLog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlowApproveLog record, @Param("example") FlowApproveLogExample example);

    int updateByExample(@Param("record") FlowApproveLog record, @Param("example") FlowApproveLogExample example);

    int updateByPrimaryKeySelective(FlowApproveLog record);

    int updateByPrimaryKey(FlowApproveLog record);
    // 审批模板与审批日志关联查询
    List<FlowApproveLog> selectApproveLogList(String id);

    List<FlowApproveLog> selectApproveLogListWithProxy(String caseId, String userId);
}