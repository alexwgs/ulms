package com.cmbccd.ulms.flow.service.impl;

import com.cmbccd.ulms.flow.dao.FlowCaseMapper;
import com.cmbccd.ulms.flow.service.FlowApproveLogService;
import com.cmbccd.ulms.flow.service.FlowApproveTempService;
import com.cmbccd.ulms.flow.service.FlowInfoService;
import com.cmbccd.ulms.flow.service.FlowProxyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * {@link FlowCaseServiceImpl#report} 单元测试：
 * 验证 flowId 缺失时正确返回 null（空入参保护，不触达 mapper）。
 */
@ExtendWith(MockitoExtension.class)
public class FlowCaseServiceImplTest {

    @Mock
    private FlowCaseMapper flowCaseMapper;

    @Mock
    private FlowInfoService flowInfoService;

    @Mock
    private FlowApproveLogService flowApproveLogService;

    @Mock
    private FlowApproveTempService flowApproveTempService;

    @Mock
    private FlowProxyService flowProxyService;

    @InjectMocks
    private FlowCaseServiceImpl flowCaseService;

    @Test
    void reportReturnsNullWhenFlowIdMissing() {
        assertNull(flowCaseService.report(new HashMap<>()));
    }
}
