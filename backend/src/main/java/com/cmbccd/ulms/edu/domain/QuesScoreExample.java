package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class QuesScoreExample {
	 protected String orderByClause;

	    protected boolean distinct;

	    protected List<Criteria> oredCriteria;

	    public QuesScoreExample() {
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

	        public Criteria andJournoIsNull() {
	            addCriterion("JOURNO is null");
	            return (Criteria) this;
	        }

	        public Criteria andJournoIsNotNull() {
	            addCriterion("JOURNO is not null");
	            return (Criteria) this;
	        }

	        public Criteria andJournoEqualTo(String value) {
	            addCriterion("JOURNO =", value, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoNotEqualTo(String value) {
	            addCriterion("JOURNO <>", value, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoGreaterThan(String value) {
	            addCriterion("JOURNO >", value, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoGreaterThanOrEqualTo(String value) {
	            addCriterion("JOURNO >=", value, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoLessThan(String value) {
	            addCriterion("JOURNO <", value, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoLessThanOrEqualTo(String value) {
	            addCriterion("JOURNO <=", value, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoLike(String value) {
	            addCriterion("JOURNO like", value, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoNotLike(String value) {
	            addCriterion("JOURNO not like", value, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoIn(List<String> values) {
	            addCriterion("JOURNO in", values, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoNotIn(List<String> values) {
	            addCriterion("JOURNO not in", values, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoBetween(String value1, String value2) {
	            addCriterion("JOURNO between", value1, value2, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andJournoNotBetween(String value1, String value2) {
	            addCriterion("JOURNO not between", value1, value2, "journo");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeIsNull() {
	            addCriterion("EXAM_CODE is null");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeIsNotNull() {
	            addCriterion("EXAM_CODE is not null");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeEqualTo(String value) {
	            addCriterion("EXAM_CODE =", value, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeNotEqualTo(String value) {
	            addCriterion("EXAM_CODE <>", value, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeGreaterThan(String value) {
	            addCriterion("EXAM_CODE >", value, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeGreaterThanOrEqualTo(String value) {
	            addCriterion("EXAM_CODE >=", value, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeLessThan(String value) {
	            addCriterion("EXAM_CODE <", value, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeLessThanOrEqualTo(String value) {
	            addCriterion("EXAM_CODE <=", value, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeLike(String value) {
	            addCriterion("EXAM_CODE like", value, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeNotLike(String value) {
	            addCriterion("EXAM_CODE not like", value, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeIn(List<String> values) {
	            addCriterion("EXAM_CODE in", values, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeNotIn(List<String> values) {
	            addCriterion("EXAM_CODE not in", values, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeBetween(String value1, String value2) {
	            addCriterion("EXAM_CODE between", value1, value2, "examCode");
	            return (Criteria) this;
	        }

	        public Criteria andExamCodeNotBetween(String value1, String value2) {
	            addCriterion("EXAM_CODE not between", value1, value2, "examCode");
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

	        public Criteria andBegDateIsNull() {
	            addCriterion("BEG_DATE is null");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateIsNotNull() {
	            addCriterion("BEG_DATE is not null");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateEqualTo(String value) {
	            addCriterion("BEG_DATE =", value, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateNotEqualTo(String value) {
	            addCriterion("BEG_DATE <>", value, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateGreaterThan(String value) {
	            addCriterion("BEG_DATE >", value, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateGreaterThanOrEqualTo(String value) {
	            addCriterion("BEG_DATE >=", value, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateLessThan(String value) {
	            addCriterion("BEG_DATE <", value, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateLessThanOrEqualTo(String value) {
	            addCriterion("BEG_DATE <=", value, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateLike(String value) {
	            addCriterion("BEG_DATE like", value, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateNotLike(String value) {
	            addCriterion("BEG_DATE not like", value, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateIn(List<String> values) {
	            addCriterion("BEG_DATE in", values, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateNotIn(List<String> values) {
	            addCriterion("BEG_DATE not in", values, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateBetween(String value1, String value2) {
	            addCriterion("BEG_DATE between", value1, value2, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andBegDateNotBetween(String value1, String value2) {
	            addCriterion("BEG_DATE not between", value1, value2, "begDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateIsNull() {
	            addCriterion("END_DATE is null");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateIsNotNull() {
	            addCriterion("END_DATE is not null");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateEqualTo(String value) {
	            addCriterion("END_DATE =", value, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateNotEqualTo(String value) {
	            addCriterion("END_DATE <>", value, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateGreaterThan(String value) {
	            addCriterion("END_DATE >", value, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateGreaterThanOrEqualTo(String value) {
	            addCriterion("END_DATE >=", value, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateLessThan(String value) {
	            addCriterion("END_DATE <", value, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateLessThanOrEqualTo(String value) {
	            addCriterion("END_DATE <=", value, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateLike(String value) {
	            addCriterion("END_DATE like", value, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateNotLike(String value) {
	            addCriterion("END_DATE not like", value, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateIn(List<String> values) {
	            addCriterion("END_DATE in", values, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateNotIn(List<String> values) {
	            addCriterion("END_DATE not in", values, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateBetween(String value1, String value2) {
	            addCriterion("END_DATE between", value1, value2, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andEndDateNotBetween(String value1, String value2) {
	            addCriterion("END_DATE not between", value1, value2, "endDate");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreIsNull() {
	            addCriterion("USER_SCORE is null");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreIsNotNull() {
	            addCriterion("USER_SCORE is not null");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreEqualTo(Double value) {
	            addCriterion("USER_SCORE =", value, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreNotEqualTo(Double value) {
	            addCriterion("USER_SCORE <>", value, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreGreaterThan(Double value) {
	            addCriterion("USER_SCORE >", value, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreGreaterThanOrEqualTo(Double value) {
	            addCriterion("USER_SCORE >=", value, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreLessThan(Double value) {
	            addCriterion("USER_SCORE <", value, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreLessThanOrEqualTo(Double value) {
	            addCriterion("USER_SCORE <=", value, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreIn(List<Double> values) {
	            addCriterion("USER_SCORE in", values, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreNotIn(List<Double> values) {
	            addCriterion("USER_SCORE not in", values, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreBetween(Double value1, Double value2) {
	            addCriterion("USER_SCORE between", value1, value2, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andUserScoreNotBetween(Double value1, Double value2) {
	            addCriterion("USER_SCORE not between", value1, value2, "userScore");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatIsNull() {
	            addCriterion("COMP_STAT is null");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatIsNotNull() {
	            addCriterion("COMP_STAT is not null");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatEqualTo(Integer value) {
	            addCriterion("COMP_STAT =", value, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatNotEqualTo(Integer value) {
	            addCriterion("COMP_STAT <>", value, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatGreaterThan(Integer value) {
	            addCriterion("COMP_STAT >", value, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatGreaterThanOrEqualTo(Integer value) {
	            addCriterion("COMP_STAT >=", value, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatLessThan(Integer value) {
	            addCriterion("COMP_STAT <", value, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatLessThanOrEqualTo(Integer value) {
	            addCriterion("COMP_STAT <=", value, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatIn(List<Integer> values) {
	            addCriterion("COMP_STAT in", values, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatNotIn(List<Integer> values) {
	            addCriterion("COMP_STAT not in", values, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatBetween(Integer value1, Integer value2) {
	            addCriterion("COMP_STAT between", value1, value2, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andCompStatNotBetween(Integer value1, Integer value2) {
	            addCriterion("COMP_STAT not between", value1, value2, "compStat");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpIsNull() {
	            addCriterion("HANDLE_IP is null");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpIsNotNull() {
	            addCriterion("HANDLE_IP is not null");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpEqualTo(String value) {
	            addCriterion("HANDLE_IP =", value, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpNotEqualTo(String value) {
	            addCriterion("HANDLE_IP <>", value, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpGreaterThan(String value) {
	            addCriterion("HANDLE_IP >", value, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpGreaterThanOrEqualTo(String value) {
	            addCriterion("HANDLE_IP >=", value, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpLessThan(String value) {
	            addCriterion("HANDLE_IP <", value, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpLessThanOrEqualTo(String value) {
	            addCriterion("HANDLE_IP <=", value, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpLike(String value) {
	            addCriterion("HANDLE_IP like", value, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpNotLike(String value) {
	            addCriterion("HANDLE_IP not like", value, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpIn(List<String> values) {
	            addCriterion("HANDLE_IP in", values, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpNotIn(List<String> values) {
	            addCriterion("HANDLE_IP not in", values, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpBetween(String value1, String value2) {
	            addCriterion("HANDLE_IP between", value1, value2, "handleIp");
	            return (Criteria) this;
	        }

	        public Criteria andHandleIpNotBetween(String value1, String value2) {
	            addCriterion("HANDLE_IP not between", value1, value2, "handleIp");
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