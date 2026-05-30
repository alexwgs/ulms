package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class QuesTempExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuesTempExample() {
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

        public Criteria andQuesNumIsNull() {
            addCriterion("QUES_NUM is null");
            return (Criteria) this;
        }

        public Criteria andQuesNumIsNotNull() {
            addCriterion("QUES_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andQuesNumEqualTo(Short value) {
            addCriterion("QUES_NUM =", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotEqualTo(Short value) {
            addCriterion("QUES_NUM <>", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumGreaterThan(Short value) {
            addCriterion("QUES_NUM >", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumGreaterThanOrEqualTo(Short value) {
            addCriterion("QUES_NUM >=", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumLessThan(Short value) {
            addCriterion("QUES_NUM <", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumLessThanOrEqualTo(Short value) {
            addCriterion("QUES_NUM <=", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumIn(List<Short> values) {
            addCriterion("QUES_NUM in", values, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotIn(List<Short> values) {
            addCriterion("QUES_NUM not in", values, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumBetween(Short value1, Short value2) {
            addCriterion("QUES_NUM between", value1, value2, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotBetween(Short value1, Short value2) {
            addCriterion("QUES_NUM not between", value1, value2, "quesNum");
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