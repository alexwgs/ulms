package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class DailyScoreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DailyScoreExample() {
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

        public Criteria andQuesDateIsNull() {
            addCriterion("QUES_DATE is null");
            return (Criteria) this;
        }

        public Criteria andQuesDateIsNotNull() {
            addCriterion("QUES_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andQuesDateEqualTo(String value) {
            addCriterion("QUES_DATE =", value, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateNotEqualTo(String value) {
            addCriterion("QUES_DATE <>", value, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateGreaterThan(String value) {
            addCriterion("QUES_DATE >", value, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateGreaterThanOrEqualTo(String value) {
            addCriterion("QUES_DATE >=", value, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateLessThan(String value) {
            addCriterion("QUES_DATE <", value, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateLessThanOrEqualTo(String value) {
            addCriterion("QUES_DATE <=", value, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateLike(String value) {
            addCriterion("QUES_DATE like", value, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateNotLike(String value) {
            addCriterion("QUES_DATE not like", value, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateIn(List<String> values) {
            addCriterion("QUES_DATE in", values, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateNotIn(List<String> values) {
            addCriterion("QUES_DATE not in", values, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateBetween(String value1, String value2) {
            addCriterion("QUES_DATE between", value1, value2, "quesDate");
            return (Criteria) this;
        }

        public Criteria andQuesDateNotBetween(String value1, String value2) {
            addCriterion("QUES_DATE not between", value1, value2, "quesDate");
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

        public Criteria andMistakesIsNull() {
            addCriterion("MISTAKES is null");
            return (Criteria) this;
        }

        public Criteria andMistakesIsNotNull() {
            addCriterion("MISTAKES is not null");
            return (Criteria) this;
        }

        public Criteria andMistakesEqualTo(Short value) {
            addCriterion("MISTAKES =", value, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesNotEqualTo(Short value) {
            addCriterion("MISTAKES <>", value, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesGreaterThan(Short value) {
            addCriterion("MISTAKES >", value, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesGreaterThanOrEqualTo(Short value) {
            addCriterion("MISTAKES >=", value, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesLessThan(Short value) {
            addCriterion("MISTAKES <", value, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesLessThanOrEqualTo(Short value) {
            addCriterion("MISTAKES <=", value, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesIn(List<Short> values) {
            addCriterion("MISTAKES in", values, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesNotIn(List<Short> values) {
            addCriterion("MISTAKES not in", values, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesBetween(Short value1, Short value2) {
            addCriterion("MISTAKES between", value1, value2, "mistakes");
            return (Criteria) this;
        }

        public Criteria andMistakesNotBetween(Short value1, Short value2) {
            addCriterion("MISTAKES not between", value1, value2, "mistakes");
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

        public Criteria andPassFlagEqualTo(Short value) {
            addCriterion("PASS_FLAG =", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagNotEqualTo(Short value) {
            addCriterion("PASS_FLAG <>", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagGreaterThan(Short value) {
            addCriterion("PASS_FLAG >", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagGreaterThanOrEqualTo(Short value) {
            addCriterion("PASS_FLAG >=", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagLessThan(Short value) {
            addCriterion("PASS_FLAG <", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagLessThanOrEqualTo(Short value) {
            addCriterion("PASS_FLAG <=", value, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagIn(List<Short> values) {
            addCriterion("PASS_FLAG in", values, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagNotIn(List<Short> values) {
            addCriterion("PASS_FLAG not in", values, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagBetween(Short value1, Short value2) {
            addCriterion("PASS_FLAG between", value1, value2, "passFlag");
            return (Criteria) this;
        }

        public Criteria andPassFlagNotBetween(Short value1, Short value2) {
            addCriterion("PASS_FLAG not between", value1, value2, "passFlag");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerIsNull() {
            addCriterion("FIRST_ANSWER is null");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerIsNotNull() {
            addCriterion("FIRST_ANSWER is not null");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerEqualTo(String value) {
            addCriterion("FIRST_ANSWER =", value, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerNotEqualTo(String value) {
            addCriterion("FIRST_ANSWER <>", value, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerGreaterThan(String value) {
            addCriterion("FIRST_ANSWER >", value, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("FIRST_ANSWER >=", value, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerLessThan(String value) {
            addCriterion("FIRST_ANSWER <", value, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerLessThanOrEqualTo(String value) {
            addCriterion("FIRST_ANSWER <=", value, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerLike(String value) {
            addCriterion("FIRST_ANSWER like", value, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerNotLike(String value) {
            addCriterion("FIRST_ANSWER not like", value, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerIn(List<String> values) {
            addCriterion("FIRST_ANSWER in", values, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerNotIn(List<String> values) {
            addCriterion("FIRST_ANSWER not in", values, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerBetween(String value1, String value2) {
            addCriterion("FIRST_ANSWER between", value1, value2, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andFirstAnswerNotBetween(String value1, String value2) {
            addCriterion("FIRST_ANSWER not between", value1, value2, "firstAnswer");
            return (Criteria) this;
        }

        public Criteria andArticleStatusIsNull() {
            addCriterion("ARTICAL_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andArticleStatusIsNotNull() {
            addCriterion("ARTICAL_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andArticleStatusEqualTo(Short value) {
            addCriterion("ARTICAL_STATUS =", value, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusNotEqualTo(Short value) {
            addCriterion("ARTICAL_STATUS <>", value, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusGreaterThan(Short value) {
            addCriterion("ARTICAL_STATUS >", value, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("ARTICAL_STATUS >=", value, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusLessThan(Short value) {
            addCriterion("ARTICAL_STATUS <", value, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusLessThanOrEqualTo(Short value) {
            addCriterion("ARTICAL_STATUS <=", value, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusIn(List<Short> values) {
            addCriterion("ARTICAL_STATUS in", values, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusNotIn(List<Short> values) {
            addCriterion("ARTICAL_STATUS not in", values, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusBetween(Short value1, Short value2) {
            addCriterion("ARTICAL_STATUS between", value1, value2, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andArticleStatusNotBetween(Short value1, Short value2) {
            addCriterion("ARTICAL_STATUS not between", value1, value2, "articleStatus");
            return (Criteria) this;
        }

        public Criteria andStudyBegIsNull() {
            addCriterion("STUDY_BEG is null");
            return (Criteria) this;
        }

        public Criteria andStudyBegIsNotNull() {
            addCriterion("STUDY_BEG is not null");
            return (Criteria) this;
        }

        public Criteria andStudyBegEqualTo(String value) {
            addCriterion("STUDY_BEG =", value, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegNotEqualTo(String value) {
            addCriterion("STUDY_BEG <>", value, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegGreaterThan(String value) {
            addCriterion("STUDY_BEG >", value, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegGreaterThanOrEqualTo(String value) {
            addCriterion("STUDY_BEG >=", value, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegLessThan(String value) {
            addCriterion("STUDY_BEG <", value, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegLessThanOrEqualTo(String value) {
            addCriterion("STUDY_BEG <=", value, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegLike(String value) {
            addCriterion("STUDY_BEG like", value, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegNotLike(String value) {
            addCriterion("STUDY_BEG not like", value, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegIn(List<String> values) {
            addCriterion("STUDY_BEG in", values, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegNotIn(List<String> values) {
            addCriterion("STUDY_BEG not in", values, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegBetween(String value1, String value2) {
            addCriterion("STUDY_BEG between", value1, value2, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyBegNotBetween(String value1, String value2) {
            addCriterion("STUDY_BEG not between", value1, value2, "studyBeg");
            return (Criteria) this;
        }

        public Criteria andStudyEndIsNull() {
            addCriterion("STUDY_END is null");
            return (Criteria) this;
        }

        public Criteria andStudyEndIsNotNull() {
            addCriterion("STUDY_END is not null");
            return (Criteria) this;
        }

        public Criteria andStudyEndEqualTo(String value) {
            addCriterion("STUDY_END =", value, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndNotEqualTo(String value) {
            addCriterion("STUDY_END <>", value, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndGreaterThan(String value) {
            addCriterion("STUDY_END >", value, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndGreaterThanOrEqualTo(String value) {
            addCriterion("STUDY_END >=", value, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndLessThan(String value) {
            addCriterion("STUDY_END <", value, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndLessThanOrEqualTo(String value) {
            addCriterion("STUDY_END <=", value, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndLike(String value) {
            addCriterion("STUDY_END like", value, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndNotLike(String value) {
            addCriterion("STUDY_END not like", value, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndIn(List<String> values) {
            addCriterion("STUDY_END in", values, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndNotIn(List<String> values) {
            addCriterion("STUDY_END not in", values, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndBetween(String value1, String value2) {
            addCriterion("STUDY_END between", value1, value2, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andStudyEndNotBetween(String value1, String value2) {
            addCriterion("STUDY_END not between", value1, value2, "studyEnd");
            return (Criteria) this;
        }

        public Criteria andMoodTypeIsNull() {
            addCriterion("MOOD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andMoodTypeIsNotNull() {
            addCriterion("MOOD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andMoodTypeEqualTo(String value) {
            addCriterion("MOOD_TYPE =", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeNotEqualTo(String value) {
            addCriterion("MOOD_TYPE <>", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeGreaterThan(String value) {
            addCriterion("MOOD_TYPE >", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeGreaterThanOrEqualTo(String value) {
            addCriterion("MOOD_TYPE >=", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeLessThan(String value) {
            addCriterion("MOOD_TYPE <", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeLessThanOrEqualTo(String value) {
            addCriterion("MOOD_TYPE <=", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeLike(String value) {
            addCriterion("MOOD_TYPE like", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeNotLike(String value) {
            addCriterion("MOOD_TYPE not like", value, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeIn(List<String> values) {
            addCriterion("MOOD_TYPE in", values, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeNotIn(List<String> values) {
            addCriterion("MOOD_TYPE not in", values, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeBetween(String value1, String value2) {
            addCriterion("MOOD_TYPE between", value1, value2, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodTypeNotBetween(String value1, String value2) {
            addCriterion("MOOD_TYPE not between", value1, value2, "moodType");
            return (Criteria) this;
        }

        public Criteria andMoodMemoIsNull() {
            addCriterion("MOOD_MEMO is null");
            return (Criteria) this;
        }

        public Criteria andMoodMemoIsNotNull() {
            addCriterion("MOOD_MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andMoodMemoEqualTo(String value) {
            addCriterion("MOOD_MEMO =", value, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoNotEqualTo(String value) {
            addCriterion("MOOD_MEMO <>", value, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoGreaterThan(String value) {
            addCriterion("MOOD_MEMO >", value, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoGreaterThanOrEqualTo(String value) {
            addCriterion("MOOD_MEMO >=", value, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoLessThan(String value) {
            addCriterion("MOOD_MEMO <", value, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoLessThanOrEqualTo(String value) {
            addCriterion("MOOD_MEMO <=", value, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoLike(String value) {
            addCriterion("MOOD_MEMO like", value, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoNotLike(String value) {
            addCriterion("MOOD_MEMO not like", value, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoIn(List<String> values) {
            addCriterion("MOOD_MEMO in", values, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoNotIn(List<String> values) {
            addCriterion("MOOD_MEMO not in", values, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoBetween(String value1, String value2) {
            addCriterion("MOOD_MEMO between", value1, value2, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodMemoNotBetween(String value1, String value2) {
            addCriterion("MOOD_MEMO not between", value1, value2, "moodMemo");
            return (Criteria) this;
        }

        public Criteria andMoodDateIsNull() {
            addCriterion("MOOD_DATE is null");
            return (Criteria) this;
        }

        public Criteria andMoodDateIsNotNull() {
            addCriterion("MOOD_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andMoodDateEqualTo(String value) {
            addCriterion("MOOD_DATE =", value, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateNotEqualTo(String value) {
            addCriterion("MOOD_DATE <>", value, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateGreaterThan(String value) {
            addCriterion("MOOD_DATE >", value, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateGreaterThanOrEqualTo(String value) {
            addCriterion("MOOD_DATE >=", value, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateLessThan(String value) {
            addCriterion("MOOD_DATE <", value, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateLessThanOrEqualTo(String value) {
            addCriterion("MOOD_DATE <=", value, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateLike(String value) {
            addCriterion("MOOD_DATE like", value, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateNotLike(String value) {
            addCriterion("MOOD_DATE not like", value, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateIn(List<String> values) {
            addCriterion("MOOD_DATE in", values, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateNotIn(List<String> values) {
            addCriterion("MOOD_DATE not in", values, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateBetween(String value1, String value2) {
            addCriterion("MOOD_DATE between", value1, value2, "moodDate");
            return (Criteria) this;
        }

        public Criteria andMoodDateNotBetween(String value1, String value2) {
            addCriterion("MOOD_DATE not between", value1, value2, "moodDate");
            return (Criteria) this;
        }

        public Criteria andValidIsNull() {
            addCriterion("VALID is null");
            return (Criteria) this;
        }

        public Criteria andValidIsNotNull() {
            addCriterion("VALID is not null");
            return (Criteria) this;
        }

        public Criteria andValidEqualTo(Short value) {
            addCriterion("VALID =", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotEqualTo(Short value) {
            addCriterion("VALID <>", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThan(Short value) {
            addCriterion("VALID >", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThanOrEqualTo(Short value) {
            addCriterion("VALID >=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThan(Short value) {
            addCriterion("VALID <", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThanOrEqualTo(Short value) {
            addCriterion("VALID <=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidIn(List<Short> values) {
            addCriterion("VALID in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotIn(List<Short> values) {
            addCriterion("VALID not in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidBetween(Short value1, Short value2) {
            addCriterion("VALID between", value1, value2, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotBetween(Short value1, Short value2) {
            addCriterion("VALID not between", value1, value2, "valid");
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