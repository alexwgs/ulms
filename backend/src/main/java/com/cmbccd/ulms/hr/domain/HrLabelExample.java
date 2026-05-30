package com.cmbccd.ulms.hr.domain;

import java.util.ArrayList;
import java.util.List;

public class HrLabelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HrLabelExample() {
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPerfMonthIsNull() {
            addCriterion("PERF_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andPerfMonthIsNotNull() {
            addCriterion("PERF_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andPerfMonthEqualTo(String value) {
            addCriterion("PERF_MONTH =", value, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthNotEqualTo(String value) {
            addCriterion("PERF_MONTH <>", value, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthGreaterThan(String value) {
            addCriterion("PERF_MONTH >", value, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthGreaterThanOrEqualTo(String value) {
            addCriterion("PERF_MONTH >=", value, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthLessThan(String value) {
            addCriterion("PERF_MONTH <", value, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthLessThanOrEqualTo(String value) {
            addCriterion("PERF_MONTH <=", value, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthLike(String value) {
            addCriterion("PERF_MONTH like", value, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthNotLike(String value) {
            addCriterion("PERF_MONTH not like", value, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthIn(List<String> values) {
            addCriterion("PERF_MONTH in", values, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthNotIn(List<String> values) {
            addCriterion("PERF_MONTH not in", values, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthBetween(String value1, String value2) {
            addCriterion("PERF_MONTH between", value1, value2, "perfMonth");
            return (Criteria) this;
        }

        public Criteria andPerfMonthNotBetween(String value1, String value2) {
            addCriterion("PERF_MONTH not between", value1, value2, "perfMonth");
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

        public Criteria andLabelNameIsNull() {
            addCriterion("LABEL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLabelNameIsNotNull() {
            addCriterion("LABEL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLabelNameEqualTo(String value) {
            addCriterion("LABEL_NAME =", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotEqualTo(String value) {
            addCriterion("LABEL_NAME <>", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThan(String value) {
            addCriterion("LABEL_NAME >", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThanOrEqualTo(String value) {
            addCriterion("LABEL_NAME >=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThan(String value) {
            addCriterion("LABEL_NAME <", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThanOrEqualTo(String value) {
            addCriterion("LABEL_NAME <=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLike(String value) {
            addCriterion("LABEL_NAME like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotLike(String value) {
            addCriterion("LABEL_NAME not like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameIn(List<String> values) {
            addCriterion("LABEL_NAME in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotIn(List<String> values) {
            addCriterion("LABEL_NAME not in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameBetween(String value1, String value2) {
            addCriterion("LABEL_NAME between", value1, value2, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotBetween(String value1, String value2) {
            addCriterion("LABEL_NAME not between", value1, value2, "labelName");
            return (Criteria) this;
        }

        public Criteria andHumFlagIsNull() {
            addCriterion("HUM_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andHumFlagIsNotNull() {
            addCriterion("HUM_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andHumFlagEqualTo(Short value) {
            addCriterion("HUM_FLAG =", value, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagNotEqualTo(Short value) {
            addCriterion("HUM_FLAG <>", value, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagGreaterThan(Short value) {
            addCriterion("HUM_FLAG >", value, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagGreaterThanOrEqualTo(Short value) {
            addCriterion("HUM_FLAG >=", value, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagLessThan(Short value) {
            addCriterion("HUM_FLAG <", value, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagLessThanOrEqualTo(Short value) {
            addCriterion("HUM_FLAG <=", value, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagIn(List<Short> values) {
            addCriterion("HUM_FLAG in", values, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagNotIn(List<Short> values) {
            addCriterion("HUM_FLAG not in", values, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagBetween(Short value1, Short value2) {
            addCriterion("HUM_FLAG between", value1, value2, "humFlag");
            return (Criteria) this;
        }

        public Criteria andHumFlagNotBetween(Short value1, Short value2) {
            addCriterion("HUM_FLAG not between", value1, value2, "humFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagIsNull() {
            addCriterion("GROUP_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andGroupFlagIsNotNull() {
            addCriterion("GROUP_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andGroupFlagEqualTo(Short value) {
            addCriterion("GROUP_FLAG =", value, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagNotEqualTo(Short value) {
            addCriterion("GROUP_FLAG <>", value, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagGreaterThan(Short value) {
            addCriterion("GROUP_FLAG >", value, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagGreaterThanOrEqualTo(Short value) {
            addCriterion("GROUP_FLAG >=", value, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagLessThan(Short value) {
            addCriterion("GROUP_FLAG <", value, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagLessThanOrEqualTo(Short value) {
            addCriterion("GROUP_FLAG <=", value, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagIn(List<Short> values) {
            addCriterion("GROUP_FLAG in", values, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagNotIn(List<Short> values) {
            addCriterion("GROUP_FLAG not in", values, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagBetween(Short value1, Short value2) {
            addCriterion("GROUP_FLAG between", value1, value2, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andGroupFlagNotBetween(Short value1, Short value2) {
            addCriterion("GROUP_FLAG not between", value1, value2, "groupFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagIsNull() {
            addCriterion("DEPT_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDeptFlagIsNotNull() {
            addCriterion("DEPT_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDeptFlagEqualTo(Short value) {
            addCriterion("DEPT_FLAG =", value, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagNotEqualTo(Short value) {
            addCriterion("DEPT_FLAG <>", value, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagGreaterThan(Short value) {
            addCriterion("DEPT_FLAG >", value, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagGreaterThanOrEqualTo(Short value) {
            addCriterion("DEPT_FLAG >=", value, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagLessThan(Short value) {
            addCriterion("DEPT_FLAG <", value, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagLessThanOrEqualTo(Short value) {
            addCriterion("DEPT_FLAG <=", value, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagIn(List<Short> values) {
            addCriterion("DEPT_FLAG in", values, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagNotIn(List<Short> values) {
            addCriterion("DEPT_FLAG not in", values, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagBetween(Short value1, Short value2) {
            addCriterion("DEPT_FLAG between", value1, value2, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andDeptFlagNotBetween(Short value1, Short value2) {
            addCriterion("DEPT_FLAG not between", value1, value2, "deptFlag");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("MEMO is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("MEMO =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("MEMO <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("MEMO >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("MEMO >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("MEMO <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("MEMO <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("MEMO like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("MEMO not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("MEMO in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("MEMO not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("MEMO between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("MEMO not between", value1, value2, "memo");
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