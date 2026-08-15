package com.cmbccd.ulms.flow.dao;

import com.cmbccd.ulms.flow.domain.FlowCase;
import com.cmbccd.ulms.flow.domain.FlowCaseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FlowCaseMapper {
    long countByExample(FlowCaseExample example);

    int deleteByExample(FlowCaseExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlowCase record);

    int insertSelective(FlowCase record);

    List<FlowCase> selectByExample(FlowCaseExample example);

    FlowCase selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlowCase record, @Param("example") FlowCaseExample example);

    int updateByExample(@Param("record") FlowCase record, @Param("example") FlowCaseExample example);

    int updateByPrimaryKeySelective(FlowCase record);

    int updateByPrimaryKey(FlowCase record);

    int cancelCaseDetail(@Param("tableName") String tableName, @Param("statusColumn") String statusColumn,
                         @Param("idColumn") String idColumn, @Param("caseId") String caseId);

    List<String> getCaseTodoIds(String userId);

    List<String> myCaseId(@Param("ploNum") String ploNum);
}