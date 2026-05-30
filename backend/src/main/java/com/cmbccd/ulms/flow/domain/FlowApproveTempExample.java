package com.cmbccd.ulms.flow.domain;

import java.util.ArrayList;
import java.util.List;

public class FlowApproveTempExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowApproveTempExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andApproveLevelIsNull() {
            addCriterion("APPROVE_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andApproveLevelIsNotNull() {
            addCriterion("APPROVE_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andApproveLevelEqualTo(Short value) {
            addCriterion("APPROVE_LEVEL =", value, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelNotEqualTo(Short value) {
            addCriterion("APPROVE_LEVEL <>", value, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelGreaterThan(Short value) {
            addCriterion("APPROVE_LEVEL >", value, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelGreaterThanOrEqualTo(Short value) {
            addCriterion("APPROVE_LEVEL >=", value, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelLessThan(Short value) {
            addCriterion("APPROVE_LEVEL <", value, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelLessThanOrEqualTo(Short value) {
            addCriterion("APPROVE_LEVEL <=", value, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelIn(List<Short> values) {
            addCriterion("APPROVE_LEVEL in", values, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelNotIn(List<Short> values) {
            addCriterion("APPROVE_LEVEL not in", values, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelBetween(Short value1, Short value2) {
            addCriterion("APPROVE_LEVEL between", value1, value2, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andApproveLevelNotBetween(Short value1, Short value2) {
            addCriterion("APPROVE_LEVEL not between", value1, value2, "approveLevel");
            return (Criteria) this;
        }

        public Criteria andModelIsNull() {
            addCriterion("MODEL is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(Short value) {
            addCriterion("MODEL =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(Short value) {
            addCriterion("MODEL <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(Short value) {
            addCriterion("MODEL >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(Short value) {
            addCriterion("MODEL >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(Short value) {
            addCriterion("MODEL <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(Short value) {
            addCriterion("MODEL <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<Short> values) {
            addCriterion("MODEL in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<Short> values) {
            addCriterion("MODEL not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(Short value1, Short value2) {
            addCriterion("MODEL between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(Short value1, Short value2) {
            addCriterion("MODEL not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelInfoIsNull() {
            addCriterion("MODEL_INFO is null");
            return (Criteria) this;
        }

        public Criteria andModelInfoIsNotNull() {
            addCriterion("MODEL_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andModelInfoEqualTo(String value) {
            addCriterion("MODEL_INFO =", value, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoNotEqualTo(String value) {
            addCriterion("MODEL_INFO <>", value, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoGreaterThan(String value) {
            addCriterion("MODEL_INFO >", value, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoGreaterThanOrEqualTo(String value) {
            addCriterion("MODEL_INFO >=", value, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoLessThan(String value) {
            addCriterion("MODEL_INFO <", value, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoLessThanOrEqualTo(String value) {
            addCriterion("MODEL_INFO <=", value, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoLike(String value) {
            addCriterion("MODEL_INFO like", value, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoNotLike(String value) {
            addCriterion("MODEL_INFO not like", value, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoIn(List<String> values) {
            addCriterion("MODEL_INFO in", values, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoNotIn(List<String> values) {
            addCriterion("MODEL_INFO not in", values, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoBetween(String value1, String value2) {
            addCriterion("MODEL_INFO between", value1, value2, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andModelInfoNotBetween(String value1, String value2) {
            addCriterion("MODEL_INFO not between", value1, value2, "modelInfo");
            return (Criteria) this;
        }

        public Criteria andFlowIdIsNull() {
            addCriterion("FLOW_ID is null");
            return (Criteria) this;
        }

        public Criteria andFlowIdIsNotNull() {
            addCriterion("FLOW_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFlowIdEqualTo(String value) {
            addCriterion("FLOW_ID =", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotEqualTo(String value) {
            addCriterion("FLOW_ID <>", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThan(String value) {
            addCriterion("FLOW_ID >", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThanOrEqualTo(String value) {
            addCriterion("FLOW_ID >=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThan(String value) {
            addCriterion("FLOW_ID <", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThanOrEqualTo(String value) {
            addCriterion("FLOW_ID <=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLike(String value) {
            addCriterion("FLOW_ID like", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotLike(String value) {
            addCriterion("FLOW_ID not like", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdIn(List<String> values) {
            addCriterion("FLOW_ID in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotIn(List<String> values) {
            addCriterion("FLOW_ID not in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdBetween(String value1, String value2) {
            addCriterion("FLOW_ID between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotBetween(String value1, String value2) {
            addCriterion("FLOW_ID not between", value1, value2, "flowId");
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

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRequireIsNull() {
            addCriterion("REQUIRE is null");
            return (Criteria) this;
        }

        public Criteria andRequireIsNotNull() {
            addCriterion("REQUIRE is not null");
            return (Criteria) this;
        }

        public Criteria andRequireEqualTo(Short value) {
            addCriterion("REQUIRE =", value, "require");
            return (Criteria) this;
        }

        public Criteria andRequireNotEqualTo(Short value) {
            addCriterion("REQUIRE <>", value, "require");
            return (Criteria) this;
        }

        public Criteria andRequireGreaterThan(Short value) {
            addCriterion("REQUIRE >", value, "require");
            return (Criteria) this;
        }

        public Criteria andRequireGreaterThanOrEqualTo(Short value) {
            addCriterion("REQUIRE >=", value, "require");
            return (Criteria) this;
        }

        public Criteria andRequireLessThan(Short value) {
            addCriterion("REQUIRE <", value, "require");
            return (Criteria) this;
        }

        public Criteria andRequireLessThanOrEqualTo(Short value) {
            addCriterion("REQUIRE <=", value, "require");
            return (Criteria) this;
        }

        public Criteria andRequireIn(List<Short> values) {
            addCriterion("REQUIRE in", values, "require");
            return (Criteria) this;
        }

        public Criteria andRequireNotIn(List<Short> values) {
            addCriterion("REQUIRE not in", values, "require");
            return (Criteria) this;
        }

        public Criteria andRequireBetween(Short value1, Short value2) {
            addCriterion("REQUIRE between", value1, value2, "require");
            return (Criteria) this;
        }

        public Criteria andRequireNotBetween(Short value1, Short value2) {
            addCriterion("REQUIRE not between", value1, value2, "require");
            return (Criteria) this;
        }

        public Criteria andRequireInfoIsNull() {
            addCriterion("REQUIRE_INFO is null");
            return (Criteria) this;
        }

        public Criteria andRequireInfoIsNotNull() {
            addCriterion("REQUIRE_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andRequireInfoEqualTo(String value) {
            addCriterion("REQUIRE_INFO =", value, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoNotEqualTo(String value) {
            addCriterion("REQUIRE_INFO <>", value, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoGreaterThan(String value) {
            addCriterion("REQUIRE_INFO >", value, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoGreaterThanOrEqualTo(String value) {
            addCriterion("REQUIRE_INFO >=", value, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoLessThan(String value) {
            addCriterion("REQUIRE_INFO <", value, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoLessThanOrEqualTo(String value) {
            addCriterion("REQUIRE_INFO <=", value, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoLike(String value) {
            addCriterion("REQUIRE_INFO like", value, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoNotLike(String value) {
            addCriterion("REQUIRE_INFO not like", value, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoIn(List<String> values) {
            addCriterion("REQUIRE_INFO in", values, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoNotIn(List<String> values) {
            addCriterion("REQUIRE_INFO not in", values, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoBetween(String value1, String value2) {
            addCriterion("REQUIRE_INFO between", value1, value2, "requireInfo");
            return (Criteria) this;
        }

        public Criteria andRequireInfoNotBetween(String value1, String value2) {
            addCriterion("REQUIRE_INFO not between", value1, value2, "requireInfo");
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