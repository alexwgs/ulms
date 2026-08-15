package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.oht.dao.CaseMapper;
import com.cmbccd.ulms.oht.dao.StatusJourMapper;
import com.cmbccd.ulms.sys.service.DictionaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * {@link CaseServiceImpl} 单元测试：验证无未结案时正确返回 null（空结果保护）。
 */
@ExtendWith(MockitoExtension.class)
public class CaseServiceImplTest {

    @Mock
    private CaseMapper caseMapper;

    @Mock
    private StatusJourMapper statusJourMapper;

    @Mock
    private DictionaryService dictionaryService;

    @InjectMocks
    private CaseServiceImpl caseService;

    @Test
    void getUnfinishCaseByHelperReturnsNullWhenNoCase() {
        when(caseMapper.selectByExample(any())).thenReturn(new ArrayList<>());
        assertNull(caseService.getUnfinishCaseByHelper("u1"));
    }

    @Test
    void getUnfinishCaseByListenerReturnsNullWhenNoCase() {
        when(caseMapper.selectByExample(any())).thenReturn(new ArrayList<>());
        assertNull(caseService.getUnfinishCaseByListener("u1"));
    }
}
