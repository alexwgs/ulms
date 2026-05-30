package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.flow.domain.PerfFlag;
import com.cmbccd.ulms.sys.domain.Msg;

import java.util.List;
import java.util.Map;

public interface PerfFlagFlowService {

    int insert(PerfFlag record, String caseId);

    Msg batchInsert(List<PerfFlag> list, String caseId);

    Msg check(List<PerfFlag> list);

    int update(PerfFlag record);

    int delete(String id);

    List<PerfFlag> list(Map<String, String> params);
}
