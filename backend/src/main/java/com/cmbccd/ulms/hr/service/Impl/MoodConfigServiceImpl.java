package com.cmbccd.ulms.hr.service.Impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.dao.MoodConfigMapper;
import com.cmbccd.ulms.hr.domain.MoodConfig;
import com.cmbccd.ulms.hr.domain.MoodConfigExample;
import com.cmbccd.ulms.hr.service.MoodConfigService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class MoodConfigServiceImpl implements MoodConfigService {
    @Resource
    private MoodConfigMapper moodConfigMapper;

    @Override
    public List<MoodConfig> list(Short status) {
        if(Util.isNullorEmpty(status)){
            return moodConfigMapper.selectByExample(null);
        }else {
            MoodConfigExample example = new MoodConfigExample();
            MoodConfigExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo(status);
            return moodConfigMapper.selectByExampleWithBLOBs(example);
        }
    }
}
