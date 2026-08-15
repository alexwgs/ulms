package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.edu.domain.QuesBank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link QuesBankServiceImpl#checkUserAnswer} 单元测试：
 * 覆盖单选/多选判分逻辑与空答案保护（纯逻辑，无外部依赖）。
 */
public class QuesBankServiceImplTest {

    private QuesBank question(Integer quesType, String answer) {
        QuesBank q = new QuesBank();
        q.setQuesType(quesType);
        q.setAnswer(answer);
        return q;
    }

    @Test
    void checkUserAnswerRejectsEmptyAnswer() {
        QuesBankServiceImpl service = new QuesBankServiceImpl();
        assertEquals(0, service.checkUserAnswer(question(1, "A"), null));
        assertEquals(0, service.checkUserAnswer(question(1, "A"), ""));
    }

    @Test
    void checkSingleChoiceCorrect() {
        QuesBankServiceImpl service = new QuesBankServiceImpl();
        assertEquals(1, service.checkUserAnswer(question(1, "A"), "A"));
    }

    @Test
    void checkSingleChoiceWrong() {
        QuesBankServiceImpl service = new QuesBankServiceImpl();
        assertEquals(0, service.checkUserAnswer(question(1, "A"), "B"));
    }

    @Test
    void checkMultipleChoiceCorrect() {
        QuesBankServiceImpl service = new QuesBankServiceImpl();
        assertEquals(1, service.checkUserAnswer(question(2, "A,B,C"), "A,B,C"));
    }

    @Test
    void checkMultipleChoiceWrong() {
        QuesBankServiceImpl service = new QuesBankServiceImpl();
        assertEquals(0, service.checkUserAnswer(question(2, "A,B,C"), "A,B,D"));
    }

    @Test
    void checkMultipleChoiceSubsetRejected() {
        QuesBankServiceImpl service = new QuesBankServiceImpl();
        assertEquals(0, service.checkUserAnswer(question(2, "A,B,C"), "A,B"));
    }
}
