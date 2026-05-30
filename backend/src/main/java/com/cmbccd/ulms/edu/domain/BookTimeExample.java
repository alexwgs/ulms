package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class BookTimeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BookTimeExample() {
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

        public Criteria andBookCodeIsNull() {
            addCriterion("BOOK_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBookCodeIsNotNull() {
            addCriterion("BOOK_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBookCodeEqualTo(String value) {
            addCriterion("BOOK_CODE =", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotEqualTo(String value) {
            addCriterion("BOOK_CODE <>", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeGreaterThan(String value) {
            addCriterion("BOOK_CODE >", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BOOK_CODE >=", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLessThan(String value) {
            addCriterion("BOOK_CODE <", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLessThanOrEqualTo(String value) {
            addCriterion("BOOK_CODE <=", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLike(String value) {
            addCriterion("BOOK_CODE like", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotLike(String value) {
            addCriterion("BOOK_CODE not like", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeIn(List<String> values) {
            addCriterion("BOOK_CODE in", values, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotIn(List<String> values) {
            addCriterion("BOOK_CODE not in", values, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeBetween(String value1, String value2) {
            addCriterion("BOOK_CODE between", value1, value2, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotBetween(String value1, String value2) {
            addCriterion("BOOK_CODE not between", value1, value2, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookLimitIsNull() {
            addCriterion("BOOK_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andBookLimitIsNotNull() {
            addCriterion("BOOK_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andBookLimitEqualTo(Integer value) {
            addCriterion("BOOK_LIMIT =", value, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitNotEqualTo(Integer value) {
            addCriterion("BOOK_LIMIT <>", value, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitGreaterThan(Integer value) {
            addCriterion("BOOK_LIMIT >", value, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("BOOK_LIMIT >=", value, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitLessThan(Integer value) {
            addCriterion("BOOK_LIMIT <", value, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitLessThanOrEqualTo(Integer value) {
            addCriterion("BOOK_LIMIT <=", value, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitIn(List<Integer> values) {
            addCriterion("BOOK_LIMIT in", values, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitNotIn(List<Integer> values) {
            addCriterion("BOOK_LIMIT not in", values, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitBetween(Integer value1, Integer value2) {
            addCriterion("BOOK_LIMIT between", value1, value2, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBookLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("BOOK_LIMIT not between", value1, value2, "bookLimit");
            return (Criteria) this;
        }

        public Criteria andBegTimeIsNull() {
            addCriterion("BEG_TIME is null");
            return (Criteria) this;
        }

        public Criteria andBegTimeIsNotNull() {
            addCriterion("BEG_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andBegTimeEqualTo(String value) {
            addCriterion("BEG_TIME =", value, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeNotEqualTo(String value) {
            addCriterion("BEG_TIME <>", value, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeGreaterThan(String value) {
            addCriterion("BEG_TIME >", value, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeGreaterThanOrEqualTo(String value) {
            addCriterion("BEG_TIME >=", value, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeLessThan(String value) {
            addCriterion("BEG_TIME <", value, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeLessThanOrEqualTo(String value) {
            addCriterion("BEG_TIME <=", value, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeLike(String value) {
            addCriterion("BEG_TIME like", value, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeNotLike(String value) {
            addCriterion("BEG_TIME not like", value, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeIn(List<String> values) {
            addCriterion("BEG_TIME in", values, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeNotIn(List<String> values) {
            addCriterion("BEG_TIME not in", values, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeBetween(String value1, String value2) {
            addCriterion("BEG_TIME between", value1, value2, "begTime");
            return (Criteria) this;
        }

        public Criteria andBegTimeNotBetween(String value1, String value2) {
            addCriterion("BEG_TIME not between", value1, value2, "begTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("END_TIME =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("END_TIME <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("END_TIME >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("END_TIME >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("END_TIME <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("END_TIME <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("END_TIME like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("END_TIME not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("END_TIME in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("END_TIME not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("END_TIME between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("END_TIME not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andBookStatIsNull() {
            addCriterion("BOOK_STAT is null");
            return (Criteria) this;
        }

        public Criteria andBookStatIsNotNull() {
            addCriterion("BOOK_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andBookStatEqualTo(Integer value) {
            addCriterion("BOOK_STAT =", value, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatNotEqualTo(Integer value) {
            addCriterion("BOOK_STAT <>", value, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatGreaterThan(Integer value) {
            addCriterion("BOOK_STAT >", value, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("BOOK_STAT >=", value, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatLessThan(Integer value) {
            addCriterion("BOOK_STAT <", value, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatLessThanOrEqualTo(Integer value) {
            addCriterion("BOOK_STAT <=", value, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatIn(List<Integer> values) {
            addCriterion("BOOK_STAT in", values, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatNotIn(List<Integer> values) {
            addCriterion("BOOK_STAT not in", values, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatBetween(Integer value1, Integer value2) {
            addCriterion("BOOK_STAT between", value1, value2, "bookStat");
            return (Criteria) this;
        }

        public Criteria andBookStatNotBetween(Integer value1, Integer value2) {
            addCriterion("BOOK_STAT not between", value1, value2, "bookStat");
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