package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.BrushScoreMapper;
import com.cmbccd.ulms.edu.domain.BrushScore;
import com.cmbccd.ulms.edu.domain.QuesBank;
import com.cmbccd.ulms.edu.service.QuesBankService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.service.PublicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link BrushScoreServiceImpl#drawQuestion} / {@link BrushScoreServiceImpl#submitAnswer} 单元测试：
 * 覆盖刷题抽题限额、空题库、成功抽题与答案提交判分（Mockito + 静态 mock）。
 */
@ExtendWith(MockitoExtension.class)
public class BrushScoreServiceImplTest {

    @Mock
    private BrushScoreMapper brushScoreMapper;
    @Mock
    private QuesBankService quesBankService;
    @Mock
    private PublicService publicService;

    @InjectMocks
    private BrushScoreServiceImpl service;

    private QuesBank question(String code, String libCode) {
        QuesBank q = new QuesBank();
        q.setQuesCode(code);
        q.setLibCode(libCode);
        return q;
    }

    @Test
    void drawQuestionThrowsWhenLibEmpty() {
        when(quesBankService.getQuestion(anyList(), anyString())).thenReturn(Collections.emptyList());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.drawQuestion("L1", "655012", 10));
        assertEquals("没有在题库中找到题目~", ex.getMessage());
    }

    @Test
    void drawQuestionThrowsWhenDayLimitReached() {
        when(quesBankService.getQuestion(anyList(), anyString()))
                .thenReturn(Arrays.asList("Q1"));
        when(brushScoreMapper.dayBrushCount(anyString(), anyString())).thenReturn(10);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.drawQuestion("L1", "655012", 10));
        assertEquals("您今日刷题数已满，休息一下吧~明天再来~", ex.getMessage());
    }

    @Test
    void drawQuestionSucceeds() {
        when(quesBankService.getQuestion(anyList(), anyString()))
                .thenReturn(Arrays.asList("Q1", "Q2"));
        when(brushScoreMapper.dayBrushCount(anyString(), anyString())).thenReturn(1);
        when(quesBankService.getNoSensitive(anyString())).thenReturn(question("Q1", "L1"));
        when(publicService.getJourno()).thenReturn("JOURNO-1");
        when(brushScoreMapper.insert(any())).thenReturn(1);

        try (MockedStatic<Util> util = mockStatic(Util.class);
             MockedStatic<DataCache> cache = mockStatic(DataCache.class)) {
            util.when(Util::userIdByShiro).thenReturn("655012");
            util.when(Util::getDateToday).thenReturn("2026-08-16");
            util.when(Util::currentDateTime).thenReturn("2026-08-16 10:00:00");
            // mockStatic 后所有静态方法默认返回 null，需还原 isNullorEmpty 真实语义
            util.when(() -> Util.isNullorEmpty(any())).thenReturn(false);
            Map<String, Employee> empMap = new HashMap<>();
            Employee emp = new Employee();
            emp.setPloName("测试");
            emp.setDeptNum("D1");
            emp.setDeptGroup("G1");
            empMap.put("655012", emp);
            cache.when(DataCache::getEmployees).thenReturn(empMap);

            BrushScore score = service.drawQuestion("L1", "655012", 10);

            assertNotNull(score);
            assertEquals("Q1", score.getQuesCode());
            assertEquals("655012", score.getPloNum());
            assertEquals("2026-08-16", score.getDataDate());
            assertNotNull(score.getQuestion());
        }
    }

    @Test
    void submitAnswerReturnsNullWhenNotLogin() {
        try (MockedStatic<Util> util = mockStatic(Util.class)) {
            util.when(Util::userIdByShiro).thenReturn(null);
            // 还原 isNullorEmpty 真实语义（null -> true），使未登录分支生效
            util.when(() -> Util.isNullorEmpty(any())).thenAnswer(inv -> inv.getArgument(0) == null);
            assertNull(service.submitAnswer(new BrushScore()));
        }
    }

    @Test
    void submitAnswerScoresCorrectAnswer() {
        BrushScore record = new BrushScore();
        record.setQuesCode("Q1");
        record.setAnswer("A");
        QuesBank q = question("Q1", "L1");
        q.setQuesType(1);
        q.setAnswer("A");

        when(quesBankService.get("Q1")).thenReturn(q);
        when(quesBankService.checkUserAnswer(q, "A")).thenReturn(1);
        when(brushScoreMapper.updateByPrimaryKeySelective(any())).thenReturn(1);

        try (MockedStatic<Util> util = mockStatic(Util.class)) {
            util.when(Util::userIdByShiro).thenReturn("655012");
            util.when(Util::currentDateTime).thenReturn("2026-08-16 10:00:00");

            BrushScore result = service.submitAnswer(record);

            assertNotNull(result);
            assertEquals(1, result.getPassFlag());
            assertEquals("2026-08-16", result.getAnswerDate());
        }
    }
}
