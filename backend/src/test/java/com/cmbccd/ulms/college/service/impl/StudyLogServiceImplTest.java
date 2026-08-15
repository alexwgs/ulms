package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.domain.StudyLog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link StudyLogServiceImpl#chekCourseComplete} 单元测试：
 * 覆盖「无需考试/无需评价/仅考试/仅评价/考试+评价」五种课程完成判定分支（纯逻辑）。
 */
public class StudyLogServiceImplTest {

    private StudyLog log(short ifExam, short ifEval, short examComp, short evalComp) {
        StudyLog s = new StudyLog();
        s.setIfExam(ifExam);
        s.setIfEval(ifEval);
        s.setExamComp(examComp);
        s.setEvalComp(evalComp);
        return s;
    }

    @Test
    void completeWhenNoExamAndNoEval() {
        assertTrue(new StudyLogServiceImpl().chekCourseComplete(log((short) 0, (short) 0, (short) 0, (short) 0)));
    }

    @Test
    void completeWhenOnlyExamPassed() {
        assertTrue(new StudyLogServiceImpl().chekCourseComplete(log((short) 1, (short) 0, (short) 1, (short) 0)));
    }

    @Test
    void incompleteWhenOnlyExamNotPassed() {
        assertFalse(new StudyLogServiceImpl().chekCourseComplete(log((short) 1, (short) 0, (short) 0, (short) 0)));
    }

    @Test
    void completeWhenOnlyEvalPassed() {
        assertTrue(new StudyLogServiceImpl().chekCourseComplete(log((short) 0, (short) 1, (short) 0, (short) 1)));
    }

    @Test
    void incompleteWhenOnlyEvalNotPassed() {
        assertFalse(new StudyLogServiceImpl().chekCourseComplete(log((short) 0, (short) 1, (short) 0, (short) 0)));
    }

    @Test
    void completeWhenBothPassed() {
        assertTrue(new StudyLogServiceImpl().chekCourseComplete(log((short) 1, (short) 1, (short) 1, (short) 1)));
    }

    @Test
    void incompleteWhenExamNotPassedBothRequired() {
        assertFalse(new StudyLogServiceImpl().chekCourseComplete(log((short) 1, (short) 1, (short) 0, (short) 1)));
    }
}
