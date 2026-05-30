package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class ExamInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExamInfoExample() {
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

        public Criteria andExamNameIsNull() {
            addCriterion("EXAM_NAME is null");
            return (Criteria) this;
        }

        public Criteria andExamNameIsNotNull() {
            addCriterion("EXAM_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andExamNameEqualTo(String value) {
            addCriterion("EXAM_NAME =", value, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameNotEqualTo(String value) {
            addCriterion("EXAM_NAME <>", value, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameGreaterThan(String value) {
            addCriterion("EXAM_NAME >", value, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameGreaterThanOrEqualTo(String value) {
            addCriterion("EXAM_NAME >=", value, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameLessThan(String value) {
            addCriterion("EXAM_NAME <", value, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameLessThanOrEqualTo(String value) {
            addCriterion("EXAM_NAME <=", value, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameLike(String value) {
            addCriterion("EXAM_NAME like", value, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameNotLike(String value) {
            addCriterion("EXAM_NAME not like", value, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameIn(List<String> values) {
            addCriterion("EXAM_NAME in", values, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameNotIn(List<String> values) {
            addCriterion("EXAM_NAME not in", values, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameBetween(String value1, String value2) {
            addCriterion("EXAM_NAME between", value1, value2, "examName");
            return (Criteria) this;
        }

        public Criteria andExamNameNotBetween(String value1, String value2) {
            addCriterion("EXAM_NAME not between", value1, value2, "examName");
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

        public Criteria andExamTimeIsNull() {
            addCriterion("EXAM_TIME is null");
            return (Criteria) this;
        }

        public Criteria andExamTimeIsNotNull() {
            addCriterion("EXAM_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andExamTimeEqualTo(Integer value) {
            addCriterion("EXAM_TIME =", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeNotEqualTo(Integer value) {
            addCriterion("EXAM_TIME <>", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeGreaterThan(Integer value) {
            addCriterion("EXAM_TIME >", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXAM_TIME >=", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeLessThan(Integer value) {
            addCriterion("EXAM_TIME <", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeLessThanOrEqualTo(Integer value) {
            addCriterion("EXAM_TIME <=", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeIn(List<Integer> values) {
            addCriterion("EXAM_TIME in", values, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeNotIn(List<Integer> values) {
            addCriterion("EXAM_TIME not in", values, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_TIME between", value1, value2, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_TIME not between", value1, value2, "examTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeIsNull() {
            addCriterion("QUES_TIME is null");
            return (Criteria) this;
        }

        public Criteria andQuesTimeIsNotNull() {
            addCriterion("QUES_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andQuesTimeEqualTo(Integer value) {
            addCriterion("QUES_TIME =", value, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeNotEqualTo(Integer value) {
            addCriterion("QUES_TIME <>", value, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeGreaterThan(Integer value) {
            addCriterion("QUES_TIME >", value, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUES_TIME >=", value, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeLessThan(Integer value) {
            addCriterion("QUES_TIME <", value, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeLessThanOrEqualTo(Integer value) {
            addCriterion("QUES_TIME <=", value, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeIn(List<Integer> values) {
            addCriterion("QUES_TIME in", values, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeNotIn(List<Integer> values) {
            addCriterion("QUES_TIME not in", values, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeBetween(Integer value1, Integer value2) {
            addCriterion("QUES_TIME between", value1, value2, "quesTime");
            return (Criteria) this;
        }

        public Criteria andQuesTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("QUES_TIME not between", value1, value2, "quesTime");
            return (Criteria) this;
        }

        public Criteria andIfBreakIsNull() {
            addCriterion("IF_BREAK is null");
            return (Criteria) this;
        }

        public Criteria andIfBreakIsNotNull() {
            addCriterion("IF_BREAK is not null");
            return (Criteria) this;
        }

        public Criteria andIfBreakEqualTo(Integer value) {
            addCriterion("IF_BREAK =", value, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakNotEqualTo(Integer value) {
            addCriterion("IF_BREAK <>", value, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakGreaterThan(Integer value) {
            addCriterion("IF_BREAK >", value, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakGreaterThanOrEqualTo(Integer value) {
            addCriterion("IF_BREAK >=", value, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakLessThan(Integer value) {
            addCriterion("IF_BREAK <", value, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakLessThanOrEqualTo(Integer value) {
            addCriterion("IF_BREAK <=", value, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakIn(List<Integer> values) {
            addCriterion("IF_BREAK in", values, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakNotIn(List<Integer> values) {
            addCriterion("IF_BREAK not in", values, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakBetween(Integer value1, Integer value2) {
            addCriterion("IF_BREAK between", value1, value2, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfBreakNotBetween(Integer value1, Integer value2) {
            addCriterion("IF_BREAK not between", value1, value2, "ifBreak");
            return (Criteria) this;
        }

        public Criteria andIfAgainIsNull() {
            addCriterion("IF_AGAIN is null");
            return (Criteria) this;
        }

        public Criteria andIfAgainIsNotNull() {
            addCriterion("IF_AGAIN is not null");
            return (Criteria) this;
        }

        public Criteria andIfAgainEqualTo(Integer value) {
            addCriterion("IF_AGAIN =", value, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainNotEqualTo(Integer value) {
            addCriterion("IF_AGAIN <>", value, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainGreaterThan(Integer value) {
            addCriterion("IF_AGAIN >", value, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainGreaterThanOrEqualTo(Integer value) {
            addCriterion("IF_AGAIN >=", value, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainLessThan(Integer value) {
            addCriterion("IF_AGAIN <", value, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainLessThanOrEqualTo(Integer value) {
            addCriterion("IF_AGAIN <=", value, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainIn(List<Integer> values) {
            addCriterion("IF_AGAIN in", values, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainNotIn(List<Integer> values) {
            addCriterion("IF_AGAIN not in", values, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainBetween(Integer value1, Integer value2) {
            addCriterion("IF_AGAIN between", value1, value2, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andIfAgainNotBetween(Integer value1, Integer value2) {
            addCriterion("IF_AGAIN not between", value1, value2, "ifAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainIsNull() {
            addCriterion("AUDIO_AGAIN is null");
            return (Criteria) this;
        }

        public Criteria andAudioAgainIsNotNull() {
            addCriterion("AUDIO_AGAIN is not null");
            return (Criteria) this;
        }

        public Criteria andAudioAgainEqualTo(Integer value) {
            addCriterion("AUDIO_AGAIN =", value, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainNotEqualTo(Integer value) {
            addCriterion("AUDIO_AGAIN <>", value, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainGreaterThan(Integer value) {
            addCriterion("AUDIO_AGAIN >", value, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainGreaterThanOrEqualTo(Integer value) {
            addCriterion("AUDIO_AGAIN >=", value, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainLessThan(Integer value) {
            addCriterion("AUDIO_AGAIN <", value, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainLessThanOrEqualTo(Integer value) {
            addCriterion("AUDIO_AGAIN <=", value, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainIn(List<Integer> values) {
            addCriterion("AUDIO_AGAIN in", values, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainNotIn(List<Integer> values) {
            addCriterion("AUDIO_AGAIN not in", values, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainBetween(Integer value1, Integer value2) {
            addCriterion("AUDIO_AGAIN between", value1, value2, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andAudioAgainNotBetween(Integer value1, Integer value2) {
            addCriterion("AUDIO_AGAIN not between", value1, value2, "audioAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainIsNull() {
            addCriterion("VIDEO_AGAIN is null");
            return (Criteria) this;
        }

        public Criteria andVideoAgainIsNotNull() {
            addCriterion("VIDEO_AGAIN is not null");
            return (Criteria) this;
        }

        public Criteria andVideoAgainEqualTo(Integer value) {
            addCriterion("VIDEO_AGAIN =", value, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainNotEqualTo(Integer value) {
            addCriterion("VIDEO_AGAIN <>", value, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainGreaterThan(Integer value) {
            addCriterion("VIDEO_AGAIN >", value, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainGreaterThanOrEqualTo(Integer value) {
            addCriterion("VIDEO_AGAIN >=", value, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainLessThan(Integer value) {
            addCriterion("VIDEO_AGAIN <", value, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainLessThanOrEqualTo(Integer value) {
            addCriterion("VIDEO_AGAIN <=", value, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainIn(List<Integer> values) {
            addCriterion("VIDEO_AGAIN in", values, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainNotIn(List<Integer> values) {
            addCriterion("VIDEO_AGAIN not in", values, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainBetween(Integer value1, Integer value2) {
            addCriterion("VIDEO_AGAIN between", value1, value2, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andVideoAgainNotBetween(Integer value1, Integer value2) {
            addCriterion("VIDEO_AGAIN not between", value1, value2, "videoAgain");
            return (Criteria) this;
        }

        public Criteria andScoreMethodIsNull() {
            addCriterion("SCORE_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andScoreMethodIsNotNull() {
            addCriterion("SCORE_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andScoreMethodEqualTo(Integer value) {
            addCriterion("SCORE_METHOD =", value, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodNotEqualTo(Integer value) {
            addCriterion("SCORE_METHOD <>", value, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodGreaterThan(Integer value) {
            addCriterion("SCORE_METHOD >", value, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodGreaterThanOrEqualTo(Integer value) {
            addCriterion("SCORE_METHOD >=", value, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodLessThan(Integer value) {
            addCriterion("SCORE_METHOD <", value, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodLessThanOrEqualTo(Integer value) {
            addCriterion("SCORE_METHOD <=", value, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodIn(List<Integer> values) {
            addCriterion("SCORE_METHOD in", values, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodNotIn(List<Integer> values) {
            addCriterion("SCORE_METHOD not in", values, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodBetween(Integer value1, Integer value2) {
            addCriterion("SCORE_METHOD between", value1, value2, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andScoreMethodNotBetween(Integer value1, Integer value2) {
            addCriterion("SCORE_METHOD not between", value1, value2, "scoreMethod");
            return (Criteria) this;
        }

        public Criteria andUnitScoreIsNull() {
            addCriterion("UNIT_SCORE is null");
            return (Criteria) this;
        }

        public Criteria andUnitScoreIsNotNull() {
            addCriterion("UNIT_SCORE is not null");
            return (Criteria) this;
        }

        public Criteria andUnitScoreEqualTo(Integer value) {
            addCriterion("UNIT_SCORE =", value, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreNotEqualTo(Integer value) {
            addCriterion("UNIT_SCORE <>", value, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreGreaterThan(Integer value) {
            addCriterion("UNIT_SCORE >", value, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("UNIT_SCORE >=", value, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreLessThan(Integer value) {
            addCriterion("UNIT_SCORE <", value, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreLessThanOrEqualTo(Integer value) {
            addCriterion("UNIT_SCORE <=", value, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreIn(List<Integer> values) {
            addCriterion("UNIT_SCORE in", values, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreNotIn(List<Integer> values) {
            addCriterion("UNIT_SCORE not in", values, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreBetween(Integer value1, Integer value2) {
            addCriterion("UNIT_SCORE between", value1, value2, "unitScore");
            return (Criteria) this;
        }

        public Criteria andUnitScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("UNIT_SCORE not between", value1, value2, "unitScore");
            return (Criteria) this;
        }

        public Criteria andQuesRandomIsNull() {
            addCriterion("QUES_RANDOM is null");
            return (Criteria) this;
        }

        public Criteria andQuesRandomIsNotNull() {
            addCriterion("QUES_RANDOM is not null");
            return (Criteria) this;
        }

        public Criteria andQuesRandomEqualTo(Integer value) {
            addCriterion("QUES_RANDOM =", value, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomNotEqualTo(Integer value) {
            addCriterion("QUES_RANDOM <>", value, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomGreaterThan(Integer value) {
            addCriterion("QUES_RANDOM >", value, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUES_RANDOM >=", value, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomLessThan(Integer value) {
            addCriterion("QUES_RANDOM <", value, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomLessThanOrEqualTo(Integer value) {
            addCriterion("QUES_RANDOM <=", value, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomIn(List<Integer> values) {
            addCriterion("QUES_RANDOM in", values, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomNotIn(List<Integer> values) {
            addCriterion("QUES_RANDOM not in", values, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomBetween(Integer value1, Integer value2) {
            addCriterion("QUES_RANDOM between", value1, value2, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andQuesRandomNotBetween(Integer value1, Integer value2) {
            addCriterion("QUES_RANDOM not between", value1, value2, "quesRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomIsNull() {
            addCriterion("OPTION_RANDOM is null");
            return (Criteria) this;
        }

        public Criteria andOptionRandomIsNotNull() {
            addCriterion("OPTION_RANDOM is not null");
            return (Criteria) this;
        }

        public Criteria andOptionRandomEqualTo(Integer value) {
            addCriterion("OPTION_RANDOM =", value, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomNotEqualTo(Integer value) {
            addCriterion("OPTION_RANDOM <>", value, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomGreaterThan(Integer value) {
            addCriterion("OPTION_RANDOM >", value, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomGreaterThanOrEqualTo(Integer value) {
            addCriterion("OPTION_RANDOM >=", value, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomLessThan(Integer value) {
            addCriterion("OPTION_RANDOM <", value, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomLessThanOrEqualTo(Integer value) {
            addCriterion("OPTION_RANDOM <=", value, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomIn(List<Integer> values) {
            addCriterion("OPTION_RANDOM in", values, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomNotIn(List<Integer> values) {
            addCriterion("OPTION_RANDOM not in", values, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomBetween(Integer value1, Integer value2) {
            addCriterion("OPTION_RANDOM between", value1, value2, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionRandomNotBetween(Integer value1, Integer value2) {
            addCriterion("OPTION_RANDOM not between", value1, value2, "optionRandom");
            return (Criteria) this;
        }

        public Criteria andOptionOrderIsNull() {
            addCriterion("OPTION_ORDER is null");
            return (Criteria) this;
        }

        public Criteria andOptionOrderIsNotNull() {
            addCriterion("OPTION_ORDER is not null");
            return (Criteria) this;
        }

        public Criteria andOptionOrderEqualTo(Integer value) {
            addCriterion("OPTION_ORDER =", value, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderNotEqualTo(Integer value) {
            addCriterion("OPTION_ORDER <>", value, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderGreaterThan(Integer value) {
            addCriterion("OPTION_ORDER >", value, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("OPTION_ORDER >=", value, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderLessThan(Integer value) {
            addCriterion("OPTION_ORDER <", value, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderLessThanOrEqualTo(Integer value) {
            addCriterion("OPTION_ORDER <=", value, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderIn(List<Integer> values) {
            addCriterion("OPTION_ORDER in", values, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderNotIn(List<Integer> values) {
            addCriterion("OPTION_ORDER not in", values, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderBetween(Integer value1, Integer value2) {
            addCriterion("OPTION_ORDER between", value1, value2, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andOptionOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("OPTION_ORDER not between", value1, value2, "optionOrder");
            return (Criteria) this;
        }

        public Criteria andWrongBreakIsNull() {
            addCriterion("WRONG_BREAK is null");
            return (Criteria) this;
        }

        public Criteria andWrongBreakIsNotNull() {
            addCriterion("WRONG_BREAK is not null");
            return (Criteria) this;
        }

        public Criteria andWrongBreakEqualTo(Integer value) {
            addCriterion("WRONG_BREAK =", value, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakNotEqualTo(Integer value) {
            addCriterion("WRONG_BREAK <>", value, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakGreaterThan(Integer value) {
            addCriterion("WRONG_BREAK >", value, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakGreaterThanOrEqualTo(Integer value) {
            addCriterion("WRONG_BREAK >=", value, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakLessThan(Integer value) {
            addCriterion("WRONG_BREAK <", value, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakLessThanOrEqualTo(Integer value) {
            addCriterion("WRONG_BREAK <=", value, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakIn(List<Integer> values) {
            addCriterion("WRONG_BREAK in", values, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakNotIn(List<Integer> values) {
            addCriterion("WRONG_BREAK not in", values, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakBetween(Integer value1, Integer value2) {
            addCriterion("WRONG_BREAK between", value1, value2, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andWrongBreakNotBetween(Integer value1, Integer value2) {
            addCriterion("WRONG_BREAK not between", value1, value2, "wrongBreak");
            return (Criteria) this;
        }

        public Criteria andExamUseIsNull() {
            addCriterion("EXAM_USE is null");
            return (Criteria) this;
        }

        public Criteria andExamUseIsNotNull() {
            addCriterion("EXAM_USE is not null");
            return (Criteria) this;
        }

        public Criteria andExamUseEqualTo(Integer value) {
            addCriterion("EXAM_USE =", value, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseNotEqualTo(Integer value) {
            addCriterion("EXAM_USE <>", value, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseGreaterThan(Integer value) {
            addCriterion("EXAM_USE >", value, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXAM_USE >=", value, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseLessThan(Integer value) {
            addCriterion("EXAM_USE <", value, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseLessThanOrEqualTo(Integer value) {
            addCriterion("EXAM_USE <=", value, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseIn(List<Integer> values) {
            addCriterion("EXAM_USE in", values, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseNotIn(List<Integer> values) {
            addCriterion("EXAM_USE not in", values, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_USE between", value1, value2, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamUseNotBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_USE not between", value1, value2, "examUse");
            return (Criteria) this;
        }

        public Criteria andExamStatIsNull() {
            addCriterion("EXAM_STAT is null");
            return (Criteria) this;
        }

        public Criteria andExamStatIsNotNull() {
            addCriterion("EXAM_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andExamStatEqualTo(Integer value) {
            addCriterion("EXAM_STAT =", value, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatNotEqualTo(Integer value) {
            addCriterion("EXAM_STAT <>", value, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatGreaterThan(Integer value) {
            addCriterion("EXAM_STAT >", value, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXAM_STAT >=", value, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatLessThan(Integer value) {
            addCriterion("EXAM_STAT <", value, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatLessThanOrEqualTo(Integer value) {
            addCriterion("EXAM_STAT <=", value, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatIn(List<Integer> values) {
            addCriterion("EXAM_STAT in", values, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatNotIn(List<Integer> values) {
            addCriterion("EXAM_STAT not in", values, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_STAT between", value1, value2, "examStat");
            return (Criteria) this;
        }

        public Criteria andExamStatNotBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_STAT not between", value1, value2, "examStat");
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

        public Criteria andExamConditionIsNull() {
            addCriterion("EXAM_CONDITION is null");
            return (Criteria) this;
        }

        public Criteria andExamConditionIsNotNull() {
            addCriterion("EXAM_CONDITION is not null");
            return (Criteria) this;
        }

        public Criteria andExamConditionEqualTo(Integer value) {
            addCriterion("EXAM_CONDITION =", value, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionNotEqualTo(Integer value) {
            addCriterion("EXAM_CONDITION <>", value, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionGreaterThan(Integer value) {
            addCriterion("EXAM_CONDITION >", value, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXAM_CONDITION >=", value, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionLessThan(Integer value) {
            addCriterion("EXAM_CONDITION <", value, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionLessThanOrEqualTo(Integer value) {
            addCriterion("EXAM_CONDITION <=", value, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionIn(List<Integer> values) {
            addCriterion("EXAM_CONDITION in", values, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionNotIn(List<Integer> values) {
            addCriterion("EXAM_CONDITION not in", values, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_CONDITION between", value1, value2, "examCondition");
            return (Criteria) this;
        }

        public Criteria andExamConditionNotBetween(Integer value1, Integer value2) {
            addCriterion("EXAM_CONDITION not between", value1, value2, "examCondition");
            return (Criteria) this;
        }

        public Criteria andPerfDateIsNull() {
            addCriterion("PERF_DATE is null");
            return (Criteria) this;
        }

        public Criteria andPerfDateIsNotNull() {
            addCriterion("PERF_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andPerfDateEqualTo(String value) {
            addCriterion("PERF_DATE =", value, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateNotEqualTo(String value) {
            addCriterion("PERF_DATE <>", value, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateGreaterThan(String value) {
            addCriterion("PERF_DATE >", value, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateGreaterThanOrEqualTo(String value) {
            addCriterion("PERF_DATE >=", value, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateLessThan(String value) {
            addCriterion("PERF_DATE <", value, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateLessThanOrEqualTo(String value) {
            addCriterion("PERF_DATE <=", value, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateLike(String value) {
            addCriterion("PERF_DATE like", value, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateNotLike(String value) {
            addCriterion("PERF_DATE not like", value, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateIn(List<String> values) {
            addCriterion("PERF_DATE in", values, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateNotIn(List<String> values) {
            addCriterion("PERF_DATE not in", values, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateBetween(String value1, String value2) {
            addCriterion("PERF_DATE between", value1, value2, "perfDate");
            return (Criteria) this;
        }

        public Criteria andPerfDateNotBetween(String value1, String value2) {
            addCriterion("PERF_DATE not between", value1, value2, "perfDate");
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

        public Criteria andQuesNumEqualTo(Integer value) {
            addCriterion("QUES_NUM =", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotEqualTo(Integer value) {
            addCriterion("QUES_NUM <>", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumGreaterThan(Integer value) {
            addCriterion("QUES_NUM >", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUES_NUM >=", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumLessThan(Integer value) {
            addCriterion("QUES_NUM <", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumLessThanOrEqualTo(Integer value) {
            addCriterion("QUES_NUM <=", value, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumIn(List<Integer> values) {
            addCriterion("QUES_NUM in", values, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotIn(List<Integer> values) {
            addCriterion("QUES_NUM not in", values, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumBetween(Integer value1, Integer value2) {
            addCriterion("QUES_NUM between", value1, value2, "quesNum");
            return (Criteria) this;
        }

        public Criteria andQuesNumNotBetween(Integer value1, Integer value2) {
            addCriterion("QUES_NUM not between", value1, value2, "quesNum");
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

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("SCORE =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("SCORE <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("SCORE >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("SCORE >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("SCORE <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("SCORE <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("SCORE in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("SCORE not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("SCORE between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("SCORE not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andIfReadIsNull() {
            addCriterion("IF_READ is null");
            return (Criteria) this;
        }

        public Criteria andIfReadIsNotNull() {
            addCriterion("IF_READ is not null");
            return (Criteria) this;
        }

        public Criteria andIfReadEqualTo(Integer value) {
            addCriterion("IF_READ =", value, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadNotEqualTo(Integer value) {
            addCriterion("IF_READ <>", value, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadGreaterThan(Integer value) {
            addCriterion("IF_READ >", value, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadGreaterThanOrEqualTo(Integer value) {
            addCriterion("IF_READ >=", value, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadLessThan(Integer value) {
            addCriterion("IF_READ <", value, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadLessThanOrEqualTo(Integer value) {
            addCriterion("IF_READ <=", value, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadIn(List<Integer> values) {
            addCriterion("IF_READ in", values, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadNotIn(List<Integer> values) {
            addCriterion("IF_READ not in", values, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadBetween(Integer value1, Integer value2) {
            addCriterion("IF_READ between", value1, value2, "ifRead");
            return (Criteria) this;
        }

        public Criteria andIfReadNotBetween(Integer value1, Integer value2) {
            addCriterion("IF_READ not between", value1, value2, "ifRead");
            return (Criteria) this;
        }

        public Criteria andDisputeDateIsNull() {
            addCriterion("DISPUTE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDisputeDateIsNotNull() {
            addCriterion("DISPUTE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDisputeDateEqualTo(String value) {
            addCriterion("DISPUTE_DATE =", value, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateNotEqualTo(String value) {
            addCriterion("DISPUTE_DATE <>", value, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateGreaterThan(String value) {
            addCriterion("DISPUTE_DATE >", value, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateGreaterThanOrEqualTo(String value) {
            addCriterion("DISPUTE_DATE >=", value, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateLessThan(String value) {
            addCriterion("DISPUTE_DATE <", value, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateLessThanOrEqualTo(String value) {
            addCriterion("DISPUTE_DATE <=", value, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateLike(String value) {
            addCriterion("DISPUTE_DATE like", value, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateNotLike(String value) {
            addCriterion("DISPUTE_DATE not like", value, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateIn(List<String> values) {
            addCriterion("DISPUTE_DATE in", values, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateNotIn(List<String> values) {
            addCriterion("DISPUTE_DATE not in", values, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateBetween(String value1, String value2) {
            addCriterion("DISPUTE_DATE between", value1, value2, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andDisputeDateNotBetween(String value1, String value2) {
            addCriterion("DISPUTE_DATE not between", value1, value2, "disputeDate");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNull() {
            addCriterion("AREA_CODE is null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNotNull() {
            addCriterion("AREA_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeEqualTo(String value) {
            addCriterion("AREA_CODE =", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotEqualTo(String value) {
            addCriterion("AREA_CODE <>", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThan(String value) {
            addCriterion("AREA_CODE >", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("AREA_CODE >=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThan(String value) {
            addCriterion("AREA_CODE <", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("AREA_CODE <=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLike(String value) {
            addCriterion("AREA_CODE like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotLike(String value) {
            addCriterion("AREA_CODE not like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIn(List<String> values) {
            addCriterion("AREA_CODE in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotIn(List<String> values) {
            addCriterion("AREA_CODE not in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeBetween(String value1, String value2) {
            addCriterion("AREA_CODE between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotBetween(String value1, String value2) {
            addCriterion("AREA_CODE not between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andSkipQuesIsNull() {
            addCriterion("SKIP_QUES is null");
            return (Criteria) this;
        }

        public Criteria andSkipQuesIsNotNull() {
            addCriterion("SKIP_QUES is not null");
            return (Criteria) this;
        }

        public Criteria andSkipQuesEqualTo(Integer value) {
            addCriterion("SKIP_QUES =", value, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesNotEqualTo(Integer value) {
            addCriterion("SKIP_QUES <>", value, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesGreaterThan(Integer value) {
            addCriterion("SKIP_QUES >", value, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesGreaterThanOrEqualTo(Integer value) {
            addCriterion("SKIP_QUES >=", value, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesLessThan(Integer value) {
            addCriterion("SKIP_QUES <", value, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesLessThanOrEqualTo(Integer value) {
            addCriterion("SKIP_QUES <=", value, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesIn(List<Integer> values) {
            addCriterion("SKIP_QUES in", values, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesNotIn(List<Integer> values) {
            addCriterion("SKIP_QUES not in", values, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesBetween(Integer value1, Integer value2) {
            addCriterion("SKIP_QUES between", value1, value2, "skipQues");
            return (Criteria) this;
        }

        public Criteria andSkipQuesNotBetween(Integer value1, Integer value2) {
            addCriterion("SKIP_QUES not between", value1, value2, "skipQues");
            return (Criteria) this;
        }

        public Criteria andBookExamIsNull() {
            addCriterion("BOOK_EXAM is null");
            return (Criteria) this;
        }

        public Criteria andBookExamIsNotNull() {
            addCriterion("BOOK_EXAM is not null");
            return (Criteria) this;
        }

        public Criteria andBookExamEqualTo(Integer value) {
            addCriterion("BOOK_EXAM =", value, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamNotEqualTo(Integer value) {
            addCriterion("BOOK_EXAM <>", value, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamGreaterThan(Integer value) {
            addCriterion("BOOK_EXAM >", value, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamGreaterThanOrEqualTo(Integer value) {
            addCriterion("BOOK_EXAM >=", value, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamLessThan(Integer value) {
            addCriterion("BOOK_EXAM <", value, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamLessThanOrEqualTo(Integer value) {
            addCriterion("BOOK_EXAM <=", value, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamIn(List<Integer> values) {
            addCriterion("BOOK_EXAM in", values, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamNotIn(List<Integer> values) {
            addCriterion("BOOK_EXAM not in", values, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamBetween(Integer value1, Integer value2) {
            addCriterion("BOOK_EXAM between", value1, value2, "bookExam");
            return (Criteria) this;
        }

        public Criteria andBookExamNotBetween(Integer value1, Integer value2) {
            addCriterion("BOOK_EXAM not between", value1, value2, "bookExam");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateIsNull() {
            addCriterion("QUES_TEMPLATE is null");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateIsNotNull() {
            addCriterion("QUES_TEMPLATE is not null");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateEqualTo(String value) {
            addCriterion("QUES_TEMPLATE =", value, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateNotEqualTo(String value) {
            addCriterion("QUES_TEMPLATE <>", value, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateGreaterThan(String value) {
            addCriterion("QUES_TEMPLATE >", value, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateGreaterThanOrEqualTo(String value) {
            addCriterion("QUES_TEMPLATE >=", value, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateLessThan(String value) {
            addCriterion("QUES_TEMPLATE <", value, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateLessThanOrEqualTo(String value) {
            addCriterion("QUES_TEMPLATE <=", value, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateLike(String value) {
            addCriterion("QUES_TEMPLATE like", value, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateNotLike(String value) {
            addCriterion("QUES_TEMPLATE not like", value, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateIn(List<String> values) {
            addCriterion("QUES_TEMPLATE in", values, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateNotIn(List<String> values) {
            addCriterion("QUES_TEMPLATE not in", values, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateBetween(String value1, String value2) {
            addCriterion("QUES_TEMPLATE between", value1, value2, "quesTemplate");
            return (Criteria) this;
        }

        public Criteria andQuesTemplateNotBetween(String value1, String value2) {
            addCriterion("QUES_TEMPLATE not between", value1, value2, "quesTemplate");
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