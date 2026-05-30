package com.cmbccd.ulms.sys.domain;

import java.util.ArrayList;
import java.util.List;

public class DepartmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DepartmentExample() {
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

        public Criteria andDeptNumIsNull() {
            addCriterion("DEPT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andDeptNumIsNotNull() {
            addCriterion("DEPT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNumEqualTo(String value) {
            addCriterion("DEPT_NUM =", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumNotEqualTo(String value) {
            addCriterion("DEPT_NUM <>", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumGreaterThan(String value) {
            addCriterion("DEPT_NUM >", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumGreaterThanOrEqualTo(String value) {
            addCriterion("DEPT_NUM >=", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumLessThan(String value) {
            addCriterion("DEPT_NUM <", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumLessThanOrEqualTo(String value) {
            addCriterion("DEPT_NUM <=", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumLike(String value) {
            addCriterion("DEPT_NUM like", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumNotLike(String value) {
            addCriterion("DEPT_NUM not like", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumIn(List<String> values) {
            addCriterion("DEPT_NUM in", values, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumNotIn(List<String> values) {
            addCriterion("DEPT_NUM not in", values, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumBetween(String value1, String value2) {
            addCriterion("DEPT_NUM between", value1, value2, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumNotBetween(String value1, String value2) {
            addCriterion("DEPT_NUM not between", value1, value2, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNull() {
            addCriterion("DEPT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNotNull() {
            addCriterion("DEPT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNameEqualTo(String value) {
            addCriterion("DEPT_NAME =", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotEqualTo(String value) {
            addCriterion("DEPT_NAME <>", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThan(String value) {
            addCriterion("DEPT_NAME >", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("DEPT_NAME >=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThan(String value) {
            addCriterion("DEPT_NAME <", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThanOrEqualTo(String value) {
            addCriterion("DEPT_NAME <=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLike(String value) {
            addCriterion("DEPT_NAME like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotLike(String value) {
            addCriterion("DEPT_NAME not like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameIn(List<String> values) {
            addCriterion("DEPT_NAME in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotIn(List<String> values) {
            addCriterion("DEPT_NAME not in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameBetween(String value1, String value2) {
            addCriterion("DEPT_NAME between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotBetween(String value1, String value2) {
            addCriterion("DEPT_NAME not between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andEmpSumIsNull() {
            addCriterion("EMP_SUM is null");
            return (Criteria) this;
        }

        public Criteria andEmpSumIsNotNull() {
            addCriterion("EMP_SUM is not null");
            return (Criteria) this;
        }

        public Criteria andEmpSumEqualTo(String value) {
            addCriterion("EMP_SUM =", value, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumNotEqualTo(String value) {
            addCriterion("EMP_SUM <>", value, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumGreaterThan(String value) {
            addCriterion("EMP_SUM >", value, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumGreaterThanOrEqualTo(String value) {
            addCriterion("EMP_SUM >=", value, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumLessThan(String value) {
            addCriterion("EMP_SUM <", value, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumLessThanOrEqualTo(String value) {
            addCriterion("EMP_SUM <=", value, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumLike(String value) {
            addCriterion("EMP_SUM like", value, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumNotLike(String value) {
            addCriterion("EMP_SUM not like", value, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumIn(List<String> values) {
            addCriterion("EMP_SUM in", values, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumNotIn(List<String> values) {
            addCriterion("EMP_SUM not in", values, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumBetween(String value1, String value2) {
            addCriterion("EMP_SUM between", value1, value2, "empSum");
            return (Criteria) this;
        }

        public Criteria andEmpSumNotBetween(String value1, String value2) {
            addCriterion("EMP_SUM not between", value1, value2, "empSum");
            return (Criteria) this;
        }

        public Criteria andUpDeptIsNull() {
            addCriterion("UP_DEPT is null");
            return (Criteria) this;
        }

        public Criteria andUpDeptIsNotNull() {
            addCriterion("UP_DEPT is not null");
            return (Criteria) this;
        }

        public Criteria andUpDeptEqualTo(String value) {
            addCriterion("UP_DEPT =", value, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptNotEqualTo(String value) {
            addCriterion("UP_DEPT <>", value, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptGreaterThan(String value) {
            addCriterion("UP_DEPT >", value, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptGreaterThanOrEqualTo(String value) {
            addCriterion("UP_DEPT >=", value, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptLessThan(String value) {
            addCriterion("UP_DEPT <", value, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptLessThanOrEqualTo(String value) {
            addCriterion("UP_DEPT <=", value, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptLike(String value) {
            addCriterion("UP_DEPT like", value, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptNotLike(String value) {
            addCriterion("UP_DEPT not like", value, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptIn(List<String> values) {
            addCriterion("UP_DEPT in", values, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptNotIn(List<String> values) {
            addCriterion("UP_DEPT not in", values, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptBetween(String value1, String value2) {
            addCriterion("UP_DEPT between", value1, value2, "upDept");
            return (Criteria) this;
        }

        public Criteria andUpDeptNotBetween(String value1, String value2) {
            addCriterion("UP_DEPT not between", value1, value2, "upDept");
            return (Criteria) this;
        }

        public Criteria andOrganIsNull() {
            addCriterion("ORGAN is null");
            return (Criteria) this;
        }

        public Criteria andOrganIsNotNull() {
            addCriterion("ORGAN is not null");
            return (Criteria) this;
        }

        public Criteria andOrganEqualTo(String value) {
            addCriterion("ORGAN =", value, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganNotEqualTo(String value) {
            addCriterion("ORGAN <>", value, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganGreaterThan(String value) {
            addCriterion("ORGAN >", value, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganGreaterThanOrEqualTo(String value) {
            addCriterion("ORGAN >=", value, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganLessThan(String value) {
            addCriterion("ORGAN <", value, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganLessThanOrEqualTo(String value) {
            addCriterion("ORGAN <=", value, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganLike(String value) {
            addCriterion("ORGAN like", value, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganNotLike(String value) {
            addCriterion("ORGAN not like", value, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganIn(List<String> values) {
            addCriterion("ORGAN in", values, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganNotIn(List<String> values) {
            addCriterion("ORGAN not in", values, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganBetween(String value1, String value2) {
            addCriterion("ORGAN between", value1, value2, "organ");
            return (Criteria) this;
        }

        public Criteria andOrganNotBetween(String value1, String value2) {
            addCriterion("ORGAN not between", value1, value2, "organ");
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