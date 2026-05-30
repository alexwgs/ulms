package com.cmbccd.ulms.sys.service.impl;


import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.OperLogMapper;
import com.cmbccd.ulms.sys.domain.OperLog;
import com.cmbccd.ulms.sys.domain.OperLogExample;
import com.cmbccd.ulms.sys.service.OperLogService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class OperLogServiceImpl implements OperLogService {

    @Resource
    private OperLogMapper operLogMapper;

    @Override
    public int insert(OperLog operLog) {
        operLog.setId(Util.getUUID());
        operLog.setOperTime(Util.currentDateTime()); // 时间
        operLog.setPloNum(Util.userIdByShiro()); // 获取用户名（真实环境中，肯定有工具类获取当前登录者的账号或ID的，或者从token中解析而来）
        if(Util.isNullorEmpty(operLog.getContent()) && !Util.isNullorEmpty(operLog.getRequestMethod())) {
            String method = operLog.getRequestMethod();
            operLog.setContent("GET".equals(method) ? "查询操作" : "PUT".equals(method) ? "更新操作" :
                    "POST".equals(method) ? "新增操作" :
                            "DELETE".equals(method) ?"删除操作" : "未知操作");
        }
        return operLogMapper.insert(operLog);
    }

    @Override
    public List<OperLog> list(OperLogExample example) {
        return operLogMapper.selectByExample(example);
    }
}
