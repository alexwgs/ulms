package com.cmbccd.ulms.oht.dao;

import com.cmbccd.ulms.oht.domain.Case;
import com.cmbccd.ulms.oht.domain.CaseExample;
import com.cmbccd.ulms.oht.domain.CaseReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CaseMapper {
    long countByExample(CaseExample example);

    int deleteByExample(CaseExample example);

    int deleteByPrimaryKey(String caseId);

    int insert(Case record);

    int insertSelective(Case record);

    List<Case> selectByExample(CaseExample example);
    
    List<CaseReport> selectByExampleReport(CaseExample example);

    Case selectByPrimaryKey(String caseId);

    int updateByExampleSelective(@Param("record") Case record, @Param("example") CaseExample example);

    int updateByExample(@Param("record") Case record, @Param("example") CaseExample example);

    int updateByPrimaryKeySelective(Case record);

    int updateByPrimaryKey(Case record);
    
    int updateDisconnectCaseStatus(String buildId);
    
    List<String> selectPickIdByDateTime(String begTime,String endTime);
    
    List<Map<String,Object>> selectCaseCompleteCountByCaseType(String begTime,String endTime,String caseType,String pickId);

    List<Map<String,Object>> selectStartCaseCenterByCaseType(String begTime,String endTime,String caseType);
}