package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class BrushConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BrushConfigExample() {
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

        public Criteria andBrushCodeIsNull() {
            addCriterion("BRUSH_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBrushCodeIsNotNull() {
            addCriterion("BRUSH_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBrushCodeEqualTo(String value) {
            addCriterion("BRUSH_CODE =", value, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeNotEqualTo(String value) {
            addCriterion("BRUSH_CODE <>", value, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeGreaterThan(String value) {
            addCriterion("BRUSH_CODE >", value, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BRUSH_CODE >=", value, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeLessThan(String value) {
            addCriterion("BRUSH_CODE <", value, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeLessThanOrEqualTo(String value) {
            addCriterion("BRUSH_CODE <=", value, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeLike(String value) {
            addCriterion("BRUSH_CODE like", value, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeNotLike(String value) {
            addCriterion("BRUSH_CODE not like", value, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeIn(List<String> values) {
            addCriterion("BRUSH_CODE in", values, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeNotIn(List<String> values) {
            addCriterion("BRUSH_CODE not in", values, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeBetween(String value1, String value2) {
            addCriterion("BRUSH_CODE between", value1, value2, "brushCode");
            return (Criteria) this;
        }

        public Criteria andBrushCodeNotBetween(String value1, String value2) {
            addCriterion("BRUSH_CODE not between", value1, value2, "brushCode");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNull() {
            addCriterion("GROUP_ID is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("GROUP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(String value) {
            addCriterion("GROUP_ID =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(String value) {
            addCriterion("GROUP_ID <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(String value) {
            addCriterion("GROUP_ID >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_ID >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(String value) {
            addCriterion("GROUP_ID <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(String value) {
            addCriterion("GROUP_ID <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLike(String value) {
            addCriterion("GROUP_ID like", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotLike(String value) {
            addCriterion("GROUP_ID not like", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<String> values) {
            addCriterion("GROUP_ID in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<String> values) {
            addCriterion("GROUP_ID not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(String value1, String value2) {
            addCriterion("GROUP_ID between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(String value1, String value2) {
            addCriterion("GROUP_ID not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andMonthLimitIsNull() {
            addCriterion("MONTH_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andMonthLimitIsNotNull() {
            addCriterion("MONTH_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andMonthLimitEqualTo(Integer value) {
            addCriterion("MONTH_LIMIT =", value, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitNotEqualTo(Integer value) {
            addCriterion("MONTH_LIMIT <>", value, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitGreaterThan(Integer value) {
            addCriterion("MONTH_LIMIT >", value, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("MONTH_LIMIT >=", value, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitLessThan(Integer value) {
            addCriterion("MONTH_LIMIT <", value, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitLessThanOrEqualTo(Integer value) {
            addCriterion("MONTH_LIMIT <=", value, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitIn(List<Integer> values) {
            addCriterion("MONTH_LIMIT in", values, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitNotIn(List<Integer> values) {
            addCriterion("MONTH_LIMIT not in", values, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitBetween(Integer value1, Integer value2) {
            addCriterion("MONTH_LIMIT between", value1, value2, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andMonthLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("MONTH_LIMIT not between", value1, value2, "monthLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitIsNull() {
            addCriterion("DAY_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andDayLimitIsNotNull() {
            addCriterion("DAY_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andDayLimitEqualTo(Integer value) {
            addCriterion("DAY_LIMIT =", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitNotEqualTo(Integer value) {
            addCriterion("DAY_LIMIT <>", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitGreaterThan(Integer value) {
            addCriterion("DAY_LIMIT >", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("DAY_LIMIT >=", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitLessThan(Integer value) {
            addCriterion("DAY_LIMIT <", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitLessThanOrEqualTo(Integer value) {
            addCriterion("DAY_LIMIT <=", value, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitIn(List<Integer> values) {
            addCriterion("DAY_LIMIT in", values, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitNotIn(List<Integer> values) {
            addCriterion("DAY_LIMIT not in", values, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitBetween(Integer value1, Integer value2) {
            addCriterion("DAY_LIMIT between", value1, value2, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andDayLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("DAY_LIMIT not between", value1, value2, "dayLimit");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatIsNull() {
            addCriterion("AUDIO_REPEAT is null");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatIsNotNull() {
            addCriterion("AUDIO_REPEAT is not null");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatEqualTo(Integer value) {
            addCriterion("AUDIO_REPEAT =", value, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatNotEqualTo(Integer value) {
            addCriterion("AUDIO_REPEAT <>", value, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatGreaterThan(Integer value) {
            addCriterion("AUDIO_REPEAT >", value, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatGreaterThanOrEqualTo(Integer value) {
            addCriterion("AUDIO_REPEAT >=", value, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatLessThan(Integer value) {
            addCriterion("AUDIO_REPEAT <", value, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatLessThanOrEqualTo(Integer value) {
            addCriterion("AUDIO_REPEAT <=", value, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatIn(List<Integer> values) {
            addCriterion("AUDIO_REPEAT in", values, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatNotIn(List<Integer> values) {
            addCriterion("AUDIO_REPEAT not in", values, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatBetween(Integer value1, Integer value2) {
            addCriterion("AUDIO_REPEAT between", value1, value2, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andAudioRepeatNotBetween(Integer value1, Integer value2) {
            addCriterion("AUDIO_REPEAT not between", value1, value2, "audioRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatIsNull() {
            addCriterion("VIDEO_REPEAT is null");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatIsNotNull() {
            addCriterion("VIDEO_REPEAT is not null");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatEqualTo(Integer value) {
            addCriterion("VIDEO_REPEAT =", value, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatNotEqualTo(Integer value) {
            addCriterion("VIDEO_REPEAT <>", value, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatGreaterThan(Integer value) {
            addCriterion("VIDEO_REPEAT >", value, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatGreaterThanOrEqualTo(Integer value) {
            addCriterion("VIDEO_REPEAT >=", value, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatLessThan(Integer value) {
            addCriterion("VIDEO_REPEAT <", value, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatLessThanOrEqualTo(Integer value) {
            addCriterion("VIDEO_REPEAT <=", value, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatIn(List<Integer> values) {
            addCriterion("VIDEO_REPEAT in", values, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatNotIn(List<Integer> values) {
            addCriterion("VIDEO_REPEAT not in", values, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatBetween(Integer value1, Integer value2) {
            addCriterion("VIDEO_REPEAT between", value1, value2, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andVideoRepeatNotBetween(Integer value1, Integer value2) {
            addCriterion("VIDEO_REPEAT not between", value1, value2, "videoRepeat");
            return (Criteria) this;
        }

        public Criteria andOptionRandIsNull() {
            addCriterion("OPTION_RAND is null");
            return (Criteria) this;
        }

        public Criteria andOptionRandIsNotNull() {
            addCriterion("OPTION_RAND is not null");
            return (Criteria) this;
        }

        public Criteria andOptionRandEqualTo(Integer value) {
            addCriterion("OPTION_RAND =", value, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandNotEqualTo(Integer value) {
            addCriterion("OPTION_RAND <>", value, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandGreaterThan(Integer value) {
            addCriterion("OPTION_RAND >", value, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandGreaterThanOrEqualTo(Integer value) {
            addCriterion("OPTION_RAND >=", value, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandLessThan(Integer value) {
            addCriterion("OPTION_RAND <", value, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandLessThanOrEqualTo(Integer value) {
            addCriterion("OPTION_RAND <=", value, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandIn(List<Integer> values) {
            addCriterion("OPTION_RAND in", values, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandNotIn(List<Integer> values) {
            addCriterion("OPTION_RAND not in", values, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandBetween(Integer value1, Integer value2) {
            addCriterion("OPTION_RAND between", value1, value2, "optionRand");
            return (Criteria) this;
        }

        public Criteria andOptionRandNotBetween(Integer value1, Integer value2) {
            addCriterion("OPTION_RAND not between", value1, value2, "optionRand");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstIsNull() {
            addCriterion("MISTAKE_FIRST is null");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstIsNotNull() {
            addCriterion("MISTAKE_FIRST is not null");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstEqualTo(Integer value) {
            addCriterion("MISTAKE_FIRST =", value, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstNotEqualTo(Integer value) {
            addCriterion("MISTAKE_FIRST <>", value, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstGreaterThan(Integer value) {
            addCriterion("MISTAKE_FIRST >", value, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstGreaterThanOrEqualTo(Integer value) {
            addCriterion("MISTAKE_FIRST >=", value, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstLessThan(Integer value) {
            addCriterion("MISTAKE_FIRST <", value, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstLessThanOrEqualTo(Integer value) {
            addCriterion("MISTAKE_FIRST <=", value, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstIn(List<Integer> values) {
            addCriterion("MISTAKE_FIRST in", values, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstNotIn(List<Integer> values) {
            addCriterion("MISTAKE_FIRST not in", values, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstBetween(Integer value1, Integer value2) {
            addCriterion("MISTAKE_FIRST between", value1, value2, "mistakeFirst");
            return (Criteria) this;
        }

        public Criteria andMistakeFirstNotBetween(Integer value1, Integer value2) {
            addCriterion("MISTAKE_FIRST not between", value1, value2, "mistakeFirst");
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

        public Criteria andAnswerTimeEqualTo(Integer value) {
            addCriterion("ANSWER_TIME =", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotEqualTo(Integer value) {
            addCriterion("ANSWER_TIME <>", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeGreaterThan(Integer value) {
            addCriterion("ANSWER_TIME >", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ANSWER_TIME >=", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeLessThan(Integer value) {
            addCriterion("ANSWER_TIME <", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeLessThanOrEqualTo(Integer value) {
            addCriterion("ANSWER_TIME <=", value, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeIn(List<Integer> values) {
            addCriterion("ANSWER_TIME in", values, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotIn(List<Integer> values) {
            addCriterion("ANSWER_TIME not in", values, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeBetween(Integer value1, Integer value2) {
            addCriterion("ANSWER_TIME between", value1, value2, "answerTime");
            return (Criteria) this;
        }

        public Criteria andAnswerTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("ANSWER_TIME not between", value1, value2, "answerTime");
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

        public Criteria andWrongDayIsNull() {
            addCriterion("WRONG_DAY is null");
            return (Criteria) this;
        }

        public Criteria andWrongDayIsNotNull() {
            addCriterion("WRONG_DAY is not null");
            return (Criteria) this;
        }

        public Criteria andWrongDayEqualTo(Short value) {
            addCriterion("WRONG_DAY =", value, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayNotEqualTo(Short value) {
            addCriterion("WRONG_DAY <>", value, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayGreaterThan(Short value) {
            addCriterion("WRONG_DAY >", value, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayGreaterThanOrEqualTo(Short value) {
            addCriterion("WRONG_DAY >=", value, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayLessThan(Short value) {
            addCriterion("WRONG_DAY <", value, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayLessThanOrEqualTo(Short value) {
            addCriterion("WRONG_DAY <=", value, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayIn(List<Short> values) {
            addCriterion("WRONG_DAY in", values, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayNotIn(List<Short> values) {
            addCriterion("WRONG_DAY not in", values, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayBetween(Short value1, Short value2) {
            addCriterion("WRONG_DAY between", value1, value2, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongDayNotBetween(Short value1, Short value2) {
            addCriterion("WRONG_DAY not between", value1, value2, "wrongDay");
            return (Criteria) this;
        }

        public Criteria andWrongLimitIsNull() {
            addCriterion("WRONG_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andWrongLimitIsNotNull() {
            addCriterion("WRONG_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andWrongLimitEqualTo(Short value) {
            addCriterion("WRONG_LIMIT =", value, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitNotEqualTo(Short value) {
            addCriterion("WRONG_LIMIT <>", value, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitGreaterThan(Short value) {
            addCriterion("WRONG_LIMIT >", value, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitGreaterThanOrEqualTo(Short value) {
            addCriterion("WRONG_LIMIT >=", value, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitLessThan(Short value) {
            addCriterion("WRONG_LIMIT <", value, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitLessThanOrEqualTo(Short value) {
            addCriterion("WRONG_LIMIT <=", value, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitIn(List<Short> values) {
            addCriterion("WRONG_LIMIT in", values, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitNotIn(List<Short> values) {
            addCriterion("WRONG_LIMIT not in", values, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitBetween(Short value1, Short value2) {
            addCriterion("WRONG_LIMIT between", value1, value2, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andWrongLimitNotBetween(Short value1, Short value2) {
            addCriterion("WRONG_LIMIT not between", value1, value2, "wrongLimit");
            return (Criteria) this;
        }

        public Criteria andCollectDayIsNull() {
            addCriterion("COLLECT_DAY is null");
            return (Criteria) this;
        }

        public Criteria andCollectDayIsNotNull() {
            addCriterion("COLLECT_DAY is not null");
            return (Criteria) this;
        }

        public Criteria andCollectDayEqualTo(Short value) {
            addCriterion("COLLECT_DAY =", value, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayNotEqualTo(Short value) {
            addCriterion("COLLECT_DAY <>", value, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayGreaterThan(Short value) {
            addCriterion("COLLECT_DAY >", value, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayGreaterThanOrEqualTo(Short value) {
            addCriterion("COLLECT_DAY >=", value, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayLessThan(Short value) {
            addCriterion("COLLECT_DAY <", value, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayLessThanOrEqualTo(Short value) {
            addCriterion("COLLECT_DAY <=", value, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayIn(List<Short> values) {
            addCriterion("COLLECT_DAY in", values, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayNotIn(List<Short> values) {
            addCriterion("COLLECT_DAY not in", values, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayBetween(Short value1, Short value2) {
            addCriterion("COLLECT_DAY between", value1, value2, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectDayNotBetween(Short value1, Short value2) {
            addCriterion("COLLECT_DAY not between", value1, value2, "collectDay");
            return (Criteria) this;
        }

        public Criteria andCollectLimitIsNull() {
            addCriterion("COLLECT_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andCollectLimitIsNotNull() {
            addCriterion("COLLECT_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andCollectLimitEqualTo(Short value) {
            addCriterion("COLLECT_LIMIT =", value, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitNotEqualTo(Short value) {
            addCriterion("COLLECT_LIMIT <>", value, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitGreaterThan(Short value) {
            addCriterion("COLLECT_LIMIT >", value, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitGreaterThanOrEqualTo(Short value) {
            addCriterion("COLLECT_LIMIT >=", value, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitLessThan(Short value) {
            addCriterion("COLLECT_LIMIT <", value, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitLessThanOrEqualTo(Short value) {
            addCriterion("COLLECT_LIMIT <=", value, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitIn(List<Short> values) {
            addCriterion("COLLECT_LIMIT in", values, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitNotIn(List<Short> values) {
            addCriterion("COLLECT_LIMIT not in", values, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitBetween(Short value1, Short value2) {
            addCriterion("COLLECT_LIMIT between", value1, value2, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andCollectLimitNotBetween(Short value1, Short value2) {
            addCriterion("COLLECT_LIMIT not between", value1, value2, "collectLimit");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("GROUP_NAME is null");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("GROUP_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("GROUP_NAME =", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("GROUP_NAME <>", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("GROUP_NAME >", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_NAME >=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("GROUP_NAME <", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("GROUP_NAME <=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("GROUP_NAME like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("GROUP_NAME not like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("GROUP_NAME in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("GROUP_NAME not in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("GROUP_NAME between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("GROUP_NAME not between", value1, value2, "groupName");
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