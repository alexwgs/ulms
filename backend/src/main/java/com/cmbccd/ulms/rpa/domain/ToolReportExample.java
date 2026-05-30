package com.cmbccd.ulms.rpa.domain;

import java.util.ArrayList;
import java.util.List;

public class ToolReportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ToolReportExample() {
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

        public Criteria andToolIdIsNull() {
            addCriterion("TOOL_ID is null");
            return (Criteria) this;
        }

        public Criteria andToolIdIsNotNull() {
            addCriterion("TOOL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andToolIdEqualTo(String value) {
            addCriterion("TOOL_ID =", value, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdNotEqualTo(String value) {
            addCriterion("TOOL_ID <>", value, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdGreaterThan(String value) {
            addCriterion("TOOL_ID >", value, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdGreaterThanOrEqualTo(String value) {
            addCriterion("TOOL_ID >=", value, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdLessThan(String value) {
            addCriterion("TOOL_ID <", value, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdLessThanOrEqualTo(String value) {
            addCriterion("TOOL_ID <=", value, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdLike(String value) {
            addCriterion("TOOL_ID like", value, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdNotLike(String value) {
            addCriterion("TOOL_ID not like", value, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdIn(List<String> values) {
            addCriterion("TOOL_ID in", values, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdNotIn(List<String> values) {
            addCriterion("TOOL_ID not in", values, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdBetween(String value1, String value2) {
            addCriterion("TOOL_ID between", value1, value2, "toolId");
            return (Criteria) this;
        }

        public Criteria andToolIdNotBetween(String value1, String value2) {
            addCriterion("TOOL_ID not between", value1, value2, "toolId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andRunTimeIsNull() {
            addCriterion("RUN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andRunTimeIsNotNull() {
            addCriterion("RUN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andRunTimeEqualTo(String value) {
            addCriterion("RUN_TIME =", value, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeNotEqualTo(String value) {
            addCriterion("RUN_TIME <>", value, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeGreaterThan(String value) {
            addCriterion("RUN_TIME >", value, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeGreaterThanOrEqualTo(String value) {
            addCriterion("RUN_TIME >=", value, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeLessThan(String value) {
            addCriterion("RUN_TIME <", value, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeLessThanOrEqualTo(String value) {
            addCriterion("RUN_TIME <=", value, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeLike(String value) {
            addCriterion("RUN_TIME like", value, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeNotLike(String value) {
            addCriterion("RUN_TIME not like", value, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeIn(List<String> values) {
            addCriterion("RUN_TIME in", values, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeNotIn(List<String> values) {
            addCriterion("RUN_TIME not in", values, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeBetween(String value1, String value2) {
            addCriterion("RUN_TIME between", value1, value2, "runTime");
            return (Criteria) this;
        }

        public Criteria andRunTimeNotBetween(String value1, String value2) {
            addCriterion("RUN_TIME not between", value1, value2, "runTime");
            return (Criteria) this;
        }

        public Criteria andReturnCodeIsNull() {
            addCriterion("RETURN_CODE is null");
            return (Criteria) this;
        }

        public Criteria andReturnCodeIsNotNull() {
            addCriterion("RETURN_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andReturnCodeEqualTo(String value) {
            addCriterion("RETURN_CODE =", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeNotEqualTo(String value) {
            addCriterion("RETURN_CODE <>", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeGreaterThan(String value) {
            addCriterion("RETURN_CODE >", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_CODE >=", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeLessThan(String value) {
            addCriterion("RETURN_CODE <", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeLessThanOrEqualTo(String value) {
            addCriterion("RETURN_CODE <=", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeLike(String value) {
            addCriterion("RETURN_CODE like", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeNotLike(String value) {
            addCriterion("RETURN_CODE not like", value, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeIn(List<String> values) {
            addCriterion("RETURN_CODE in", values, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeNotIn(List<String> values) {
            addCriterion("RETURN_CODE not in", values, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeBetween(String value1, String value2) {
            addCriterion("RETURN_CODE between", value1, value2, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnCodeNotBetween(String value1, String value2) {
            addCriterion("RETURN_CODE not between", value1, value2, "returnCode");
            return (Criteria) this;
        }

        public Criteria andReturnMessageIsNull() {
            addCriterion("RETURN_MESSAGE is null");
            return (Criteria) this;
        }

        public Criteria andReturnMessageIsNotNull() {
            addCriterion("RETURN_MESSAGE is not null");
            return (Criteria) this;
        }

        public Criteria andReturnMessageEqualTo(String value) {
            addCriterion("RETURN_MESSAGE =", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageNotEqualTo(String value) {
            addCriterion("RETURN_MESSAGE <>", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageGreaterThan(String value) {
            addCriterion("RETURN_MESSAGE >", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_MESSAGE >=", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageLessThan(String value) {
            addCriterion("RETURN_MESSAGE <", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageLessThanOrEqualTo(String value) {
            addCriterion("RETURN_MESSAGE <=", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageLike(String value) {
            addCriterion("RETURN_MESSAGE like", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageNotLike(String value) {
            addCriterion("RETURN_MESSAGE not like", value, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageIn(List<String> values) {
            addCriterion("RETURN_MESSAGE in", values, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageNotIn(List<String> values) {
            addCriterion("RETURN_MESSAGE not in", values, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageBetween(String value1, String value2) {
            addCriterion("RETURN_MESSAGE between", value1, value2, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnMessageNotBetween(String value1, String value2) {
            addCriterion("RETURN_MESSAGE not between", value1, value2, "returnMessage");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNull() {
            addCriterion("RETURN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNotNull() {
            addCriterion("RETURN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeEqualTo(String value) {
            addCriterion("RETURN_TIME =", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotEqualTo(String value) {
            addCriterion("RETURN_TIME <>", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThan(String value) {
            addCriterion("RETURN_TIME >", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_TIME >=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThan(String value) {
            addCriterion("RETURN_TIME <", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThanOrEqualTo(String value) {
            addCriterion("RETURN_TIME <=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLike(String value) {
            addCriterion("RETURN_TIME like", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotLike(String value) {
            addCriterion("RETURN_TIME not like", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIn(List<String> values) {
            addCriterion("RETURN_TIME in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotIn(List<String> values) {
            addCriterion("RETURN_TIME not in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeBetween(String value1, String value2) {
            addCriterion("RETURN_TIME between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotBetween(String value1, String value2) {
            addCriterion("RETURN_TIME not between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andRunParamIsNull() {
            addCriterion("RUN_PARAM is null");
            return (Criteria) this;
        }

        public Criteria andRunParamIsNotNull() {
            addCriterion("RUN_PARAM is not null");
            return (Criteria) this;
        }

        public Criteria andRunParamEqualTo(String value) {
            addCriterion("RUN_PARAM =", value, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamNotEqualTo(String value) {
            addCriterion("RUN_PARAM <>", value, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamGreaterThan(String value) {
            addCriterion("RUN_PARAM >", value, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamGreaterThanOrEqualTo(String value) {
            addCriterion("RUN_PARAM >=", value, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamLessThan(String value) {
            addCriterion("RUN_PARAM <", value, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamLessThanOrEqualTo(String value) {
            addCriterion("RUN_PARAM <=", value, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamLike(String value) {
            addCriterion("RUN_PARAM like", value, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamNotLike(String value) {
            addCriterion("RUN_PARAM not like", value, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamIn(List<String> values) {
            addCriterion("RUN_PARAM in", values, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamNotIn(List<String> values) {
            addCriterion("RUN_PARAM not in", values, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamBetween(String value1, String value2) {
            addCriterion("RUN_PARAM between", value1, value2, "runParam");
            return (Criteria) this;
        }

        public Criteria andRunParamNotBetween(String value1, String value2) {
            addCriterion("RUN_PARAM not between", value1, value2, "runParam");
            return (Criteria) this;
        }

        public Criteria andToolNameIsNull() {
            addCriterion("TOOL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andToolNameIsNotNull() {
            addCriterion("TOOL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andToolNameEqualTo(String value) {
            addCriterion("TOOL_NAME =", value, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameNotEqualTo(String value) {
            addCriterion("TOOL_NAME <>", value, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameGreaterThan(String value) {
            addCriterion("TOOL_NAME >", value, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameGreaterThanOrEqualTo(String value) {
            addCriterion("TOOL_NAME >=", value, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameLessThan(String value) {
            addCriterion("TOOL_NAME <", value, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameLessThanOrEqualTo(String value) {
            addCriterion("TOOL_NAME <=", value, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameLike(String value) {
            addCriterion("TOOL_NAME like", value, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameNotLike(String value) {
            addCriterion("TOOL_NAME not like", value, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameIn(List<String> values) {
            addCriterion("TOOL_NAME in", values, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameNotIn(List<String> values) {
            addCriterion("TOOL_NAME not in", values, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameBetween(String value1, String value2) {
            addCriterion("TOOL_NAME between", value1, value2, "toolName");
            return (Criteria) this;
        }

        public Criteria andToolNameNotBetween(String value1, String value2) {
            addCriterion("TOOL_NAME not between", value1, value2, "toolName");
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