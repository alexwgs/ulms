package com.cmbccd.ulms.edu.domain;

import java.util.ArrayList;
import java.util.List;

public class AreaListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AreaListExample() {
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

        public Criteria andSeatIpIsNull() {
            addCriterion("SEAT_IP is null");
            return (Criteria) this;
        }

        public Criteria andSeatIpIsNotNull() {
            addCriterion("SEAT_IP is not null");
            return (Criteria) this;
        }

        public Criteria andSeatIpEqualTo(String value) {
            addCriterion("SEAT_IP =", value, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpNotEqualTo(String value) {
            addCriterion("SEAT_IP <>", value, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpGreaterThan(String value) {
            addCriterion("SEAT_IP >", value, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpGreaterThanOrEqualTo(String value) {
            addCriterion("SEAT_IP >=", value, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpLessThan(String value) {
            addCriterion("SEAT_IP <", value, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpLessThanOrEqualTo(String value) {
            addCriterion("SEAT_IP <=", value, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpLike(String value) {
            addCriterion("SEAT_IP like", value, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpNotLike(String value) {
            addCriterion("SEAT_IP not like", value, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpIn(List<String> values) {
            addCriterion("SEAT_IP in", values, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpNotIn(List<String> values) {
            addCriterion("SEAT_IP not in", values, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpBetween(String value1, String value2) {
            addCriterion("SEAT_IP between", value1, value2, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatIpNotBetween(String value1, String value2) {
            addCriterion("SEAT_IP not between", value1, value2, "seatIp");
            return (Criteria) this;
        }

        public Criteria andSeatMacIsNull() {
            addCriterion("SEAT_MAC is null");
            return (Criteria) this;
        }

        public Criteria andSeatMacIsNotNull() {
            addCriterion("SEAT_MAC is not null");
            return (Criteria) this;
        }

        public Criteria andSeatMacEqualTo(String value) {
            addCriterion("SEAT_MAC =", value, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacNotEqualTo(String value) {
            addCriterion("SEAT_MAC <>", value, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacGreaterThan(String value) {
            addCriterion("SEAT_MAC >", value, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacGreaterThanOrEqualTo(String value) {
            addCriterion("SEAT_MAC >=", value, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacLessThan(String value) {
            addCriterion("SEAT_MAC <", value, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacLessThanOrEqualTo(String value) {
            addCriterion("SEAT_MAC <=", value, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacLike(String value) {
            addCriterion("SEAT_MAC like", value, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacNotLike(String value) {
            addCriterion("SEAT_MAC not like", value, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacIn(List<String> values) {
            addCriterion("SEAT_MAC in", values, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacNotIn(List<String> values) {
            addCriterion("SEAT_MAC not in", values, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacBetween(String value1, String value2) {
            addCriterion("SEAT_MAC between", value1, value2, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatMacNotBetween(String value1, String value2) {
            addCriterion("SEAT_MAC not between", value1, value2, "seatMac");
            return (Criteria) this;
        }

        public Criteria andSeatDescIsNull() {
            addCriterion("SEAT_DESC is null");
            return (Criteria) this;
        }

        public Criteria andSeatDescIsNotNull() {
            addCriterion("SEAT_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andSeatDescEqualTo(String value) {
            addCriterion("SEAT_DESC =", value, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescNotEqualTo(String value) {
            addCriterion("SEAT_DESC <>", value, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescGreaterThan(String value) {
            addCriterion("SEAT_DESC >", value, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescGreaterThanOrEqualTo(String value) {
            addCriterion("SEAT_DESC >=", value, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescLessThan(String value) {
            addCriterion("SEAT_DESC <", value, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescLessThanOrEqualTo(String value) {
            addCriterion("SEAT_DESC <=", value, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescLike(String value) {
            addCriterion("SEAT_DESC like", value, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescNotLike(String value) {
            addCriterion("SEAT_DESC not like", value, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescIn(List<String> values) {
            addCriterion("SEAT_DESC in", values, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescNotIn(List<String> values) {
            addCriterion("SEAT_DESC not in", values, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescBetween(String value1, String value2) {
            addCriterion("SEAT_DESC between", value1, value2, "seatDesc");
            return (Criteria) this;
        }

        public Criteria andSeatDescNotBetween(String value1, String value2) {
            addCriterion("SEAT_DESC not between", value1, value2, "seatDesc");
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