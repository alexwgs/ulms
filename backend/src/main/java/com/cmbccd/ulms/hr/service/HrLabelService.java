package com.cmbccd.ulms.hr.service;

import com.cmbccd.ulms.hr.domain.HrLabel;

import java.util.List;

public interface HrLabelService {
    int delete(String perfMonth);
    List<HrLabel> gennerLabel(String perfMonth);

    List<HrLabel> list(String perfMonth);
    List<HrLabel> list(String perfMonth, String ploNum);

    List<HrLabel> listLabelHumanFlag(String perfMonth);
}
