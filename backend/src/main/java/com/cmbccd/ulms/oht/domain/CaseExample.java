package com.cmbccd.ulms.oht.domain;

import java.util.ArrayList;
import java.util.List;
/**
 * 
* <p>Title: CaseExample.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public class CaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CaseExample() {
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

        public Criteria andDataTimeIsNull() {
            addCriterion("DATA_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDataTimeIsNotNull() {
            addCriterion("DATA_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDataTimeEqualTo(String value) {
            addCriterion("DATA_TIME =", value, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeNotEqualTo(String value) {
            addCriterion("DATA_TIME <>", value, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeGreaterThan(String value) {
            addCriterion("DATA_TIME >", value, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_TIME >=", value, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeLessThan(String value) {
            addCriterion("DATA_TIME <", value, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeLessThanOrEqualTo(String value) {
            addCriterion("DATA_TIME <=", value, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeLike(String value) {
            addCriterion("DATA_TIME like", value, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeNotLike(String value) {
            addCriterion("DATA_TIME not like", value, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeIn(List<String> values) {
            addCriterion("DATA_TIME in", values, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeNotIn(List<String> values) {
            addCriterion("DATA_TIME not in", values, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeBetween(String value1, String value2) {
            addCriterion("DATA_TIME between", value1, value2, "dataTime");
            return (Criteria) this;
        }

        public Criteria andDataTimeNotBetween(String value1, String value2) {
            addCriterion("DATA_TIME not between", value1, value2, "dataTime");
            return (Criteria) this;
        }

        public Criteria andBuildIdIsNull() {
            addCriterion("BUILD_ID is null");
            return (Criteria) this;
        }

        public Criteria andBuildIdIsNotNull() {
            addCriterion("BUILD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBuildIdEqualTo(String value) {
            addCriterion("BUILD_ID =", value, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdNotEqualTo(String value) {
            addCriterion("BUILD_ID <>", value, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdGreaterThan(String value) {
            addCriterion("BUILD_ID >", value, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUILD_ID >=", value, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdLessThan(String value) {
            addCriterion("BUILD_ID <", value, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdLessThanOrEqualTo(String value) {
            addCriterion("BUILD_ID <=", value, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdLike(String value) {
            addCriterion("BUILD_ID like", value, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdNotLike(String value) {
            addCriterion("BUILD_ID not like", value, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdIn(List<String> values) {
            addCriterion("BUILD_ID in", values, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdNotIn(List<String> values) {
            addCriterion("BUILD_ID not in", values, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdBetween(String value1, String value2) {
            addCriterion("BUILD_ID between", value1, value2, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildIdNotBetween(String value1, String value2) {
            addCriterion("BUILD_ID not between", value1, value2, "buildId");
            return (Criteria) this;
        }

        public Criteria andBuildDeptIsNull() {
            addCriterion("BUILD_DEPT is null");
            return (Criteria) this;
        }

        public Criteria andBuildDeptIsNotNull() {
            addCriterion("BUILD_DEPT is not null");
            return (Criteria) this;
        }

        public Criteria andBuildDeptEqualTo(String value) {
            addCriterion("BUILD_DEPT =", value, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptNotEqualTo(String value) {
            addCriterion("BUILD_DEPT <>", value, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptGreaterThan(String value) {
            addCriterion("BUILD_DEPT >", value, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptGreaterThanOrEqualTo(String value) {
            addCriterion("BUILD_DEPT >=", value, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptLessThan(String value) {
            addCriterion("BUILD_DEPT <", value, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptLessThanOrEqualTo(String value) {
            addCriterion("BUILD_DEPT <=", value, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptLike(String value) {
            addCriterion("BUILD_DEPT like", value, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptNotLike(String value) {
            addCriterion("BUILD_DEPT not like", value, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptIn(List<String> values) {
            addCriterion("BUILD_DEPT in", values, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptNotIn(List<String> values) {
            addCriterion("BUILD_DEPT not in", values, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptBetween(String value1, String value2) {
            addCriterion("BUILD_DEPT between", value1, value2, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildDeptNotBetween(String value1, String value2) {
            addCriterion("BUILD_DEPT not between", value1, value2, "buildDept");
            return (Criteria) this;
        }

        public Criteria andBuildGroupIsNull() {
            addCriterion("BUILD_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andBuildGroupIsNotNull() {
            addCriterion("BUILD_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andBuildGroupEqualTo(String value) {
            addCriterion("BUILD_GROUP =", value, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupNotEqualTo(String value) {
            addCriterion("BUILD_GROUP <>", value, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupGreaterThan(String value) {
            addCriterion("BUILD_GROUP >", value, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupGreaterThanOrEqualTo(String value) {
            addCriterion("BUILD_GROUP >=", value, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupLessThan(String value) {
            addCriterion("BUILD_GROUP <", value, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupLessThanOrEqualTo(String value) {
            addCriterion("BUILD_GROUP <=", value, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupLike(String value) {
            addCriterion("BUILD_GROUP like", value, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupNotLike(String value) {
            addCriterion("BUILD_GROUP not like", value, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupIn(List<String> values) {
            addCriterion("BUILD_GROUP in", values, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupNotIn(List<String> values) {
            addCriterion("BUILD_GROUP not in", values, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupBetween(String value1, String value2) {
            addCriterion("BUILD_GROUP between", value1, value2, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildGroupNotBetween(String value1, String value2) {
            addCriterion("BUILD_GROUP not between", value1, value2, "buildGroup");
            return (Criteria) this;
        }

        public Criteria andBuildExtnIsNull() {
            addCriterion("BUILD_EXTN is null");
            return (Criteria) this;
        }

        public Criteria andBuildExtnIsNotNull() {
            addCriterion("BUILD_EXTN is not null");
            return (Criteria) this;
        }

        public Criteria andBuildExtnEqualTo(String value) {
            addCriterion("BUILD_EXTN =", value, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnNotEqualTo(String value) {
            addCriterion("BUILD_EXTN <>", value, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnGreaterThan(String value) {
            addCriterion("BUILD_EXTN >", value, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnGreaterThanOrEqualTo(String value) {
            addCriterion("BUILD_EXTN >=", value, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnLessThan(String value) {
            addCriterion("BUILD_EXTN <", value, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnLessThanOrEqualTo(String value) {
            addCriterion("BUILD_EXTN <=", value, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnLike(String value) {
            addCriterion("BUILD_EXTN like", value, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnNotLike(String value) {
            addCriterion("BUILD_EXTN not like", value, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnIn(List<String> values) {
            addCriterion("BUILD_EXTN in", values, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnNotIn(List<String> values) {
            addCriterion("BUILD_EXTN not in", values, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnBetween(String value1, String value2) {
            addCriterion("BUILD_EXTN between", value1, value2, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildExtnNotBetween(String value1, String value2) {
            addCriterion("BUILD_EXTN not between", value1, value2, "buildExtn");
            return (Criteria) this;
        }

        public Criteria andBuildTimeIsNull() {
            addCriterion("BUILD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andBuildTimeIsNotNull() {
            addCriterion("BUILD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andBuildTimeEqualTo(Integer value) {
            addCriterion("BUILD_TIME =", value, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeNotEqualTo(Integer value) {
            addCriterion("BUILD_TIME <>", value, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeGreaterThan(Integer value) {
            addCriterion("BUILD_TIME >", value, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("BUILD_TIME >=", value, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeLessThan(Integer value) {
            addCriterion("BUILD_TIME <", value, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeLessThanOrEqualTo(Integer value) {
            addCriterion("BUILD_TIME <=", value, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeIn(List<Integer> values) {
            addCriterion("BUILD_TIME in", values, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeNotIn(List<Integer> values) {
            addCriterion("BUILD_TIME not in", values, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeBetween(Integer value1, Integer value2) {
            addCriterion("BUILD_TIME between", value1, value2, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBuildTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("BUILD_TIME not between", value1, value2, "buildTime");
            return (Criteria) this;
        }

        public Criteria andBcompTypeIsNull() {
            addCriterion("BCOMP_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBcompTypeIsNotNull() {
            addCriterion("BCOMP_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBcompTypeEqualTo(Integer value) {
            addCriterion("BCOMP_TYPE =", value, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeNotEqualTo(Integer value) {
            addCriterion("BCOMP_TYPE <>", value, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeGreaterThan(Integer value) {
            addCriterion("BCOMP_TYPE >", value, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("BCOMP_TYPE >=", value, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeLessThan(Integer value) {
            addCriterion("BCOMP_TYPE <", value, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeLessThanOrEqualTo(Integer value) {
            addCriterion("BCOMP_TYPE <=", value, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeIn(List<Integer> values) {
            addCriterion("BCOMP_TYPE in", values, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeNotIn(List<Integer> values) {
            addCriterion("BCOMP_TYPE not in", values, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeBetween(Integer value1, Integer value2) {
            addCriterion("BCOMP_TYPE between", value1, value2, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("BCOMP_TYPE not between", value1, value2, "bcompType");
            return (Criteria) this;
        }

        public Criteria andBcompMemoIsNull() {
            addCriterion("BCOMP_MEMO is null");
            return (Criteria) this;
        }

        public Criteria andBcompMemoIsNotNull() {
            addCriterion("BCOMP_MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andBcompMemoEqualTo(String value) {
            addCriterion("BCOMP_MEMO =", value, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoNotEqualTo(String value) {
            addCriterion("BCOMP_MEMO <>", value, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoGreaterThan(String value) {
            addCriterion("BCOMP_MEMO >", value, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoGreaterThanOrEqualTo(String value) {
            addCriterion("BCOMP_MEMO >=", value, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoLessThan(String value) {
            addCriterion("BCOMP_MEMO <", value, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoLessThanOrEqualTo(String value) {
            addCriterion("BCOMP_MEMO <=", value, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoLike(String value) {
            addCriterion("BCOMP_MEMO like", value, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoNotLike(String value) {
            addCriterion("BCOMP_MEMO not like", value, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoIn(List<String> values) {
            addCriterion("BCOMP_MEMO in", values, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoNotIn(List<String> values) {
            addCriterion("BCOMP_MEMO not in", values, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoBetween(String value1, String value2) {
            addCriterion("BCOMP_MEMO between", value1, value2, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompMemoNotBetween(String value1, String value2) {
            addCriterion("BCOMP_MEMO not between", value1, value2, "bcompMemo");
            return (Criteria) this;
        }

        public Criteria andBcompTimeIsNull() {
            addCriterion("BCOMP_TIME is null");
            return (Criteria) this;
        }

        public Criteria andBcompTimeIsNotNull() {
            addCriterion("BCOMP_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andBcompTimeEqualTo(String value) {
            addCriterion("BCOMP_TIME =", value, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeNotEqualTo(String value) {
            addCriterion("BCOMP_TIME <>", value, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeGreaterThan(String value) {
            addCriterion("BCOMP_TIME >", value, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeGreaterThanOrEqualTo(String value) {
            addCriterion("BCOMP_TIME >=", value, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeLessThan(String value) {
            addCriterion("BCOMP_TIME <", value, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeLessThanOrEqualTo(String value) {
            addCriterion("BCOMP_TIME <=", value, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeLike(String value) {
            addCriterion("BCOMP_TIME like", value, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeNotLike(String value) {
            addCriterion("BCOMP_TIME not like", value, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeIn(List<String> values) {
            addCriterion("BCOMP_TIME in", values, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeNotIn(List<String> values) {
            addCriterion("BCOMP_TIME not in", values, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeBetween(String value1, String value2) {
            addCriterion("BCOMP_TIME between", value1, value2, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andBcompTimeNotBetween(String value1, String value2) {
            addCriterion("BCOMP_TIME not between", value1, value2, "bcompTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeIsNull() {
            addCriterion("WAIT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andWaitTimeIsNotNull() {
            addCriterion("WAIT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andWaitTimeEqualTo(Integer value) {
            addCriterion("WAIT_TIME =", value, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeNotEqualTo(Integer value) {
            addCriterion("WAIT_TIME <>", value, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeGreaterThan(Integer value) {
            addCriterion("WAIT_TIME >", value, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("WAIT_TIME >=", value, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeLessThan(Integer value) {
            addCriterion("WAIT_TIME <", value, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeLessThanOrEqualTo(Integer value) {
            addCriterion("WAIT_TIME <=", value, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeIn(List<Integer> values) {
            addCriterion("WAIT_TIME in", values, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeNotIn(List<Integer> values) {
            addCriterion("WAIT_TIME not in", values, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeBetween(Integer value1, Integer value2) {
            addCriterion("WAIT_TIME between", value1, value2, "waitTime");
            return (Criteria) this;
        }

        public Criteria andWaitTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("WAIT_TIME not between", value1, value2, "waitTime");
            return (Criteria) this;
        }

        public Criteria andCaseTypeIsNull() {
            addCriterion("CASE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCaseTypeIsNotNull() {
            addCriterion("CASE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCaseTypeEqualTo(Integer value) {
            addCriterion("CASE_TYPE =", value, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeNotEqualTo(Integer value) {
            addCriterion("CASE_TYPE <>", value, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeGreaterThan(Integer value) {
            addCriterion("CASE_TYPE >", value, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("CASE_TYPE >=", value, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeLessThan(Integer value) {
            addCriterion("CASE_TYPE <", value, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeLessThanOrEqualTo(Integer value) {
            addCriterion("CASE_TYPE <=", value, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeIn(List<Integer> values) {
            addCriterion("CASE_TYPE in", values, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeNotIn(List<Integer> values) {
            addCriterion("CASE_TYPE not in", values, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeBetween(Integer value1, Integer value2) {
            addCriterion("CASE_TYPE between", value1, value2, "caseType");
            return (Criteria) this;
        }

        public Criteria andCaseTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("CASE_TYPE not between", value1, value2, "caseType");
            return (Criteria) this;
        }

        public Criteria andPickIdIsNull() {
            addCriterion("PICK_ID is null");
            return (Criteria) this;
        }

        public Criteria andPickIdIsNotNull() {
            addCriterion("PICK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPickIdEqualTo(String value) {
            addCriterion("PICK_ID =", value, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdNotEqualTo(String value) {
            addCriterion("PICK_ID <>", value, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdGreaterThan(String value) {
            addCriterion("PICK_ID >", value, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdGreaterThanOrEqualTo(String value) {
            addCriterion("PICK_ID >=", value, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdLessThan(String value) {
            addCriterion("PICK_ID <", value, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdLessThanOrEqualTo(String value) {
            addCriterion("PICK_ID <=", value, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdLike(String value) {
            addCriterion("PICK_ID like", value, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdNotLike(String value) {
            addCriterion("PICK_ID not like", value, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdIn(List<String> values) {
            addCriterion("PICK_ID in", values, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdNotIn(List<String> values) {
            addCriterion("PICK_ID not in", values, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdBetween(String value1, String value2) {
            addCriterion("PICK_ID between", value1, value2, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickIdNotBetween(String value1, String value2) {
            addCriterion("PICK_ID not between", value1, value2, "pickId");
            return (Criteria) this;
        }

        public Criteria andPickDeptIsNull() {
            addCriterion("PICK_DEPT is null");
            return (Criteria) this;
        }

        public Criteria andPickDeptIsNotNull() {
            addCriterion("PICK_DEPT is not null");
            return (Criteria) this;
        }

        public Criteria andPickDeptEqualTo(String value) {
            addCriterion("PICK_DEPT =", value, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptNotEqualTo(String value) {
            addCriterion("PICK_DEPT <>", value, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptGreaterThan(String value) {
            addCriterion("PICK_DEPT >", value, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptGreaterThanOrEqualTo(String value) {
            addCriterion("PICK_DEPT >=", value, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptLessThan(String value) {
            addCriterion("PICK_DEPT <", value, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptLessThanOrEqualTo(String value) {
            addCriterion("PICK_DEPT <=", value, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptLike(String value) {
            addCriterion("PICK_DEPT like", value, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptNotLike(String value) {
            addCriterion("PICK_DEPT not like", value, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptIn(List<String> values) {
            addCriterion("PICK_DEPT in", values, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptNotIn(List<String> values) {
            addCriterion("PICK_DEPT not in", values, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptBetween(String value1, String value2) {
            addCriterion("PICK_DEPT between", value1, value2, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickDeptNotBetween(String value1, String value2) {
            addCriterion("PICK_DEPT not between", value1, value2, "pickDept");
            return (Criteria) this;
        }

        public Criteria andPickGroupIsNull() {
            addCriterion("PICK_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andPickGroupIsNotNull() {
            addCriterion("PICK_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andPickGroupEqualTo(String value) {
            addCriterion("PICK_GROUP =", value, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupNotEqualTo(String value) {
            addCriterion("PICK_GROUP <>", value, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupGreaterThan(String value) {
            addCriterion("PICK_GROUP >", value, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupGreaterThanOrEqualTo(String value) {
            addCriterion("PICK_GROUP >=", value, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupLessThan(String value) {
            addCriterion("PICK_GROUP <", value, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupLessThanOrEqualTo(String value) {
            addCriterion("PICK_GROUP <=", value, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupLike(String value) {
            addCriterion("PICK_GROUP like", value, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupNotLike(String value) {
            addCriterion("PICK_GROUP not like", value, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupIn(List<String> values) {
            addCriterion("PICK_GROUP in", values, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupNotIn(List<String> values) {
            addCriterion("PICK_GROUP not in", values, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupBetween(String value1, String value2) {
            addCriterion("PICK_GROUP between", value1, value2, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickGroupNotBetween(String value1, String value2) {
            addCriterion("PICK_GROUP not between", value1, value2, "pickGroup");
            return (Criteria) this;
        }

        public Criteria andPickExtnIsNull() {
            addCriterion("PICK_EXTN is null");
            return (Criteria) this;
        }

        public Criteria andPickExtnIsNotNull() {
            addCriterion("PICK_EXTN is not null");
            return (Criteria) this;
        }

        public Criteria andPickExtnEqualTo(String value) {
            addCriterion("PICK_EXTN =", value, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnNotEqualTo(String value) {
            addCriterion("PICK_EXTN <>", value, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnGreaterThan(String value) {
            addCriterion("PICK_EXTN >", value, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnGreaterThanOrEqualTo(String value) {
            addCriterion("PICK_EXTN >=", value, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnLessThan(String value) {
            addCriterion("PICK_EXTN <", value, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnLessThanOrEqualTo(String value) {
            addCriterion("PICK_EXTN <=", value, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnLike(String value) {
            addCriterion("PICK_EXTN like", value, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnNotLike(String value) {
            addCriterion("PICK_EXTN not like", value, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnIn(List<String> values) {
            addCriterion("PICK_EXTN in", values, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnNotIn(List<String> values) {
            addCriterion("PICK_EXTN not in", values, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnBetween(String value1, String value2) {
            addCriterion("PICK_EXTN between", value1, value2, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickExtnNotBetween(String value1, String value2) {
            addCriterion("PICK_EXTN not between", value1, value2, "pickExtn");
            return (Criteria) this;
        }

        public Criteria andPickTimeIsNull() {
            addCriterion("PICK_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPickTimeIsNotNull() {
            addCriterion("PICK_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPickTimeEqualTo(Integer value) {
            addCriterion("PICK_TIME =", value, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeNotEqualTo(Integer value) {
            addCriterion("PICK_TIME <>", value, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeGreaterThan(Integer value) {
            addCriterion("PICK_TIME >", value, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICK_TIME >=", value, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeLessThan(Integer value) {
            addCriterion("PICK_TIME <", value, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeLessThanOrEqualTo(Integer value) {
            addCriterion("PICK_TIME <=", value, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeIn(List<Integer> values) {
            addCriterion("PICK_TIME in", values, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeNotIn(List<Integer> values) {
            addCriterion("PICK_TIME not in", values, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeBetween(Integer value1, Integer value2) {
            addCriterion("PICK_TIME between", value1, value2, "pickTime");
            return (Criteria) this;
        }

        public Criteria andPickTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("PICK_TIME not between", value1, value2, "pickTime");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNull() {
            addCriterion("CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNotNull() {
            addCriterion("CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCustIdEqualTo(String value) {
            addCriterion("CUST_ID =", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotEqualTo(String value) {
            addCriterion("CUST_ID <>", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThan(String value) {
            addCriterion("CUST_ID >", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("CUST_ID >=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThan(String value) {
            addCriterion("CUST_ID <", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThanOrEqualTo(String value) {
            addCriterion("CUST_ID <=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLike(String value) {
            addCriterion("CUST_ID like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotLike(String value) {
            addCriterion("CUST_ID not like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdIn(List<String> values) {
            addCriterion("CUST_ID in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotIn(List<String> values) {
            addCriterion("CUST_ID not in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdBetween(String value1, String value2) {
            addCriterion("CUST_ID between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotBetween(String value1, String value2) {
            addCriterion("CUST_ID not between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andPcompTypeIsNull() {
            addCriterion("PCOMP_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPcompTypeIsNotNull() {
            addCriterion("PCOMP_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPcompTypeEqualTo(Integer value) {
            addCriterion("PCOMP_TYPE =", value, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeNotEqualTo(Integer value) {
            addCriterion("PCOMP_TYPE <>", value, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeGreaterThan(Integer value) {
            addCriterion("PCOMP_TYPE >", value, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PCOMP_TYPE >=", value, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeLessThan(Integer value) {
            addCriterion("PCOMP_TYPE <", value, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeLessThanOrEqualTo(Integer value) {
            addCriterion("PCOMP_TYPE <=", value, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeIn(List<Integer> values) {
            addCriterion("PCOMP_TYPE in", values, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeNotIn(List<Integer> values) {
            addCriterion("PCOMP_TYPE not in", values, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeBetween(Integer value1, Integer value2) {
            addCriterion("PCOMP_TYPE between", value1, value2, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PCOMP_TYPE not between", value1, value2, "pcompType");
            return (Criteria) this;
        }

        public Criteria andPcompMemoIsNull() {
            addCriterion("PCOMP_MEMO is null");
            return (Criteria) this;
        }

        public Criteria andPcompMemoIsNotNull() {
            addCriterion("PCOMP_MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andPcompMemoEqualTo(String value) {
            addCriterion("PCOMP_MEMO =", value, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoNotEqualTo(String value) {
            addCriterion("PCOMP_MEMO <>", value, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoGreaterThan(String value) {
            addCriterion("PCOMP_MEMO >", value, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoGreaterThanOrEqualTo(String value) {
            addCriterion("PCOMP_MEMO >=", value, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoLessThan(String value) {
            addCriterion("PCOMP_MEMO <", value, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoLessThanOrEqualTo(String value) {
            addCriterion("PCOMP_MEMO <=", value, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoLike(String value) {
            addCriterion("PCOMP_MEMO like", value, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoNotLike(String value) {
            addCriterion("PCOMP_MEMO not like", value, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoIn(List<String> values) {
            addCriterion("PCOMP_MEMO in", values, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoNotIn(List<String> values) {
            addCriterion("PCOMP_MEMO not in", values, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoBetween(String value1, String value2) {
            addCriterion("PCOMP_MEMO between", value1, value2, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompMemoNotBetween(String value1, String value2) {
            addCriterion("PCOMP_MEMO not between", value1, value2, "pcompMemo");
            return (Criteria) this;
        }

        public Criteria andPcompTimeIsNull() {
            addCriterion("PCOMP_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPcompTimeIsNotNull() {
            addCriterion("PCOMP_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPcompTimeEqualTo(String value) {
            addCriterion("PCOMP_TIME =", value, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeNotEqualTo(String value) {
            addCriterion("PCOMP_TIME <>", value, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeGreaterThan(String value) {
            addCriterion("PCOMP_TIME >", value, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeGreaterThanOrEqualTo(String value) {
            addCriterion("PCOMP_TIME >=", value, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeLessThan(String value) {
            addCriterion("PCOMP_TIME <", value, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeLessThanOrEqualTo(String value) {
            addCriterion("PCOMP_TIME <=", value, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeLike(String value) {
            addCriterion("PCOMP_TIME like", value, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeNotLike(String value) {
            addCriterion("PCOMP_TIME not like", value, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeIn(List<String> values) {
            addCriterion("PCOMP_TIME in", values, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeNotIn(List<String> values) {
            addCriterion("PCOMP_TIME not in", values, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeBetween(String value1, String value2) {
            addCriterion("PCOMP_TIME between", value1, value2, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andPcompTimeNotBetween(String value1, String value2) {
            addCriterion("PCOMP_TIME not between", value1, value2, "pcompTime");
            return (Criteria) this;
        }

        public Criteria andCaseStatusIsNull() {
            addCriterion("CASE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCaseStatusIsNotNull() {
            addCriterion("CASE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCaseStatusEqualTo(Integer value) {
            addCriterion("CASE_STATUS =", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusNotEqualTo(Integer value) {
            addCriterion("CASE_STATUS <>", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusGreaterThan(Integer value) {
            addCriterion("CASE_STATUS >", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("CASE_STATUS >=", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusLessThan(Integer value) {
            addCriterion("CASE_STATUS <", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusLessThanOrEqualTo(Integer value) {
            addCriterion("CASE_STATUS <=", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusIn(List<Integer> values) {
            addCriterion("CASE_STATUS in", values, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusNotIn(List<Integer> values) {
            addCriterion("CASE_STATUS not in", values, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusBetween(Integer value1, Integer value2) {
            addCriterion("CASE_STATUS between", value1, value2, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("CASE_STATUS not between", value1, value2, "caseStatus");
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