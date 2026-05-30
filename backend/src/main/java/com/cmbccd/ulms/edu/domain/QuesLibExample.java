package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class QuesLibExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuesLibExample() {
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

        public Criteria andLibCodeIsNull() {
            addCriterion("LIB_CODE is null");
            return (Criteria) this;
        }

        public Criteria andLibCodeIsNotNull() {
            addCriterion("LIB_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andLibCodeEqualTo(String value) {
            addCriterion("LIB_CODE =", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeNotEqualTo(String value) {
            addCriterion("LIB_CODE <>", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeGreaterThan(String value) {
            addCriterion("LIB_CODE >", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeGreaterThanOrEqualTo(String value) {
            addCriterion("LIB_CODE >=", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeLessThan(String value) {
            addCriterion("LIB_CODE <", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeLessThanOrEqualTo(String value) {
            addCriterion("LIB_CODE <=", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeLike(String value) {
            addCriterion("LIB_CODE like", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeNotLike(String value) {
            addCriterion("LIB_CODE not like", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeIn(List<String> values) {
            addCriterion("LIB_CODE in", values, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeNotIn(List<String> values) {
            addCriterion("LIB_CODE not in", values, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeBetween(String value1, String value2) {
            addCriterion("LIB_CODE between", value1, value2, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeNotBetween(String value1, String value2) {
            addCriterion("LIB_CODE not between", value1, value2, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibNameIsNull() {
            addCriterion("LIB_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLibNameIsNotNull() {
            addCriterion("LIB_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLibNameEqualTo(String value) {
            addCriterion("LIB_NAME =", value, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameNotEqualTo(String value) {
            addCriterion("LIB_NAME <>", value, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameGreaterThan(String value) {
            addCriterion("LIB_NAME >", value, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameGreaterThanOrEqualTo(String value) {
            addCriterion("LIB_NAME >=", value, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameLessThan(String value) {
            addCriterion("LIB_NAME <", value, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameLessThanOrEqualTo(String value) {
            addCriterion("LIB_NAME <=", value, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameLike(String value) {
            addCriterion("LIB_NAME like", value, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameNotLike(String value) {
            addCriterion("LIB_NAME not like", value, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameIn(List<String> values) {
            addCriterion("LIB_NAME in", values, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameNotIn(List<String> values) {
            addCriterion("LIB_NAME not in", values, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameBetween(String value1, String value2) {
            addCriterion("LIB_NAME between", value1, value2, "libName");
            return (Criteria) this;
        }

        public Criteria andLibNameNotBetween(String value1, String value2) {
            addCriterion("LIB_NAME not between", value1, value2, "libName");
            return (Criteria) this;
        }

        public Criteria andLibLevelIsNull() {
            addCriterion("LIB_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andLibLevelIsNotNull() {
            addCriterion("LIB_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andLibLevelEqualTo(Integer value) {
            addCriterion("LIB_LEVEL =", value, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelNotEqualTo(Integer value) {
            addCriterion("LIB_LEVEL <>", value, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelGreaterThan(Integer value) {
            addCriterion("LIB_LEVEL >", value, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("LIB_LEVEL >=", value, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelLessThan(Integer value) {
            addCriterion("LIB_LEVEL <", value, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelLessThanOrEqualTo(Integer value) {
            addCriterion("LIB_LEVEL <=", value, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelIn(List<Integer> values) {
            addCriterion("LIB_LEVEL in", values, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelNotIn(List<Integer> values) {
            addCriterion("LIB_LEVEL not in", values, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelBetween(Integer value1, Integer value2) {
            addCriterion("LIB_LEVEL between", value1, value2, "libLevel");
            return (Criteria) this;
        }

        public Criteria andLibLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("LIB_LEVEL not between", value1, value2, "libLevel");
            return (Criteria) this;
        }

        public Criteria andUpCodeIsNull() {
            addCriterion("UP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andUpCodeIsNotNull() {
            addCriterion("UP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andUpCodeEqualTo(String value) {
            addCriterion("UP_CODE =", value, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeNotEqualTo(String value) {
            addCriterion("UP_CODE <>", value, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeGreaterThan(String value) {
            addCriterion("UP_CODE >", value, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeGreaterThanOrEqualTo(String value) {
            addCriterion("UP_CODE >=", value, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeLessThan(String value) {
            addCriterion("UP_CODE <", value, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeLessThanOrEqualTo(String value) {
            addCriterion("UP_CODE <=", value, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeLike(String value) {
            addCriterion("UP_CODE like", value, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeNotLike(String value) {
            addCriterion("UP_CODE not like", value, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeIn(List<String> values) {
            addCriterion("UP_CODE in", values, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeNotIn(List<String> values) {
            addCriterion("UP_CODE not in", values, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeBetween(String value1, String value2) {
            addCriterion("UP_CODE between", value1, value2, "upCode");
            return (Criteria) this;
        }

        public Criteria andUpCodeNotBetween(String value1, String value2) {
            addCriterion("UP_CODE not between", value1, value2, "upCode");
            return (Criteria) this;
        }

        public Criteria andLibDiffIsNull() {
            addCriterion("LIB_DIFF is null");
            return (Criteria) this;
        }

        public Criteria andLibDiffIsNotNull() {
            addCriterion("LIB_DIFF is not null");
            return (Criteria) this;
        }

        public Criteria andLibDiffEqualTo(String value) {
            addCriterion("LIB_DIFF =", value, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffNotEqualTo(String value) {
            addCriterion("LIB_DIFF <>", value, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffGreaterThan(String value) {
            addCriterion("LIB_DIFF >", value, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffGreaterThanOrEqualTo(String value) {
            addCriterion("LIB_DIFF >=", value, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffLessThan(String value) {
            addCriterion("LIB_DIFF <", value, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffLessThanOrEqualTo(String value) {
            addCriterion("LIB_DIFF <=", value, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffLike(String value) {
            addCriterion("LIB_DIFF like", value, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffNotLike(String value) {
            addCriterion("LIB_DIFF not like", value, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffIn(List<String> values) {
            addCriterion("LIB_DIFF in", values, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffNotIn(List<String> values) {
            addCriterion("LIB_DIFF not in", values, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffBetween(String value1, String value2) {
            addCriterion("LIB_DIFF between", value1, value2, "libDiff");
            return (Criteria) this;
        }

        public Criteria andLibDiffNotBetween(String value1, String value2) {
            addCriterion("LIB_DIFF not between", value1, value2, "libDiff");
            return (Criteria) this;
        }

        public Criteria andQuesNumIsNull() {
            addCriterion("QUES_NUM is null");
            return (Criteria) this;
        }

        public Criteria andQuesNumIsNotNull() {
            addCriterion("QUES_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andQuesNumEqualTo(Integer value) {
            addCriterion("QUES_NUM =", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotEqualTo(Integer value) {
            addCriterion("QUES_NUM <>", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumGreaterThan(Integer value) {
            addCriterion("QUES_NUM >", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUES_NUM >=", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumLessThan(Integer value) {
            addCriterion("QUES_NUM <", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumLessThanOrEqualTo(Integer value) {
            addCriterion("QUES_NUM <=", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumIn(List<Integer> values) {
            addCriterion("QUES_NUM in", values, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotIn(List<Integer> values) {
            addCriterion("QUES_NUM not in", values, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumBetween(Integer value1, Integer value2) {
            addCriterion("QUES_NUM between", value1, value2, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotBetween(Integer value1, Integer value2) {
            addCriterion("QUES_NUM not between", value1, value2, "quesNum");
            return (Criteria) this;
        }

        public Criteria andLibStatIsNull() {
            addCriterion("LIB_STAT is null");
            return (Criteria) this;
        }

        public Criteria andLibStatIsNotNull() {
            addCriterion("LIB_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andLibStatEqualTo(Integer value) {
            addCriterion("LIB_STAT =", value, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatNotEqualTo(Integer value) {
            addCriterion("LIB_STAT <>", value, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatGreaterThan(Integer value) {
            addCriterion("LIB_STAT >", value, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("LIB_STAT >=", value, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatLessThan(Integer value) {
            addCriterion("LIB_STAT <", value, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatLessThanOrEqualTo(Integer value) {
            addCriterion("LIB_STAT <=", value, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatIn(List<Integer> values) {
            addCriterion("LIB_STAT in", values, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatNotIn(List<Integer> values) {
            addCriterion("LIB_STAT not in", values, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatBetween(Integer value1, Integer value2) {
            addCriterion("LIB_STAT between", value1, value2, "libStat");
            return (Criteria) this;
        }

        public Criteria andLibStatNotBetween(Integer value1, Integer value2) {
            addCriterion("LIB_STAT not between", value1, value2, "libStat");
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