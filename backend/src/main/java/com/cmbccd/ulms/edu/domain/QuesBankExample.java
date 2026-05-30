package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class QuesBankExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuesBankExample() {
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

        public Criteria andQuesStemIsNull() {
            addCriterion("QUES_STEM is null");
            return (Criteria) this;
        }

        public Criteria andQuesStemIsNotNull() {
            addCriterion("QUES_STEM is not null");
            return (Criteria) this;
        }

        public Criteria andQuesStemEqualTo(String value) {
            addCriterion("QUES_STEM =", value, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemNotEqualTo(String value) {
            addCriterion("QUES_STEM <>", value, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemGreaterThan(String value) {
            addCriterion("QUES_STEM >", value, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemGreaterThanOrEqualTo(String value) {
            addCriterion("QUES_STEM >=", value, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemLessThan(String value) {
            addCriterion("QUES_STEM <", value, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemLessThanOrEqualTo(String value) {
            addCriterion("QUES_STEM <=", value, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemLike(String value) {
            addCriterion("QUES_STEM like", value, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemNotLike(String value) {
            addCriterion("QUES_STEM not like", value, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemIn(List<String> values) {
            addCriterion("QUES_STEM in", values, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemNotIn(List<String> values) {
            addCriterion("QUES_STEM not in", values, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemBetween(String value1, String value2) {
            addCriterion("QUES_STEM between", value1, value2, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesStemNotBetween(String value1, String value2) {
            addCriterion("QUES_STEM not between", value1, value2, "quesStem");
            return (Criteria) this;
        }

        public Criteria andQuesTypeIsNull() {
            addCriterion("QUES_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andQuesTypeIsNotNull() {
            addCriterion("QUES_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andQuesTypeEqualTo(Integer value) {
            addCriterion("QUES_TYPE =", value, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeNotEqualTo(Integer value) {
            addCriterion("QUES_TYPE <>", value, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeGreaterThan(Integer value) {
            addCriterion("QUES_TYPE >", value, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUES_TYPE >=", value, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeLessThan(Integer value) {
            addCriterion("QUES_TYPE <", value, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeLessThanOrEqualTo(Integer value) {
            addCriterion("QUES_TYPE <=", value, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeIn(List<Integer> values) {
            addCriterion("QUES_TYPE in", values, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeNotIn(List<Integer> values) {
            addCriterion("QUES_TYPE not in", values, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeBetween(Integer value1, Integer value2) {
            addCriterion("QUES_TYPE between", value1, value2, "quesType");
            return (Criteria) this;
        }

        public Criteria andQuesTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("QUES_TYPE not between", value1, value2, "quesType");
            return (Criteria) this;
        }

        public Criteria andFileCodeIsNull() {
            addCriterion("FILE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andFileCodeIsNotNull() {
            addCriterion("FILE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andFileCodeEqualTo(String value) {
            addCriterion("FILE_CODE =", value, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeNotEqualTo(String value) {
            addCriterion("FILE_CODE <>", value, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeGreaterThan(String value) {
            addCriterion("FILE_CODE >", value, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_CODE >=", value, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeLessThan(String value) {
            addCriterion("FILE_CODE <", value, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeLessThanOrEqualTo(String value) {
            addCriterion("FILE_CODE <=", value, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeLike(String value) {
            addCriterion("FILE_CODE like", value, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeNotLike(String value) {
            addCriterion("FILE_CODE not like", value, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeIn(List<String> values) {
            addCriterion("FILE_CODE in", values, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeNotIn(List<String> values) {
            addCriterion("FILE_CODE not in", values, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeBetween(String value1, String value2) {
            addCriterion("FILE_CODE between", value1, value2, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileCodeNotBetween(String value1, String value2) {
            addCriterion("FILE_CODE not between", value1, value2, "fileCode");
            return (Criteria) this;
        }

        public Criteria andFileTypeIsNull() {
            addCriterion("FILE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFileTypeIsNotNull() {
            addCriterion("FILE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFileTypeEqualTo(String value) {
            addCriterion("FILE_TYPE =", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotEqualTo(String value) {
            addCriterion("FILE_TYPE <>", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeGreaterThan(String value) {
            addCriterion("FILE_TYPE >", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_TYPE >=", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLessThan(String value) {
            addCriterion("FILE_TYPE <", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLessThanOrEqualTo(String value) {
            addCriterion("FILE_TYPE <=", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLike(String value) {
            addCriterion("FILE_TYPE like", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotLike(String value) {
            addCriterion("FILE_TYPE not like", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeIn(List<String> values) {
            addCriterion("FILE_TYPE in", values, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotIn(List<String> values) {
            addCriterion("FILE_TYPE not in", values, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeBetween(String value1, String value2) {
            addCriterion("FILE_TYPE between", value1, value2, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotBetween(String value1, String value2) {
            addCriterion("FILE_TYPE not between", value1, value2, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileDurationIsNull() {
            addCriterion("FILE_DURATION is null");
            return (Criteria) this;
        }

        public Criteria andFileDurationIsNotNull() {
            addCriterion("FILE_DURATION is not null");
            return (Criteria) this;
        }

        public Criteria andFileDurationEqualTo(Integer value) {
            addCriterion("FILE_DURATION =", value, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationNotEqualTo(Integer value) {
            addCriterion("FILE_DURATION <>", value, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationGreaterThan(Integer value) {
            addCriterion("FILE_DURATION >", value, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("FILE_DURATION >=", value, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationLessThan(Integer value) {
            addCriterion("FILE_DURATION <", value, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationLessThanOrEqualTo(Integer value) {
            addCriterion("FILE_DURATION <=", value, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationIn(List<Integer> values) {
            addCriterion("FILE_DURATION in", values, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationNotIn(List<Integer> values) {
            addCriterion("FILE_DURATION not in", values, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationBetween(Integer value1, Integer value2) {
            addCriterion("FILE_DURATION between", value1, value2, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andFileDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("FILE_DURATION not between", value1, value2, "fileDuration");
            return (Criteria) this;
        }

        public Criteria andOptionsIsNull() {
            addCriterion("OPTIONS is null");
            return (Criteria) this;
        }

        public Criteria andOptionsIsNotNull() {
            addCriterion("OPTIONS is not null");
            return (Criteria) this;
        }

        public Criteria andOptionsEqualTo(String value) {
            addCriterion("OPTIONS =", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsNotEqualTo(String value) {
            addCriterion("OPTIONS <>", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsGreaterThan(String value) {
            addCriterion("OPTIONS >", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsGreaterThanOrEqualTo(String value) {
            addCriterion("OPTIONS >=", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsLessThan(String value) {
            addCriterion("OPTIONS <", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsLessThanOrEqualTo(String value) {
            addCriterion("OPTIONS <=", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsLike(String value) {
            addCriterion("OPTIONS like", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsNotLike(String value) {
            addCriterion("OPTIONS not like", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsIn(List<String> values) {
            addCriterion("OPTIONS in", values, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsNotIn(List<String> values) {
            addCriterion("OPTIONS not in", values, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsBetween(String value1, String value2) {
            addCriterion("OPTIONS between", value1, value2, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsNotBetween(String value1, String value2) {
            addCriterion("OPTIONS not between", value1, value2, "options");
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

        public Criteria andQuesExplainIsNull() {
            addCriterion("QUES_EXPLAIN is null");
            return (Criteria) this;
        }

        public Criteria andQuesExplainIsNotNull() {
            addCriterion("QUES_EXPLAIN is not null");
            return (Criteria) this;
        }

        public Criteria andQuesExplainEqualTo(String value) {
            addCriterion("QUES_EXPLAIN =", value, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainNotEqualTo(String value) {
            addCriterion("QUES_EXPLAIN <>", value, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainGreaterThan(String value) {
            addCriterion("QUES_EXPLAIN >", value, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainGreaterThanOrEqualTo(String value) {
            addCriterion("QUES_EXPLAIN >=", value, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainLessThan(String value) {
            addCriterion("QUES_EXPLAIN <", value, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainLessThanOrEqualTo(String value) {
            addCriterion("QUES_EXPLAIN <=", value, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainLike(String value) {
            addCriterion("QUES_EXPLAIN like", value, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainNotLike(String value) {
            addCriterion("QUES_EXPLAIN not like", value, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainIn(List<String> values) {
            addCriterion("QUES_EXPLAIN in", values, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainNotIn(List<String> values) {
            addCriterion("QUES_EXPLAIN not in", values, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainBetween(String value1, String value2) {
            addCriterion("QUES_EXPLAIN between", value1, value2, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesExplainNotBetween(String value1, String value2) {
            addCriterion("QUES_EXPLAIN not between", value1, value2, "quesExplain");
            return (Criteria) this;
        }

        public Criteria andQuesStatIsNull() {
            addCriterion("QUES_STAT is null");
            return (Criteria) this;
        }

        public Criteria andQuesStatIsNotNull() {
            addCriterion("QUES_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andQuesStatEqualTo(Integer value) {
            addCriterion("QUES_STAT =", value, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatNotEqualTo(Integer value) {
            addCriterion("QUES_STAT <>", value, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatGreaterThan(Integer value) {
            addCriterion("QUES_STAT >", value, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUES_STAT >=", value, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatLessThan(Integer value) {
            addCriterion("QUES_STAT <", value, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatLessThanOrEqualTo(Integer value) {
            addCriterion("QUES_STAT <=", value, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatIn(List<Integer> values) {
            addCriterion("QUES_STAT in", values, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatNotIn(List<Integer> values) {
            addCriterion("QUES_STAT not in", values, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatBetween(Integer value1, Integer value2) {
            addCriterion("QUES_STAT between", value1, value2, "quesStat");
            return (Criteria) this;
        }

        public Criteria andQuesStatNotBetween(Integer value1, Integer value2) {
            addCriterion("QUES_STAT not between", value1, value2, "quesStat");
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

        public Criteria andCategoryIsNull() {
            addCriterion("CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("CATEGORY =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("CATEGORY <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("CATEGORY >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("CATEGORY <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("CATEGORY like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("CATEGORY not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("CATEGORY in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("CATEGORY not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("CATEGORY between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("CATEGORY not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andKnowledgeIsNull() {
            addCriterion("KNOWLEDGE is null");
            return (Criteria) this;
        }

        public Criteria andKnowledgeIsNotNull() {
            addCriterion("KNOWLEDGE is not null");
            return (Criteria) this;
        }

        public Criteria andKnowledgeEqualTo(String value) {
            addCriterion("KNOWLEDGE =", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeNotEqualTo(String value) {
            addCriterion("KNOWLEDGE <>", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeGreaterThan(String value) {
            addCriterion("KNOWLEDGE >", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeGreaterThanOrEqualTo(String value) {
            addCriterion("KNOWLEDGE >=", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeLessThan(String value) {
            addCriterion("KNOWLEDGE <", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeLessThanOrEqualTo(String value) {
            addCriterion("KNOWLEDGE <=", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeLike(String value) {
            addCriterion("KNOWLEDGE like", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeNotLike(String value) {
            addCriterion("KNOWLEDGE not like", value, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeIn(List<String> values) {
            addCriterion("KNOWLEDGE in", values, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeNotIn(List<String> values) {
            addCriterion("KNOWLEDGE not in", values, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeBetween(String value1, String value2) {
            addCriterion("KNOWLEDGE between", value1, value2, "knowledge");
            return (Criteria) this;
        }

        public Criteria andKnowledgeNotBetween(String value1, String value2) {
            addCriterion("KNOWLEDGE not between", value1, value2, "knowledge");
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