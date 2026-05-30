package com.cmbccd.ulms.flow.domain;

import java.util.ArrayList;
import java.util.List;

public class PerfUnitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PerfUnitExample() {
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

        public Criteria andPerfUnitIsNull() {
            addCriterion("PERF_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andPerfUnitIsNotNull() {
            addCriterion("PERF_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andPerfUnitEqualTo(Integer value) {
            addCriterion("PERF_UNIT =", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitNotEqualTo(Integer value) {
            addCriterion("PERF_UNIT <>", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitGreaterThan(Integer value) {
            addCriterion("PERF_UNIT >", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitGreaterThanOrEqualTo(Integer value) {
            addCriterion("PERF_UNIT >=", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitLessThan(Integer value) {
            addCriterion("PERF_UNIT <", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitLessThanOrEqualTo(Integer value) {
            addCriterion("PERF_UNIT <=", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitIn(List<Integer> values) {
            addCriterion("PERF_UNIT in", values, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitNotIn(List<Integer> values) {
            addCriterion("PERF_UNIT not in", values, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitBetween(Integer value1, Integer value2) {
            addCriterion("PERF_UNIT between", value1, value2, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitNotBetween(Integer value1, Integer value2) {
            addCriterion("PERF_UNIT not between", value1, value2, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andIfPerfIsNull() {
            addCriterion("IF_PERF is null");
            return (Criteria) this;
        }

        public Criteria andIfPerfIsNotNull() {
            addCriterion("IF_PERF is not null");
            return (Criteria) this;
        }

        public Criteria andIfPerfEqualTo(Short value) {
            addCriterion("IF_PERF =", value, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfNotEqualTo(Short value) {
            addCriterion("IF_PERF <>", value, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfGreaterThan(Short value) {
            addCriterion("IF_PERF >", value, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfGreaterThanOrEqualTo(Short value) {
            addCriterion("IF_PERF >=", value, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfLessThan(Short value) {
            addCriterion("IF_PERF <", value, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfLessThanOrEqualTo(Short value) {
            addCriterion("IF_PERF <=", value, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfIn(List<Short> values) {
            addCriterion("IF_PERF in", values, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfNotIn(List<Short> values) {
            addCriterion("IF_PERF not in", values, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfBetween(Short value1, Short value2) {
            addCriterion("IF_PERF between", value1, value2, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andIfPerfNotBetween(Short value1, Short value2) {
            addCriterion("IF_PERF not between", value1, value2, "ifPerf");
            return (Criteria) this;
        }

        public Criteria andBegMonthIsNull() {
            addCriterion("BEG_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andBegMonthIsNotNull() {
            addCriterion("BEG_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andBegMonthEqualTo(String value) {
            addCriterion("BEG_MONTH =", value, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthNotEqualTo(String value) {
            addCriterion("BEG_MONTH <>", value, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthGreaterThan(String value) {
            addCriterion("BEG_MONTH >", value, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthGreaterThanOrEqualTo(String value) {
            addCriterion("BEG_MONTH >=", value, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthLessThan(String value) {
            addCriterion("BEG_MONTH <", value, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthLessThanOrEqualTo(String value) {
            addCriterion("BEG_MONTH <=", value, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthLike(String value) {
            addCriterion("BEG_MONTH like", value, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthNotLike(String value) {
            addCriterion("BEG_MONTH not like", value, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthIn(List<String> values) {
            addCriterion("BEG_MONTH in", values, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthNotIn(List<String> values) {
            addCriterion("BEG_MONTH not in", values, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthBetween(String value1, String value2) {
            addCriterion("BEG_MONTH between", value1, value2, "begMonth");
            return (Criteria) this;
        }

        public Criteria andBegMonthNotBetween(String value1, String value2) {
            addCriterion("BEG_MONTH not between", value1, value2, "begMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthIsNull() {
            addCriterion("END_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andEndMonthIsNotNull() {
            addCriterion("END_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andEndMonthEqualTo(String value) {
            addCriterion("END_MONTH =", value, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthNotEqualTo(String value) {
            addCriterion("END_MONTH <>", value, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthGreaterThan(String value) {
            addCriterion("END_MONTH >", value, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthGreaterThanOrEqualTo(String value) {
            addCriterion("END_MONTH >=", value, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthLessThan(String value) {
            addCriterion("END_MONTH <", value, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthLessThanOrEqualTo(String value) {
            addCriterion("END_MONTH <=", value, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthLike(String value) {
            addCriterion("END_MONTH like", value, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthNotLike(String value) {
            addCriterion("END_MONTH not like", value, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthIn(List<String> values) {
            addCriterion("END_MONTH in", values, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthNotIn(List<String> values) {
            addCriterion("END_MONTH not in", values, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthBetween(String value1, String value2) {
            addCriterion("END_MONTH between", value1, value2, "endMonth");
            return (Criteria) this;
        }

        public Criteria andEndMonthNotBetween(String value1, String value2) {
            addCriterion("END_MONTH not between", value1, value2, "endMonth");
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

        public Criteria andFlowStatusIsNull() {
            addCriterion("FLOW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andFlowStatusIsNotNull() {
            addCriterion("FLOW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andFlowStatusEqualTo(Short value) {
            addCriterion("FLOW_STATUS =", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusNotEqualTo(Short value) {
            addCriterion("FLOW_STATUS <>", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusGreaterThan(Short value) {
            addCriterion("FLOW_STATUS >", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("FLOW_STATUS >=", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusLessThan(Short value) {
            addCriterion("FLOW_STATUS <", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusLessThanOrEqualTo(Short value) {
            addCriterion("FLOW_STATUS <=", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusIn(List<Short> values) {
            addCriterion("FLOW_STATUS in", values, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusNotIn(List<Short> values) {
            addCriterion("FLOW_STATUS not in", values, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusBetween(Short value1, Short value2) {
            addCriterion("FLOW_STATUS between", value1, value2, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusNotBetween(Short value1, Short value2) {
            addCriterion("FLOW_STATUS not between", value1, value2, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("MODIFY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("MODIFY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(String value) {
            addCriterion("MODIFY_TIME =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(String value) {
            addCriterion("MODIFY_TIME <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(String value) {
            addCriterion("MODIFY_TIME >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(String value) {
            addCriterion("MODIFY_TIME >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(String value) {
            addCriterion("MODIFY_TIME <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(String value) {
            addCriterion("MODIFY_TIME <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLike(String value) {
            addCriterion("MODIFY_TIME like", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotLike(String value) {
            addCriterion("MODIFY_TIME not like", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<String> values) {
            addCriterion("MODIFY_TIME in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<String> values) {
            addCriterion("MODIFY_TIME not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(String value1, String value2) {
            addCriterion("MODIFY_TIME between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(String value1, String value2) {
            addCriterion("MODIFY_TIME not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyIdIsNull() {
            addCriterion("MODIFY_ID is null");
            return (Criteria) this;
        }

        public Criteria andModifyIdIsNotNull() {
            addCriterion("MODIFY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andModifyIdEqualTo(String value) {
            addCriterion("MODIFY_ID =", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdNotEqualTo(String value) {
            addCriterion("MODIFY_ID <>", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdGreaterThan(String value) {
            addCriterion("MODIFY_ID >", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdGreaterThanOrEqualTo(String value) {
            addCriterion("MODIFY_ID >=", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdLessThan(String value) {
            addCriterion("MODIFY_ID <", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdLessThanOrEqualTo(String value) {
            addCriterion("MODIFY_ID <=", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdLike(String value) {
            addCriterion("MODIFY_ID like", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdNotLike(String value) {
            addCriterion("MODIFY_ID not like", value, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdIn(List<String> values) {
            addCriterion("MODIFY_ID in", values, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdNotIn(List<String> values) {
            addCriterion("MODIFY_ID not in", values, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdBetween(String value1, String value2) {
            addCriterion("MODIFY_ID between", value1, value2, "modifyId");
            return (Criteria) this;
        }

        public Criteria andModifyIdNotBetween(String value1, String value2) {
            addCriterion("MODIFY_ID not between", value1, value2, "modifyId");
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