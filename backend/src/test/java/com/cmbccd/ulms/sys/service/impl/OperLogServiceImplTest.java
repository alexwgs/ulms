package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.OperLogMapper;
import com.cmbccd.ulms.sys.domain.OperLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * {@link OperLogServiceImpl#listOperLogForReport} 单元测试：
 * 验证报表查询条件构造下沉到 Service 后正确委托 mapper。
 */
@ExtendWith(MockitoExtension.class)
public class OperLogServiceImplTest {

    @Mock
    private OperLogMapper operLogMapper;

    @InjectMocks
    private OperLogServiceImpl operLogService;

    @Test
    void listOperLogForReportDelegatesToMapper() {
        List<OperLog> expected = Collections.singletonList(new OperLog());
        when(operLogMapper.selectByExample(any())).thenReturn(expected);

        Map<String, String> params = new HashMap<>();
        params.put("begDate", "2026-08-01");
        params.put("endDate", "2026-08-15");

        List<OperLog> result = operLogService.listOperLogForReport(params);

        assertSame(expected, result);
    }
}
