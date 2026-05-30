package com.cmbccd.ulms.flow.domain;

import java.util.ArrayList;
import java.util.List;

public class FlowInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowInfoExample() {
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

        public Criteria andCategroyIsNull() {
            addCriterion("CATEGROY is null");
            return (Criteria) this;
        }

        public Criteria andCategroyIsNotNull() {
            addCriterion("CATEGROY is not null");
            return (Criteria) this;
        }

        public Criteria andCategroyEqualTo(String value) {
            addCriterion("CATEGROY =", value, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyNotEqualTo(String value) {
            addCriterion("CATEGROY <>", value, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyGreaterThan(String value) {
            addCriterion("CATEGROY >", value, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGROY >=", value, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyLessThan(String value) {
            addCriterion("CATEGROY <", value, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyLessThanOrEqualTo(String value) {
            addCriterion("CATEGROY <=", value, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyLike(String value) {
            addCriterion("CATEGROY like", value, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyNotLike(String value) {
            addCriterion("CATEGROY not like", value, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyIn(List<String> values) {
            addCriterion("CATEGROY in", values, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyNotIn(List<String> values) {
            addCriterion("CATEGROY not in", values, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyBetween(String value1, String value2) {
            addCriterion("CATEGROY between", value1, value2, "categroy");
            return (Criteria) this;
        }

        public Criteria andCategroyNotBetween(String value1, String value2) {
            addCriterion("CATEGROY not between", value1, value2, "categroy");
            return (Criteria) this;
        }

        public Criteria andFlowNameIsNull() {
            addCriterion("FLOW_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFlowNameIsNotNull() {
            addCriterion("FLOW_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFlowNameEqualTo(String value) {
            addCriterion("FLOW_NAME =", value, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameNotEqualTo(String value) {
            addCriterion("FLOW_NAME <>", value, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameGreaterThan(String value) {
            addCriterion("FLOW_NAME >", value, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameGreaterThanOrEqualTo(String value) {
            addCriterion("FLOW_NAME >=", value, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameLessThan(String value) {
            addCriterion("FLOW_NAME <", value, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameLessThanOrEqualTo(String value) {
            addCriterion("FLOW_NAME <=", value, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameLike(String value) {
            addCriterion("FLOW_NAME like", value, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameNotLike(String value) {
            addCriterion("FLOW_NAME not like", value, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameIn(List<String> values) {
            addCriterion("FLOW_NAME in", values, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameNotIn(List<String> values) {
            addCriterion("FLOW_NAME not in", values, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameBetween(String value1, String value2) {
            addCriterion("FLOW_NAME between", value1, value2, "flowName");
            return (Criteria) this;
        }

        public Criteria andFlowNameNotBetween(String value1, String value2) {
            addCriterion("FLOW_NAME not between", value1, value2, "flowName");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNull() {
            addCriterion("TABLE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNotNull() {
            addCriterion("TABLE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTableNameEqualTo(String value) {
            addCriterion("TABLE_NAME =", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotEqualTo(String value) {
            addCriterion("TABLE_NAME <>", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThan(String value) {
            addCriterion("TABLE_NAME >", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("TABLE_NAME >=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThan(String value) {
            addCriterion("TABLE_NAME <", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("TABLE_NAME <=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLike(String value) {
            addCriterion("TABLE_NAME like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotLike(String value) {
            addCriterion("TABLE_NAME not like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameIn(List<String> values) {
            addCriterion("TABLE_NAME in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotIn(List<String> values) {
            addCriterion("TABLE_NAME not in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameBetween(String value1, String value2) {
            addCriterion("TABLE_NAME between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("TABLE_NAME not between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andApproveTempIsNull() {
            addCriterion("APPROVE_TEMP is null");
            return (Criteria) this;
        }

        public Criteria andApproveTempIsNotNull() {
            addCriterion("APPROVE_TEMP is not null");
            return (Criteria) this;
        }

        public Criteria andApproveTempEqualTo(String value) {
            addCriterion("APPROVE_TEMP =", value, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempNotEqualTo(String value) {
            addCriterion("APPROVE_TEMP <>", value, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempGreaterThan(String value) {
            addCriterion("APPROVE_TEMP >", value, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVE_TEMP >=", value, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempLessThan(String value) {
            addCriterion("APPROVE_TEMP <", value, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempLessThanOrEqualTo(String value) {
            addCriterion("APPROVE_TEMP <=", value, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempLike(String value) {
            addCriterion("APPROVE_TEMP like", value, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempNotLike(String value) {
            addCriterion("APPROVE_TEMP not like", value, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempIn(List<String> values) {
            addCriterion("APPROVE_TEMP in", values, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempNotIn(List<String> values) {
            addCriterion("APPROVE_TEMP not in", values, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempBetween(String value1, String value2) {
            addCriterion("APPROVE_TEMP between", value1, value2, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andApproveTempNotBetween(String value1, String value2) {
            addCriterion("APPROVE_TEMP not between", value1, value2, "approveTemp");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("SORT is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("SORT is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Short value) {
            addCriterion("SORT =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Short value) {
            addCriterion("SORT <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Short value) {
            addCriterion("SORT >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Short value) {
            addCriterion("SORT >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Short value) {
            addCriterion("SORT <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Short value) {
            addCriterion("SORT <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Short> values) {
            addCriterion("SORT in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Short> values) {
            addCriterion("SORT not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Short value1, Short value2) {
            addCriterion("SORT between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Short value1, Short value2) {
            addCriterion("SORT not between", value1, value2, "sort");
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

        public Criteria andCancelIsNull() {
            addCriterion("CANCEL is null");
            return (Criteria) this;
        }

        public Criteria andCancelIsNotNull() {
            addCriterion("CANCEL is not null");
            return (Criteria) this;
        }

        public Criteria andCancelEqualTo(Short value) {
            addCriterion("CANCEL =", value, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelNotEqualTo(Short value) {
            addCriterion("CANCEL <>", value, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelGreaterThan(Short value) {
            addCriterion("CANCEL >", value, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelGreaterThanOrEqualTo(Short value) {
            addCriterion("CANCEL >=", value, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelLessThan(Short value) {
            addCriterion("CANCEL <", value, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelLessThanOrEqualTo(Short value) {
            addCriterion("CANCEL <=", value, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelIn(List<Short> values) {
            addCriterion("CANCEL in", values, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelNotIn(List<Short> values) {
            addCriterion("CANCEL not in", values, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelBetween(Short value1, Short value2) {
            addCriterion("CANCEL between", value1, value2, "cancel");
            return (Criteria) this;
        }

        public Criteria andCancelNotBetween(Short value1, Short value2) {
            addCriterion("CANCEL not between", value1, value2, "cancel");
            return (Criteria) this;
        }

        public Criteria andDraftIsNull() {
            addCriterion("DRAFT is null");
            return (Criteria) this;
        }

        public Criteria andDraftIsNotNull() {
            addCriterion("DRAFT is not null");
            return (Criteria) this;
        }

        public Criteria andDraftEqualTo(Short value) {
            addCriterion("DRAFT =", value, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftNotEqualTo(Short value) {
            addCriterion("DRAFT <>", value, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftGreaterThan(Short value) {
            addCriterion("DRAFT >", value, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftGreaterThanOrEqualTo(Short value) {
            addCriterion("DRAFT >=", value, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftLessThan(Short value) {
            addCriterion("DRAFT <", value, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftLessThanOrEqualTo(Short value) {
            addCriterion("DRAFT <=", value, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftIn(List<Short> values) {
            addCriterion("DRAFT in", values, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftNotIn(List<Short> values) {
            addCriterion("DRAFT not in", values, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftBetween(Short value1, Short value2) {
            addCriterion("DRAFT between", value1, value2, "draft");
            return (Criteria) this;
        }

        public Criteria andDraftNotBetween(Short value1, Short value2) {
            addCriterion("DRAFT not between", value1, value2, "draft");
            return (Criteria) this;
        }

        public Criteria andPathIsNull() {
            addCriterion("PATH is null");
            return (Criteria) this;
        }

        public Criteria andPathIsNotNull() {
            addCriterion("PATH is not null");
            return (Criteria) this;
        }

        public Criteria andPathEqualTo(String value) {
            addCriterion("PATH =", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotEqualTo(String value) {
            addCriterion("PATH <>", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThan(String value) {
            addCriterion("PATH >", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThanOrEqualTo(String value) {
            addCriterion("PATH >=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThan(String value) {
            addCriterion("PATH <", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThanOrEqualTo(String value) {
            addCriterion("PATH <=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLike(String value) {
            addCriterion("PATH like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotLike(String value) {
            addCriterion("PATH not like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathIn(List<String> values) {
            addCriterion("PATH in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotIn(List<String> values) {
            addCriterion("PATH not in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathBetween(String value1, String value2) {
            addCriterion("PATH between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotBetween(String value1, String value2) {
            addCriterion("PATH not between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andTableInfoIsNull() {
            addCriterion("TABLE_INFO is null");
            return (Criteria) this;
        }

        public Criteria andTableInfoIsNotNull() {
            addCriterion("TABLE_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andTableInfoEqualTo(String value) {
            addCriterion("TABLE_INFO =", value, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoNotEqualTo(String value) {
            addCriterion("TABLE_INFO <>", value, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoGreaterThan(String value) {
            addCriterion("TABLE_INFO >", value, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoGreaterThanOrEqualTo(String value) {
            addCriterion("TABLE_INFO >=", value, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoLessThan(String value) {
            addCriterion("TABLE_INFO <", value, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoLessThanOrEqualTo(String value) {
            addCriterion("TABLE_INFO <=", value, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoLike(String value) {
            addCriterion("TABLE_INFO like", value, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoNotLike(String value) {
            addCriterion("TABLE_INFO not like", value, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoIn(List<String> values) {
            addCriterion("TABLE_INFO in", values, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoNotIn(List<String> values) {
            addCriterion("TABLE_INFO not in", values, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoBetween(String value1, String value2) {
            addCriterion("TABLE_INFO between", value1, value2, "tableInfo");
            return (Criteria) this;
        }

        public Criteria andTableInfoNotBetween(String value1, String value2) {
            addCriterion("TABLE_INFO not between", value1, value2, "tableInfo");
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