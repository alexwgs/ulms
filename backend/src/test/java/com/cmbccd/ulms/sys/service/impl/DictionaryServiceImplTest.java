package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.DictionaryMapper;
import com.cmbccd.ulms.sys.domain.Dictionary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

/**
 * {@link DictionaryServiceImpl#getDictionaryMapByName} 单元测试：
 * 验证跨模块 DAO 收口后 Service 正确委托 mapper。
 */
@ExtendWith(MockitoExtension.class)
public class DictionaryServiceImplTest {

    @Mock
    private DictionaryMapper dictionaryMapper;

    @InjectMocks
    private DictionaryServiceImpl dictionaryService;

    @Test
    void getDictionaryMapByNameDelegatesToMapper() {
        Map<String, Dictionary> expected = new HashMap<>();
        expected.put("1", new Dictionary());
        when(dictionaryMapper.selectDictinaryMapByName("oht_case_status")).thenReturn(expected);

        Map<String, Dictionary> result = dictionaryService.getDictionaryMapByName("oht_case_status");

        assertSame(expected, result);
    }
}
