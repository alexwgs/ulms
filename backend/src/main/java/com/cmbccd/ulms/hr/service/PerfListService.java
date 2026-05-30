package com.cmbccd.ulms.hr.service;

import com.cmbccd.ulms.hr.domain.PerfList;

import java.util.List;
import java.util.Map;

public interface PerfListService {

    int insertInit(String perfMonth);

    List<PerfList> list(Map<String, String> params);

    List<PerfList> report(Map<String, String> params);

    PerfList getByUserId(String userId,String perfMonth);

    int updatePerfUnit(String perfMonth);

    int delete(String perfMonth);

    // 获取我的团队
    List<PerfList> listMyTeam(String perfMonth);
}
