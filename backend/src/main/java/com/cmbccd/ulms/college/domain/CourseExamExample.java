package com.cmbccd.ulms.college.domain;

import java.util.ArrayList;
import java.util.List;

public class CourseExamExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CourseExamExample() {
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

        public Criteria andQuesNumIsNull() {
            addCriterion("QUES_NUM is null");
            return (Criteria) this;
        }

        public Criteria andQuesNumIsNotNull() {
            addCriterion("QUES_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andQuesNumEqualTo(Short value) {
            addCriterion("QUES_NUM =", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotEqualTo(Short value) {
            addCriterion("QUES_NUM <>", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumGreaterThan(Short value) {
            addCriterion("QUES_NUM >", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumGreaterThanOrEqualTo(Short value) {
            addCriterion("QUES_NUM >=", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumLessThan(Short value) {
            addCriterion("QUES_NUM <", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumLessThanOrEqualTo(Short value) {
            addCriterion("QUES_NUM <=", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumIn(List<Short> values) {
            addCriterion("QUES_NUM in", values, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotIn(List<Short> values) {
            addCriterion("QUES_NUM not in", values, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumBetween(Short value1, Short value2) {
            addCriterion("QUES_NUM between", value1, value2, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotBetween(Short value1, Short value2) {
            addCriterion("QUES_NUM not between", value1, value2, "quesNum");
            return (Criteria) this;
        }

        public Criteria andPassNumIsNull() {
            addCriterion("PASS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPassNumIsNotNull() {
            addCriterion("PASS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPassNumEqualTo(Short value) {
            addCriterion("PASS_NUM =", value, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumNotEqualTo(Short value) {
            addCriterion("PASS_NUM <>", value, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumGreaterThan(Short value) {
            addCriterion("PASS_NUM >", value, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumGreaterThanOrEqualTo(Short value) {
            addCriterion("PASS_NUM >=", value, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumLessThan(Short value) {
            addCriterion("PASS_NUM <", value, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumLessThanOrEqualTo(Short value) {
            addCriterion("PASS_NUM <=", value, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumIn(List<Short> values) {
            addCriterion("PASS_NUM in", values, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumNotIn(List<Short> values) {
            addCriterion("PASS_NUM not in", values, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumBetween(Short value1, Short value2) {
            addCriterion("PASS_NUM between", value1, value2, "passNum");
            return (Criteria) this;
        }

        public Criteria andPassNumNotBetween(Short value1, Short value2) {
            addCriterion("PASS_NUM not between", value1, value2, "passNum");
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

        public Criteria andRightNumIsNull() {
            addCriterion("RIGHT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andRightNumIsNotNull() {
            addCriterion("RIGHT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andRightNumEqualTo(Short value) {
            addCriterion("RIGHT_NUM =", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumNotEqualTo(Short value) {
            addCriterion("RIGHT_NUM <>", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumGreaterThan(Short value) {
            addCriterion("RIGHT_NUM >", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumGreaterThanOrEqualTo(Short value) {
            addCriterion("RIGHT_NUM >=", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumLessThan(Short value) {
            addCriterion("RIGHT_NUM <", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumLessThanOrEqualTo(Short value) {
            addCriterion("RIGHT_NUM <=", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumIn(List<Short> values) {
            addCriterion("RIGHT_NUM in", values, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumNotIn(List<Short> values) {
            addCriterion("RIGHT_NUM not in", values, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumBetween(Short value1, Short value2) {
            addCriterion("RIGHT_NUM between", value1, value2, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumNotBetween(Short value1, Short value2) {
            addCriterion("RIGHT_NUM not between", value1, value2, "rightNum");
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