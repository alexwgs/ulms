package com.cmbccd.ulms.college.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TeacherExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TeacherExample() {
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

        public Criteria andSkillTypeIsNull() {
            addCriterion("SKILL_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSkillTypeIsNotNull() {
            addCriterion("SKILL_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSkillTypeEqualTo(Short value) {
            addCriterion("SKILL_TYPE =", value, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeNotEqualTo(Short value) {
            addCriterion("SKILL_TYPE <>", value, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeGreaterThan(Short value) {
            addCriterion("SKILL_TYPE >", value, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("SKILL_TYPE >=", value, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeLessThan(Short value) {
            addCriterion("SKILL_TYPE <", value, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeLessThanOrEqualTo(Short value) {
            addCriterion("SKILL_TYPE <=", value, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeIn(List<Short> values) {
            addCriterion("SKILL_TYPE in", values, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeNotIn(List<Short> values) {
            addCriterion("SKILL_TYPE not in", values, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeBetween(Short value1, Short value2) {
            addCriterion("SKILL_TYPE between", value1, value2, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillTypeNotBetween(Short value1, Short value2) {
            addCriterion("SKILL_TYPE not between", value1, value2, "skillType");
            return (Criteria) this;
        }

        public Criteria andSkillNameIsNull() {
            addCriterion("SKILL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSkillNameIsNotNull() {
            addCriterion("SKILL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSkillNameEqualTo(Short value) {
            addCriterion("SKILL_NAME =", value, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameNotEqualTo(Short value) {
            addCriterion("SKILL_NAME <>", value, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameGreaterThan(Short value) {
            addCriterion("SKILL_NAME >", value, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameGreaterThanOrEqualTo(Short value) {
            addCriterion("SKILL_NAME >=", value, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameLessThan(Short value) {
            addCriterion("SKILL_NAME <", value, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameLessThanOrEqualTo(Short value) {
            addCriterion("SKILL_NAME <=", value, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameIn(List<Short> values) {
            addCriterion("SKILL_NAME in", values, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameNotIn(List<Short> values) {
            addCriterion("SKILL_NAME not in", values, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameBetween(Short value1, Short value2) {
            addCriterion("SKILL_NAME between", value1, value2, "skillName");
            return (Criteria) this;
        }

        public Criteria andSkillNameNotBetween(Short value1, Short value2) {
            addCriterion("SKILL_NAME not between", value1, value2, "skillName");
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

        public Criteria andAvatarIsNull() {
            addCriterion("AVATAR is null");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNotNull() {
            addCriterion("AVATAR is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarEqualTo(String value) {
            addCriterion("AVATAR =", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotEqualTo(String value) {
            addCriterion("AVATAR <>", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThan(String value) {
            addCriterion("AVATAR >", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("AVATAR >=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThan(String value) {
            addCriterion("AVATAR <", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThanOrEqualTo(String value) {
            addCriterion("AVATAR <=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLike(String value) {
            addCriterion("AVATAR like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotLike(String value) {
            addCriterion("AVATAR not like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarIn(List<String> values) {
            addCriterion("AVATAR in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotIn(List<String> values) {
            addCriterion("AVATAR not in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarBetween(String value1, String value2) {
            addCriterion("AVATAR between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotBetween(String value1, String value2) {
            addCriterion("AVATAR not between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andIntroduceIsNull() {
            addCriterion("INTRODUCE is null");
            return (Criteria) this;
        }

        public Criteria andIntroduceIsNotNull() {
            addCriterion("INTRODUCE is not null");
            return (Criteria) this;
        }

        public Criteria andIntroduceEqualTo(String value) {
            addCriterion("INTRODUCE =", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotEqualTo(String value) {
            addCriterion("INTRODUCE <>", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceGreaterThan(String value) {
            addCriterion("INTRODUCE >", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceGreaterThanOrEqualTo(String value) {
            addCriterion("INTRODUCE >=", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLessThan(String value) {
            addCriterion("INTRODUCE <", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLessThanOrEqualTo(String value) {
            addCriterion("INTRODUCE <=", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLike(String value) {
            addCriterion("INTRODUCE like", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotLike(String value) {
            addCriterion("INTRODUCE not like", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceIn(List<String> values) {
            addCriterion("INTRODUCE in", values, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotIn(List<String> values) {
            addCriterion("INTRODUCE not in", values, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceBetween(String value1, String value2) {
            addCriterion("INTRODUCE between", value1, value2, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotBetween(String value1, String value2) {
            addCriterion("INTRODUCE not between", value1, value2, "introduce");
            return (Criteria) this;
        }

        public Criteria andCourseNumIsNull() {
            addCriterion("COURSE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andCourseNumIsNotNull() {
            addCriterion("COURSE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andCourseNumEqualTo(Short value) {
            addCriterion("COURSE_NUM =", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumNotEqualTo(Short value) {
            addCriterion("COURSE_NUM <>", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumGreaterThan(Short value) {
            addCriterion("COURSE_NUM >", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumGreaterThanOrEqualTo(Short value) {
            addCriterion("COURSE_NUM >=", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumLessThan(Short value) {
            addCriterion("COURSE_NUM <", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumLessThanOrEqualTo(Short value) {
            addCriterion("COURSE_NUM <=", value, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumIn(List<Short> values) {
            addCriterion("COURSE_NUM in", values, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumNotIn(List<Short> values) {
            addCriterion("COURSE_NUM not in", values, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumBetween(Short value1, Short value2) {
            addCriterion("COURSE_NUM between", value1, value2, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseNumNotBetween(Short value1, Short value2) {
            addCriterion("COURSE_NUM not between", value1, value2, "courseNum");
            return (Criteria) this;
        }

        public Criteria andCourseHourIsNull() {
            addCriterion("COURSE_HOUR is null");
            return (Criteria) this;
        }

        public Criteria andCourseHourIsNotNull() {
            addCriterion("COURSE_HOUR is not null");
            return (Criteria) this;
        }

        public Criteria andCourseHourEqualTo(BigDecimal value) {
            addCriterion("COURSE_HOUR =", value, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourNotEqualTo(BigDecimal value) {
            addCriterion("COURSE_HOUR <>", value, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourGreaterThan(BigDecimal value) {
            addCriterion("COURSE_HOUR >", value, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COURSE_HOUR >=", value, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourLessThan(BigDecimal value) {
            addCriterion("COURSE_HOUR <", value, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COURSE_HOUR <=", value, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourIn(List<BigDecimal> values) {
            addCriterion("COURSE_HOUR in", values, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourNotIn(List<BigDecimal> values) {
            addCriterion("COURSE_HOUR not in", values, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COURSE_HOUR between", value1, value2, "courseHour");
            return (Criteria) this;
        }

        public Criteria andCourseHourNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COURSE_HOUR not between", value1, value2, "courseHour");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("SCORE is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("SCORE is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(BigDecimal value) {
            addCriterion("SCORE =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(BigDecimal value) {
            addCriterion("SCORE <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(BigDecimal value) {
            addCriterion("SCORE >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SCORE >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(BigDecimal value) {
            addCriterion("SCORE <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SCORE <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<BigDecimal> values) {
            addCriterion("SCORE in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<BigDecimal> values) {
            addCriterion("SCORE not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCORE between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCORE not between", value1, value2, "score");
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

        public Criteria andHonorIsNull() {
            addCriterion("HONOR is null");
            return (Criteria) this;
        }

        public Criteria andHonorIsNotNull() {
            addCriterion("HONOR is not null");
            return (Criteria) this;
        }

        public Criteria andHonorEqualTo(String value) {
            addCriterion("HONOR =", value, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorNotEqualTo(String value) {
            addCriterion("HONOR <>", value, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorGreaterThan(String value) {
            addCriterion("HONOR >", value, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorGreaterThanOrEqualTo(String value) {
            addCriterion("HONOR >=", value, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorLessThan(String value) {
            addCriterion("HONOR <", value, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorLessThanOrEqualTo(String value) {
            addCriterion("HONOR <=", value, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorLike(String value) {
            addCriterion("HONOR like", value, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorNotLike(String value) {
            addCriterion("HONOR not like", value, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorIn(List<String> values) {
            addCriterion("HONOR in", values, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorNotIn(List<String> values) {
            addCriterion("HONOR not in", values, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorBetween(String value1, String value2) {
            addCriterion("HONOR between", value1, value2, "honor");
            return (Criteria) this;
        }

        public Criteria andHonorNotBetween(String value1, String value2) {
            addCriterion("HONOR not between", value1, value2, "honor");
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