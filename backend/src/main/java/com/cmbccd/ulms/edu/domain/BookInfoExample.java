package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class BookInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BookInfoExample() {
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

        public Criteria andInfoCodeIsNull() {
            addCriterion("INFO_CODE is null");
            return (Criteria) this;
        }

        public Criteria andInfoCodeIsNotNull() {
            addCriterion("INFO_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andInfoCodeEqualTo(String value) {
            addCriterion("INFO_CODE =", value, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeNotEqualTo(String value) {
            addCriterion("INFO_CODE <>", value, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeGreaterThan(String value) {
            addCriterion("INFO_CODE >", value, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeGreaterThanOrEqualTo(String value) {
            addCriterion("INFO_CODE >=", value, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeLessThan(String value) {
            addCriterion("INFO_CODE <", value, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeLessThanOrEqualTo(String value) {
            addCriterion("INFO_CODE <=", value, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeLike(String value) {
            addCriterion("INFO_CODE like", value, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeNotLike(String value) {
            addCriterion("INFO_CODE not like", value, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeIn(List<String> values) {
            addCriterion("INFO_CODE in", values, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeNotIn(List<String> values) {
            addCriterion("INFO_CODE not in", values, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeBetween(String value1, String value2) {
            addCriterion("INFO_CODE between", value1, value2, "infoCode");
            return (Criteria) this;
        }

        public Criteria andInfoCodeNotBetween(String value1, String value2) {
            addCriterion("INFO_CODE not between", value1, value2, "infoCode");
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

        public Criteria andBookCodeIsNull() {
            addCriterion("BOOK_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBookCodeIsNotNull() {
            addCriterion("BOOK_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBookCodeEqualTo(String value) {
            addCriterion("BOOK_CODE =", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotEqualTo(String value) {
            addCriterion("BOOK_CODE <>", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeGreaterThan(String value) {
            addCriterion("BOOK_CODE >", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BOOK_CODE >=", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLessThan(String value) {
            addCriterion("BOOK_CODE <", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLessThanOrEqualTo(String value) {
            addCriterion("BOOK_CODE <=", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLike(String value) {
            addCriterion("BOOK_CODE like", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotLike(String value) {
            addCriterion("BOOK_CODE not like", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeIn(List<String> values) {
            addCriterion("BOOK_CODE in", values, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotIn(List<String> values) {
            addCriterion("BOOK_CODE not in", values, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeBetween(String value1, String value2) {
            addCriterion("BOOK_CODE between", value1, value2, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotBetween(String value1, String value2) {
            addCriterion("BOOK_CODE not between", value1, value2, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookTypeIsNull() {
            addCriterion("BOOK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBookTypeIsNotNull() {
            addCriterion("BOOK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBookTypeEqualTo(Integer value) {
            addCriterion("BOOK_TYPE =", value, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeNotEqualTo(Integer value) {
            addCriterion("BOOK_TYPE <>", value, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeGreaterThan(Integer value) {
            addCriterion("BOOK_TYPE >", value, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("BOOK_TYPE >=", value, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeLessThan(Integer value) {
            addCriterion("BOOK_TYPE <", value, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeLessThanOrEqualTo(Integer value) {
            addCriterion("BOOK_TYPE <=", value, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeIn(List<Integer> values) {
            addCriterion("BOOK_TYPE in", values, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeNotIn(List<Integer> values) {
            addCriterion("BOOK_TYPE not in", values, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeBetween(Integer value1, Integer value2) {
            addCriterion("BOOK_TYPE between", value1, value2, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("BOOK_TYPE not between", value1, value2, "bookType");
            return (Criteria) this;
        }

        public Criteria andBookDateIsNull() {
            addCriterion("BOOK_DATE is null");
            return (Criteria) this;
        }

        public Criteria andBookDateIsNotNull() {
            addCriterion("BOOK_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andBookDateEqualTo(String value) {
            addCriterion("BOOK_DATE =", value, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateNotEqualTo(String value) {
            addCriterion("BOOK_DATE <>", value, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateGreaterThan(String value) {
            addCriterion("BOOK_DATE >", value, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateGreaterThanOrEqualTo(String value) {
            addCriterion("BOOK_DATE >=", value, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateLessThan(String value) {
            addCriterion("BOOK_DATE <", value, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateLessThanOrEqualTo(String value) {
            addCriterion("BOOK_DATE <=", value, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateLike(String value) {
            addCriterion("BOOK_DATE like", value, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateNotLike(String value) {
            addCriterion("BOOK_DATE not like", value, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateIn(List<String> values) {
            addCriterion("BOOK_DATE in", values, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateNotIn(List<String> values) {
            addCriterion("BOOK_DATE not in", values, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateBetween(String value1, String value2) {
            addCriterion("BOOK_DATE between", value1, value2, "bookDate");
            return (Criteria) this;
        }

        public Criteria andBookDateNotBetween(String value1, String value2) {
            addCriterion("BOOK_DATE not between", value1, value2, "bookDate");
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