package com.cmbccd.ulms.hr.service;

import com.cmbccd.ulms.hr.domain.MoodRecord;
import com.cmbccd.ulms.hr.domain.MoodRecordExample;

import java.util.List;

public interface MoodRecordService {

    int create(MoodRecord record);

    List<MoodRecord> list(MoodRecordExample example);
}
