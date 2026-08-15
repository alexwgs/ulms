package com.cmbccd.ulms.hr.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.dao.MoodRecordMapper;
import com.cmbccd.ulms.hr.domain.MoodRecord;
import com.cmbccd.ulms.hr.domain.MoodRecordExample;
import com.cmbccd.ulms.hr.service.MoodRecordService;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class MoodRecordServiceImpl implements MoodRecordService {

    @Resource
    private MoodRecordMapper moodRecordMapper;

    @Override
    public int create(MoodRecord record) {
        record = fillRecord(record);
        return moodRecordMapper.insert(record);
    }

    @Override
    public List<MoodRecord> list(MoodRecordExample example) {
        return moodRecordMapper.selectByExample(example);
    }

    public MoodRecord fillRecord(MoodRecord record) {
        String userId = Util.userIdByShiro();
        Employee user = DataCache.getEmployees().get(userId);
        record.setHandleDate(Util.currentDateTime());
        record.setDataDate(Util.getDateToday());
        record.setUserId(userId);
        record.setDeptNum(user.getDeptNum());
        record.setDeptGroup(user.getDeptGroup());
        return record;
    }
}
