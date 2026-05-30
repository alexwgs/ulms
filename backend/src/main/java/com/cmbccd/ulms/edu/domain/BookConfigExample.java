package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class BookConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BookConfigExample() {
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

        public Criteria andBookNameIsNull() {
            addCriterion("BOOK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBookNameIsNotNull() {
            addCriterion("BOOK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBookNameEqualTo(String value) {
            addCriterion("BOOK_NAME =", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotEqualTo(String value) {
            addCriterion("BOOK_NAME <>", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameGreaterThan(String value) {
            addCriterion("BOOK_NAME >", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameGreaterThanOrEqualTo(String value) {
            addCriterion("BOOK_NAME >=", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLessThan(String value) {
            addCriterion("BOOK_NAME <", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLessThanOrEqualTo(String value) {
            addCriterion("BOOK_NAME <=", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLike(String value) {
            addCriterion("BOOK_NAME like", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotLike(String value) {
            addCriterion("BOOK_NAME not like", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameIn(List<String> values) {
            addCriterion("BOOK_NAME in", values, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotIn(List<String> values) {
            addCriterion("BOOK_NAME not in", values, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameBetween(String value1, String value2) {
            addCriterion("BOOK_NAME between", value1, value2, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotBetween(String value1, String value2) {
            addCriterion("BOOK_NAME not between", value1, value2, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookDescIsNull() {
            addCriterion("BOOK_DESC is null");
            return (Criteria) this;
        }

        public Criteria andBookDescIsNotNull() {
            addCriterion("BOOK_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andBookDescEqualTo(String value) {
            addCriterion("BOOK_DESC =", value, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescNotEqualTo(String value) {
            addCriterion("BOOK_DESC <>", value, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescGreaterThan(String value) {
            addCriterion("BOOK_DESC >", value, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescGreaterThanOrEqualTo(String value) {
            addCriterion("BOOK_DESC >=", value, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescLessThan(String value) {
            addCriterion("BOOK_DESC <", value, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescLessThanOrEqualTo(String value) {
            addCriterion("BOOK_DESC <=", value, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescLike(String value) {
            addCriterion("BOOK_DESC like", value, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescNotLike(String value) {
            addCriterion("BOOK_DESC not like", value, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescIn(List<String> values) {
            addCriterion("BOOK_DESC in", values, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescNotIn(List<String> values) {
            addCriterion("BOOK_DESC not in", values, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescBetween(String value1, String value2) {
            addCriterion("BOOK_DESC between", value1, value2, "bookDesc");
            return (Criteria) this;
        }

        public Criteria andBookDescNotBetween(String value1, String value2) {
            addCriterion("BOOK_DESC not between", value1, value2, "bookDesc");
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