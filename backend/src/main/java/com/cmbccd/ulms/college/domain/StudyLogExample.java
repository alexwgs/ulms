package com.cmbccd.ulms.college.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StudyLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StudyLogExample() {
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

        public Criteria andCourseIdIsNull() {
            addCriterion("COURSE_ID is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("COURSE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(String value) {
            addCriterion("COURSE_ID =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(String value) {
            addCriterion("COURSE_ID <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(String value) {
            addCriterion("COURSE_ID >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(String value) {
            addCriterion("COURSE_ID >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(String value) {
            addCriterion("COURSE_ID <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(String value) {
            addCriterion("COURSE_ID <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLike(String value) {
            addCriterion("COURSE_ID like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotLike(String value) {
            addCriterion("COURSE_ID not like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<String> values) {
            addCriterion("COURSE_ID in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<String> values) {
            addCriterion("COURSE_ID not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(String value1, String value2) {
            addCriterion("COURSE_ID between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(String value1, String value2) {
            addCriterion("COURSE_ID not between", value1, value2, "courseId");
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

        public Criteria andStudyDateIsNull() {
            addCriterion("STUDY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andStudyDateIsNotNull() {
            addCriterion("STUDY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andStudyDateEqualTo(String value) {
            addCriterion("STUDY_DATE =", value, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateNotEqualTo(String value) {
            addCriterion("STUDY_DATE <>", value, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateGreaterThan(String value) {
            addCriterion("STUDY_DATE >", value, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateGreaterThanOrEqualTo(String value) {
            addCriterion("STUDY_DATE >=", value, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateLessThan(String value) {
            addCriterion("STUDY_DATE <", value, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateLessThanOrEqualTo(String value) {
            addCriterion("STUDY_DATE <=", value, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateLike(String value) {
            addCriterion("STUDY_DATE like", value, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateNotLike(String value) {
            addCriterion("STUDY_DATE not like", value, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateIn(List<String> values) {
            addCriterion("STUDY_DATE in", values, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateNotIn(List<String> values) {
            addCriterion("STUDY_DATE not in", values, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateBetween(String value1, String value2) {
            addCriterion("STUDY_DATE between", value1, value2, "studyDate");
            return (Criteria) this;
        }

        public Criteria andStudyDateNotBetween(String value1, String value2) {
            addCriterion("STUDY_DATE not between", value1, value2, "studyDate");
            return (Criteria) this;
        }

        public Criteria andLecturerIsNull() {
            addCriterion("LECTURER is null");
            return (Criteria) this;
        }

        public Criteria andLecturerIsNotNull() {
            addCriterion("LECTURER is not null");
            return (Criteria) this;
        }

        public Criteria andLecturerEqualTo(String value) {
            addCriterion("LECTURER =", value, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerNotEqualTo(String value) {
            addCriterion("LECTURER <>", value, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerGreaterThan(String value) {
            addCriterion("LECTURER >", value, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerGreaterThanOrEqualTo(String value) {
            addCriterion("LECTURER >=", value, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerLessThan(String value) {
            addCriterion("LECTURER <", value, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerLessThanOrEqualTo(String value) {
            addCriterion("LECTURER <=", value, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerLike(String value) {
            addCriterion("LECTURER like", value, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerNotLike(String value) {
            addCriterion("LECTURER not like", value, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerIn(List<String> values) {
            addCriterion("LECTURER in", values, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerNotIn(List<String> values) {
            addCriterion("LECTURER not in", values, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerBetween(String value1, String value2) {
            addCriterion("LECTURER between", value1, value2, "lecturer");
            return (Criteria) this;
        }

        public Criteria andLecturerNotBetween(String value1, String value2) {
            addCriterion("LECTURER not between", value1, value2, "lecturer");
            return (Criteria) this;
        }

        public Criteria andCoinIsNull() {
            addCriterion("COIN is null");
            return (Criteria) this;
        }

        public Criteria andCoinIsNotNull() {
            addCriterion("COIN is not null");
            return (Criteria) this;
        }

        public Criteria andCoinEqualTo(BigDecimal value) {
            addCriterion("COIN =", value, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinNotEqualTo(BigDecimal value) {
            addCriterion("COIN <>", value, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinGreaterThan(BigDecimal value) {
            addCriterion("COIN >", value, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COIN >=", value, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinLessThan(BigDecimal value) {
            addCriterion("COIN <", value, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COIN <=", value, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinIn(List<BigDecimal> values) {
            addCriterion("COIN in", values, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinNotIn(List<BigDecimal> values) {
            addCriterion("COIN not in", values, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COIN between", value1, value2, "coin");
            return (Criteria) this;
        }

        public Criteria andCoinNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COIN not between", value1, value2, "coin");
            return (Criteria) this;
        }

        public Criteria andHoursIsNull() {
            addCriterion("HOURS is null");
            return (Criteria) this;
        }

        public Criteria andHoursIsNotNull() {
            addCriterion("HOURS is not null");
            return (Criteria) this;
        }

        public Criteria andHoursEqualTo(BigDecimal value) {
            addCriterion("HOURS =", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursNotEqualTo(BigDecimal value) {
            addCriterion("HOURS <>", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursGreaterThan(BigDecimal value) {
            addCriterion("HOURS >", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("HOURS >=", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursLessThan(BigDecimal value) {
            addCriterion("HOURS <", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursLessThanOrEqualTo(BigDecimal value) {
            addCriterion("HOURS <=", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursIn(List<BigDecimal> values) {
            addCriterion("HOURS in", values, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursNotIn(List<BigDecimal> values) {
            addCriterion("HOURS not in", values, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HOURS between", value1, value2, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HOURS not between", value1, value2, "hours");
            return (Criteria) this;
        }

        public Criteria andTeachMethodIsNull() {
            addCriterion("TEACH_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andTeachMethodIsNotNull() {
            addCriterion("TEACH_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andTeachMethodEqualTo(Short value) {
            addCriterion("TEACH_METHOD =", value, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodNotEqualTo(Short value) {
            addCriterion("TEACH_METHOD <>", value, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodGreaterThan(Short value) {
            addCriterion("TEACH_METHOD >", value, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodGreaterThanOrEqualTo(Short value) {
            addCriterion("TEACH_METHOD >=", value, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodLessThan(Short value) {
            addCriterion("TEACH_METHOD <", value, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodLessThanOrEqualTo(Short value) {
            addCriterion("TEACH_METHOD <=", value, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodIn(List<Short> values) {
            addCriterion("TEACH_METHOD in", values, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodNotIn(List<Short> values) {
            addCriterion("TEACH_METHOD not in", values, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodBetween(Short value1, Short value2) {
            addCriterion("TEACH_METHOD between", value1, value2, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andTeachMethodNotBetween(Short value1, Short value2) {
            addCriterion("TEACH_METHOD not between", value1, value2, "teachMethod");
            return (Criteria) this;
        }

        public Criteria andStudyCompIsNull() {
            addCriterion("STUDY_COMP is null");
            return (Criteria) this;
        }

        public Criteria andStudyCompIsNotNull() {
            addCriterion("STUDY_COMP is not null");
            return (Criteria) this;
        }

        public Criteria andStudyCompEqualTo(Short value) {
            addCriterion("STUDY_COMP =", value, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompNotEqualTo(Short value) {
            addCriterion("STUDY_COMP <>", value, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompGreaterThan(Short value) {
            addCriterion("STUDY_COMP >", value, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompGreaterThanOrEqualTo(Short value) {
            addCriterion("STUDY_COMP >=", value, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompLessThan(Short value) {
            addCriterion("STUDY_COMP <", value, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompLessThanOrEqualTo(Short value) {
            addCriterion("STUDY_COMP <=", value, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompIn(List<Short> values) {
            addCriterion("STUDY_COMP in", values, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompNotIn(List<Short> values) {
            addCriterion("STUDY_COMP not in", values, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompBetween(Short value1, Short value2) {
            addCriterion("STUDY_COMP between", value1, value2, "studyComp");
            return (Criteria) this;
        }

        public Criteria andStudyCompNotBetween(Short value1, Short value2) {
            addCriterion("STUDY_COMP not between", value1, value2, "studyComp");
            return (Criteria) this;
        }

        public Criteria andIfExamIsNull() {
            addCriterion("IF_EXAM is null");
            return (Criteria) this;
        }

        public Criteria andIfExamIsNotNull() {
            addCriterion("IF_EXAM is not null");
            return (Criteria) this;
        }

        public Criteria andIfExamEqualTo(Short value) {
            addCriterion("IF_EXAM =", value, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamNotEqualTo(Short value) {
            addCriterion("IF_EXAM <>", value, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamGreaterThan(Short value) {
            addCriterion("IF_EXAM >", value, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamGreaterThanOrEqualTo(Short value) {
            addCriterion("IF_EXAM >=", value, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamLessThan(Short value) {
            addCriterion("IF_EXAM <", value, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamLessThanOrEqualTo(Short value) {
            addCriterion("IF_EXAM <=", value, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamIn(List<Short> values) {
            addCriterion("IF_EXAM in", values, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamNotIn(List<Short> values) {
            addCriterion("IF_EXAM not in", values, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamBetween(Short value1, Short value2) {
            addCriterion("IF_EXAM between", value1, value2, "ifExam");
            return (Criteria) this;
        }

        public Criteria andIfExamNotBetween(Short value1, Short value2) {
            addCriterion("IF_EXAM not between", value1, value2, "ifExam");
            return (Criteria) this;
        }

        public Criteria andExamIdIsNull() {
            addCriterion("EXAM_ID is null");
            return (Criteria) this;
        }

        public Criteria andExamIdIsNotNull() {
            addCriterion("EXAM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andExamIdEqualTo(String value) {
            addCriterion("EXAM_ID =", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotEqualTo(String value) {
            addCriterion("EXAM_ID <>", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdGreaterThan(String value) {
            addCriterion("EXAM_ID >", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdGreaterThanOrEqualTo(String value) {
            addCriterion("EXAM_ID >=", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLessThan(String value) {
            addCriterion("EXAM_ID <", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLessThanOrEqualTo(String value) {
            addCriterion("EXAM_ID <=", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLike(String value) {
            addCriterion("EXAM_ID like", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotLike(String value) {
            addCriterion("EXAM_ID not like", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdIn(List<String> values) {
            addCriterion("EXAM_ID in", values, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotIn(List<String> values) {
            addCriterion("EXAM_ID not in", values, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdBetween(String value1, String value2) {
            addCriterion("EXAM_ID between", value1, value2, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotBetween(String value1, String value2) {
            addCriterion("EXAM_ID not between", value1, value2, "examId");
            return (Criteria) this;
        }

        public Criteria andExamCompIsNull() {
            addCriterion("EXAM_COMP is null");
            return (Criteria) this;
        }

        public Criteria andExamCompIsNotNull() {
            addCriterion("EXAM_COMP is not null");
            return (Criteria) this;
        }

        public Criteria andExamCompEqualTo(Short value) {
            addCriterion("EXAM_COMP =", value, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompNotEqualTo(Short value) {
            addCriterion("EXAM_COMP <>", value, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompGreaterThan(Short value) {
            addCriterion("EXAM_COMP >", value, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompGreaterThanOrEqualTo(Short value) {
            addCriterion("EXAM_COMP >=", value, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompLessThan(Short value) {
            addCriterion("EXAM_COMP <", value, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompLessThanOrEqualTo(Short value) {
            addCriterion("EXAM_COMP <=", value, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompIn(List<Short> values) {
            addCriterion("EXAM_COMP in", values, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompNotIn(List<Short> values) {
            addCriterion("EXAM_COMP not in", values, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompBetween(Short value1, Short value2) {
            addCriterion("EXAM_COMP between", value1, value2, "examComp");
            return (Criteria) this;
        }

        public Criteria andExamCompNotBetween(Short value1, Short value2) {
            addCriterion("EXAM_COMP not between", value1, value2, "examComp");
            return (Criteria) this;
        }

        public Criteria andIfEvalIsNull() {
            addCriterion("IF_EVAL is null");
            return (Criteria) this;
        }

        public Criteria andIfEvalIsNotNull() {
            addCriterion("IF_EVAL is not null");
            return (Criteria) this;
        }

        public Criteria andIfEvalEqualTo(Short value) {
            addCriterion("IF_EVAL =", value, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalNotEqualTo(Short value) {
            addCriterion("IF_EVAL <>", value, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalGreaterThan(Short value) {
            addCriterion("IF_EVAL >", value, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalGreaterThanOrEqualTo(Short value) {
            addCriterion("IF_EVAL >=", value, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalLessThan(Short value) {
            addCriterion("IF_EVAL <", value, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalLessThanOrEqualTo(Short value) {
            addCriterion("IF_EVAL <=", value, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalIn(List<Short> values) {
            addCriterion("IF_EVAL in", values, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalNotIn(List<Short> values) {
            addCriterion("IF_EVAL not in", values, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalBetween(Short value1, Short value2) {
            addCriterion("IF_EVAL between", value1, value2, "ifEval");
            return (Criteria) this;
        }

        public Criteria andIfEvalNotBetween(Short value1, Short value2) {
            addCriterion("IF_EVAL not between", value1, value2, "ifEval");
            return (Criteria) this;
        }

        public Criteria andEvalDateIsNull() {
            addCriterion("EVAL_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEvalDateIsNotNull() {
            addCriterion("EVAL_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEvalDateEqualTo(String value) {
            addCriterion("EVAL_DATE =", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateNotEqualTo(String value) {
            addCriterion("EVAL_DATE <>", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateGreaterThan(String value) {
            addCriterion("EVAL_DATE >", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateGreaterThanOrEqualTo(String value) {
            addCriterion("EVAL_DATE >=", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateLessThan(String value) {
            addCriterion("EVAL_DATE <", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateLessThanOrEqualTo(String value) {
            addCriterion("EVAL_DATE <=", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateLike(String value) {
            addCriterion("EVAL_DATE like", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateNotLike(String value) {
            addCriterion("EVAL_DATE not like", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateIn(List<String> values) {
            addCriterion("EVAL_DATE in", values, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateNotIn(List<String> values) {
            addCriterion("EVAL_DATE not in", values, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateBetween(String value1, String value2) {
            addCriterion("EVAL_DATE between", value1, value2, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateNotBetween(String value1, String value2) {
            addCriterion("EVAL_DATE not between", value1, value2, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalCompIsNull() {
            addCriterion("EVAL_COMP is null");
            return (Criteria) this;
        }

        public Criteria andEvalCompIsNotNull() {
            addCriterion("EVAL_COMP is not null");
            return (Criteria) this;
        }

        public Criteria andEvalCompEqualTo(Short value) {
            addCriterion("EVAL_COMP =", value, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompNotEqualTo(Short value) {
            addCriterion("EVAL_COMP <>", value, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompGreaterThan(Short value) {
            addCriterion("EVAL_COMP >", value, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompGreaterThanOrEqualTo(Short value) {
            addCriterion("EVAL_COMP >=", value, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompLessThan(Short value) {
            addCriterion("EVAL_COMP <", value, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompLessThanOrEqualTo(Short value) {
            addCriterion("EVAL_COMP <=", value, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompIn(List<Short> values) {
            addCriterion("EVAL_COMP in", values, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompNotIn(List<Short> values) {
            addCriterion("EVAL_COMP not in", values, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompBetween(Short value1, Short value2) {
            addCriterion("EVAL_COMP between", value1, value2, "evalComp");
            return (Criteria) this;
        }

        public Criteria andEvalCompNotBetween(Short value1, Short value2) {
            addCriterion("EVAL_COMP not between", value1, value2, "evalComp");
            return (Criteria) this;
        }

        public Criteria andCoursePassIsNull() {
            addCriterion("COURSE_PASS is null");
            return (Criteria) this;
        }

        public Criteria andCoursePassIsNotNull() {
            addCriterion("COURSE_PASS is not null");
            return (Criteria) this;
        }

        public Criteria andCoursePassEqualTo(Short value) {
            addCriterion("COURSE_PASS =", value, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassNotEqualTo(Short value) {
            addCriterion("COURSE_PASS <>", value, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassGreaterThan(Short value) {
            addCriterion("COURSE_PASS >", value, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassGreaterThanOrEqualTo(Short value) {
            addCriterion("COURSE_PASS >=", value, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassLessThan(Short value) {
            addCriterion("COURSE_PASS <", value, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassLessThanOrEqualTo(Short value) {
            addCriterion("COURSE_PASS <=", value, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassIn(List<Short> values) {
            addCriterion("COURSE_PASS in", values, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassNotIn(List<Short> values) {
            addCriterion("COURSE_PASS not in", values, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassBetween(Short value1, Short value2) {
            addCriterion("COURSE_PASS between", value1, value2, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCoursePassNotBetween(Short value1, Short value2) {
            addCriterion("COURSE_PASS not between", value1, value2, "coursePass");
            return (Criteria) this;
        }

        public Criteria andCompDateIsNull() {
            addCriterion("COMP_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCompDateIsNotNull() {
            addCriterion("COMP_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCompDateEqualTo(String value) {
            addCriterion("COMP_DATE =", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateNotEqualTo(String value) {
            addCriterion("COMP_DATE <>", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateGreaterThan(String value) {
            addCriterion("COMP_DATE >", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateGreaterThanOrEqualTo(String value) {
            addCriterion("COMP_DATE >=", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateLessThan(String value) {
            addCriterion("COMP_DATE <", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateLessThanOrEqualTo(String value) {
            addCriterion("COMP_DATE <=", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateLike(String value) {
            addCriterion("COMP_DATE like", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateNotLike(String value) {
            addCriterion("COMP_DATE not like", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateIn(List<String> values) {
            addCriterion("COMP_DATE in", values, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateNotIn(List<String> values) {
            addCriterion("COMP_DATE not in", values, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateBetween(String value1, String value2) {
            addCriterion("COMP_DATE between", value1, value2, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateNotBetween(String value1, String value2) {
            addCriterion("COMP_DATE not between", value1, value2, "compDate");
            return (Criteria) this;
        }

        public Criteria andTempIdIsNull() {
            addCriterion("TEMP_ID is null");
            return (Criteria) this;
        }

        public Criteria andTempIdIsNotNull() {
            addCriterion("TEMP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTempIdEqualTo(String value) {
            addCriterion("TEMP_ID =", value, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdNotEqualTo(String value) {
            addCriterion("TEMP_ID <>", value, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdGreaterThan(String value) {
            addCriterion("TEMP_ID >", value, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdGreaterThanOrEqualTo(String value) {
            addCriterion("TEMP_ID >=", value, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdLessThan(String value) {
            addCriterion("TEMP_ID <", value, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdLessThanOrEqualTo(String value) {
            addCriterion("TEMP_ID <=", value, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdLike(String value) {
            addCriterion("TEMP_ID like", value, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdNotLike(String value) {
            addCriterion("TEMP_ID not like", value, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdIn(List<String> values) {
            addCriterion("TEMP_ID in", values, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdNotIn(List<String> values) {
            addCriterion("TEMP_ID not in", values, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdBetween(String value1, String value2) {
            addCriterion("TEMP_ID between", value1, value2, "tempId");
            return (Criteria) this;
        }

        public Criteria andTempIdNotBetween(String value1, String value2) {
            addCriterion("TEMP_ID not between", value1, value2, "tempId");
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