package com.cmbccd.ulms.sys.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andCodeIsNull() {
            addCriterion("CODE is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("CODE =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("CODE <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("CODE >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CODE >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("CODE <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("CODE <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("CODE like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("CODE not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("CODE in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("CODE not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("CODE between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("CODE not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeIsNull() {
            addCriterion("XZJG_CODE is null");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeIsNotNull() {
            addCriterion("XZJG_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeEqualTo(String value) {
            addCriterion("XZJG_CODE =", value, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeNotEqualTo(String value) {
            addCriterion("XZJG_CODE <>", value, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeGreaterThan(String value) {
            addCriterion("XZJG_CODE >", value, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("XZJG_CODE >=", value, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeLessThan(String value) {
            addCriterion("XZJG_CODE <", value, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeLessThanOrEqualTo(String value) {
            addCriterion("XZJG_CODE <=", value, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeLike(String value) {
            addCriterion("XZJG_CODE like", value, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeNotLike(String value) {
            addCriterion("XZJG_CODE not like", value, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeIn(List<String> values) {
            addCriterion("XZJG_CODE in", values, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeNotIn(List<String> values) {
            addCriterion("XZJG_CODE not in", values, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeBetween(String value1, String value2) {
            addCriterion("XZJG_CODE between", value1, value2, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgCodeNotBetween(String value1, String value2) {
            addCriterion("XZJG_CODE not between", value1, value2, "xzjgCode");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmIsNull() {
            addCriterion("XZJG_JGHM is null");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmIsNotNull() {
            addCriterion("XZJG_JGHM is not null");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmEqualTo(String value) {
            addCriterion("XZJG_JGHM =", value, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmNotEqualTo(String value) {
            addCriterion("XZJG_JGHM <>", value, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmGreaterThan(String value) {
            addCriterion("XZJG_JGHM >", value, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmGreaterThanOrEqualTo(String value) {
            addCriterion("XZJG_JGHM >=", value, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmLessThan(String value) {
            addCriterion("XZJG_JGHM <", value, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmLessThanOrEqualTo(String value) {
            addCriterion("XZJG_JGHM <=", value, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmLike(String value) {
            addCriterion("XZJG_JGHM like", value, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmNotLike(String value) {
            addCriterion("XZJG_JGHM not like", value, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmIn(List<String> values) {
            addCriterion("XZJG_JGHM in", values, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmNotIn(List<String> values) {
            addCriterion("XZJG_JGHM not in", values, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmBetween(String value1, String value2) {
            addCriterion("XZJG_JGHM between", value1, value2, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andXzjgJghmNotBetween(String value1, String value2) {
            addCriterion("XZJG_JGHM not between", value1, value2, "xzjgJghm");
            return (Criteria) this;
        }

        public Criteria andCzyCodeIsNull() {
            addCriterion("CZY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCzyCodeIsNotNull() {
            addCriterion("CZY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCzyCodeEqualTo(String value) {
            addCriterion("CZY_CODE =", value, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeNotEqualTo(String value) {
            addCriterion("CZY_CODE <>", value, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeGreaterThan(String value) {
            addCriterion("CZY_CODE >", value, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CZY_CODE >=", value, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeLessThan(String value) {
            addCriterion("CZY_CODE <", value, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeLessThanOrEqualTo(String value) {
            addCriterion("CZY_CODE <=", value, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeLike(String value) {
            addCriterion("CZY_CODE like", value, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeNotLike(String value) {
            addCriterion("CZY_CODE not like", value, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeIn(List<String> values) {
            addCriterion("CZY_CODE in", values, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeNotIn(List<String> values) {
            addCriterion("CZY_CODE not in", values, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeBetween(String value1, String value2) {
            addCriterion("CZY_CODE between", value1, value2, "czyCode");
            return (Criteria) this;
        }

        public Criteria andCzyCodeNotBetween(String value1, String value2) {
            addCriterion("CZY_CODE not between", value1, value2, "czyCode");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andLxdhIsNull() {
            addCriterion("LXDH is null");
            return (Criteria) this;
        }

        public Criteria andLxdhIsNotNull() {
            addCriterion("LXDH is not null");
            return (Criteria) this;
        }

        public Criteria andLxdhEqualTo(String value) {
            addCriterion("LXDH =", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhNotEqualTo(String value) {
            addCriterion("LXDH <>", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhGreaterThan(String value) {
            addCriterion("LXDH >", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhGreaterThanOrEqualTo(String value) {
            addCriterion("LXDH >=", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhLessThan(String value) {
            addCriterion("LXDH <", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhLessThanOrEqualTo(String value) {
            addCriterion("LXDH <=", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhLike(String value) {
            addCriterion("LXDH like", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhNotLike(String value) {
            addCriterion("LXDH not like", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhIn(List<String> values) {
            addCriterion("LXDH in", values, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhNotIn(List<String> values) {
            addCriterion("LXDH not in", values, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhBetween(String value1, String value2) {
            addCriterion("LXDH between", value1, value2, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhNotBetween(String value1, String value2) {
            addCriterion("LXDH not between", value1, value2, "lxdh");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("PASSWORD is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("PASSWORD is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("PASSWORD =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("PASSWORD <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("PASSWORD >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("PASSWORD >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("PASSWORD <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("PASSWORD <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("PASSWORD like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("PASSWORD not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("PASSWORD in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("PASSWORD not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("PASSWORD between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("PASSWORD not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andZjmIsNull() {
            addCriterion("ZJM is null");
            return (Criteria) this;
        }

        public Criteria andZjmIsNotNull() {
            addCriterion("ZJM is not null");
            return (Criteria) this;
        }

        public Criteria andZjmEqualTo(String value) {
            addCriterion("ZJM =", value, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmNotEqualTo(String value) {
            addCriterion("ZJM <>", value, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmGreaterThan(String value) {
            addCriterion("ZJM >", value, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmGreaterThanOrEqualTo(String value) {
            addCriterion("ZJM >=", value, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmLessThan(String value) {
            addCriterion("ZJM <", value, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmLessThanOrEqualTo(String value) {
            addCriterion("ZJM <=", value, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmLike(String value) {
            addCriterion("ZJM like", value, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmNotLike(String value) {
            addCriterion("ZJM not like", value, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmIn(List<String> values) {
            addCriterion("ZJM in", values, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmNotIn(List<String> values) {
            addCriterion("ZJM not in", values, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmBetween(String value1, String value2) {
            addCriterion("ZJM between", value1, value2, "zjm");
            return (Criteria) this;
        }

        public Criteria andZjmNotBetween(String value1, String value2) {
            addCriterion("ZJM not between", value1, value2, "zjm");
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

        public Criteria andMytsIsNull() {
            addCriterion("MYTS is null");
            return (Criteria) this;
        }

        public Criteria andMytsIsNotNull() {
            addCriterion("MYTS is not null");
            return (Criteria) this;
        }

        public Criteria andMytsEqualTo(BigDecimal value) {
            addCriterion("MYTS =", value, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsNotEqualTo(BigDecimal value) {
            addCriterion("MYTS <>", value, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsGreaterThan(BigDecimal value) {
            addCriterion("MYTS >", value, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MYTS >=", value, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsLessThan(BigDecimal value) {
            addCriterion("MYTS <", value, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MYTS <=", value, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsIn(List<BigDecimal> values) {
            addCriterion("MYTS in", values, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsNotIn(List<BigDecimal> values) {
            addCriterion("MYTS not in", values, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MYTS between", value1, value2, "myts");
            return (Criteria) this;
        }

        public Criteria andMytsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MYTS not between", value1, value2, "myts");
            return (Criteria) this;
        }

        public Criteria andCjsjIsNull() {
            addCriterion("CJSJ is null");
            return (Criteria) this;
        }

        public Criteria andCjsjIsNotNull() {
            addCriterion("CJSJ is not null");
            return (Criteria) this;
        }

        public Criteria andCjsjEqualTo(Date value) {
            addCriterionForJDBCDate("CJSJ =", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotEqualTo(Date value) {
            addCriterionForJDBCDate("CJSJ <>", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThan(Date value) {
            addCriterionForJDBCDate("CJSJ >", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CJSJ >=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThan(Date value) {
            addCriterionForJDBCDate("CJSJ <", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CJSJ <=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjIn(List<Date> values) {
            addCriterionForJDBCDate("CJSJ in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotIn(List<Date> values) {
            addCriterionForJDBCDate("CJSJ not in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CJSJ between", value1, value2, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CJSJ not between", value1, value2, "cjsj");
            return (Criteria) this;
        }

        public Criteria andRoleidIsNull() {
            addCriterion("ROLEID is null");
            return (Criteria) this;
        }

        public Criteria andRoleidIsNotNull() {
            addCriterion("ROLEID is not null");
            return (Criteria) this;
        }

        public Criteria andRoleidEqualTo(String value) {
            addCriterion("ROLEID =", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotEqualTo(String value) {
            addCriterion("ROLEID <>", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidGreaterThan(String value) {
            addCriterion("ROLEID >", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidGreaterThanOrEqualTo(String value) {
            addCriterion("ROLEID >=", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidLessThan(String value) {
            addCriterion("ROLEID <", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidLessThanOrEqualTo(String value) {
            addCriterion("ROLEID <=", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidLike(String value) {
            addCriterion("ROLEID like", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotLike(String value) {
            addCriterion("ROLEID not like", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidIn(List<String> values) {
            addCriterion("ROLEID in", values, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotIn(List<String> values) {
            addCriterion("ROLEID not in", values, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidBetween(String value1, String value2) {
            addCriterion("ROLEID between", value1, value2, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotBetween(String value1, String value2) {
            addCriterion("ROLEID not between", value1, value2, "roleid");
            return (Criteria) this;
        }

        public Criteria andZhdlIsNull() {
            addCriterion("ZHDL is null");
            return (Criteria) this;
        }

        public Criteria andZhdlIsNotNull() {
            addCriterion("ZHDL is not null");
            return (Criteria) this;
        }

        public Criteria andZhdlEqualTo(Date value) {
            addCriterionForJDBCDate("ZHDL =", value, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlNotEqualTo(Date value) {
            addCriterionForJDBCDate("ZHDL <>", value, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlGreaterThan(Date value) {
            addCriterionForJDBCDate("ZHDL >", value, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ZHDL >=", value, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlLessThan(Date value) {
            addCriterionForJDBCDate("ZHDL <", value, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ZHDL <=", value, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlIn(List<Date> values) {
            addCriterionForJDBCDate("ZHDL in", values, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlNotIn(List<Date> values) {
            addCriterionForJDBCDate("ZHDL not in", values, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ZHDL between", value1, value2, "zhdl");
            return (Criteria) this;
        }

        public Criteria andZhdlNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ZHDL not between", value1, value2, "zhdl");
            return (Criteria) this;
        }

        public Criteria andSfzxIsNull() {
            addCriterion("SFZX is null");
            return (Criteria) this;
        }

        public Criteria andSfzxIsNotNull() {
            addCriterion("SFZX is not null");
            return (Criteria) this;
        }

        public Criteria andSfzxEqualTo(String value) {
            addCriterion("SFZX =", value, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxNotEqualTo(String value) {
            addCriterion("SFZX <>", value, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxGreaterThan(String value) {
            addCriterion("SFZX >", value, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxGreaterThanOrEqualTo(String value) {
            addCriterion("SFZX >=", value, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxLessThan(String value) {
            addCriterion("SFZX <", value, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxLessThanOrEqualTo(String value) {
            addCriterion("SFZX <=", value, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxLike(String value) {
            addCriterion("SFZX like", value, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxNotLike(String value) {
            addCriterion("SFZX not like", value, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxIn(List<String> values) {
            addCriterion("SFZX in", values, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxNotIn(List<String> values) {
            addCriterion("SFZX not in", values, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxBetween(String value1, String value2) {
            addCriterion("SFZX between", value1, value2, "sfzx");
            return (Criteria) this;
        }

        public Criteria andSfzxNotBetween(String value1, String value2) {
            addCriterion("SFZX not between", value1, value2, "sfzx");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("IP is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("IP is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("IP =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("IP <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("IP >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("IP >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("IP <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("IP <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("IP like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("IP not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("IP in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("IP not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("IP between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("IP not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andSfYxjlIsNull() {
            addCriterion("SF_YXJL is null");
            return (Criteria) this;
        }

        public Criteria andSfYxjlIsNotNull() {
            addCriterion("SF_YXJL is not null");
            return (Criteria) this;
        }

        public Criteria andSfYxjlEqualTo(String value) {
            addCriterion("SF_YXJL =", value, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlNotEqualTo(String value) {
            addCriterion("SF_YXJL <>", value, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlGreaterThan(String value) {
            addCriterion("SF_YXJL >", value, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlGreaterThanOrEqualTo(String value) {
            addCriterion("SF_YXJL >=", value, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlLessThan(String value) {
            addCriterion("SF_YXJL <", value, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlLessThanOrEqualTo(String value) {
            addCriterion("SF_YXJL <=", value, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlLike(String value) {
            addCriterion("SF_YXJL like", value, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlNotLike(String value) {
            addCriterion("SF_YXJL not like", value, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlIn(List<String> values) {
            addCriterion("SF_YXJL in", values, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlNotIn(List<String> values) {
            addCriterion("SF_YXJL not in", values, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlBetween(String value1, String value2) {
            addCriterion("SF_YXJL between", value1, value2, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andSfYxjlNotBetween(String value1, String value2) {
            addCriterion("SF_YXJL not between", value1, value2, "sfYxjl");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeIsNull() {
            addCriterion("XZCZY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeIsNotNull() {
            addCriterion("XZCZY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeEqualTo(String value) {
            addCriterion("XZCZY_CODE =", value, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeNotEqualTo(String value) {
            addCriterion("XZCZY_CODE <>", value, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeGreaterThan(String value) {
            addCriterion("XZCZY_CODE >", value, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("XZCZY_CODE >=", value, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeLessThan(String value) {
            addCriterion("XZCZY_CODE <", value, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeLessThanOrEqualTo(String value) {
            addCriterion("XZCZY_CODE <=", value, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeLike(String value) {
            addCriterion("XZCZY_CODE like", value, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeNotLike(String value) {
            addCriterion("XZCZY_CODE not like", value, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeIn(List<String> values) {
            addCriterion("XZCZY_CODE in", values, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeNotIn(List<String> values) {
            addCriterion("XZCZY_CODE not in", values, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeBetween(String value1, String value2) {
            addCriterion("XZCZY_CODE between", value1, value2, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyCodeNotBetween(String value1, String value2) {
            addCriterion("XZCZY_CODE not between", value1, value2, "xzczyCode");
            return (Criteria) this;
        }

        public Criteria andXzczyNameIsNull() {
            addCriterion("XZCZY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andXzczyNameIsNotNull() {
            addCriterion("XZCZY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andXzczyNameEqualTo(String value) {
            addCriterion("XZCZY_NAME =", value, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameNotEqualTo(String value) {
            addCriterion("XZCZY_NAME <>", value, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameGreaterThan(String value) {
            addCriterion("XZCZY_NAME >", value, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameGreaterThanOrEqualTo(String value) {
            addCriterion("XZCZY_NAME >=", value, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameLessThan(String value) {
            addCriterion("XZCZY_NAME <", value, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameLessThanOrEqualTo(String value) {
            addCriterion("XZCZY_NAME <=", value, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameLike(String value) {
            addCriterion("XZCZY_NAME like", value, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameNotLike(String value) {
            addCriterion("XZCZY_NAME not like", value, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameIn(List<String> values) {
            addCriterion("XZCZY_NAME in", values, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameNotIn(List<String> values) {
            addCriterion("XZCZY_NAME not in", values, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameBetween(String value1, String value2) {
            addCriterion("XZCZY_NAME between", value1, value2, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzczyNameNotBetween(String value1, String value2) {
            addCriterion("XZCZY_NAME not between", value1, value2, "xzczyName");
            return (Criteria) this;
        }

        public Criteria andXzrqIsNull() {
            addCriterion("XZRQ is null");
            return (Criteria) this;
        }

        public Criteria andXzrqIsNotNull() {
            addCriterion("XZRQ is not null");
            return (Criteria) this;
        }

        public Criteria andXzrqEqualTo(Date value) {
            addCriterionForJDBCDate("XZRQ =", value, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqNotEqualTo(Date value) {
            addCriterionForJDBCDate("XZRQ <>", value, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqGreaterThan(Date value) {
            addCriterionForJDBCDate("XZRQ >", value, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("XZRQ >=", value, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqLessThan(Date value) {
            addCriterionForJDBCDate("XZRQ <", value, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("XZRQ <=", value, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqIn(List<Date> values) {
            addCriterionForJDBCDate("XZRQ in", values, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqNotIn(List<Date> values) {
            addCriterionForJDBCDate("XZRQ not in", values, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("XZRQ between", value1, value2, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXzrqNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("XZRQ not between", value1, value2, "xzrq");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeIsNull() {
            addCriterion("XGCZY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeIsNotNull() {
            addCriterion("XGCZY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeEqualTo(String value) {
            addCriterion("XGCZY_CODE =", value, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeNotEqualTo(String value) {
            addCriterion("XGCZY_CODE <>", value, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeGreaterThan(String value) {
            addCriterion("XGCZY_CODE >", value, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("XGCZY_CODE >=", value, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeLessThan(String value) {
            addCriterion("XGCZY_CODE <", value, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeLessThanOrEqualTo(String value) {
            addCriterion("XGCZY_CODE <=", value, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeLike(String value) {
            addCriterion("XGCZY_CODE like", value, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeNotLike(String value) {
            addCriterion("XGCZY_CODE not like", value, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeIn(List<String> values) {
            addCriterion("XGCZY_CODE in", values, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeNotIn(List<String> values) {
            addCriterion("XGCZY_CODE not in", values, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeBetween(String value1, String value2) {
            addCriterion("XGCZY_CODE between", value1, value2, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyCodeNotBetween(String value1, String value2) {
            addCriterion("XGCZY_CODE not between", value1, value2, "xgczyCode");
            return (Criteria) this;
        }

        public Criteria andXgczyNameIsNull() {
            addCriterion("XGCZY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andXgczyNameIsNotNull() {
            addCriterion("XGCZY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andXgczyNameEqualTo(String value) {
            addCriterion("XGCZY_NAME =", value, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameNotEqualTo(String value) {
            addCriterion("XGCZY_NAME <>", value, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameGreaterThan(String value) {
            addCriterion("XGCZY_NAME >", value, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameGreaterThanOrEqualTo(String value) {
            addCriterion("XGCZY_NAME >=", value, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameLessThan(String value) {
            addCriterion("XGCZY_NAME <", value, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameLessThanOrEqualTo(String value) {
            addCriterion("XGCZY_NAME <=", value, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameLike(String value) {
            addCriterion("XGCZY_NAME like", value, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameNotLike(String value) {
            addCriterion("XGCZY_NAME not like", value, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameIn(List<String> values) {
            addCriterion("XGCZY_NAME in", values, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameNotIn(List<String> values) {
            addCriterion("XGCZY_NAME not in", values, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameBetween(String value1, String value2) {
            addCriterion("XGCZY_NAME between", value1, value2, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgczyNameNotBetween(String value1, String value2) {
            addCriterion("XGCZY_NAME not between", value1, value2, "xgczyName");
            return (Criteria) this;
        }

        public Criteria andXgrqIsNull() {
            addCriterion("XGRQ is null");
            return (Criteria) this;
        }

        public Criteria andXgrqIsNotNull() {
            addCriterion("XGRQ is not null");
            return (Criteria) this;
        }

        public Criteria andXgrqEqualTo(Date value) {
            addCriterionForJDBCDate("XGRQ =", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqNotEqualTo(Date value) {
            addCriterionForJDBCDate("XGRQ <>", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqGreaterThan(Date value) {
            addCriterionForJDBCDate("XGRQ >", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("XGRQ >=", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqLessThan(Date value) {
            addCriterionForJDBCDate("XGRQ <", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("XGRQ <=", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqIn(List<Date> values) {
            addCriterionForJDBCDate("XGRQ in", values, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqNotIn(List<Date> values) {
            addCriterionForJDBCDate("XGRQ not in", values, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("XGRQ between", value1, value2, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("XGRQ not between", value1, value2, "xgrq");
            return (Criteria) this;
        }

        public Criteria andFactrqIsNull() {
            addCriterion("FACTRQ is null");
            return (Criteria) this;
        }

        public Criteria andFactrqIsNotNull() {
            addCriterion("FACTRQ is not null");
            return (Criteria) this;
        }

        public Criteria andFactrqEqualTo(Date value) {
            addCriterionForJDBCDate("FACTRQ =", value, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqNotEqualTo(Date value) {
            addCriterionForJDBCDate("FACTRQ <>", value, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqGreaterThan(Date value) {
            addCriterionForJDBCDate("FACTRQ >", value, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("FACTRQ >=", value, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqLessThan(Date value) {
            addCriterionForJDBCDate("FACTRQ <", value, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("FACTRQ <=", value, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqIn(List<Date> values) {
            addCriterionForJDBCDate("FACTRQ in", values, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqNotIn(List<Date> values) {
            addCriterionForJDBCDate("FACTRQ not in", values, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("FACTRQ between", value1, value2, "factrq");
            return (Criteria) this;
        }

        public Criteria andFactrqNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("FACTRQ not between", value1, value2, "factrq");
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