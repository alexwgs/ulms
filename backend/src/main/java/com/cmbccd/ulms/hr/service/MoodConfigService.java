package com.cmbccd.ulms.hr.service;

import com.cmbccd.ulms.hr.domain.MoodConfig;

import java.util.List;

public interface MoodConfigService {
    List<MoodConfig> list(Short valid);
}
