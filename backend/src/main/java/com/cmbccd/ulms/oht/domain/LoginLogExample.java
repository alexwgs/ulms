package com.cmbccd.ulms.oht.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LoginLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LoginLogExample() {
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

        public Criteria andPloNameIsNull() {
            addCriterion("PLO_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPloNameIsNotNull() {
            addCriterion("PLO_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPloNameEqualTo(String value) {
            addCriterion("PLO_NAME =", value, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameNotEqualTo(String value) {
            addCriterion("PLO_NAME <>", value, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameGreaterThan(String value) {
            addCriterion("PLO_NAME >", value, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameGreaterThanOrEqualTo(String value) {
            addCriterion("PLO_NAME >=", value, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameLessThan(String value) {
            addCriterion("PLO_NAME <", value, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameLessThanOrEqualTo(String value) {
            addCriterion("PLO_NAME <=", value, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameLike(String value) {
            addCriterion("PLO_NAME like", value, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameNotLike(String value) {
            addCriterion("PLO_NAME not like", value, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameIn(List<String> values) {
            addCriterion("PLO_NAME in", values, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameNotIn(List<String> values) {
            addCriterion("PLO_NAME not in", values, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameBetween(String value1, String value2) {
            addCriterion("PLO_NAME between", value1, value2, "ploName");
            return (Criteria) this;
        }

        public Criteria andPloNameNotBetween(String value1, String value2) {
            addCriterion("PLO_NAME not between", value1, value2, "ploName");
            return (Criteria) this;
        }

        public Criteria andLineFloorIsNull() {
            addCriterion("LINE_FLOOR is null");
            return (Criteria) this;
        }

        public Criteria andLineFloorIsNotNull() {
            addCriterion("LINE_FLOOR is not null");
            return (Criteria) this;
        }

        public Criteria andLineFloorEqualTo(BigDecimal value) {
            addCriterion("LINE_FLOOR =", value, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorNotEqualTo(BigDecimal value) {
            addCriterion("LINE_FLOOR <>", value, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorGreaterThan(BigDecimal value) {
            addCriterion("LINE_FLOOR >", value, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LINE_FLOOR >=", value, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorLessThan(BigDecimal value) {
            addCriterion("LINE_FLOOR <", value, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LINE_FLOOR <=", value, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorIn(List<BigDecimal> values) {
            addCriterion("LINE_FLOOR in", values, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorNotIn(List<BigDecimal> values) {
            addCriterion("LINE_FLOOR not in", values, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LINE_FLOOR between", value1, value2, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andLineFloorNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LINE_FLOOR not between", value1, value2, "lineFloor");
            return (Criteria) this;
        }

        public Criteria andRoleCodeIsNull() {
            addCriterion("ROLE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRoleCodeIsNotNull() {
            addCriterion("ROLE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRoleCodeEqualTo(BigDecimal value) {
            addCriterion("ROLE_CODE =", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeNotEqualTo(BigDecimal value) {
            addCriterion("ROLE_CODE <>", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeGreaterThan(BigDecimal value) {
            addCriterion("ROLE_CODE >", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROLE_CODE >=", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeLessThan(BigDecimal value) {
            addCriterion("ROLE_CODE <", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROLE_CODE <=", value, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeIn(List<BigDecimal> values) {
            addCriterion("ROLE_CODE in", values, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeNotIn(List<BigDecimal> values) {
            addCriterion("ROLE_CODE not in", values, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROLE_CODE between", value1, value2, "roleCode");
            return (Criteria) this;
        }

        public Criteria andRoleCodeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROLE_CODE not between", value1, value2, "roleCode");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("IP is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("IP is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("IP =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("IP <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("IP >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("IP >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("IP <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("IP <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("IP like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("IP not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("IP in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("IP not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("IP between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("IP not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andExtnIsNull() {
            addCriterion("EXTN is null");
            return (Criteria) this;
        }

        public Criteria andExtnIsNotNull() {
            addCriterion("EXTN is not null");
            return (Criteria) this;
        }

        public Criteria andExtnEqualTo(String value) {
            addCriterion("EXTN =", value, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnNotEqualTo(String value) {
            addCriterion("EXTN <>", value, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnGreaterThan(String value) {
            addCriterion("EXTN >", value, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnGreaterThanOrEqualTo(String value) {
            addCriterion("EXTN >=", value, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnLessThan(String value) {
            addCriterion("EXTN <", value, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnLessThanOrEqualTo(String value) {
            addCriterion("EXTN <=", value, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnLike(String value) {
            addCriterion("EXTN like", value, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnNotLike(String value) {
            addCriterion("EXTN not like", value, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnIn(List<String> values) {
            addCriterion("EXTN in", values, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnNotIn(List<String> values) {
            addCriterion("EXTN not in", values, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnBetween(String value1, String value2) {
            addCriterion("EXTN between", value1, value2, "extn");
            return (Criteria) this;
        }

        public Criteria andExtnNotBetween(String value1, String value2) {
            addCriterion("EXTN not between", value1, value2, "extn");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(BigDecimal value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(BigDecimal value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(BigDecimal value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(BigDecimal value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<BigDecimal> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<BigDecimal> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDateTimeIsNull() {
            addCriterion("DATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDateTimeIsNotNull() {
            addCriterion("DATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDateTimeEqualTo(String value) {
            addCriterion("DATE_TIME =", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeNotEqualTo(String value) {
            addCriterion("DATE_TIME <>", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeGreaterThan(String value) {
            addCriterion("DATE_TIME >", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("DATE_TIME >=", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeLessThan(String value) {
            addCriterion("DATE_TIME <", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeLessThanOrEqualTo(String value) {
            addCriterion("DATE_TIME <=", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeLike(String value) {
            addCriterion("DATE_TIME like", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeNotLike(String value) {
            addCriterion("DATE_TIME not like", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeIn(List<String> values) {
            addCriterion("DATE_TIME in", values, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeNotIn(List<String> values) {
            addCriterion("DATE_TIME not in", values, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeBetween(String value1, String value2) {
            addCriterion("DATE_TIME between", value1, value2, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeNotBetween(String value1, String value2) {
            addCriterion("DATE_TIME not between", value1, value2, "dateTime");
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