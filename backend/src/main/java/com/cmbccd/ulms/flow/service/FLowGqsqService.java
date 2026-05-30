package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.flow.domain.FlowGqsq;
import com.cmbccd.ulms.sys.domain.Msg;

import java.util.List;
import java.util.Map;

public interface FLowGqsqService {
    int insert(FlowGqsq record, String caseId);

    Msg batchInsert(List<FlowGqsq> list, String caseId);

    Msg check(List<FlowGqsq> list);

    int update(FlowGqsq record);

    int delete(String id);

    List<FlowGqsq> list(Map<String, String> params);

    List<Map<String, String>> authSeq();
}
