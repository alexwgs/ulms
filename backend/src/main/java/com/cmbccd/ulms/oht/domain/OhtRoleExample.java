package com.cmbccd.ulms.oht.domain;

import java.util.ArrayList;
import java.util.List;

public class OhtRoleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OhtRoleExample() {
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

        public Criteria andRoleCodeIsNull() {
            addCriterion("ROLE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRoleCodeIsNotNull() {
            addCriterion("ROLE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRoleCodeEqualTo(Integer value) {
            addCriterion("ROLE_CODE =", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeNotEqualTo(Integer value) {
            addCriterion("ROLE_CODE <>", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeGreaterThan(Integer value) {
            addCriterion("ROLE_CODE >", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ROLE_CODE >=", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeLessThan(Integer value) {
            addCriterion("ROLE_CODE <", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeLessThanOrEqualTo(Integer value) {
            addCriterion("ROLE_CODE <=", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeIn(List<Integer> values) {
            addCriterion("ROLE_CODE in", values, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeNotIn(List<Integer> values) {
            addCriterion("ROLE_CODE not in", values, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeBetween(Integer value1, Integer value2) {
            addCriterion("ROLE_CODE between", value1, value2, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("ROLE_CODE not between", value1, value2, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleNameIsNull() {
            addCriterion("ROLE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRoleNameIsNotNull() {
            addCriterion("ROLE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andRoleNameEqualTo(String value) {
            addCriterion("ROLE_NAME =", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotEqualTo(String value) {
            addCriterion("ROLE_NAME <>", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameGreaterThan(String value) {
            addCriterion("ROLE_NAME >", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameGreaterThanOrEqualTo(String value) {
            addCriterion("ROLE_NAME >=", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLessThan(String value) {
            addCriterion("ROLE_NAME <", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLessThanOrEqualTo(String value) {
            addCriterion("ROLE_NAME <=", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLike(String value) {
            addCriterion("ROLE_NAME like", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotLike(String value) {
            addCriterion("ROLE_NAME not like", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameIn(List<String> values) {
            addCriterion("ROLE_NAME in", values, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotIn(List<String> values) {
            addCriterion("ROLE_NAME not in", values, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameBetween(String value1, String value2) {
            addCriterion("ROLE_NAME between", value1, value2, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotBetween(String value1, String value2) {
            addCriterion("ROLE_NAME not between", value1, value2, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleTypeIsNull() {
            addCriterion("ROLE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRoleTypeIsNotNull() {
            addCriterion("ROLE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRoleTypeEqualTo(Integer value) {
            addCriterion("ROLE_TYPE =", value, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeNotEqualTo(Integer value) {
            addCriterion("ROLE_TYPE <>", value, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeGreaterThan(Integer value) {
            addCriterion("ROLE_TYPE >", value, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ROLE_TYPE >=", value, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeLessThan(Integer value) {
            addCriterion("ROLE_TYPE <", value, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ROLE_TYPE <=", value, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeIn(List<Integer> values) {
            addCriterion("ROLE_TYPE in", values, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeNotIn(List<Integer> values) {
            addCriterion("ROLE_TYPE not in", values, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeBetween(Integer value1, Integer value2) {
            addCriterion("ROLE_TYPE between", value1, value2, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ROLE_TYPE not between", value1, value2, "roleType");
            return (Criteria) this;
        }

        public Criteria andRoleStatIsNull() {
            addCriterion("ROLE_STAT is null");
            return (Criteria) this;
        }

        public Criteria andRoleStatIsNotNull() {
            addCriterion("ROLE_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andRoleStatEqualTo(Integer value) {
            addCriterion("ROLE_STAT =", value, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatNotEqualTo(Integer value) {
            addCriterion("ROLE_STAT <>", value, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatGreaterThan(Integer value) {
            addCriterion("ROLE_STAT >", value, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("ROLE_STAT >=", value, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatLessThan(Integer value) {
            addCriterion("ROLE_STAT <", value, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatLessThanOrEqualTo(Integer value) {
            addCriterion("ROLE_STAT <=", value, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatIn(List<Integer> values) {
            addCriterion("ROLE_STAT in", values, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatNotIn(List<Integer> values) {
            addCriterion("ROLE_STAT not in", values, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatBetween(Integer value1, Integer value2) {
            addCriterion("ROLE_STAT between", value1, value2, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleStatNotBetween(Integer value1, Integer value2) {
            addCriterion("ROLE_STAT not between", value1, value2, "roleStat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatIsNull() {
            addCriterion("ROLE_RELAT is null");
            return (Criteria) this;
        }

        public Criteria andRoleRelatIsNotNull() {
            addCriterion("ROLE_RELAT is not null");
            return (Criteria) this;
        }

        public Criteria andRoleRelatEqualTo(String value) {
            addCriterion("ROLE_RELAT =", value, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatNotEqualTo(String value) {
            addCriterion("ROLE_RELAT <>", value, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatGreaterThan(String value) {
            addCriterion("ROLE_RELAT >", value, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatGreaterThanOrEqualTo(String value) {
            addCriterion("ROLE_RELAT >=", value, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatLessThan(String value) {
            addCriterion("ROLE_RELAT <", value, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatLessThanOrEqualTo(String value) {
            addCriterion("ROLE_RELAT <=", value, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatLike(String value) {
            addCriterion("ROLE_RELAT like", value, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatNotLike(String value) {
            addCriterion("ROLE_RELAT not like", value, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatIn(List<String> values) {
            addCriterion("ROLE_RELAT in", values, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatNotIn(List<String> values) {
            addCriterion("ROLE_RELAT not in", values, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatBetween(String value1, String value2) {
            addCriterion("ROLE_RELAT between", value1, value2, "roleRelat");
            return (Criteria) this;
        }

        public Criteria andRoleRelatNotBetween(String value1, String value2) {
            addCriterion("ROLE_RELAT not between", value1, value2, "roleRelat");
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