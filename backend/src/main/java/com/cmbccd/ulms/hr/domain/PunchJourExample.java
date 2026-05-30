package com.cmbccd.ulms.hr.domain;

import java.util.ArrayList;
import java.util.List;

public class PunchJourExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PunchJourExample() {
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

        public Criteria andWorkDateIsNull() {
            addCriterion("WORK_DATE is null");
            return (Criteria) this;
        }

        public Criteria andWorkDateIsNotNull() {
            addCriterion("WORK_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andWorkDateEqualTo(String value) {
            addCriterion("WORK_DATE =", value, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateNotEqualTo(String value) {
            addCriterion("WORK_DATE <>", value, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateGreaterThan(String value) {
            addCriterion("WORK_DATE >", value, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateGreaterThanOrEqualTo(String value) {
            addCriterion("WORK_DATE >=", value, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateLessThan(String value) {
            addCriterion("WORK_DATE <", value, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateLessThanOrEqualTo(String value) {
            addCriterion("WORK_DATE <=", value, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateLike(String value) {
            addCriterion("WORK_DATE like", value, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateNotLike(String value) {
            addCriterion("WORK_DATE not like", value, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateIn(List<String> values) {
            addCriterion("WORK_DATE in", values, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateNotIn(List<String> values) {
            addCriterion("WORK_DATE not in", values, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateBetween(String value1, String value2) {
            addCriterion("WORK_DATE between", value1, value2, "workDate");
            return (Criteria) this;
        }

        public Criteria andWorkDateNotBetween(String value1, String value2) {
            addCriterion("WORK_DATE not between", value1, value2, "workDate");
            return (Criteria) this;
        }

        public Criteria andDutyDayIsNull() {
            addCriterion("DUTY_DAY is null");
            return (Criteria) this;
        }

        public Criteria andDutyDayIsNotNull() {
            addCriterion("DUTY_DAY is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDayEqualTo(String value) {
            addCriterion("DUTY_DAY =", value, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayNotEqualTo(String value) {
            addCriterion("DUTY_DAY <>", value, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayGreaterThan(String value) {
            addCriterion("DUTY_DAY >", value, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayGreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY >=", value, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayLessThan(String value) {
            addCriterion("DUTY_DAY <", value, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayLessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY <=", value, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayLike(String value) {
            addCriterion("DUTY_DAY like", value, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayNotLike(String value) {
            addCriterion("DUTY_DAY not like", value, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayIn(List<String> values) {
            addCriterion("DUTY_DAY in", values, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayNotIn(List<String> values) {
            addCriterion("DUTY_DAY not in", values, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayBetween(String value1, String value2) {
            addCriterion("DUTY_DAY between", value1, value2, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyDayNotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY not between", value1, value2, "dutyDay");
            return (Criteria) this;
        }

        public Criteria andDutyFlagIsNull() {
            addCriterion("DUTY_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDutyFlagIsNotNull() {
            addCriterion("DUTY_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDutyFlagEqualTo(String value) {
            addCriterion("DUTY_FLAG =", value, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagNotEqualTo(String value) {
            addCriterion("DUTY_FLAG <>", value, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagGreaterThan(String value) {
            addCriterion("DUTY_FLAG >", value, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagGreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_FLAG >=", value, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagLessThan(String value) {
            addCriterion("DUTY_FLAG <", value, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagLessThanOrEqualTo(String value) {
            addCriterion("DUTY_FLAG <=", value, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagLike(String value) {
            addCriterion("DUTY_FLAG like", value, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagNotLike(String value) {
            addCriterion("DUTY_FLAG not like", value, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagIn(List<String> values) {
            addCriterion("DUTY_FLAG in", values, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagNotIn(List<String> values) {
            addCriterion("DUTY_FLAG not in", values, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagBetween(String value1, String value2) {
            addCriterion("DUTY_FLAG between", value1, value2, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andDutyFlagNotBetween(String value1, String value2) {
            addCriterion("DUTY_FLAG not between", value1, value2, "dutyFlag");
            return (Criteria) this;
        }

        public Criteria andPunDateIsNull() {
            addCriterion("PUN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andPunDateIsNotNull() {
            addCriterion("PUN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andPunDateEqualTo(String value) {
            addCriterion("PUN_DATE =", value, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateNotEqualTo(String value) {
            addCriterion("PUN_DATE <>", value, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateGreaterThan(String value) {
            addCriterion("PUN_DATE >", value, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateGreaterThanOrEqualTo(String value) {
            addCriterion("PUN_DATE >=", value, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateLessThan(String value) {
            addCriterion("PUN_DATE <", value, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateLessThanOrEqualTo(String value) {
            addCriterion("PUN_DATE <=", value, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateLike(String value) {
            addCriterion("PUN_DATE like", value, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateNotLike(String value) {
            addCriterion("PUN_DATE not like", value, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateIn(List<String> values) {
            addCriterion("PUN_DATE in", values, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateNotIn(List<String> values) {
            addCriterion("PUN_DATE not in", values, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateBetween(String value1, String value2) {
            addCriterion("PUN_DATE between", value1, value2, "punDate");
            return (Criteria) this;
        }

        public Criteria andPunDateNotBetween(String value1, String value2) {
            addCriterion("PUN_DATE not between", value1, value2, "punDate");
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

        public Criteria andPunStatusIsNull() {
            addCriterion("PUN_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPunStatusIsNotNull() {
            addCriterion("PUN_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPunStatusEqualTo(String value) {
            addCriterion("PUN_STATUS =", value, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusNotEqualTo(String value) {
            addCriterion("PUN_STATUS <>", value, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusGreaterThan(String value) {
            addCriterion("PUN_STATUS >", value, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusGreaterThanOrEqualTo(String value) {
            addCriterion("PUN_STATUS >=", value, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusLessThan(String value) {
            addCriterion("PUN_STATUS <", value, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusLessThanOrEqualTo(String value) {
            addCriterion("PUN_STATUS <=", value, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusLike(String value) {
            addCriterion("PUN_STATUS like", value, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusNotLike(String value) {
            addCriterion("PUN_STATUS not like", value, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusIn(List<String> values) {
            addCriterion("PUN_STATUS in", values, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusNotIn(List<String> values) {
            addCriterion("PUN_STATUS not in", values, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusBetween(String value1, String value2) {
            addCriterion("PUN_STATUS between", value1, value2, "punStatus");
            return (Criteria) this;
        }

        public Criteria andPunStatusNotBetween(String value1, String value2) {
            addCriterion("PUN_STATUS not between", value1, value2, "punStatus");
            return (Criteria) this;
        }

        public Criteria andJourFlagIsNull() {
            addCriterion("JOUR_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andJourFlagIsNotNull() {
            addCriterion("JOUR_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andJourFlagEqualTo(String value) {
            addCriterion("JOUR_FLAG =", value, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagNotEqualTo(String value) {
            addCriterion("JOUR_FLAG <>", value, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagGreaterThan(String value) {
            addCriterion("JOUR_FLAG >", value, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagGreaterThanOrEqualTo(String value) {
            addCriterion("JOUR_FLAG >=", value, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagLessThan(String value) {
            addCriterion("JOUR_FLAG <", value, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagLessThanOrEqualTo(String value) {
            addCriterion("JOUR_FLAG <=", value, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagLike(String value) {
            addCriterion("JOUR_FLAG like", value, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagNotLike(String value) {
            addCriterion("JOUR_FLAG not like", value, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagIn(List<String> values) {
            addCriterion("JOUR_FLAG in", values, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagNotIn(List<String> values) {
            addCriterion("JOUR_FLAG not in", values, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagBetween(String value1, String value2) {
            addCriterion("JOUR_FLAG between", value1, value2, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andJourFlagNotBetween(String value1, String value2) {
            addCriterion("JOUR_FLAG not between", value1, value2, "jourFlag");
            return (Criteria) this;
        }

        public Criteria andLocIpIsNull() {
            addCriterion("LOC_IP is null");
            return (Criteria) this;
        }

        public Criteria andLocIpIsNotNull() {
            addCriterion("LOC_IP is not null");
            return (Criteria) this;
        }

        public Criteria andLocIpEqualTo(String value) {
            addCriterion("LOC_IP =", value, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpNotEqualTo(String value) {
            addCriterion("LOC_IP <>", value, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpGreaterThan(String value) {
            addCriterion("LOC_IP >", value, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpGreaterThanOrEqualTo(String value) {
            addCriterion("LOC_IP >=", value, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpLessThan(String value) {
            addCriterion("LOC_IP <", value, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpLessThanOrEqualTo(String value) {
            addCriterion("LOC_IP <=", value, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpLike(String value) {
            addCriterion("LOC_IP like", value, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpNotLike(String value) {
            addCriterion("LOC_IP not like", value, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpIn(List<String> values) {
            addCriterion("LOC_IP in", values, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpNotIn(List<String> values) {
            addCriterion("LOC_IP not in", values, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpBetween(String value1, String value2) {
            addCriterion("LOC_IP between", value1, value2, "locIp");
            return (Criteria) this;
        }

        public Criteria andLocIpNotBetween(String value1, String value2) {
            addCriterion("LOC_IP not between", value1, value2, "locIp");
            return (Criteria) this;
        }

        public Criteria andPunMemIsNull() {
            addCriterion("PUN_MEM is null");
            return (Criteria) this;
        }

        public Criteria andPunMemIsNotNull() {
            addCriterion("PUN_MEM is not null");
            return (Criteria) this;
        }

        public Criteria andPunMemEqualTo(String value) {
            addCriterion("PUN_MEM =", value, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemNotEqualTo(String value) {
            addCriterion("PUN_MEM <>", value, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemGreaterThan(String value) {
            addCriterion("PUN_MEM >", value, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemGreaterThanOrEqualTo(String value) {
            addCriterion("PUN_MEM >=", value, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemLessThan(String value) {
            addCriterion("PUN_MEM <", value, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemLessThanOrEqualTo(String value) {
            addCriterion("PUN_MEM <=", value, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemLike(String value) {
            addCriterion("PUN_MEM like", value, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemNotLike(String value) {
            addCriterion("PUN_MEM not like", value, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemIn(List<String> values) {
            addCriterion("PUN_MEM in", values, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemNotIn(List<String> values) {
            addCriterion("PUN_MEM not in", values, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemBetween(String value1, String value2) {
            addCriterion("PUN_MEM between", value1, value2, "punMem");
            return (Criteria) this;
        }

        public Criteria andPunMemNotBetween(String value1, String value2) {
            addCriterion("PUN_MEM not between", value1, value2, "punMem");
            return (Criteria) this;
        }

        public Criteria andDyskFlagIsNull() {
            addCriterion("DYSK_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDyskFlagIsNotNull() {
            addCriterion("DYSK_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDyskFlagEqualTo(String value) {
            addCriterion("DYSK_FLAG =", value, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagNotEqualTo(String value) {
            addCriterion("DYSK_FLAG <>", value, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagGreaterThan(String value) {
            addCriterion("DYSK_FLAG >", value, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagGreaterThanOrEqualTo(String value) {
            addCriterion("DYSK_FLAG >=", value, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagLessThan(String value) {
            addCriterion("DYSK_FLAG <", value, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagLessThanOrEqualTo(String value) {
            addCriterion("DYSK_FLAG <=", value, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagLike(String value) {
            addCriterion("DYSK_FLAG like", value, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagNotLike(String value) {
            addCriterion("DYSK_FLAG not like", value, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagIn(List<String> values) {
            addCriterion("DYSK_FLAG in", values, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagNotIn(List<String> values) {
            addCriterion("DYSK_FLAG not in", values, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagBetween(String value1, String value2) {
            addCriterion("DYSK_FLAG between", value1, value2, "dyskFlag");
            return (Criteria) this;
        }

        public Criteria andDyskFlagNotBetween(String value1, String value2) {
            addCriterion("DYSK_FLAG not between", value1, value2, "dyskFlag");
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