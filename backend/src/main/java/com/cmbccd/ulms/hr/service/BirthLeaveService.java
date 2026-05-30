package com.cmbccd.ulms.hr.service;

import com.cmbccd.ulms.hr.domain.BirthLeave;

import java.util.List;
import java.util.Map;

public interface BirthLeaveService {

    List<BirthLeave> list(Map<String, String> params);

    List<BirthLeave> getByPloNum(String ploNum);
    int add(List<BirthLeave> records);

    int update(List<BirthLeave> records);


}
