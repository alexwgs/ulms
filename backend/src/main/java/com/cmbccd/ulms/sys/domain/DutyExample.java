package com.cmbccd.ulms.sys.domain;

import java.util.ArrayList;
import java.util.List;

public class DutyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DutyExample() {
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

        public Criteria andDutyDateIsNull() {
            addCriterion("DUTY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDutyDateIsNotNull() {
            addCriterion("DUTY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDateEqualTo(String value) {
            addCriterion("DUTY_DATE =", value, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateNotEqualTo(String value) {
            addCriterion("DUTY_DATE <>", value, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateGreaterThan(String value) {
            addCriterion("DUTY_DATE >", value, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateGreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DATE >=", value, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateLessThan(String value) {
            addCriterion("DUTY_DATE <", value, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateLessThanOrEqualTo(String value) {
            addCriterion("DUTY_DATE <=", value, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateLike(String value) {
            addCriterion("DUTY_DATE like", value, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateNotLike(String value) {
            addCriterion("DUTY_DATE not like", value, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateIn(List<String> values) {
            addCriterion("DUTY_DATE in", values, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateNotIn(List<String> values) {
            addCriterion("DUTY_DATE not in", values, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateBetween(String value1, String value2) {
            addCriterion("DUTY_DATE between", value1, value2, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyDateNotBetween(String value1, String value2) {
            addCriterion("DUTY_DATE not between", value1, value2, "dutyDate");
            return (Criteria) this;
        }

        public Criteria andDutyTypeIsNull() {
            addCriterion("DUTY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDutyTypeIsNotNull() {
            addCriterion("DUTY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDutyTypeEqualTo(String value) {
            addCriterion("DUTY_TYPE =", value, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeNotEqualTo(String value) {
            addCriterion("DUTY_TYPE <>", value, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeGreaterThan(String value) {
            addCriterion("DUTY_TYPE >", value, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_TYPE >=", value, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeLessThan(String value) {
            addCriterion("DUTY_TYPE <", value, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeLessThanOrEqualTo(String value) {
            addCriterion("DUTY_TYPE <=", value, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeLike(String value) {
            addCriterion("DUTY_TYPE like", value, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeNotLike(String value) {
            addCriterion("DUTY_TYPE not like", value, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeIn(List<String> values) {
            addCriterion("DUTY_TYPE in", values, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeNotIn(List<String> values) {
            addCriterion("DUTY_TYPE not in", values, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeBetween(String value1, String value2) {
            addCriterion("DUTY_TYPE between", value1, value2, "dutyType");
            return (Criteria) this;
        }

        public Criteria andDutyTypeNotBetween(String value1, String value2) {
            addCriterion("DUTY_TYPE not between", value1, value2, "dutyType");
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

        public Criteria andDutyDay01IsNull() {
            addCriterion("DUTY_DAY01 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay01IsNotNull() {
            addCriterion("DUTY_DAY01 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay01EqualTo(String value) {
            addCriterion("DUTY_DAY01 =", value, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01NotEqualTo(String value) {
            addCriterion("DUTY_DAY01 <>", value, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01GreaterThan(String value) {
            addCriterion("DUTY_DAY01 >", value, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY01 >=", value, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01LessThan(String value) {
            addCriterion("DUTY_DAY01 <", value, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY01 <=", value, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01Like(String value) {
            addCriterion("DUTY_DAY01 like", value, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01NotLike(String value) {
            addCriterion("DUTY_DAY01 not like", value, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01In(List<String> values) {
            addCriterion("DUTY_DAY01 in", values, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01NotIn(List<String> values) {
            addCriterion("DUTY_DAY01 not in", values, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01Between(String value1, String value2) {
            addCriterion("DUTY_DAY01 between", value1, value2, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay01NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY01 not between", value1, value2, "dutyDay01");
            return (Criteria) this;
        }

        public Criteria andDutyDay02IsNull() {
            addCriterion("DUTY_DAY02 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay02IsNotNull() {
            addCriterion("DUTY_DAY02 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay02EqualTo(String value) {
            addCriterion("DUTY_DAY02 =", value, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02NotEqualTo(String value) {
            addCriterion("DUTY_DAY02 <>", value, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02GreaterThan(String value) {
            addCriterion("DUTY_DAY02 >", value, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY02 >=", value, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02LessThan(String value) {
            addCriterion("DUTY_DAY02 <", value, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY02 <=", value, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02Like(String value) {
            addCriterion("DUTY_DAY02 like", value, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02NotLike(String value) {
            addCriterion("DUTY_DAY02 not like", value, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02In(List<String> values) {
            addCriterion("DUTY_DAY02 in", values, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02NotIn(List<String> values) {
            addCriterion("DUTY_DAY02 not in", values, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02Between(String value1, String value2) {
            addCriterion("DUTY_DAY02 between", value1, value2, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay02NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY02 not between", value1, value2, "dutyDay02");
            return (Criteria) this;
        }

        public Criteria andDutyDay03IsNull() {
            addCriterion("DUTY_DAY03 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay03IsNotNull() {
            addCriterion("DUTY_DAY03 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay03EqualTo(String value) {
            addCriterion("DUTY_DAY03 =", value, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03NotEqualTo(String value) {
            addCriterion("DUTY_DAY03 <>", value, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03GreaterThan(String value) {
            addCriterion("DUTY_DAY03 >", value, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY03 >=", value, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03LessThan(String value) {
            addCriterion("DUTY_DAY03 <", value, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY03 <=", value, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03Like(String value) {
            addCriterion("DUTY_DAY03 like", value, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03NotLike(String value) {
            addCriterion("DUTY_DAY03 not like", value, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03In(List<String> values) {
            addCriterion("DUTY_DAY03 in", values, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03NotIn(List<String> values) {
            addCriterion("DUTY_DAY03 not in", values, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03Between(String value1, String value2) {
            addCriterion("DUTY_DAY03 between", value1, value2, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay03NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY03 not between", value1, value2, "dutyDay03");
            return (Criteria) this;
        }

        public Criteria andDutyDay04IsNull() {
            addCriterion("DUTY_DAY04 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay04IsNotNull() {
            addCriterion("DUTY_DAY04 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay04EqualTo(String value) {
            addCriterion("DUTY_DAY04 =", value, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04NotEqualTo(String value) {
            addCriterion("DUTY_DAY04 <>", value, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04GreaterThan(String value) {
            addCriterion("DUTY_DAY04 >", value, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY04 >=", value, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04LessThan(String value) {
            addCriterion("DUTY_DAY04 <", value, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY04 <=", value, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04Like(String value) {
            addCriterion("DUTY_DAY04 like", value, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04NotLike(String value) {
            addCriterion("DUTY_DAY04 not like", value, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04In(List<String> values) {
            addCriterion("DUTY_DAY04 in", values, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04NotIn(List<String> values) {
            addCriterion("DUTY_DAY04 not in", values, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04Between(String value1, String value2) {
            addCriterion("DUTY_DAY04 between", value1, value2, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay04NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY04 not between", value1, value2, "dutyDay04");
            return (Criteria) this;
        }

        public Criteria andDutyDay05IsNull() {
            addCriterion("DUTY_DAY05 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay05IsNotNull() {
            addCriterion("DUTY_DAY05 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay05EqualTo(String value) {
            addCriterion("DUTY_DAY05 =", value, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05NotEqualTo(String value) {
            addCriterion("DUTY_DAY05 <>", value, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05GreaterThan(String value) {
            addCriterion("DUTY_DAY05 >", value, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY05 >=", value, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05LessThan(String value) {
            addCriterion("DUTY_DAY05 <", value, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY05 <=", value, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05Like(String value) {
            addCriterion("DUTY_DAY05 like", value, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05NotLike(String value) {
            addCriterion("DUTY_DAY05 not like", value, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05In(List<String> values) {
            addCriterion("DUTY_DAY05 in", values, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05NotIn(List<String> values) {
            addCriterion("DUTY_DAY05 not in", values, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05Between(String value1, String value2) {
            addCriterion("DUTY_DAY05 between", value1, value2, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay05NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY05 not between", value1, value2, "dutyDay05");
            return (Criteria) this;
        }

        public Criteria andDutyDay06IsNull() {
            addCriterion("DUTY_DAY06 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay06IsNotNull() {
            addCriterion("DUTY_DAY06 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay06EqualTo(String value) {
            addCriterion("DUTY_DAY06 =", value, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06NotEqualTo(String value) {
            addCriterion("DUTY_DAY06 <>", value, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06GreaterThan(String value) {
            addCriterion("DUTY_DAY06 >", value, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY06 >=", value, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06LessThan(String value) {
            addCriterion("DUTY_DAY06 <", value, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY06 <=", value, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06Like(String value) {
            addCriterion("DUTY_DAY06 like", value, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06NotLike(String value) {
            addCriterion("DUTY_DAY06 not like", value, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06In(List<String> values) {
            addCriterion("DUTY_DAY06 in", values, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06NotIn(List<String> values) {
            addCriterion("DUTY_DAY06 not in", values, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06Between(String value1, String value2) {
            addCriterion("DUTY_DAY06 between", value1, value2, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay06NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY06 not between", value1, value2, "dutyDay06");
            return (Criteria) this;
        }

        public Criteria andDutyDay07IsNull() {
            addCriterion("DUTY_DAY07 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay07IsNotNull() {
            addCriterion("DUTY_DAY07 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay07EqualTo(String value) {
            addCriterion("DUTY_DAY07 =", value, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07NotEqualTo(String value) {
            addCriterion("DUTY_DAY07 <>", value, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07GreaterThan(String value) {
            addCriterion("DUTY_DAY07 >", value, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY07 >=", value, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07LessThan(String value) {
            addCriterion("DUTY_DAY07 <", value, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY07 <=", value, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07Like(String value) {
            addCriterion("DUTY_DAY07 like", value, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07NotLike(String value) {
            addCriterion("DUTY_DAY07 not like", value, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07In(List<String> values) {
            addCriterion("DUTY_DAY07 in", values, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07NotIn(List<String> values) {
            addCriterion("DUTY_DAY07 not in", values, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07Between(String value1, String value2) {
            addCriterion("DUTY_DAY07 between", value1, value2, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay07NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY07 not between", value1, value2, "dutyDay07");
            return (Criteria) this;
        }

        public Criteria andDutyDay08IsNull() {
            addCriterion("DUTY_DAY08 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay08IsNotNull() {
            addCriterion("DUTY_DAY08 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay08EqualTo(String value) {
            addCriterion("DUTY_DAY08 =", value, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08NotEqualTo(String value) {
            addCriterion("DUTY_DAY08 <>", value, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08GreaterThan(String value) {
            addCriterion("DUTY_DAY08 >", value, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY08 >=", value, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08LessThan(String value) {
            addCriterion("DUTY_DAY08 <", value, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY08 <=", value, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08Like(String value) {
            addCriterion("DUTY_DAY08 like", value, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08NotLike(String value) {
            addCriterion("DUTY_DAY08 not like", value, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08In(List<String> values) {
            addCriterion("DUTY_DAY08 in", values, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08NotIn(List<String> values) {
            addCriterion("DUTY_DAY08 not in", values, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08Between(String value1, String value2) {
            addCriterion("DUTY_DAY08 between", value1, value2, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay08NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY08 not between", value1, value2, "dutyDay08");
            return (Criteria) this;
        }

        public Criteria andDutyDay09IsNull() {
            addCriterion("DUTY_DAY09 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay09IsNotNull() {
            addCriterion("DUTY_DAY09 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay09EqualTo(String value) {
            addCriterion("DUTY_DAY09 =", value, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09NotEqualTo(String value) {
            addCriterion("DUTY_DAY09 <>", value, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09GreaterThan(String value) {
            addCriterion("DUTY_DAY09 >", value, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY09 >=", value, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09LessThan(String value) {
            addCriterion("DUTY_DAY09 <", value, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY09 <=", value, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09Like(String value) {
            addCriterion("DUTY_DAY09 like", value, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09NotLike(String value) {
            addCriterion("DUTY_DAY09 not like", value, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09In(List<String> values) {
            addCriterion("DUTY_DAY09 in", values, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09NotIn(List<String> values) {
            addCriterion("DUTY_DAY09 not in", values, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09Between(String value1, String value2) {
            addCriterion("DUTY_DAY09 between", value1, value2, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay09NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY09 not between", value1, value2, "dutyDay09");
            return (Criteria) this;
        }

        public Criteria andDutyDay10IsNull() {
            addCriterion("DUTY_DAY10 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay10IsNotNull() {
            addCriterion("DUTY_DAY10 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay10EqualTo(String value) {
            addCriterion("DUTY_DAY10 =", value, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10NotEqualTo(String value) {
            addCriterion("DUTY_DAY10 <>", value, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10GreaterThan(String value) {
            addCriterion("DUTY_DAY10 >", value, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY10 >=", value, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10LessThan(String value) {
            addCriterion("DUTY_DAY10 <", value, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY10 <=", value, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10Like(String value) {
            addCriterion("DUTY_DAY10 like", value, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10NotLike(String value) {
            addCriterion("DUTY_DAY10 not like", value, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10In(List<String> values) {
            addCriterion("DUTY_DAY10 in", values, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10NotIn(List<String> values) {
            addCriterion("DUTY_DAY10 not in", values, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10Between(String value1, String value2) {
            addCriterion("DUTY_DAY10 between", value1, value2, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay10NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY10 not between", value1, value2, "dutyDay10");
            return (Criteria) this;
        }

        public Criteria andDutyDay11IsNull() {
            addCriterion("DUTY_DAY11 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay11IsNotNull() {
            addCriterion("DUTY_DAY11 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay11EqualTo(String value) {
            addCriterion("DUTY_DAY11 =", value, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11NotEqualTo(String value) {
            addCriterion("DUTY_DAY11 <>", value, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11GreaterThan(String value) {
            addCriterion("DUTY_DAY11 >", value, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY11 >=", value, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11LessThan(String value) {
            addCriterion("DUTY_DAY11 <", value, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY11 <=", value, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11Like(String value) {
            addCriterion("DUTY_DAY11 like", value, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11NotLike(String value) {
            addCriterion("DUTY_DAY11 not like", value, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11In(List<String> values) {
            addCriterion("DUTY_DAY11 in", values, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11NotIn(List<String> values) {
            addCriterion("DUTY_DAY11 not in", values, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11Between(String value1, String value2) {
            addCriterion("DUTY_DAY11 between", value1, value2, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay11NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY11 not between", value1, value2, "dutyDay11");
            return (Criteria) this;
        }

        public Criteria andDutyDay12IsNull() {
            addCriterion("DUTY_DAY12 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay12IsNotNull() {
            addCriterion("DUTY_DAY12 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay12EqualTo(String value) {
            addCriterion("DUTY_DAY12 =", value, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12NotEqualTo(String value) {
            addCriterion("DUTY_DAY12 <>", value, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12GreaterThan(String value) {
            addCriterion("DUTY_DAY12 >", value, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY12 >=", value, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12LessThan(String value) {
            addCriterion("DUTY_DAY12 <", value, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY12 <=", value, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12Like(String value) {
            addCriterion("DUTY_DAY12 like", value, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12NotLike(String value) {
            addCriterion("DUTY_DAY12 not like", value, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12In(List<String> values) {
            addCriterion("DUTY_DAY12 in", values, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12NotIn(List<String> values) {
            addCriterion("DUTY_DAY12 not in", values, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12Between(String value1, String value2) {
            addCriterion("DUTY_DAY12 between", value1, value2, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay12NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY12 not between", value1, value2, "dutyDay12");
            return (Criteria) this;
        }

        public Criteria andDutyDay13IsNull() {
            addCriterion("DUTY_DAY13 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay13IsNotNull() {
            addCriterion("DUTY_DAY13 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay13EqualTo(String value) {
            addCriterion("DUTY_DAY13 =", value, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13NotEqualTo(String value) {
            addCriterion("DUTY_DAY13 <>", value, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13GreaterThan(String value) {
            addCriterion("DUTY_DAY13 >", value, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY13 >=", value, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13LessThan(String value) {
            addCriterion("DUTY_DAY13 <", value, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY13 <=", value, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13Like(String value) {
            addCriterion("DUTY_DAY13 like", value, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13NotLike(String value) {
            addCriterion("DUTY_DAY13 not like", value, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13In(List<String> values) {
            addCriterion("DUTY_DAY13 in", values, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13NotIn(List<String> values) {
            addCriterion("DUTY_DAY13 not in", values, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13Between(String value1, String value2) {
            addCriterion("DUTY_DAY13 between", value1, value2, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay13NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY13 not between", value1, value2, "dutyDay13");
            return (Criteria) this;
        }

        public Criteria andDutyDay14IsNull() {
            addCriterion("DUTY_DAY14 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay14IsNotNull() {
            addCriterion("DUTY_DAY14 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay14EqualTo(String value) {
            addCriterion("DUTY_DAY14 =", value, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14NotEqualTo(String value) {
            addCriterion("DUTY_DAY14 <>", value, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14GreaterThan(String value) {
            addCriterion("DUTY_DAY14 >", value, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY14 >=", value, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14LessThan(String value) {
            addCriterion("DUTY_DAY14 <", value, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY14 <=", value, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14Like(String value) {
            addCriterion("DUTY_DAY14 like", value, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14NotLike(String value) {
            addCriterion("DUTY_DAY14 not like", value, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14In(List<String> values) {
            addCriterion("DUTY_DAY14 in", values, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14NotIn(List<String> values) {
            addCriterion("DUTY_DAY14 not in", values, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14Between(String value1, String value2) {
            addCriterion("DUTY_DAY14 between", value1, value2, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay14NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY14 not between", value1, value2, "dutyDay14");
            return (Criteria) this;
        }

        public Criteria andDutyDay15IsNull() {
            addCriterion("DUTY_DAY15 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay15IsNotNull() {
            addCriterion("DUTY_DAY15 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay15EqualTo(String value) {
            addCriterion("DUTY_DAY15 =", value, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15NotEqualTo(String value) {
            addCriterion("DUTY_DAY15 <>", value, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15GreaterThan(String value) {
            addCriterion("DUTY_DAY15 >", value, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY15 >=", value, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15LessThan(String value) {
            addCriterion("DUTY_DAY15 <", value, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY15 <=", value, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15Like(String value) {
            addCriterion("DUTY_DAY15 like", value, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15NotLike(String value) {
            addCriterion("DUTY_DAY15 not like", value, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15In(List<String> values) {
            addCriterion("DUTY_DAY15 in", values, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15NotIn(List<String> values) {
            addCriterion("DUTY_DAY15 not in", values, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15Between(String value1, String value2) {
            addCriterion("DUTY_DAY15 between", value1, value2, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay15NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY15 not between", value1, value2, "dutyDay15");
            return (Criteria) this;
        }

        public Criteria andDutyDay16IsNull() {
            addCriterion("DUTY_DAY16 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay16IsNotNull() {
            addCriterion("DUTY_DAY16 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay16EqualTo(String value) {
            addCriterion("DUTY_DAY16 =", value, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16NotEqualTo(String value) {
            addCriterion("DUTY_DAY16 <>", value, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16GreaterThan(String value) {
            addCriterion("DUTY_DAY16 >", value, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY16 >=", value, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16LessThan(String value) {
            addCriterion("DUTY_DAY16 <", value, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY16 <=", value, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16Like(String value) {
            addCriterion("DUTY_DAY16 like", value, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16NotLike(String value) {
            addCriterion("DUTY_DAY16 not like", value, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16In(List<String> values) {
            addCriterion("DUTY_DAY16 in", values, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16NotIn(List<String> values) {
            addCriterion("DUTY_DAY16 not in", values, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16Between(String value1, String value2) {
            addCriterion("DUTY_DAY16 between", value1, value2, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay16NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY16 not between", value1, value2, "dutyDay16");
            return (Criteria) this;
        }

        public Criteria andDutyDay17IsNull() {
            addCriterion("DUTY_DAY17 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay17IsNotNull() {
            addCriterion("DUTY_DAY17 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay17EqualTo(String value) {
            addCriterion("DUTY_DAY17 =", value, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17NotEqualTo(String value) {
            addCriterion("DUTY_DAY17 <>", value, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17GreaterThan(String value) {
            addCriterion("DUTY_DAY17 >", value, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY17 >=", value, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17LessThan(String value) {
            addCriterion("DUTY_DAY17 <", value, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY17 <=", value, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17Like(String value) {
            addCriterion("DUTY_DAY17 like", value, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17NotLike(String value) {
            addCriterion("DUTY_DAY17 not like", value, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17In(List<String> values) {
            addCriterion("DUTY_DAY17 in", values, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17NotIn(List<String> values) {
            addCriterion("DUTY_DAY17 not in", values, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17Between(String value1, String value2) {
            addCriterion("DUTY_DAY17 between", value1, value2, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay17NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY17 not between", value1, value2, "dutyDay17");
            return (Criteria) this;
        }

        public Criteria andDutyDay18IsNull() {
            addCriterion("DUTY_DAY18 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay18IsNotNull() {
            addCriterion("DUTY_DAY18 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay18EqualTo(String value) {
            addCriterion("DUTY_DAY18 =", value, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18NotEqualTo(String value) {
            addCriterion("DUTY_DAY18 <>", value, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18GreaterThan(String value) {
            addCriterion("DUTY_DAY18 >", value, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY18 >=", value, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18LessThan(String value) {
            addCriterion("DUTY_DAY18 <", value, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY18 <=", value, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18Like(String value) {
            addCriterion("DUTY_DAY18 like", value, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18NotLike(String value) {
            addCriterion("DUTY_DAY18 not like", value, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18In(List<String> values) {
            addCriterion("DUTY_DAY18 in", values, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18NotIn(List<String> values) {
            addCriterion("DUTY_DAY18 not in", values, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18Between(String value1, String value2) {
            addCriterion("DUTY_DAY18 between", value1, value2, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay18NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY18 not between", value1, value2, "dutyDay18");
            return (Criteria) this;
        }

        public Criteria andDutyDay19IsNull() {
            addCriterion("DUTY_DAY19 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay19IsNotNull() {
            addCriterion("DUTY_DAY19 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay19EqualTo(String value) {
            addCriterion("DUTY_DAY19 =", value, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19NotEqualTo(String value) {
            addCriterion("DUTY_DAY19 <>", value, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19GreaterThan(String value) {
            addCriterion("DUTY_DAY19 >", value, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY19 >=", value, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19LessThan(String value) {
            addCriterion("DUTY_DAY19 <", value, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY19 <=", value, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19Like(String value) {
            addCriterion("DUTY_DAY19 like", value, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19NotLike(String value) {
            addCriterion("DUTY_DAY19 not like", value, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19In(List<String> values) {
            addCriterion("DUTY_DAY19 in", values, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19NotIn(List<String> values) {
            addCriterion("DUTY_DAY19 not in", values, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19Between(String value1, String value2) {
            addCriterion("DUTY_DAY19 between", value1, value2, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay19NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY19 not between", value1, value2, "dutyDay19");
            return (Criteria) this;
        }

        public Criteria andDutyDay20IsNull() {
            addCriterion("DUTY_DAY20 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay20IsNotNull() {
            addCriterion("DUTY_DAY20 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay20EqualTo(String value) {
            addCriterion("DUTY_DAY20 =", value, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20NotEqualTo(String value) {
            addCriterion("DUTY_DAY20 <>", value, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20GreaterThan(String value) {
            addCriterion("DUTY_DAY20 >", value, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY20 >=", value, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20LessThan(String value) {
            addCriterion("DUTY_DAY20 <", value, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY20 <=", value, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20Like(String value) {
            addCriterion("DUTY_DAY20 like", value, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20NotLike(String value) {
            addCriterion("DUTY_DAY20 not like", value, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20In(List<String> values) {
            addCriterion("DUTY_DAY20 in", values, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20NotIn(List<String> values) {
            addCriterion("DUTY_DAY20 not in", values, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20Between(String value1, String value2) {
            addCriterion("DUTY_DAY20 between", value1, value2, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay20NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY20 not between", value1, value2, "dutyDay20");
            return (Criteria) this;
        }

        public Criteria andDutyDay21IsNull() {
            addCriterion("DUTY_DAY21 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay21IsNotNull() {
            addCriterion("DUTY_DAY21 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay21EqualTo(String value) {
            addCriterion("DUTY_DAY21 =", value, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21NotEqualTo(String value) {
            addCriterion("DUTY_DAY21 <>", value, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21GreaterThan(String value) {
            addCriterion("DUTY_DAY21 >", value, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY21 >=", value, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21LessThan(String value) {
            addCriterion("DUTY_DAY21 <", value, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY21 <=", value, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21Like(String value) {
            addCriterion("DUTY_DAY21 like", value, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21NotLike(String value) {
            addCriterion("DUTY_DAY21 not like", value, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21In(List<String> values) {
            addCriterion("DUTY_DAY21 in", values, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21NotIn(List<String> values) {
            addCriterion("DUTY_DAY21 not in", values, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21Between(String value1, String value2) {
            addCriterion("DUTY_DAY21 between", value1, value2, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay21NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY21 not between", value1, value2, "dutyDay21");
            return (Criteria) this;
        }

        public Criteria andDutyDay22IsNull() {
            addCriterion("DUTY_DAY22 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay22IsNotNull() {
            addCriterion("DUTY_DAY22 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay22EqualTo(String value) {
            addCriterion("DUTY_DAY22 =", value, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22NotEqualTo(String value) {
            addCriterion("DUTY_DAY22 <>", value, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22GreaterThan(String value) {
            addCriterion("DUTY_DAY22 >", value, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY22 >=", value, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22LessThan(String value) {
            addCriterion("DUTY_DAY22 <", value, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY22 <=", value, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22Like(String value) {
            addCriterion("DUTY_DAY22 like", value, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22NotLike(String value) {
            addCriterion("DUTY_DAY22 not like", value, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22In(List<String> values) {
            addCriterion("DUTY_DAY22 in", values, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22NotIn(List<String> values) {
            addCriterion("DUTY_DAY22 not in", values, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22Between(String value1, String value2) {
            addCriterion("DUTY_DAY22 between", value1, value2, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay22NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY22 not between", value1, value2, "dutyDay22");
            return (Criteria) this;
        }

        public Criteria andDutyDay23IsNull() {
            addCriterion("DUTY_DAY23 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay23IsNotNull() {
            addCriterion("DUTY_DAY23 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay23EqualTo(String value) {
            addCriterion("DUTY_DAY23 =", value, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23NotEqualTo(String value) {
            addCriterion("DUTY_DAY23 <>", value, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23GreaterThan(String value) {
            addCriterion("DUTY_DAY23 >", value, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY23 >=", value, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23LessThan(String value) {
            addCriterion("DUTY_DAY23 <", value, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY23 <=", value, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23Like(String value) {
            addCriterion("DUTY_DAY23 like", value, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23NotLike(String value) {
            addCriterion("DUTY_DAY23 not like", value, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23In(List<String> values) {
            addCriterion("DUTY_DAY23 in", values, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23NotIn(List<String> values) {
            addCriterion("DUTY_DAY23 not in", values, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23Between(String value1, String value2) {
            addCriterion("DUTY_DAY23 between", value1, value2, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay23NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY23 not between", value1, value2, "dutyDay23");
            return (Criteria) this;
        }

        public Criteria andDutyDay24IsNull() {
            addCriterion("DUTY_DAY24 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay24IsNotNull() {
            addCriterion("DUTY_DAY24 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay24EqualTo(String value) {
            addCriterion("DUTY_DAY24 =", value, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24NotEqualTo(String value) {
            addCriterion("DUTY_DAY24 <>", value, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24GreaterThan(String value) {
            addCriterion("DUTY_DAY24 >", value, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY24 >=", value, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24LessThan(String value) {
            addCriterion("DUTY_DAY24 <", value, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY24 <=", value, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24Like(String value) {
            addCriterion("DUTY_DAY24 like", value, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24NotLike(String value) {
            addCriterion("DUTY_DAY24 not like", value, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24In(List<String> values) {
            addCriterion("DUTY_DAY24 in", values, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24NotIn(List<String> values) {
            addCriterion("DUTY_DAY24 not in", values, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24Between(String value1, String value2) {
            addCriterion("DUTY_DAY24 between", value1, value2, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay24NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY24 not between", value1, value2, "dutyDay24");
            return (Criteria) this;
        }

        public Criteria andDutyDay25IsNull() {
            addCriterion("DUTY_DAY25 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay25IsNotNull() {
            addCriterion("DUTY_DAY25 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay25EqualTo(String value) {
            addCriterion("DUTY_DAY25 =", value, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25NotEqualTo(String value) {
            addCriterion("DUTY_DAY25 <>", value, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25GreaterThan(String value) {
            addCriterion("DUTY_DAY25 >", value, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY25 >=", value, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25LessThan(String value) {
            addCriterion("DUTY_DAY25 <", value, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY25 <=", value, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25Like(String value) {
            addCriterion("DUTY_DAY25 like", value, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25NotLike(String value) {
            addCriterion("DUTY_DAY25 not like", value, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25In(List<String> values) {
            addCriterion("DUTY_DAY25 in", values, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25NotIn(List<String> values) {
            addCriterion("DUTY_DAY25 not in", values, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25Between(String value1, String value2) {
            addCriterion("DUTY_DAY25 between", value1, value2, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay25NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY25 not between", value1, value2, "dutyDay25");
            return (Criteria) this;
        }

        public Criteria andDutyDay26IsNull() {
            addCriterion("DUTY_DAY26 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay26IsNotNull() {
            addCriterion("DUTY_DAY26 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay26EqualTo(String value) {
            addCriterion("DUTY_DAY26 =", value, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26NotEqualTo(String value) {
            addCriterion("DUTY_DAY26 <>", value, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26GreaterThan(String value) {
            addCriterion("DUTY_DAY26 >", value, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY26 >=", value, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26LessThan(String value) {
            addCriterion("DUTY_DAY26 <", value, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY26 <=", value, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26Like(String value) {
            addCriterion("DUTY_DAY26 like", value, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26NotLike(String value) {
            addCriterion("DUTY_DAY26 not like", value, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26In(List<String> values) {
            addCriterion("DUTY_DAY26 in", values, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26NotIn(List<String> values) {
            addCriterion("DUTY_DAY26 not in", values, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26Between(String value1, String value2) {
            addCriterion("DUTY_DAY26 between", value1, value2, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay26NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY26 not between", value1, value2, "dutyDay26");
            return (Criteria) this;
        }

        public Criteria andDutyDay27IsNull() {
            addCriterion("DUTY_DAY27 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay27IsNotNull() {
            addCriterion("DUTY_DAY27 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay27EqualTo(String value) {
            addCriterion("DUTY_DAY27 =", value, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27NotEqualTo(String value) {
            addCriterion("DUTY_DAY27 <>", value, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27GreaterThan(String value) {
            addCriterion("DUTY_DAY27 >", value, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY27 >=", value, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27LessThan(String value) {
            addCriterion("DUTY_DAY27 <", value, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY27 <=", value, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27Like(String value) {
            addCriterion("DUTY_DAY27 like", value, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27NotLike(String value) {
            addCriterion("DUTY_DAY27 not like", value, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27In(List<String> values) {
            addCriterion("DUTY_DAY27 in", values, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27NotIn(List<String> values) {
            addCriterion("DUTY_DAY27 not in", values, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27Between(String value1, String value2) {
            addCriterion("DUTY_DAY27 between", value1, value2, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay27NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY27 not between", value1, value2, "dutyDay27");
            return (Criteria) this;
        }

        public Criteria andDutyDay28IsNull() {
            addCriterion("DUTY_DAY28 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay28IsNotNull() {
            addCriterion("DUTY_DAY28 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay28EqualTo(String value) {
            addCriterion("DUTY_DAY28 =", value, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28NotEqualTo(String value) {
            addCriterion("DUTY_DAY28 <>", value, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28GreaterThan(String value) {
            addCriterion("DUTY_DAY28 >", value, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY28 >=", value, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28LessThan(String value) {
            addCriterion("DUTY_DAY28 <", value, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY28 <=", value, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28Like(String value) {
            addCriterion("DUTY_DAY28 like", value, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28NotLike(String value) {
            addCriterion("DUTY_DAY28 not like", value, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28In(List<String> values) {
            addCriterion("DUTY_DAY28 in", values, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28NotIn(List<String> values) {
            addCriterion("DUTY_DAY28 not in", values, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28Between(String value1, String value2) {
            addCriterion("DUTY_DAY28 between", value1, value2, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay28NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY28 not between", value1, value2, "dutyDay28");
            return (Criteria) this;
        }

        public Criteria andDutyDay29IsNull() {
            addCriterion("DUTY_DAY29 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay29IsNotNull() {
            addCriterion("DUTY_DAY29 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay29EqualTo(String value) {
            addCriterion("DUTY_DAY29 =", value, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29NotEqualTo(String value) {
            addCriterion("DUTY_DAY29 <>", value, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29GreaterThan(String value) {
            addCriterion("DUTY_DAY29 >", value, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY29 >=", value, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29LessThan(String value) {
            addCriterion("DUTY_DAY29 <", value, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY29 <=", value, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29Like(String value) {
            addCriterion("DUTY_DAY29 like", value, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29NotLike(String value) {
            addCriterion("DUTY_DAY29 not like", value, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29In(List<String> values) {
            addCriterion("DUTY_DAY29 in", values, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29NotIn(List<String> values) {
            addCriterion("DUTY_DAY29 not in", values, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29Between(String value1, String value2) {
            addCriterion("DUTY_DAY29 between", value1, value2, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay29NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY29 not between", value1, value2, "dutyDay29");
            return (Criteria) this;
        }

        public Criteria andDutyDay30IsNull() {
            addCriterion("DUTY_DAY30 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay30IsNotNull() {
            addCriterion("DUTY_DAY30 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay30EqualTo(String value) {
            addCriterion("DUTY_DAY30 =", value, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30NotEqualTo(String value) {
            addCriterion("DUTY_DAY30 <>", value, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30GreaterThan(String value) {
            addCriterion("DUTY_DAY30 >", value, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY30 >=", value, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30LessThan(String value) {
            addCriterion("DUTY_DAY30 <", value, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY30 <=", value, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30Like(String value) {
            addCriterion("DUTY_DAY30 like", value, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30NotLike(String value) {
            addCriterion("DUTY_DAY30 not like", value, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30In(List<String> values) {
            addCriterion("DUTY_DAY30 in", values, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30NotIn(List<String> values) {
            addCriterion("DUTY_DAY30 not in", values, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30Between(String value1, String value2) {
            addCriterion("DUTY_DAY30 between", value1, value2, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay30NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY30 not between", value1, value2, "dutyDay30");
            return (Criteria) this;
        }

        public Criteria andDutyDay31IsNull() {
            addCriterion("DUTY_DAY31 is null");
            return (Criteria) this;
        }

        public Criteria andDutyDay31IsNotNull() {
            addCriterion("DUTY_DAY31 is not null");
            return (Criteria) this;
        }

        public Criteria andDutyDay31EqualTo(String value) {
            addCriterion("DUTY_DAY31 =", value, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31NotEqualTo(String value) {
            addCriterion("DUTY_DAY31 <>", value, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31GreaterThan(String value) {
            addCriterion("DUTY_DAY31 >", value, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31GreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY31 >=", value, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31LessThan(String value) {
            addCriterion("DUTY_DAY31 <", value, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31LessThanOrEqualTo(String value) {
            addCriterion("DUTY_DAY31 <=", value, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31Like(String value) {
            addCriterion("DUTY_DAY31 like", value, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31NotLike(String value) {
            addCriterion("DUTY_DAY31 not like", value, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31In(List<String> values) {
            addCriterion("DUTY_DAY31 in", values, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31NotIn(List<String> values) {
            addCriterion("DUTY_DAY31 not in", values, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31Between(String value1, String value2) {
            addCriterion("DUTY_DAY31 between", value1, value2, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andDutyDay31NotBetween(String value1, String value2) {
            addCriterion("DUTY_DAY31 not between", value1, value2, "dutyDay31");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNull() {
            addCriterion("WORK_TIME is null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNotNull() {
            addCriterion("WORK_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeEqualTo(String value) {
            addCriterion("WORK_TIME =", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotEqualTo(String value) {
            addCriterion("WORK_TIME <>", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThan(String value) {
            addCriterion("WORK_TIME >", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThanOrEqualTo(String value) {
            addCriterion("WORK_TIME >=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThan(String value) {
            addCriterion("WORK_TIME <", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThanOrEqualTo(String value) {
            addCriterion("WORK_TIME <=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLike(String value) {
            addCriterion("WORK_TIME like", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotLike(String value) {
            addCriterion("WORK_TIME not like", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIn(List<String> values) {
            addCriterion("WORK_TIME in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotIn(List<String> values) {
            addCriterion("WORK_TIME not in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeBetween(String value1, String value2) {
            addCriterion("WORK_TIME between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotBetween(String value1, String value2) {
            addCriterion("WORK_TIME not between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andDutyMemIsNull() {
            addCriterion("DUTY_MEM is null");
            return (Criteria) this;
        }

        public Criteria andDutyMemIsNotNull() {
            addCriterion("DUTY_MEM is not null");
            return (Criteria) this;
        }

        public Criteria andDutyMemEqualTo(String value) {
            addCriterion("DUTY_MEM =", value, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemNotEqualTo(String value) {
            addCriterion("DUTY_MEM <>", value, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemGreaterThan(String value) {
            addCriterion("DUTY_MEM >", value, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemGreaterThanOrEqualTo(String value) {
            addCriterion("DUTY_MEM >=", value, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemLessThan(String value) {
            addCriterion("DUTY_MEM <", value, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemLessThanOrEqualTo(String value) {
            addCriterion("DUTY_MEM <=", value, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemLike(String value) {
            addCriterion("DUTY_MEM like", value, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemNotLike(String value) {
            addCriterion("DUTY_MEM not like", value, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemIn(List<String> values) {
            addCriterion("DUTY_MEM in", values, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemNotIn(List<String> values) {
            addCriterion("DUTY_MEM not in", values, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemBetween(String value1, String value2) {
            addCriterion("DUTY_MEM between", value1, value2, "dutyMem");
            return (Criteria) this;
        }

        public Criteria andDutyMemNotBetween(String value1, String value2) {
            addCriterion("DUTY_MEM not between", value1, value2, "dutyMem");
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