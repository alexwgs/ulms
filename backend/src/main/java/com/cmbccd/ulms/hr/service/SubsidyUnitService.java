package com.cmbccd.ulms.hr.service;

import com.cmbccd.ulms.hr.domain.SubsidyUnit;

import java.util.List;
import java.util.Map;

public interface SubsidyUnitService {
    List<SubsidyUnit> list(Map<String, String> params);

    SubsidyUnit get(String id);

    int add(SubsidyUnit subsidyUnit);

    int update(SubsidyUnit subsidyUnit);

    int delete(String id);
}
