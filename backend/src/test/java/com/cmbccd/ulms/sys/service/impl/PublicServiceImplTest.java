package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * {@link PublicServiceImpl#getJourno} 单元测试：
 * 验证跨模块 DAO 收口后 Service 正确委托 mapper。
 */
@ExtendWith(MockitoExtension.class)
public class PublicServiceImplTest {

    @Mock
    private PublicMapper publicMapper;

    @InjectMocks
    private PublicServiceImpl publicService;

    @Test
    void getJournoDelegatesToMapper() {
        when(publicMapper.selectNewJourno()).thenReturn("J20260001");

        String result = publicService.getJourno();

        assertEquals("J20260001", result);
    }
}
