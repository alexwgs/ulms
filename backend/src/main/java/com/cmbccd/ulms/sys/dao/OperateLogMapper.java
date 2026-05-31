package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.OperateLog;
import com.cmbccd.ulms.sys.domain.OperateLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OperateLogMapper {
    long countByExample(OperateLogExample example);

    int deleteByExample(OperateLogExample example);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    List<OperateLog> selectByExample(OperateLogExample example);

    int updateByExampleSelective(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByExample(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    // ==================== Dashboard 自定义查询 ====================
    List<Map<String, Object>> subsysVisitTrend(@Param("begDate") String begDate, @Param("endDate") String endDate);
}