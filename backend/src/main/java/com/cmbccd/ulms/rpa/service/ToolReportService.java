package com.cmbccd.ulms.rpa.service;

import com.cmbccd.ulms.rpa.domain.ToolReport;
import com.cmbccd.ulms.rpa.domain.ToolReportVO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ToolReportService {

    List<ToolReport> list(Map<String, String> params);

    int add(ToolReport record);

    Long getToolCoolDown(String toolId) throws ParseException;

    List<ToolReportVO> download(Map<String, String> params);

    Map<String,Map<String, Object>> chart(Map<String, String> params);

}
