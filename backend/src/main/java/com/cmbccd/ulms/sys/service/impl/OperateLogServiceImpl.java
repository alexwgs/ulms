package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.OperateLogMapper;
import com.cmbccd.ulms.sys.domain.OperateLog;
import com.cmbccd.ulms.sys.service.OperateLogService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Override
    public int add(String module, String operate) {
        String userId = Util.userIdByShiro();
        OperateLog record = new OperateLog();
        record.setDataDate(Util.getDateToday());
        record.setDataTime(Util.currentDateTime());
        record.setPloNum(userId);
        record.setModule(module.toUpperCase());
        record.setOperate(operate);
        return operateLogMapper.insert(record);
    }
}
