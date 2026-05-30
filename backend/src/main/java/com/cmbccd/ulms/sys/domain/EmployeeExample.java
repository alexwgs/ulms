package com.cmbccd.ulms.sys.domain;

import java.util.ArrayList;
import java.util.List;

public class EmployeeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmployeeExample() {
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

        public Criteria andBatchGroupIsNull() {
            addCriterion("BATCH_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andBatchGroupIsNotNull() {
            addCriterion("BATCH_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andBatchGroupEqualTo(String value) {
            addCriterion("BATCH_GROUP =", value, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupNotEqualTo(String value) {
            addCriterion("BATCH_GROUP <>", value, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupGreaterThan(String value) {
            addCriterion("BATCH_GROUP >", value, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_GROUP >=", value, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupLessThan(String value) {
            addCriterion("BATCH_GROUP <", value, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupLessThanOrEqualTo(String value) {
            addCriterion("BATCH_GROUP <=", value, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupLike(String value) {
            addCriterion("BATCH_GROUP like", value, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupNotLike(String value) {
            addCriterion("BATCH_GROUP not like", value, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupIn(List<String> values) {
            addCriterion("BATCH_GROUP in", values, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupNotIn(List<String> values) {
            addCriterion("BATCH_GROUP not in", values, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupBetween(String value1, String value2) {
            addCriterion("BATCH_GROUP between", value1, value2, "batchGroup");
            return (Criteria) this;
        }

        public Criteria andBatchGroupNotBetween(String value1, String value2) {
            addCriterion("BATCH_GROUP not between", value1, value2, "batchGroup");
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

        public Criteria andInDateIsNull() {
            addCriterion("IN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andInDateIsNotNull() {
            addCriterion("IN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andInDateEqualTo(String value) {
            addCriterion("IN_DATE =", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateNotEqualTo(String value) {
            addCriterion("IN_DATE <>", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateGreaterThan(String value) {
            addCriterion("IN_DATE >", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateGreaterThanOrEqualTo(String value) {
            addCriterion("IN_DATE >=", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateLessThan(String value) {
            addCriterion("IN_DATE <", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateLessThanOrEqualTo(String value) {
            addCriterion("IN_DATE <=", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateLike(String value) {
            addCriterion("IN_DATE like", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateNotLike(String value) {
            addCriterion("IN_DATE not like", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateIn(List<String> values) {
            addCriterion("IN_DATE in", values, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateNotIn(List<String> values) {
            addCriterion("IN_DATE not in", values, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateBetween(String value1, String value2) {
            addCriterion("IN_DATE between", value1, value2, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateNotBetween(String value1, String value2) {
            addCriterion("IN_DATE not between", value1, value2, "inDate");
            return (Criteria) this;
        }

        public Criteria andOutDateIsNull() {
            addCriterion("OUT_DATE is null");
            return (Criteria) this;
        }

        public Criteria andOutDateIsNotNull() {
            addCriterion("OUT_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andOutDateEqualTo(String value) {
            addCriterion("OUT_DATE =", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateNotEqualTo(String value) {
            addCriterion("OUT_DATE <>", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateGreaterThan(String value) {
            addCriterion("OUT_DATE >", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateGreaterThanOrEqualTo(String value) {
            addCriterion("OUT_DATE >=", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateLessThan(String value) {
            addCriterion("OUT_DATE <", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateLessThanOrEqualTo(String value) {
            addCriterion("OUT_DATE <=", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateLike(String value) {
            addCriterion("OUT_DATE like", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateNotLike(String value) {
            addCriterion("OUT_DATE not like", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateIn(List<String> values) {
            addCriterion("OUT_DATE in", values, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateNotIn(List<String> values) {
            addCriterion("OUT_DATE not in", values, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateBetween(String value1, String value2) {
            addCriterion("OUT_DATE between", value1, value2, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateNotBetween(String value1, String value2) {
            addCriterion("OUT_DATE not between", value1, value2, "outDate");
            return (Criteria) this;
        }

        public Criteria andPloDescIsNull() {
            addCriterion("PLO_DESC is null");
            return (Criteria) this;
        }

        public Criteria andPloDescIsNotNull() {
            addCriterion("PLO_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andPloDescEqualTo(String value) {
            addCriterion("PLO_DESC =", value, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescNotEqualTo(String value) {
            addCriterion("PLO_DESC <>", value, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescGreaterThan(String value) {
            addCriterion("PLO_DESC >", value, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescGreaterThanOrEqualTo(String value) {
            addCriterion("PLO_DESC >=", value, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescLessThan(String value) {
            addCriterion("PLO_DESC <", value, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescLessThanOrEqualTo(String value) {
            addCriterion("PLO_DESC <=", value, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescLike(String value) {
            addCriterion("PLO_DESC like", value, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescNotLike(String value) {
            addCriterion("PLO_DESC not like", value, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescIn(List<String> values) {
            addCriterion("PLO_DESC in", values, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescNotIn(List<String> values) {
            addCriterion("PLO_DESC not in", values, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescBetween(String value1, String value2) {
            addCriterion("PLO_DESC between", value1, value2, "ploDesc");
            return (Criteria) this;
        }

        public Criteria andPloDescNotBetween(String value1, String value2) {
            addCriterion("PLO_DESC not between", value1, value2, "ploDesc");
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

        public Criteria andFlagIsNull() {
            addCriterion("FLAG is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(String value) {
            addCriterion("FLAG =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(String value) {
            addCriterion("FLAG <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(String value) {
            addCriterion("FLAG >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(String value) {
            addCriterion("FLAG >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(String value) {
            addCriterion("FLAG <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(String value) {
            addCriterion("FLAG <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLike(String value) {
            addCriterion("FLAG like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotLike(String value) {
            addCriterion("FLAG not like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<String> values) {
            addCriterion("FLAG in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<String> values) {
            addCriterion("FLAG not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(String value1, String value2) {
            addCriterion("FLAG between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(String value1, String value2) {
            addCriterion("FLAG not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andEngNameIsNull() {
            addCriterion("ENG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andEngNameIsNotNull() {
            addCriterion("ENG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andEngNameEqualTo(String value) {
            addCriterion("ENG_NAME =", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotEqualTo(String value) {
            addCriterion("ENG_NAME <>", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameGreaterThan(String value) {
            addCriterion("ENG_NAME >", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameGreaterThanOrEqualTo(String value) {
            addCriterion("ENG_NAME >=", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLessThan(String value) {
            addCriterion("ENG_NAME <", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLessThanOrEqualTo(String value) {
            addCriterion("ENG_NAME <=", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLike(String value) {
            addCriterion("ENG_NAME like", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotLike(String value) {
            addCriterion("ENG_NAME not like", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameIn(List<String> values) {
            addCriterion("ENG_NAME in", values, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotIn(List<String> values) {
            addCriterion("ENG_NAME not in", values, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameBetween(String value1, String value2) {
            addCriterion("ENG_NAME between", value1, value2, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotBetween(String value1, String value2) {
            addCriterion("ENG_NAME not between", value1, value2, "engName");
            return (Criteria) this;
        }

        public Criteria andPloAddrIsNull() {
            addCriterion("PLO_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andPloAddrIsNotNull() {
            addCriterion("PLO_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andPloAddrEqualTo(String value) {
            addCriterion("PLO_ADDR =", value, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrNotEqualTo(String value) {
            addCriterion("PLO_ADDR <>", value, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrGreaterThan(String value) {
            addCriterion("PLO_ADDR >", value, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrGreaterThanOrEqualTo(String value) {
            addCriterion("PLO_ADDR >=", value, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrLessThan(String value) {
            addCriterion("PLO_ADDR <", value, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrLessThanOrEqualTo(String value) {
            addCriterion("PLO_ADDR <=", value, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrLike(String value) {
            addCriterion("PLO_ADDR like", value, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrNotLike(String value) {
            addCriterion("PLO_ADDR not like", value, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrIn(List<String> values) {
            addCriterion("PLO_ADDR in", values, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrNotIn(List<String> values) {
            addCriterion("PLO_ADDR not in", values, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrBetween(String value1, String value2) {
            addCriterion("PLO_ADDR between", value1, value2, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloAddrNotBetween(String value1, String value2) {
            addCriterion("PLO_ADDR not between", value1, value2, "ploAddr");
            return (Criteria) this;
        }

        public Criteria andPloMobilIsNull() {
            addCriterion("PLO_MOBIL is null");
            return (Criteria) this;
        }

        public Criteria andPloMobilIsNotNull() {
            addCriterion("PLO_MOBIL is not null");
            return (Criteria) this;
        }

        public Criteria andPloMobilEqualTo(String value) {
            addCriterion("PLO_MOBIL =", value, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilNotEqualTo(String value) {
            addCriterion("PLO_MOBIL <>", value, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilGreaterThan(String value) {
            addCriterion("PLO_MOBIL >", value, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilGreaterThanOrEqualTo(String value) {
            addCriterion("PLO_MOBIL >=", value, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilLessThan(String value) {
            addCriterion("PLO_MOBIL <", value, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilLessThanOrEqualTo(String value) {
            addCriterion("PLO_MOBIL <=", value, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilLike(String value) {
            addCriterion("PLO_MOBIL like", value, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilNotLike(String value) {
            addCriterion("PLO_MOBIL not like", value, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilIn(List<String> values) {
            addCriterion("PLO_MOBIL in", values, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilNotIn(List<String> values) {
            addCriterion("PLO_MOBIL not in", values, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilBetween(String value1, String value2) {
            addCriterion("PLO_MOBIL between", value1, value2, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andPloMobilNotBetween(String value1, String value2) {
            addCriterion("PLO_MOBIL not between", value1, value2, "ploMobil");
            return (Criteria) this;
        }

        public Criteria andJobStatusIsNull() {
            addCriterion("JOB_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andJobStatusIsNotNull() {
            addCriterion("JOB_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andJobStatusEqualTo(String value) {
            addCriterion("JOB_STATUS =", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusNotEqualTo(String value) {
            addCriterion("JOB_STATUS <>", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusGreaterThan(String value) {
            addCriterion("JOB_STATUS >", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusGreaterThanOrEqualTo(String value) {
            addCriterion("JOB_STATUS >=", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusLessThan(String value) {
            addCriterion("JOB_STATUS <", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusLessThanOrEqualTo(String value) {
            addCriterion("JOB_STATUS <=", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusLike(String value) {
            addCriterion("JOB_STATUS like", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusNotLike(String value) {
            addCriterion("JOB_STATUS not like", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusIn(List<String> values) {
            addCriterion("JOB_STATUS in", values, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusNotIn(List<String> values) {
            addCriterion("JOB_STATUS not in", values, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusBetween(String value1, String value2) {
            addCriterion("JOB_STATUS between", value1, value2, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusNotBetween(String value1, String value2) {
            addCriterion("JOB_STATUS not between", value1, value2, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbIsNull() {
            addCriterion("OFFICE_CMB is null");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbIsNotNull() {
            addCriterion("OFFICE_CMB is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbEqualTo(String value) {
            addCriterion("OFFICE_CMB =", value, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbNotEqualTo(String value) {
            addCriterion("OFFICE_CMB <>", value, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbGreaterThan(String value) {
            addCriterion("OFFICE_CMB >", value, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbGreaterThanOrEqualTo(String value) {
            addCriterion("OFFICE_CMB >=", value, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbLessThan(String value) {
            addCriterion("OFFICE_CMB <", value, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbLessThanOrEqualTo(String value) {
            addCriterion("OFFICE_CMB <=", value, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbLike(String value) {
            addCriterion("OFFICE_CMB like", value, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbNotLike(String value) {
            addCriterion("OFFICE_CMB not like", value, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbIn(List<String> values) {
            addCriterion("OFFICE_CMB in", values, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbNotIn(List<String> values) {
            addCriterion("OFFICE_CMB not in", values, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbBetween(String value1, String value2) {
            addCriterion("OFFICE_CMB between", value1, value2, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andOfficeCmbNotBetween(String value1, String value2) {
            addCriterion("OFFICE_CMB not between", value1, value2, "officeCmb");
            return (Criteria) this;
        }

        public Criteria andTurnTypeIsNull() {
            addCriterion("TURN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTurnTypeIsNotNull() {
            addCriterion("TURN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTurnTypeEqualTo(String value) {
            addCriterion("TURN_TYPE =", value, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeNotEqualTo(String value) {
            addCriterion("TURN_TYPE <>", value, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeGreaterThan(String value) {
            addCriterion("TURN_TYPE >", value, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TURN_TYPE >=", value, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeLessThan(String value) {
            addCriterion("TURN_TYPE <", value, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeLessThanOrEqualTo(String value) {
            addCriterion("TURN_TYPE <=", value, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeLike(String value) {
            addCriterion("TURN_TYPE like", value, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeNotLike(String value) {
            addCriterion("TURN_TYPE not like", value, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeIn(List<String> values) {
            addCriterion("TURN_TYPE in", values, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeNotIn(List<String> values) {
            addCriterion("TURN_TYPE not in", values, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeBetween(String value1, String value2) {
            addCriterion("TURN_TYPE between", value1, value2, "turnType");
            return (Criteria) this;
        }

        public Criteria andTurnTypeNotBetween(String value1, String value2) {
            addCriterion("TURN_TYPE not between", value1, value2, "turnType");
            return (Criteria) this;
        }

        public Criteria andPzjcIsNull() {
            addCriterion("PZJC is null");
            return (Criteria) this;
        }

        public Criteria andPzjcIsNotNull() {
            addCriterion("PZJC is not null");
            return (Criteria) this;
        }

        public Criteria andPzjcEqualTo(String value) {
            addCriterion("PZJC =", value, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcNotEqualTo(String value) {
            addCriterion("PZJC <>", value, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcGreaterThan(String value) {
            addCriterion("PZJC >", value, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcGreaterThanOrEqualTo(String value) {
            addCriterion("PZJC >=", value, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcLessThan(String value) {
            addCriterion("PZJC <", value, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcLessThanOrEqualTo(String value) {
            addCriterion("PZJC <=", value, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcLike(String value) {
            addCriterion("PZJC like", value, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcNotLike(String value) {
            addCriterion("PZJC not like", value, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcIn(List<String> values) {
            addCriterion("PZJC in", values, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcNotIn(List<String> values) {
            addCriterion("PZJC not in", values, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcBetween(String value1, String value2) {
            addCriterion("PZJC between", value1, value2, "pzjc");
            return (Criteria) this;
        }

        public Criteria andPzjcNotBetween(String value1, String value2) {
            addCriterion("PZJC not between", value1, value2, "pzjc");
            return (Criteria) this;
        }
        
        public Criteria andAvatarIsNull() {
            addCriterion("Avatar is null");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNotNull() {
            addCriterion("Avatar is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarEqualTo(String value) {
            addCriterion("Avatar =", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotEqualTo(String value) {
            addCriterion("Avatar <>", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThan(String value) {
            addCriterion("Avatar >", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("Avatar >=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThan(String value) {
            addCriterion("Avatar <", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThanOrEqualTo(String value) {
            addCriterion("Avatar <=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLike(String value) {
            addCriterion("Avatar like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotLike(String value) {
            addCriterion("Avatar not like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarIn(List<String> values) {
            addCriterion("Avatar in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotIn(List<String> values) {
            addCriterion("Avatar not in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarBetween(String value1, String value2) {
            addCriterion("Avatar between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotBetween(String value1, String value2) {
            addCriterion("Avatar not between", value1, value2, "avatar");
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