package com.cmbccd.ulms.rpa.dao;

import com.cmbccd.ulms.rpa.domain.ToolReport;
import com.cmbccd.ulms.rpa.domain.ToolReportExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ToolReportMapper {
    long countByExample(ToolReportExample example);

    int deleteByExample(ToolReportExample example);

    int insert(ToolReport row);

    int insertSelective(ToolReport row);

    List<ToolReport> selectByExample(ToolReportExample example);

    int updateByExampleSelective(@Param("row") ToolReport row, @Param("example") ToolReportExample example);

    int updateByExample(@Param("row") ToolReport row, @Param("example") ToolReportExample example);
}