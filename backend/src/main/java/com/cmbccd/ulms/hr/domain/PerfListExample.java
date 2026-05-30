package com.cmbccd.ulms.hr.domain;

import java.util.ArrayList;
import java.util.List;

public class PerfListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PerfListExample() {
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

        public Criteria andDeptGroupIsNull() {
            addCriterion("DEPT_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andDeptGroupIsNotNull() {
            addCriterion("DEPT_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andDeptGroupEqualTo(String value) {
            addCriterion("DEPT_GROUP =", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupNotEqualTo(String value) {
            addCriterion("DEPT_GROUP <>", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupGreaterThan(String value) {
            addCriterion("DEPT_GROUP >", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupGreaterThanOrEqualTo(String value) {
            addCriterion("DEPT_GROUP >=", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupLessThan(String value) {
            addCriterion("DEPT_GROUP <", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupLessThanOrEqualTo(String value) {
            addCriterion("DEPT_GROUP <=", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupLike(String value) {
            addCriterion("DEPT_GROUP like", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupNotLike(String value) {
            addCriterion("DEPT_GROUP not like", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupIn(List<String> values) {
            addCriterion("DEPT_GROUP in", values, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupNotIn(List<String> values) {
            addCriterion("DEPT_GROUP not in", values, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupBetween(String value1, String value2) {
            addCriterion("DEPT_GROUP between", value1, value2, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupNotBetween(String value1, String value2) {
            addCriterion("DEPT_GROUP not between", value1, value2, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andJobLevelIsNull() {
            addCriterion("JOB_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andJobLevelIsNotNull() {
            addCriterion("JOB_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andJobLevelEqualTo(String value) {
            addCriterion("JOB_LEVEL =", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelNotEqualTo(String value) {
            addCriterion("JOB_LEVEL <>", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelGreaterThan(String value) {
            addCriterion("JOB_LEVEL >", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelGreaterThanOrEqualTo(String value) {
            addCriterion("JOB_LEVEL >=", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelLessThan(String value) {
            addCriterion("JOB_LEVEL <", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelLessThanOrEqualTo(String value) {
            addCriterion("JOB_LEVEL <=", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelLike(String value) {
            addCriterion("JOB_LEVEL like", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelNotLike(String value) {
            addCriterion("JOB_LEVEL not like", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelIn(List<String> values) {
            addCriterion("JOB_LEVEL in", values, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelNotIn(List<String> values) {
            addCriterion("JOB_LEVEL not in", values, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelBetween(String value1, String value2) {
            addCriterion("JOB_LEVEL between", value1, value2, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelNotBetween(String value1, String value2) {
            addCriterion("JOB_LEVEL not between", value1, value2, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andSapIdIsNull() {
            addCriterion("SAP_ID is null");
            return (Criteria) this;
        }

        public Criteria andSapIdIsNotNull() {
            addCriterion("SAP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSapIdEqualTo(String value) {
            addCriterion("SAP_ID =", value, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdNotEqualTo(String value) {
            addCriterion("SAP_ID <>", value, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdGreaterThan(String value) {
            addCriterion("SAP_ID >", value, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdGreaterThanOrEqualTo(String value) {
            addCriterion("SAP_ID >=", value, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdLessThan(String value) {
            addCriterion("SAP_ID <", value, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdLessThanOrEqualTo(String value) {
            addCriterion("SAP_ID <=", value, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdLike(String value) {
            addCriterion("SAP_ID like", value, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdNotLike(String value) {
            addCriterion("SAP_ID not like", value, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdIn(List<String> values) {
            addCriterion("SAP_ID in", values, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdNotIn(List<String> values) {
            addCriterion("SAP_ID not in", values, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdBetween(String value1, String value2) {
            addCriterion("SAP_ID between", value1, value2, "sapId");
            return (Criteria) this;
        }

        public Criteria andSapIdNotBetween(String value1, String value2) {
            addCriterion("SAP_ID not between", value1, value2, "sapId");
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

        public Criteria andPerfUnitEqualTo(String value) {
            addCriterion("PERF_UNIT =", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitNotEqualTo(String value) {
            addCriterion("PERF_UNIT <>", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitGreaterThan(String value) {
            addCriterion("PERF_UNIT >", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitGreaterThanOrEqualTo(String value) {
            addCriterion("PERF_UNIT >=", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitLessThan(String value) {
            addCriterion("PERF_UNIT <", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitLessThanOrEqualTo(String value) {
            addCriterion("PERF_UNIT <=", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitLike(String value) {
            addCriterion("PERF_UNIT like", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitNotLike(String value) {
            addCriterion("PERF_UNIT not like", value, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitIn(List<String> values) {
            addCriterion("PERF_UNIT in", values, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitNotIn(List<String> values) {
            addCriterion("PERF_UNIT not in", values, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitBetween(String value1, String value2) {
            addCriterion("PERF_UNIT between", value1, value2, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andPerfUnitNotBetween(String value1, String value2) {
            addCriterion("PERF_UNIT not between", value1, value2, "perfUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitIsNull() {
            addCriterion("SUBSIDY_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitIsNotNull() {
            addCriterion("SUBSIDY_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitEqualTo(String value) {
            addCriterion("SUBSIDY_UNIT =", value, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitNotEqualTo(String value) {
            addCriterion("SUBSIDY_UNIT <>", value, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitGreaterThan(String value) {
            addCriterion("SUBSIDY_UNIT >", value, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitGreaterThanOrEqualTo(String value) {
            addCriterion("SUBSIDY_UNIT >=", value, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitLessThan(String value) {
            addCriterion("SUBSIDY_UNIT <", value, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitLessThanOrEqualTo(String value) {
            addCriterion("SUBSIDY_UNIT <=", value, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitLike(String value) {
            addCriterion("SUBSIDY_UNIT like", value, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitNotLike(String value) {
            addCriterion("SUBSIDY_UNIT not like", value, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitIn(List<String> values) {
            addCriterion("SUBSIDY_UNIT in", values, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitNotIn(List<String> values) {
            addCriterion("SUBSIDY_UNIT not in", values, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitBetween(String value1, String value2) {
            addCriterion("SUBSIDY_UNIT between", value1, value2, "subsidyUnit");
            return (Criteria) this;
        }

        public Criteria andSubsidyUnitNotBetween(String value1, String value2) {
            addCriterion("SUBSIDY_UNIT not between", value1, value2, "subsidyUnit");
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

        public Criteria andUnitCaseIdIsNull() {
            addCriterion("UNIT_CASE_ID is null");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdIsNotNull() {
            addCriterion("UNIT_CASE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdEqualTo(String value) {
            addCriterion("UNIT_CASE_ID =", value, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdNotEqualTo(String value) {
            addCriterion("UNIT_CASE_ID <>", value, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdGreaterThan(String value) {
            addCriterion("UNIT_CASE_ID >", value, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT_CASE_ID >=", value, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdLessThan(String value) {
            addCriterion("UNIT_CASE_ID <", value, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdLessThanOrEqualTo(String value) {
            addCriterion("UNIT_CASE_ID <=", value, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdLike(String value) {
            addCriterion("UNIT_CASE_ID like", value, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdNotLike(String value) {
            addCriterion("UNIT_CASE_ID not like", value, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdIn(List<String> values) {
            addCriterion("UNIT_CASE_ID in", values, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdNotIn(List<String> values) {
            addCriterion("UNIT_CASE_ID not in", values, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdBetween(String value1, String value2) {
            addCriterion("UNIT_CASE_ID between", value1, value2, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andUnitCaseIdNotBetween(String value1, String value2) {
            addCriterion("UNIT_CASE_ID not between", value1, value2, "unitCaseId");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitIsNull() {
            addCriterion("ORI_PERF_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitIsNotNull() {
            addCriterion("ORI_PERF_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitEqualTo(String value) {
            addCriterion("ORI_PERF_UNIT =", value, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitNotEqualTo(String value) {
            addCriterion("ORI_PERF_UNIT <>", value, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitGreaterThan(String value) {
            addCriterion("ORI_PERF_UNIT >", value, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitGreaterThanOrEqualTo(String value) {
            addCriterion("ORI_PERF_UNIT >=", value, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitLessThan(String value) {
            addCriterion("ORI_PERF_UNIT <", value, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitLessThanOrEqualTo(String value) {
            addCriterion("ORI_PERF_UNIT <=", value, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitLike(String value) {
            addCriterion("ORI_PERF_UNIT like", value, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitNotLike(String value) {
            addCriterion("ORI_PERF_UNIT not like", value, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitIn(List<String> values) {
            addCriterion("ORI_PERF_UNIT in", values, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitNotIn(List<String> values) {
            addCriterion("ORI_PERF_UNIT not in", values, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitBetween(String value1, String value2) {
            addCriterion("ORI_PERF_UNIT between", value1, value2, "oriPerfUnit");
            return (Criteria) this;
        }

        public Criteria andOriPerfUnitNotBetween(String value1, String value2) {
            addCriterion("ORI_PERF_UNIT not between", value1, value2, "oriPerfUnit");
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