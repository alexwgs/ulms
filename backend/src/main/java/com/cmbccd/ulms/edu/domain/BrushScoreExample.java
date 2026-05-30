package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class BrushScoreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BrushScoreExample() {
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

        public Criteria andQuesCodeIsNull() {
            addCriterion("QUES_CODE is null");
            return (Criteria) this;
        }

        public Criteria andQuesCodeIsNotNull() {
            addCriterion("QUES_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andQuesCodeEqualTo(String value) {
            addCriterion("QUES_CODE =", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeNotEqualTo(String value) {
            addCriterion("QUES_CODE <>", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeGreaterThan(String value) {
            addCriterion("QUES_CODE >", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeGreaterThanOrEqualTo(String value) {
            addCriterion("QUES_CODE >=", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeLessThan(String value) {
            addCriterion("QUES_CODE <", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeLessThanOrEqualTo(String value) {
            addCriterion("QUES_CODE <=", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeLike(String value) {
            addCriterion("QUES_CODE like", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeNotLike(String value) {
            addCriterion("QUES_CODE not like", value, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeIn(List<String> values) {
            addCriterion("QUES_CODE in", values, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeNotIn(List<String> values) {
            addCriterion("QUES_CODE not in", values, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeBetween(String value1, String value2) {
            addCriterion("QUES_CODE between", value1, value2, "quesCode");
            return (Criteria) this;
        }

        public Criteria andQuesCodeNotBetween(String value1, String value2) {
            addCriterion("QUES_CODE not between", value1, value2, "quesCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeIsNull() {
            addCriterion("LIB_CODE is null");
            return (Criteria) this;
        }

        public Criteria andLibCodeIsNotNull() {
            addCriterion("LIB_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andLibCodeEqualTo(String value) {
            addCriterion("LIB_CODE =", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeNotEqualTo(String value) {
            addCriterion("LIB_CODE <>", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeGreaterThan(String value) {
            addCriterion("LIB_CODE >", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeGreaterThanOrEqualTo(String value) {
            addCriterion("LIB_CODE >=", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeLessThan(String value) {
            addCriterion("LIB_CODE <", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeLessThanOrEqualTo(String value) {
            addCriterion("LIB_CODE <=", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeLike(String value) {
            addCriterion("LIB_CODE like", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeNotLike(String value) {
            addCriterion("LIB_CODE not like", value, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeIn(List<String> values) {
            addCriterion("LIB_CODE in", values, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeNotIn(List<String> values) {
            addCriterion("LIB_CODE not in", values, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeBetween(String value1, String value2) {
            addCriterion("LIB_CODE between", value1, value2, "libCode");
            return (Criteria) this;
        }

        public Criteria andLibCodeNotBetween(String value1, String value2) {
            addCriterion("LIB_CODE not between", value1, value2, "libCode");
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

        public Criteria andAnswerIsNull() {
            addCriterion("ANSWER is null");
            return (Criteria) this;
        }

        public Criteria andAnswerIsNotNull() {
            addCriterion("ANSWER is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerEqualTo(String value) {
            addCriterion("ANSWER =", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotEqualTo(String value) {
            addCriterion("ANSWER <>", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerGreaterThan(String value) {
            addCriterion("ANSWER >", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("ANSWER >=", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLessThan(String value) {
            addCriterion("ANSWER <", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLessThanOrEqualTo(String value) {
            addCriterion("ANSWER <=", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLike(String value) {
            addCriterion("ANSWER like", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotLike(String value) {
            addCriterion("ANSWER not like", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerIn(List<String> values) {
            addCriterion("ANSWER in", values, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotIn(List<String> values) {
            addCriterion("ANSWER not in", values, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerBetween(String value1, String value2) {
            addCriterion("ANSWER between", value1, value2, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotBetween(String value1, String value2) {
            addCriterion("ANSWER not between", value1, value2, "answer");
            return (Criteria) this;
        }

        public Criteria andPassFlagIsNull() {
            addCriterion("PASS_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andPassFlagIsNotNull() {
            addCriterion("PASS_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andPassFlagEqualTo(Integer value) {
            addCriterion("PASS_FLAG =", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagNotEqualTo(Integer value) {
            addCriterion("PASS_FLAG <>", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagGreaterThan(Integer value) {
            addCriterion("PASS_FLAG >", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("PASS_FLAG >=", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagLessThan(Integer value) {
            addCriterion("PASS_FLAG <", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagLessThanOrEqualTo(Integer value) {
            addCriterion("PASS_FLAG <=", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagIn(List<Integer> values) {
            addCriterion("PASS_FLAG in", values, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagNotIn(List<Integer> values) {
            addCriterion("PASS_FLAG not in", values, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagBetween(Integer value1, Integer value2) {
            addCriterion("PASS_FLAG between", value1, value2, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("PASS_FLAG not between", value1, value2, "passFlag");
            return (Criteria) this;
        }

        public Criteria andDataDateIsNull() {
            addCriterion("DATA_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDataDateIsNotNull() {
            addCriterion("DATA_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDataDateEqualTo(String value) {
            addCriterion("DATA_DATE =", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotEqualTo(String value) {
            addCriterion("DATA_DATE <>", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateGreaterThan(String value) {
            addCriterion("DATA_DATE >", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_DATE >=", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLessThan(String value) {
            addCriterion("DATA_DATE <", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLessThanOrEqualTo(String value) {
            addCriterion("DATA_DATE <=", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLike(String value) {
            addCriterion("DATA_DATE like", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotLike(String value) {
            addCriterion("DATA_DATE not like", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateIn(List<String> values) {
            addCriterion("DATA_DATE in", values, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotIn(List<String> values) {
            addCriterion("DATA_DATE not in", values, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateBetween(String value1, String value2) {
            addCriterion("DATA_DATE between", value1, value2, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotBetween(String value1, String value2) {
            addCriterion("DATA_DATE not between", value1, value2, "dataDate");
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

        public Criteria andAnswerDateIsNull() {
            addCriterion("ANSWER_DATE is null");
            return (Criteria) this;
        }

        public Criteria andAnswerDateIsNotNull() {
            addCriterion("ANSWER_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerDateEqualTo(String value) {
            addCriterion("ANSWER_DATE =", value, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateNotEqualTo(String value) {
            addCriterion("ANSWER_DATE <>", value, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateGreaterThan(String value) {
            addCriterion("ANSWER_DATE >", value, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateGreaterThanOrEqualTo(String value) {
            addCriterion("ANSWER_DATE >=", value, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateLessThan(String value) {
            addCriterion("ANSWER_DATE <", value, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateLessThanOrEqualTo(String value) {
            addCriterion("ANSWER_DATE <=", value, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateLike(String value) {
            addCriterion("ANSWER_DATE like", value, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateNotLike(String value) {
            addCriterion("ANSWER_DATE not like", value, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateIn(List<String> values) {
            addCriterion("ANSWER_DATE in", values, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateNotIn(List<String> values) {
            addCriterion("ANSWER_DATE not in", values, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateBetween(String value1, String value2) {
            addCriterion("ANSWER_DATE between", value1, value2, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerDateNotBetween(String value1, String value2) {
            addCriterion("ANSWER_DATE not between", value1, value2, "answerDate");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeIsNull() {
            addCriterion("ANSWER_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeIsNotNull() {
            addCriterion("ANSWER_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeEqualTo(String value) {
            addCriterion("ANSWER_TIME =", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotEqualTo(String value) {
            addCriterion("ANSWER_TIME <>", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeGreaterThan(String value) {
            addCriterion("ANSWER_TIME >", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ANSWER_TIME >=", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeLessThan(String value) {
            addCriterion("ANSWER_TIME <", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeLessThanOrEqualTo(String value) {
            addCriterion("ANSWER_TIME <=", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeLike(String value) {
            addCriterion("ANSWER_TIME like", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotLike(String value) {
            addCriterion("ANSWER_TIME not like", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeIn(List<String> values) {
            addCriterion("ANSWER_TIME in", values, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotIn(List<String> values) {
            addCriterion("ANSWER_TIME not in", values, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeBetween(String value1, String value2) {
            addCriterion("ANSWER_TIME between", value1, value2, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotBetween(String value1, String value2) {
            addCriterion("ANSWER_TIME not between", value1, value2, "answerTime");
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