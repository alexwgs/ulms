package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.flow.domain.FlowInfo;

import java.util.List;

public interface  FlowReportService {

    List<List<Object>> reportFlowData(FlowInfo flowInfo, List<String> caseIds);


}
