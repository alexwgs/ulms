package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.OperLog;
import com.cmbccd.ulms.sys.domain.OperLogExample;

import java.util.List;

public interface OperLogService {
    int insert(OperLog operLog);

    List<OperLog> list(OperLogExample example);

}
