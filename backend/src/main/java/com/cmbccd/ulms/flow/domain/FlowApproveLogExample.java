package com.cmbccd.ulms.flow.domain;

import java.util.ArrayList;
import java.util.List;

public class FlowApproveLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowApproveLogExample() {
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

        public Criteria andCaseIdIsNull() {
            addCriterion("CASE_ID is null");
            return (Criteria) this;
        }

        public Criteria andCaseIdIsNotNull() {
            addCriterion("CASE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCaseIdEqualTo(String value) {
            addCriterion("CASE_ID =", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotEqualTo(String value) {
            addCriterion("CASE_ID <>", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdGreaterThan(String value) {
            addCriterion("CASE_ID >", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdGreaterThanOrEqualTo(String value) {
            addCriterion("CASE_ID >=", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLessThan(String value) {
            addCriterion("CASE_ID <", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLessThanOrEqualTo(String value) {
            addCriterion("CASE_ID <=", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLike(String value) {
            addCriterion("CASE_ID like", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotLike(String value) {
            addCriterion("CASE_ID not like", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdIn(List<String> values) {
            addCriterion("CASE_ID in", values, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotIn(List<String> values) {
            addCriterion("CASE_ID not in", values, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdBetween(String value1, String value2) {
            addCriterion("CASE_ID between", value1, value2, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotBetween(String value1, String value2) {
            addCriterion("CASE_ID not between", value1, value2, "caseId");
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

        public Criteria andApproveNumIsNull() {
            addCriterion("APPROVE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andApproveNumIsNotNull() {
            addCriterion("APPROVE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andApproveNumEqualTo(String value) {
            addCriterion("APPROVE_NUM =", value, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumNotEqualTo(String value) {
            addCriterion("APPROVE_NUM <>", value, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumGreaterThan(String value) {
            addCriterion("APPROVE_NUM >", value, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVE_NUM >=", value, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumLessThan(String value) {
            addCriterion("APPROVE_NUM <", value, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumLessThanOrEqualTo(String value) {
            addCriterion("APPROVE_NUM <=", value, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumLike(String value) {
            addCriterion("APPROVE_NUM like", value, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumNotLike(String value) {
            addCriterion("APPROVE_NUM not like", value, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumIn(List<String> values) {
            addCriterion("APPROVE_NUM in", values, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumNotIn(List<String> values) {
            addCriterion("APPROVE_NUM not in", values, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumBetween(String value1, String value2) {
            addCriterion("APPROVE_NUM between", value1, value2, "approveNum");
            return (Criteria) this;
        }

        public Criteria andApproveNumNotBetween(String value1, String value2) {
            addCriterion("APPROVE_NUM not between", value1, value2, "approveNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumIsNull() {
            addCriterion("HANDLE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andHandleNumIsNotNull() {
            addCriterion("HANDLE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andHandleNumEqualTo(String value) {
            addCriterion("HANDLE_NUM =", value, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumNotEqualTo(String value) {
            addCriterion("HANDLE_NUM <>", value, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumGreaterThan(String value) {
            addCriterion("HANDLE_NUM >", value, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLE_NUM >=", value, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumLessThan(String value) {
            addCriterion("HANDLE_NUM <", value, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumLessThanOrEqualTo(String value) {
            addCriterion("HANDLE_NUM <=", value, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumLike(String value) {
            addCriterion("HANDLE_NUM like", value, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumNotLike(String value) {
            addCriterion("HANDLE_NUM not like", value, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumIn(List<String> values) {
            addCriterion("HANDLE_NUM in", values, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumNotIn(List<String> values) {
            addCriterion("HANDLE_NUM not in", values, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumBetween(String value1, String value2) {
            addCriterion("HANDLE_NUM between", value1, value2, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleNumNotBetween(String value1, String value2) {
            addCriterion("HANDLE_NUM not between", value1, value2, "handleNum");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNull() {
            addCriterion("HANDLE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNotNull() {
            addCriterion("HANDLE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeEqualTo(String value) {
            addCriterion("HANDLE_TIME =", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotEqualTo(String value) {
            addCriterion("HANDLE_TIME <>", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThan(String value) {
            addCriterion("HANDLE_TIME >", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLE_TIME >=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThan(String value) {
            addCriterion("HANDLE_TIME <", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThanOrEqualTo(String value) {
            addCriterion("HANDLE_TIME <=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLike(String value) {
            addCriterion("HANDLE_TIME like", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotLike(String value) {
            addCriterion("HANDLE_TIME not like", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIn(List<String> values) {
            addCriterion("HANDLE_TIME in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotIn(List<String> values) {
            addCriterion("HANDLE_TIME not in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeBetween(String value1, String value2) {
            addCriterion("HANDLE_TIME between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotBetween(String value1, String value2) {
            addCriterion("HANDLE_TIME not between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIsNull() {
            addCriterion("HANDLE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIsNotNull() {
            addCriterion("HANDLE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andHandleTypeEqualTo(Short value) {
            addCriterion("HANDLE_TYPE =", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotEqualTo(Short value) {
            addCriterion("HANDLE_TYPE <>", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeGreaterThan(Short value) {
            addCriterion("HANDLE_TYPE >", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("HANDLE_TYPE >=", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeLessThan(Short value) {
            addCriterion("HANDLE_TYPE <", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeLessThanOrEqualTo(Short value) {
            addCriterion("HANDLE_TYPE <=", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIn(List<Short> values) {
            addCriterion("HANDLE_TYPE in", values, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotIn(List<Short> values) {
            addCriterion("HANDLE_TYPE not in", values, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeBetween(Short value1, Short value2) {
            addCriterion("HANDLE_TYPE between", value1, value2, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotBetween(Short value1, Short value2) {
            addCriterion("HANDLE_TYPE not between", value1, value2, "handleType");
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