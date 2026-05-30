package com.cmbccd.ulms.college.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CourseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CourseExample() {
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

        public Criteria andCourseNameIsNull() {
            addCriterion("COURSE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCourseNameIsNotNull() {
            addCriterion("COURSE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCourseNameEqualTo(String value) {
            addCriterion("COURSE_NAME =", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotEqualTo(String value) {
            addCriterion("COURSE_NAME <>", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameGreaterThan(String value) {
            addCriterion("COURSE_NAME >", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameGreaterThanOrEqualTo(String value) {
            addCriterion("COURSE_NAME >=", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLessThan(String value) {
            addCriterion("COURSE_NAME <", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLessThanOrEqualTo(String value) {
            addCriterion("COURSE_NAME <=", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLike(String value) {
            addCriterion("COURSE_NAME like", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotLike(String value) {
            addCriterion("COURSE_NAME not like", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameIn(List<String> values) {
            addCriterion("COURSE_NAME in", values, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotIn(List<String> values) {
            addCriterion("COURSE_NAME not in", values, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameBetween(String value1, String value2) {
            addCriterion("COURSE_NAME between", value1, value2, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotBetween(String value1, String value2) {
            addCriterion("COURSE_NAME not between", value1, value2, "courseName");
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

        public Criteria andCourseTypeIsNull() {
            addCriterion("COURSE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCourseTypeIsNotNull() {
            addCriterion("COURSE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCourseTypeEqualTo(Short value) {
            addCriterion("COURSE_TYPE =", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeNotEqualTo(Short value) {
            addCriterion("COURSE_TYPE <>", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeGreaterThan(Short value) {
            addCriterion("COURSE_TYPE >", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("COURSE_TYPE >=", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeLessThan(Short value) {
            addCriterion("COURSE_TYPE <", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeLessThanOrEqualTo(Short value) {
            addCriterion("COURSE_TYPE <=", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeIn(List<Short> values) {
            addCriterion("COURSE_TYPE in", values, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeNotIn(List<Short> values) {
            addCriterion("COURSE_TYPE not in", values, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeBetween(Short value1, Short value2) {
            addCriterion("COURSE_TYPE between", value1, value2, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeNotBetween(Short value1, Short value2) {
            addCriterion("COURSE_TYPE not between", value1, value2, "courseType");
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

        public Criteria andTeachObjectIsNull() {
            addCriterion("TEACH_OBJECT is null");
            return (Criteria) this;
        }

        public Criteria andTeachObjectIsNotNull() {
            addCriterion("TEACH_OBJECT is not null");
            return (Criteria) this;
        }

        public Criteria andTeachObjectEqualTo(String value) {
            addCriterion("TEACH_OBJECT =", value, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectNotEqualTo(String value) {
            addCriterion("TEACH_OBJECT <>", value, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectGreaterThan(String value) {
            addCriterion("TEACH_OBJECT >", value, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectGreaterThanOrEqualTo(String value) {
            addCriterion("TEACH_OBJECT >=", value, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectLessThan(String value) {
            addCriterion("TEACH_OBJECT <", value, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectLessThanOrEqualTo(String value) {
            addCriterion("TEACH_OBJECT <=", value, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectLike(String value) {
            addCriterion("TEACH_OBJECT like", value, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectNotLike(String value) {
            addCriterion("TEACH_OBJECT not like", value, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectIn(List<String> values) {
            addCriterion("TEACH_OBJECT in", values, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectNotIn(List<String> values) {
            addCriterion("TEACH_OBJECT not in", values, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectBetween(String value1, String value2) {
            addCriterion("TEACH_OBJECT between", value1, value2, "teachObject");
            return (Criteria) this;
        }

        public Criteria andTeachObjectNotBetween(String value1, String value2) {
            addCriterion("TEACH_OBJECT not between", value1, value2, "teachObject");
            return (Criteria) this;
        }

        public Criteria andStudyTypeIsNull() {
            addCriterion("STUDY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andStudyTypeIsNotNull() {
            addCriterion("STUDY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andStudyTypeEqualTo(Short value) {
            addCriterion("STUDY_TYPE =", value, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeNotEqualTo(Short value) {
            addCriterion("STUDY_TYPE <>", value, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeGreaterThan(Short value) {
            addCriterion("STUDY_TYPE >", value, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("STUDY_TYPE >=", value, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeLessThan(Short value) {
            addCriterion("STUDY_TYPE <", value, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeLessThanOrEqualTo(Short value) {
            addCriterion("STUDY_TYPE <=", value, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeIn(List<Short> values) {
            addCriterion("STUDY_TYPE in", values, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeNotIn(List<Short> values) {
            addCriterion("STUDY_TYPE not in", values, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeBetween(Short value1, Short value2) {
            addCriterion("STUDY_TYPE between", value1, value2, "studyType");
            return (Criteria) this;
        }

        public Criteria andStudyTypeNotBetween(Short value1, Short value2) {
            addCriterion("STUDY_TYPE not between", value1, value2, "studyType");
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

        public Criteria andCoverImgIsNull() {
            addCriterion("COVER_IMG is null");
            return (Criteria) this;
        }

        public Criteria andCoverImgIsNotNull() {
            addCriterion("COVER_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andCoverImgEqualTo(String value) {
            addCriterion("COVER_IMG =", value, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgNotEqualTo(String value) {
            addCriterion("COVER_IMG <>", value, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgGreaterThan(String value) {
            addCriterion("COVER_IMG >", value, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgGreaterThanOrEqualTo(String value) {
            addCriterion("COVER_IMG >=", value, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgLessThan(String value) {
            addCriterion("COVER_IMG <", value, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgLessThanOrEqualTo(String value) {
            addCriterion("COVER_IMG <=", value, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgLike(String value) {
            addCriterion("COVER_IMG like", value, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgNotLike(String value) {
            addCriterion("COVER_IMG not like", value, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgIn(List<String> values) {
            addCriterion("COVER_IMG in", values, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgNotIn(List<String> values) {
            addCriterion("COVER_IMG not in", values, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgBetween(String value1, String value2) {
            addCriterion("COVER_IMG between", value1, value2, "coverImg");
            return (Criteria) this;
        }

        public Criteria andCoverImgNotBetween(String value1, String value2) {
            addCriterion("COVER_IMG not between", value1, value2, "coverImg");
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

        public Criteria andEvalDateIsNull() {
            addCriterion("EVAL_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEvalDateIsNotNull() {
            addCriterion("EVAL_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEvalDateEqualTo(Short value) {
            addCriterion("EVAL_DATE =", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateNotEqualTo(Short value) {
            addCriterion("EVAL_DATE <>", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateGreaterThan(Short value) {
            addCriterion("EVAL_DATE >", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateGreaterThanOrEqualTo(Short value) {
            addCriterion("EVAL_DATE >=", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateLessThan(Short value) {
            addCriterion("EVAL_DATE <", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateLessThanOrEqualTo(Short value) {
            addCriterion("EVAL_DATE <=", value, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateIn(List<Short> values) {
            addCriterion("EVAL_DATE in", values, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateNotIn(List<Short> values) {
            addCriterion("EVAL_DATE not in", values, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateBetween(Short value1, Short value2) {
            addCriterion("EVAL_DATE between", value1, value2, "evalDate");
            return (Criteria) this;
        }

        public Criteria andEvalDateNotBetween(Short value1, Short value2) {
            addCriterion("EVAL_DATE not between", value1, value2, "evalDate");
            return (Criteria) this;
        }

        public Criteria andCourseScoreIsNull() {
            addCriterion("COURSE_SCORE is null");
            return (Criteria) this;
        }

        public Criteria andCourseScoreIsNotNull() {
            addCriterion("COURSE_SCORE is not null");
            return (Criteria) this;
        }

        public Criteria andCourseScoreEqualTo(BigDecimal value) {
            addCriterion("COURSE_SCORE =", value, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreNotEqualTo(BigDecimal value) {
            addCriterion("COURSE_SCORE <>", value, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreGreaterThan(BigDecimal value) {
            addCriterion("COURSE_SCORE >", value, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COURSE_SCORE >=", value, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreLessThan(BigDecimal value) {
            addCriterion("COURSE_SCORE <", value, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COURSE_SCORE <=", value, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreIn(List<BigDecimal> values) {
            addCriterion("COURSE_SCORE in", values, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreNotIn(List<BigDecimal> values) {
            addCriterion("COURSE_SCORE not in", values, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COURSE_SCORE between", value1, value2, "courseScore");
            return (Criteria) this;
        }

        public Criteria andCourseScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COURSE_SCORE not between", value1, value2, "courseScore");
            return (Criteria) this;
        }

        public Criteria andHandlePloIsNull() {
            addCriterion("HANDLE_PLO is null");
            return (Criteria) this;
        }

        public Criteria andHandlePloIsNotNull() {
            addCriterion("HANDLE_PLO is not null");
            return (Criteria) this;
        }

        public Criteria andHandlePloEqualTo(String value) {
            addCriterion("HANDLE_PLO =", value, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloNotEqualTo(String value) {
            addCriterion("HANDLE_PLO <>", value, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloGreaterThan(String value) {
            addCriterion("HANDLE_PLO >", value, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLE_PLO >=", value, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloLessThan(String value) {
            addCriterion("HANDLE_PLO <", value, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloLessThanOrEqualTo(String value) {
            addCriterion("HANDLE_PLO <=", value, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloLike(String value) {
            addCriterion("HANDLE_PLO like", value, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloNotLike(String value) {
            addCriterion("HANDLE_PLO not like", value, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloIn(List<String> values) {
            addCriterion("HANDLE_PLO in", values, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloNotIn(List<String> values) {
            addCriterion("HANDLE_PLO not in", values, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloBetween(String value1, String value2) {
            addCriterion("HANDLE_PLO between", value1, value2, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandlePloNotBetween(String value1, String value2) {
            addCriterion("HANDLE_PLO not between", value1, value2, "handlePlo");
            return (Criteria) this;
        }

        public Criteria andHandleDateIsNull() {
            addCriterion("HANDLE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andHandleDateIsNotNull() {
            addCriterion("HANDLE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andHandleDateEqualTo(String value) {
            addCriterion("HANDLE_DATE =", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotEqualTo(String value) {
            addCriterion("HANDLE_DATE <>", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateGreaterThan(String value) {
            addCriterion("HANDLE_DATE >", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLE_DATE >=", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateLessThan(String value) {
            addCriterion("HANDLE_DATE <", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateLessThanOrEqualTo(String value) {
            addCriterion("HANDLE_DATE <=", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateLike(String value) {
            addCriterion("HANDLE_DATE like", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotLike(String value) {
            addCriterion("HANDLE_DATE not like", value, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateIn(List<String> values) {
            addCriterion("HANDLE_DATE in", values, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotIn(List<String> values) {
            addCriterion("HANDLE_DATE not in", values, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateBetween(String value1, String value2) {
            addCriterion("HANDLE_DATE between", value1, value2, "handleDate");
            return (Criteria) this;
        }

        public Criteria andHandleDateNotBetween(String value1, String value2) {
            addCriterion("HANDLE_DATE not between", value1, value2, "handleDate");
            return (Criteria) this;
        }

        public Criteria andStudyNumIsNull() {
            addCriterion("STUDY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andStudyNumIsNotNull() {
            addCriterion("STUDY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andStudyNumEqualTo(Integer value) {
            addCriterion("STUDY_NUM =", value, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumNotEqualTo(Integer value) {
            addCriterion("STUDY_NUM <>", value, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumGreaterThan(Integer value) {
            addCriterion("STUDY_NUM >", value, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("STUDY_NUM >=", value, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumLessThan(Integer value) {
            addCriterion("STUDY_NUM <", value, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumLessThanOrEqualTo(Integer value) {
            addCriterion("STUDY_NUM <=", value, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumIn(List<Integer> values) {
            addCriterion("STUDY_NUM in", values, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumNotIn(List<Integer> values) {
            addCriterion("STUDY_NUM not in", values, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumBetween(Integer value1, Integer value2) {
            addCriterion("STUDY_NUM between", value1, value2, "studyNum");
            return (Criteria) this;
        }

        public Criteria andStudyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("STUDY_NUM not between", value1, value2, "studyNum");
            return (Criteria) this;
        }

        public Criteria andTopFlagIsNull() {
            addCriterion("TOP_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andTopFlagIsNotNull() {
            addCriterion("TOP_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andTopFlagEqualTo(Short value) {
            addCriterion("TOP_FLAG =", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagNotEqualTo(Short value) {
            addCriterion("TOP_FLAG <>", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagGreaterThan(Short value) {
            addCriterion("TOP_FLAG >", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagGreaterThanOrEqualTo(Short value) {
            addCriterion("TOP_FLAG >=", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagLessThan(Short value) {
            addCriterion("TOP_FLAG <", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagLessThanOrEqualTo(Short value) {
            addCriterion("TOP_FLAG <=", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagIn(List<Short> values) {
            addCriterion("TOP_FLAG in", values, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagNotIn(List<Short> values) {
            addCriterion("TOP_FLAG not in", values, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagBetween(Short value1, Short value2) {
            addCriterion("TOP_FLAG between", value1, value2, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagNotBetween(Short value1, Short value2) {
            addCriterion("TOP_FLAG not between", value1, value2, "topFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagIsNull() {
            addCriterion("RECOMMEND_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagIsNotNull() {
            addCriterion("RECOMMEND_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagEqualTo(BigDecimal value) {
            addCriterion("RECOMMEND_FLAG =", value, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagNotEqualTo(BigDecimal value) {
            addCriterion("RECOMMEND_FLAG <>", value, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagGreaterThan(BigDecimal value) {
            addCriterion("RECOMMEND_FLAG >", value, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RECOMMEND_FLAG >=", value, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagLessThan(BigDecimal value) {
            addCriterion("RECOMMEND_FLAG <", value, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RECOMMEND_FLAG <=", value, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagIn(List<BigDecimal> values) {
            addCriterion("RECOMMEND_FLAG in", values, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagNotIn(List<BigDecimal> values) {
            addCriterion("RECOMMEND_FLAG not in", values, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RECOMMEND_FLAG between", value1, value2, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendFlagNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RECOMMEND_FLAG not between", value1, value2, "recommendFlag");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNull() {
            addCriterion("RECOMMEND is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNotNull() {
            addCriterion("RECOMMEND is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendEqualTo(String value) {
            addCriterion("RECOMMEND =", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotEqualTo(String value) {
            addCriterion("RECOMMEND <>", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThan(String value) {
            addCriterion("RECOMMEND >", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThanOrEqualTo(String value) {
            addCriterion("RECOMMEND >=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThan(String value) {
            addCriterion("RECOMMEND <", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThanOrEqualTo(String value) {
            addCriterion("RECOMMEND <=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLike(String value) {
            addCriterion("RECOMMEND like", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotLike(String value) {
            addCriterion("RECOMMEND not like", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendIn(List<String> values) {
            addCriterion("RECOMMEND in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotIn(List<String> values) {
            addCriterion("RECOMMEND not in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendBetween(String value1, String value2) {
            addCriterion("RECOMMEND between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotBetween(String value1, String value2) {
            addCriterion("RECOMMEND not between", value1, value2, "recommend");
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