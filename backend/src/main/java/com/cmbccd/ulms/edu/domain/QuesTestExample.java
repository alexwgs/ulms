package com.cmbccd.ulms.edu.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class QuesTestExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuesTestExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andJournoIsNull() {
            addCriterion("JOURNO is null");
            return (Criteria) this;
        }

        public Criteria andJournoIsNotNull() {
            addCriterion("JOURNO is not null");
            return (Criteria) this;
        }

        public Criteria andJournoEqualTo(String value) {
            addCriterion("JOURNO =", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoNotEqualTo(String value) {
            addCriterion("JOURNO <>", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoGreaterThan(String value) {
            addCriterion("JOURNO >", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoGreaterThanOrEqualTo(String value) {
            addCriterion("JOURNO >=", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoLessThan(String value) {
            addCriterion("JOURNO <", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoLessThanOrEqualTo(String value) {
            addCriterion("JOURNO <=", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoLike(String value) {
            addCriterion("JOURNO like", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoNotLike(String value) {
            addCriterion("JOURNO not like", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoIn(List<String> values) {
            addCriterion("JOURNO in", values, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoNotIn(List<String> values) {
            addCriterion("JOURNO not in", values, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoBetween(String value1, String value2) {
            addCriterion("JOURNO between", value1, value2, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoNotBetween(String value1, String value2) {
            addCriterion("JOURNO not between", value1, value2, "journo");
            return (Criteria) this;
        }

        public Criteria andExamCodeIsNull() {
            addCriterion("EXAM_CODE is null");
            return (Criteria) this;
        }

        public Criteria andExamCodeIsNotNull() {
            addCriterion("EXAM_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andExamCodeEqualTo(String value) {
            addCriterion("EXAM_CODE =", value, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeNotEqualTo(String value) {
            addCriterion("EXAM_CODE <>", value, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeGreaterThan(String value) {
            addCriterion("EXAM_CODE >", value, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeGreaterThanOrEqualTo(String value) {
            addCriterion("EXAM_CODE >=", value, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeLessThan(String value) {
            addCriterion("EXAM_CODE <", value, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeLessThanOrEqualTo(String value) {
            addCriterion("EXAM_CODE <=", value, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeLike(String value) {
            addCriterion("EXAM_CODE like", value, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeNotLike(String value) {
            addCriterion("EXAM_CODE not like", value, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeIn(List<String> values) {
            addCriterion("EXAM_CODE in", values, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeNotIn(List<String> values) {
            addCriterion("EXAM_CODE not in", values, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeBetween(String value1, String value2) {
            addCriterion("EXAM_CODE between", value1, value2, "examCode");
            return (Criteria) this;
        }

        public Criteria andExamCodeNotBetween(String value1, String value2) {
            addCriterion("EXAM_CODE not between", value1, value2, "examCode");
            return (Criteria) this;
        }

        public Criteria andPloNumIsNull() {
            addCriterion("PLO_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPloNumIsNotNull() {
            addCriterion("PLO_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPloNumEqualTo(String value) {
            addCriterion("PLO_NUM =", value, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumNotEqualTo(String value) {
            addCriterion("PLO_NUM <>", value, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumGreaterThan(String value) {
            addCriterion("PLO_NUM >", value, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumGreaterThanOrEqualTo(String value) {
            addCriterion("PLO_NUM >=", value, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumLessThan(String value) {
            addCriterion("PLO_NUM <", value, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumLessThanOrEqualTo(String value) {
            addCriterion("PLO_NUM <=", value, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumLike(String value) {
            addCriterion("PLO_NUM like", value, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumNotLike(String value) {
            addCriterion("PLO_NUM not like", value, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumIn(List<String> values) {
            addCriterion("PLO_NUM in", values, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumNotIn(List<String> values) {
            addCriterion("PLO_NUM not in", values, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumBetween(String value1, String value2) {
            addCriterion("PLO_NUM between", value1, value2, "ploNum");
            return (Criteria) this;
        }

        public Criteria andPloNumNotBetween(String value1, String value2) {
            addCriterion("PLO_NUM not between", value1, value2, "ploNum");
            return (Criteria) this;
        }

        public Criteria andQuesCodeIsNull() {
            addCriterion("QUES_CODE is null");
            return (Criteria) this;
        }

        public Criteria andQuesCodeIsNotNull() {
            addCriterion("QUES_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andQuesCodeEqualTo(String value) {
            addCriterion("QUES_CODE =", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeNotEqualTo(String value) {
            addCriterion("QUES_CODE <>", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeGreaterThan(String value) {
            addCriterion("QUES_CODE >", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeGreaterThanOrEqualTo(String value) {
            addCriterion("QUES_CODE >=", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeLessThan(String value) {
            addCriterion("QUES_CODE <", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeLessThanOrEqualTo(String value) {
            addCriterion("QUES_CODE <=", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeLike(String value) {
            addCriterion("QUES_CODE like", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeNotLike(String value) {
            addCriterion("QUES_CODE not like", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeIn(List<String> values) {
            addCriterion("QUES_CODE in", values, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeNotIn(List<String> values) {
            addCriterion("QUES_CODE not in", values, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeBetween(String value1, String value2) {
            addCriterion("QUES_CODE between", value1, value2, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeNotBetween(String value1, String value2) {
            addCriterion("QUES_CODE not between", value1, value2, "quesCode");
            return (Criteria) this;
        }

        public Criteria andTestStatIsNull() {
            addCriterion("TEST_STAT is null");
            return (Criteria) this;
        }

        public Criteria andTestStatIsNotNull() {
            addCriterion("TEST_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andTestStatEqualTo(Integer value) {
            addCriterion("TEST_STAT =", value, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatNotEqualTo(Integer value) {
            addCriterion("TEST_STAT <>", value, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatGreaterThan(Integer value) {
            addCriterion("TEST_STAT >", value, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("TEST_STAT >=", value, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatLessThan(Integer value) {
            addCriterion("TEST_STAT <", value, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatLessThanOrEqualTo(Integer value) {
            addCriterion("TEST_STAT <=", value, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatIn(List<Integer> values) {
            addCriterion("TEST_STAT in", values, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatNotIn(List<Integer> values) {
            addCriterion("TEST_STAT not in", values, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatBetween(Integer value1, Integer value2) {
            addCriterion("TEST_STAT between", value1, value2, "testStat");
            return (Criteria) this;
        }

        public Criteria andTestStatNotBetween(Integer value1, Integer value2) {
            addCriterion("TEST_STAT not between", value1, value2, "testStat");
            return (Criteria) this;
        }

        public Criteria andUserAnswerIsNull() {
            addCriterion("USER_ANSWER is null");
            return (Criteria) this;
        }

        public Criteria andUserAnswerIsNotNull() {
            addCriterion("USER_ANSWER is not null");
            return (Criteria) this;
        }

        public Criteria andUserAnswerEqualTo(String value) {
            addCriterion("USER_ANSWER =", value, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerNotEqualTo(String value) {
            addCriterion("USER_ANSWER <>", value, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerGreaterThan(String value) {
            addCriterion("USER_ANSWER >", value, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ANSWER >=", value, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerLessThan(String value) {
            addCriterion("USER_ANSWER <", value, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerLessThanOrEqualTo(String value) {
            addCriterion("USER_ANSWER <=", value, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerLike(String value) {
            addCriterion("USER_ANSWER like", value, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerNotLike(String value) {
            addCriterion("USER_ANSWER not like", value, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerIn(List<String> values) {
            addCriterion("USER_ANSWER in", values, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerNotIn(List<String> values) {
            addCriterion("USER_ANSWER not in", values, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerBetween(String value1, String value2) {
            addCriterion("USER_ANSWER between", value1, value2, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andUserAnswerNotBetween(String value1, String value2) {
            addCriterion("USER_ANSWER not between", value1, value2, "userAnswer");
            return (Criteria) this;
        }

        public Criteria andIfCorrectIsNull() {
            addCriterion("IF_CORRECT is null");
            return (Criteria) this;
        }

        public Criteria andIfCorrectIsNotNull() {
            addCriterion("IF_CORRECT is not null");
            return (Criteria) this;
        }

        public Criteria andIfCorrectEqualTo(Integer value) {
            addCriterion("IF_CORRECT =", value, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectNotEqualTo(Integer value) {
            addCriterion("IF_CORRECT <>", value, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectGreaterThan(Integer value) {
            addCriterion("IF_CORRECT >", value, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectGreaterThanOrEqualTo(Integer value) {
            addCriterion("IF_CORRECT >=", value, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectLessThan(Integer value) {
            addCriterion("IF_CORRECT <", value, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectLessThanOrEqualTo(Integer value) {
            addCriterion("IF_CORRECT <=", value, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectIn(List<Integer> values) {
            addCriterion("IF_CORRECT in", values, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectNotIn(List<Integer> values) {
            addCriterion("IF_CORRECT not in", values, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectBetween(Integer value1, Integer value2) {
            addCriterion("IF_CORRECT between", value1, value2, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andIfCorrectNotBetween(Integer value1, Integer value2) {
            addCriterion("IF_CORRECT not between", value1, value2, "ifCorrect");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("SCORE is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("SCORE is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(BigDecimal value) {
            addCriterion("SCORE =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(BigDecimal value) {
            addCriterion("SCORE <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(BigDecimal value) {
            addCriterion("SCORE >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SCORE >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(BigDecimal value) {
            addCriterion("SCORE <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SCORE <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<BigDecimal> values) {
            addCriterion("SCORE in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<BigDecimal> values) {
            addCriterion("SCORE not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCORE between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCORE not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andHandleDateIsNull() {
            addCriterion("HANDLE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andHandleDateIsNotNull() {
            addCriterion("HANDLE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andHandleDateEqualTo(String value) {
            addCriterion("HANDLE_DATE =", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotEqualTo(String value) {
            addCriterion("HANDLE_DATE <>", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateGreaterThan(String value) {
            addCriterion("HANDLE_DATE >", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLE_DATE >=", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateLessThan(String value) {
            addCriterion("HANDLE_DATE <", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateLessThanOrEqualTo(String value) {
            addCriterion("HANDLE_DATE <=", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateLike(String value) {
            addCriterion("HANDLE_DATE like", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotLike(String value) {
            addCriterion("HANDLE_DATE not like", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateIn(List<String> values) {
            addCriterion("HANDLE_DATE in", values, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotIn(List<String> values) {
            addCriterion("HANDLE_DATE not in", values, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateBetween(String value1, String value2) {
            addCriterion("HANDLE_DATE between", value1, value2, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotBetween(String value1, String value2) {
            addCriterion("HANDLE_DATE not between", value1, value2, "handleDate");
            return (Criteria) this;
        }

        public Criteria andQuesOrderIsNull() {
            addCriterion("QUES_ORDER is null");
            return (Criteria) this;
        }

        public Criteria andQuesOrderIsNotNull() {
            addCriterion("QUES_ORDER is not null");
            return (Criteria) this;
        }

        public Criteria andQuesOrderEqualTo(Integer value) {
            addCriterion("QUES_ORDER =", value, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderNotEqualTo(Integer value) {
            addCriterion("QUES_ORDER <>", value, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderGreaterThan(Integer value) {
            addCriterion("QUES_ORDER >", value, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUES_ORDER >=", value, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderLessThan(Integer value) {
            addCriterion("QUES_ORDER <", value, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderLessThanOrEqualTo(Integer value) {
            addCriterion("QUES_ORDER <=", value, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderIn(List<Integer> values) {
            addCriterion("QUES_ORDER in", values, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderNotIn(List<Integer> values) {
            addCriterion("QUES_ORDER not in", values, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderBetween(Integer value1, Integer value2) {
            addCriterion("QUES_ORDER between", value1, value2, "quesOrder");
            return (Criteria) this;
        }

        public Criteria andQuesOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("QUES_ORDER not between", value1, value2, "quesOrder");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}