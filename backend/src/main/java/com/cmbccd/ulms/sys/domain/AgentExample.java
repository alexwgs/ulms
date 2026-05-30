package com.cmbccd.ulms.sys.domain;

import java.util.ArrayList;
import java.util.List;

public class AgentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AgentExample() {
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

        public Criteria andAgentNumIsNull() {
            addCriterion("AGENT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAgentNumIsNotNull() {
            addCriterion("AGENT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNumEqualTo(String value) {
            addCriterion("AGENT_NUM =", value, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumNotEqualTo(String value) {
            addCriterion("AGENT_NUM <>", value, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumGreaterThan(String value) {
            addCriterion("AGENT_NUM >", value, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_NUM >=", value, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumLessThan(String value) {
            addCriterion("AGENT_NUM <", value, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumLessThanOrEqualTo(String value) {
            addCriterion("AGENT_NUM <=", value, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumLike(String value) {
            addCriterion("AGENT_NUM like", value, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumNotLike(String value) {
            addCriterion("AGENT_NUM not like", value, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumIn(List<String> values) {
            addCriterion("AGENT_NUM in", values, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumNotIn(List<String> values) {
            addCriterion("AGENT_NUM not in", values, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumBetween(String value1, String value2) {
            addCriterion("AGENT_NUM between", value1, value2, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNumNotBetween(String value1, String value2) {
            addCriterion("AGENT_NUM not between", value1, value2, "agentNum");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNull() {
            addCriterion("AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("AGENT_NAME =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("AGENT_NAME <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("AGENT_NAME >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("AGENT_NAME <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("AGENT_NAME like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("AGENT_NAME not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("AGENT_NAME in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("AGENT_NAME not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("AGENT_NAME between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("AGENT_NAME not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andPloStatusIsNull() {
            addCriterion("PLO_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPloStatusIsNotNull() {
            addCriterion("PLO_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPloStatusEqualTo(String value) {
            addCriterion("PLO_STATUS =", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusNotEqualTo(String value) {
            addCriterion("PLO_STATUS <>", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusGreaterThan(String value) {
            addCriterion("PLO_STATUS >", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusGreaterThanOrEqualTo(String value) {
            addCriterion("PLO_STATUS >=", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusLessThan(String value) {
            addCriterion("PLO_STATUS <", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusLessThanOrEqualTo(String value) {
            addCriterion("PLO_STATUS <=", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusLike(String value) {
            addCriterion("PLO_STATUS like", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusNotLike(String value) {
            addCriterion("PLO_STATUS not like", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusIn(List<String> values) {
            addCriterion("PLO_STATUS in", values, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusNotIn(List<String> values) {
            addCriterion("PLO_STATUS not in", values, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusBetween(String value1, String value2) {
            addCriterion("PLO_STATUS between", value1, value2, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusNotBetween(String value1, String value2) {
            addCriterion("PLO_STATUS not between", value1, value2, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(String value) {
            addCriterion("START_TIME =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(String value) {
            addCriterion("START_TIME <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(String value) {
            addCriterion("START_TIME >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("START_TIME >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(String value) {
            addCriterion("START_TIME <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(String value) {
            addCriterion("START_TIME <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLike(String value) {
            addCriterion("START_TIME like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotLike(String value) {
            addCriterion("START_TIME not like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<String> values) {
            addCriterion("START_TIME in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<String> values) {
            addCriterion("START_TIME not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(String value1, String value2) {
            addCriterion("START_TIME between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(String value1, String value2) {
            addCriterion("START_TIME not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeIsNull() {
            addCriterion("STOP_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStopTimeIsNotNull() {
            addCriterion("STOP_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStopTimeEqualTo(String value) {
            addCriterion("STOP_TIME =", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotEqualTo(String value) {
            addCriterion("STOP_TIME <>", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeGreaterThan(String value) {
            addCriterion("STOP_TIME >", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeGreaterThanOrEqualTo(String value) {
            addCriterion("STOP_TIME >=", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeLessThan(String value) {
            addCriterion("STOP_TIME <", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeLessThanOrEqualTo(String value) {
            addCriterion("STOP_TIME <=", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeLike(String value) {
            addCriterion("STOP_TIME like", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotLike(String value) {
            addCriterion("STOP_TIME not like", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeIn(List<String> values) {
            addCriterion("STOP_TIME in", values, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotIn(List<String> values) {
            addCriterion("STOP_TIME not in", values, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeBetween(String value1, String value2) {
            addCriterion("STOP_TIME between", value1, value2, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotBetween(String value1, String value2) {
            addCriterion("STOP_TIME not between", value1, value2, "stopTime");
            return (Criteria) this;
        }

        public Criteria andAgentStatusIsNull() {
            addCriterion("AGENT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAgentStatusIsNotNull() {
            addCriterion("AGENT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAgentStatusEqualTo(String value) {
            addCriterion("AGENT_STATUS =", value, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusNotEqualTo(String value) {
            addCriterion("AGENT_STATUS <>", value, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusGreaterThan(String value) {
            addCriterion("AGENT_STATUS >", value, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_STATUS >=", value, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusLessThan(String value) {
            addCriterion("AGENT_STATUS <", value, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusLessThanOrEqualTo(String value) {
            addCriterion("AGENT_STATUS <=", value, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusLike(String value) {
            addCriterion("AGENT_STATUS like", value, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusNotLike(String value) {
            addCriterion("AGENT_STATUS not like", value, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusIn(List<String> values) {
            addCriterion("AGENT_STATUS in", values, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusNotIn(List<String> values) {
            addCriterion("AGENT_STATUS not in", values, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusBetween(String value1, String value2) {
            addCriterion("AGENT_STATUS between", value1, value2, "agentStatus");
            return (Criteria) this;
        }

        public Criteria andAgentStatusNotBetween(String value1, String value2) {
            addCriterion("AGENT_STATUS not between", value1, value2, "agentStatus");
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