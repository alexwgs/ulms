package com.cmbccd.ulms.oht.dao;

import com.cmbccd.ulms.oht.domain.CaseTask;
import com.cmbccd.ulms.oht.domain.CaseTaskExample;
import com.cmbccd.ulms.oht.domain.CaseTaskReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CaseTaskMapper {
    long countByExample(CaseTaskExample example);

    int deleteByExample(CaseTaskExample example);

    int insert(CaseTask record);

    int insertSelective(CaseTask record);

    List<CaseTask> selectByExample(CaseTaskExample example);

    int updateByExampleSelective(@Param("record") CaseTask record, @Param("example") CaseTaskExample example);

    int updateByExample(@Param("record") CaseTask record, @Param("example") CaseTaskExample example);
    
    int countTaskByUserIdAndCaseId(String userId,String caseId);
    
    int updateTaskCompleteByUserIdAndCaseId(String userId,String caseId);
    
    int updateTaskRefuseByUserIdAndCaseId(String userId,String caseId, String memo);
    
    List<Map<String,Object>> selectTsakCountTodayByUserId(String userId);
    
    List<CaseTask> selectTsakByCaseId(String caseId);
    
    List<CaseTaskReport> selectTsaksByDate(String begDate,String endDate);

}