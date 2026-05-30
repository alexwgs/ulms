package com.cmbccd.ulms.hr.domain;

import java.util.ArrayList;
import java.util.List;

public class MoodConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MoodConfigExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMoodInfoIsNull() {
            addCriterion("mood_info is null");
            return (Criteria) this;
        }

        public Criteria andMoodInfoIsNotNull() {
            addCriterion("mood_info is not null");
            return (Criteria) this;
        }

        public Criteria andMoodInfoEqualTo(String value) {
            addCriterion("mood_info =", value, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoNotEqualTo(String value) {
            addCriterion("mood_info <>", value, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoGreaterThan(String value) {
            addCriterion("mood_info >", value, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoGreaterThanOrEqualTo(String value) {
            addCriterion("mood_info >=", value, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoLessThan(String value) {
            addCriterion("mood_info <", value, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoLessThanOrEqualTo(String value) {
            addCriterion("mood_info <=", value, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoLike(String value) {
            addCriterion("mood_info like", value, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoNotLike(String value) {
            addCriterion("mood_info not like", value, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoIn(List<String> values) {
            addCriterion("mood_info in", values, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoNotIn(List<String> values) {
            addCriterion("mood_info not in", values, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoBetween(String value1, String value2) {
            addCriterion("mood_info between", value1, value2, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodInfoNotBetween(String value1, String value2) {
            addCriterion("mood_info not between", value1, value2, "moodInfo");
            return (Criteria) this;
        }

        public Criteria andMoodTypeIsNull() {
            addCriterion("mood_type is null");
            return (Criteria) this;
        }

        public Criteria andMoodTypeIsNotNull() {
            addCriterion("mood_type is not null");
            return (Criteria) this;
        }

        public Criteria andMoodTypeEqualTo(String value) {
            addCriterion("mood_type =", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeNotEqualTo(String value) {
            addCriterion("mood_type <>", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeGreaterThan(String value) {
            addCriterion("mood_type >", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeGreaterThanOrEqualTo(String value) {
            addCriterion("mood_type >=", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeLessThan(String value) {
            addCriterion("mood_type <", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeLessThanOrEqualTo(String value) {
            addCriterion("mood_type <=", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeLike(String value) {
            addCriterion("mood_type like", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeNotLike(String value) {
            addCriterion("mood_type not like", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeIn(List<String> values) {
            addCriterion("mood_type in", values, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeNotIn(List<String> values) {
            addCriterion("mood_type not in", values, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeBetween(String value1, String value2) {
            addCriterion("mood_type between", value1, value2, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeNotBetween(String value1, String value2) {
            addCriterion("mood_type not between", value1, value2, "moodType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
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