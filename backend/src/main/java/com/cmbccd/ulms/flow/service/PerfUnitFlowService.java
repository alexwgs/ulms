package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.flow.domain.PerfUnit;
import com.cmbccd.ulms.sys.domain.Msg;

import java.util.List;
import java.util.Map;

public interface PerfUnitFlowService {

    int insert(PerfUnit record,String caseId);

    Msg batchInsert(List<PerfUnit> list, String caseId);

    Msg check(List<PerfUnit> list);

    int update(PerfUnit record);

    int delete(String id);

    List<PerfUnit> list(Map<String, String> params);
}
