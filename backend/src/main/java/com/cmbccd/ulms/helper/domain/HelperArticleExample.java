package com.cmbccd.ulms.helper.domain;

import java.util.ArrayList;
import java.util.List;

public class HelperArticleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HelperArticleExample() {
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

        public Criteria andJournoEqualTo(Object value) {
            addCriterion("JOURNO =", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoNotEqualTo(Object value) {
            addCriterion("JOURNO <>", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoGreaterThan(Object value) {
            addCriterion("JOURNO >", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoGreaterThanOrEqualTo(Object value) {
            addCriterion("JOURNO >=", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoLessThan(Object value) {
            addCriterion("JOURNO <", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoLessThanOrEqualTo(Object value) {
            addCriterion("JOURNO <=", value, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoIn(List<Object> values) {
            addCriterion("JOURNO in", values, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoNotIn(List<Object> values) {
            addCriterion("JOURNO not in", values, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoBetween(Object value1, Object value2) {
            addCriterion("JOURNO between", value1, value2, "journo");
            return (Criteria) this;
        }

        public Criteria andJournoNotBetween(Object value1, Object value2) {
            addCriterion("JOURNO not between", value1, value2, "journo");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("AREA is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("AREA is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("AREA =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("AREA <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("AREA >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("AREA >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("AREA <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("AREA <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("AREA like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("AREA not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("AREA in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("AREA not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("AREA between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("AREA not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("OWNER is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("OWNER is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            addCriterion("OWNER =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            addCriterion("OWNER <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            addCriterion("OWNER >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("OWNER >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            addCriterion("OWNER <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            addCriterion("OWNER <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            addCriterion("OWNER like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            addCriterion("OWNER not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            addCriterion("OWNER in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            addCriterion("OWNER not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            addCriterion("OWNER between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            addCriterion("OWNER not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("TITLE is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("TITLE =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("TITLE <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("TITLE >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("TITLE >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("TITLE <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("TITLE <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("TITLE like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("TITLE not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("TITLE in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("TITLE not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("TITLE between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("TITLE not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andKeyWordIsNull() {
            addCriterion("KEY_WORD is null");
            return (Criteria) this;
        }

        public Criteria andKeyWordIsNotNull() {
            addCriterion("KEY_WORD is not null");
            return (Criteria) this;
        }

        public Criteria andKeyWordEqualTo(String value) {
            addCriterion("KEY_WORD =", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordNotEqualTo(String value) {
            addCriterion("KEY_WORD <>", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordGreaterThan(String value) {
            addCriterion("KEY_WORD >", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordGreaterThanOrEqualTo(String value) {
            addCriterion("KEY_WORD >=", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordLessThan(String value) {
            addCriterion("KEY_WORD <", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordLessThanOrEqualTo(String value) {
            addCriterion("KEY_WORD <=", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordLike(String value) {
            addCriterion("KEY_WORD like", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordNotLike(String value) {
            addCriterion("KEY_WORD not like", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordIn(List<String> values) {
            addCriterion("KEY_WORD in", values, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordNotIn(List<String> values) {
            addCriterion("KEY_WORD not in", values, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordBetween(String value1, String value2) {
            addCriterion("KEY_WORD between", value1, value2, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordNotBetween(String value1, String value2) {
            addCriterion("KEY_WORD not between", value1, value2, "keyWord");
            return (Criteria) this;
        }

        public Criteria andInsertDateIsNull() {
            addCriterion("INSERT_DATE is null");
            return (Criteria) this;
        }

        public Criteria andInsertDateIsNotNull() {
            addCriterion("INSERT_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andInsertDateEqualTo(Object value) {
            addCriterion("INSERT_DATE =", value, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateNotEqualTo(Object value) {
            addCriterion("INSERT_DATE <>", value, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateGreaterThan(Object value) {
            addCriterion("INSERT_DATE >", value, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateGreaterThanOrEqualTo(Object value) {
            addCriterion("INSERT_DATE >=", value, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateLessThan(Object value) {
            addCriterion("INSERT_DATE <", value, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateLessThanOrEqualTo(Object value) {
            addCriterion("INSERT_DATE <=", value, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateIn(List<Object> values) {
            addCriterion("INSERT_DATE in", values, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateNotIn(List<Object> values) {
            addCriterion("INSERT_DATE not in", values, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateBetween(Object value1, Object value2) {
            addCriterion("INSERT_DATE between", value1, value2, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertDateNotBetween(Object value1, Object value2) {
            addCriterion("INSERT_DATE not between", value1, value2, "insertDate");
            return (Criteria) this;
        }

        public Criteria andInsertPloIsNull() {
            addCriterion("INSERT_PLO is null");
            return (Criteria) this;
        }

        public Criteria andInsertPloIsNotNull() {
            addCriterion("INSERT_PLO is not null");
            return (Criteria) this;
        }

        public Criteria andInsertPloEqualTo(String value) {
            addCriterion("INSERT_PLO =", value, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloNotEqualTo(String value) {
            addCriterion("INSERT_PLO <>", value, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloGreaterThan(String value) {
            addCriterion("INSERT_PLO >", value, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloGreaterThanOrEqualTo(String value) {
            addCriterion("INSERT_PLO >=", value, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloLessThan(String value) {
            addCriterion("INSERT_PLO <", value, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloLessThanOrEqualTo(String value) {
            addCriterion("INSERT_PLO <=", value, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloLike(String value) {
            addCriterion("INSERT_PLO like", value, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloNotLike(String value) {
            addCriterion("INSERT_PLO not like", value, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloIn(List<String> values) {
            addCriterion("INSERT_PLO in", values, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloNotIn(List<String> values) {
            addCriterion("INSERT_PLO not in", values, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloBetween(String value1, String value2) {
            addCriterion("INSERT_PLO between", value1, value2, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andInsertPloNotBetween(String value1, String value2) {
            addCriterion("INSERT_PLO not between", value1, value2, "insertPlo");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Object value) {
            addCriterion("UPDATE_DATE =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Object value) {
            addCriterion("UPDATE_DATE <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Object value) {
            addCriterion("UPDATE_DATE >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Object value) {
            addCriterion("UPDATE_DATE >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Object value) {
            addCriterion("UPDATE_DATE <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Object value) {
            addCriterion("UPDATE_DATE <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Object> values) {
            addCriterion("UPDATE_DATE in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Object> values) {
            addCriterion("UPDATE_DATE not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Object value1, Object value2) {
            addCriterion("UPDATE_DATE between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Object value1, Object value2) {
            addCriterion("UPDATE_DATE not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdatePloIsNull() {
            addCriterion("UPDATE_PLO is null");
            return (Criteria) this;
        }

        public Criteria andUpdatePloIsNotNull() {
            addCriterion("UPDATE_PLO is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatePloEqualTo(Object value) {
            addCriterion("UPDATE_PLO =", value, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloNotEqualTo(Object value) {
            addCriterion("UPDATE_PLO <>", value, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloGreaterThan(Object value) {
            addCriterion("UPDATE_PLO >", value, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloGreaterThanOrEqualTo(Object value) {
            addCriterion("UPDATE_PLO >=", value, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloLessThan(Object value) {
            addCriterion("UPDATE_PLO <", value, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloLessThanOrEqualTo(Object value) {
            addCriterion("UPDATE_PLO <=", value, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloIn(List<Object> values) {
            addCriterion("UPDATE_PLO in", values, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloNotIn(List<Object> values) {
            addCriterion("UPDATE_PLO not in", values, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloBetween(Object value1, Object value2) {
            addCriterion("UPDATE_PLO between", value1, value2, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andUpdatePloNotBetween(Object value1, Object value2) {
            addCriterion("UPDATE_PLO not between", value1, value2, "updatePlo");
            return (Criteria) this;
        }

        public Criteria andRalateCodeIsNull() {
            addCriterion("RALATE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRalateCodeIsNotNull() {
            addCriterion("RALATE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRalateCodeEqualTo(String value) {
            addCriterion("RALATE_CODE =", value, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeNotEqualTo(String value) {
            addCriterion("RALATE_CODE <>", value, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeGreaterThan(String value) {
            addCriterion("RALATE_CODE >", value, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeGreaterThanOrEqualTo(String value) {
            addCriterion("RALATE_CODE >=", value, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeLessThan(String value) {
            addCriterion("RALATE_CODE <", value, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeLessThanOrEqualTo(String value) {
            addCriterion("RALATE_CODE <=", value, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeLike(String value) {
            addCriterion("RALATE_CODE like", value, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeNotLike(String value) {
            addCriterion("RALATE_CODE not like", value, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeIn(List<String> values) {
            addCriterion("RALATE_CODE in", values, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeNotIn(List<String> values) {
            addCriterion("RALATE_CODE not in", values, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeBetween(String value1, String value2) {
            addCriterion("RALATE_CODE between", value1, value2, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andRalateCodeNotBetween(String value1, String value2) {
            addCriterion("RALATE_CODE not between", value1, value2, "ralateCode");
            return (Criteria) this;
        }

        public Criteria andSortingIsNull() {
            addCriterion("SORTING is null");
            return (Criteria) this;
        }

        public Criteria andSortingIsNotNull() {
            addCriterion("SORTING is not null");
            return (Criteria) this;
        }

        public Criteria andSortingEqualTo(Integer value) {
            addCriterion("SORTING =", value, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingNotEqualTo(Integer value) {
            addCriterion("SORTING <>", value, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingGreaterThan(Integer value) {
            addCriterion("SORTING >", value, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingGreaterThanOrEqualTo(Integer value) {
            addCriterion("SORTING >=", value, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingLessThan(Integer value) {
            addCriterion("SORTING <", value, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingLessThanOrEqualTo(Integer value) {
            addCriterion("SORTING <=", value, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingIn(List<Integer> values) {
            addCriterion("SORTING in", values, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingNotIn(List<Integer> values) {
            addCriterion("SORTING not in", values, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingBetween(Integer value1, Integer value2) {
            addCriterion("SORTING between", value1, value2, "sorting");
            return (Criteria) this;
        }

        public Criteria andSortingNotBetween(Integer value1, Integer value2) {
            addCriterion("SORTING not between", value1, value2, "sorting");
            return (Criteria) this;
        }

        public Criteria andSearchIsNull() {
            addCriterion("SEARCH is null");
            return (Criteria) this;
        }

        public Criteria andSearchIsNotNull() {
            addCriterion("SEARCH is not null");
            return (Criteria) this;
        }

        public Criteria andSearchEqualTo(Integer value) {
            addCriterion("SEARCH =", value, "search");
            return (Criteria) this;
        }

        public Criteria andSearchNotEqualTo(Integer value) {
            addCriterion("SEARCH <>", value, "search");
            return (Criteria) this;
        }

        public Criteria andSearchGreaterThan(Integer value) {
            addCriterion("SEARCH >", value, "search");
            return (Criteria) this;
        }

        public Criteria andSearchGreaterThanOrEqualTo(Integer value) {
            addCriterion("SEARCH >=", value, "search");
            return (Criteria) this;
        }

        public Criteria andSearchLessThan(Integer value) {
            addCriterion("SEARCH <", value, "search");
            return (Criteria) this;
        }

        public Criteria andSearchLessThanOrEqualTo(Integer value) {
            addCriterion("SEARCH <=", value, "search");
            return (Criteria) this;
        }

        public Criteria andSearchIn(List<Integer> values) {
            addCriterion("SEARCH in", values, "search");
            return (Criteria) this;
        }

        public Criteria andSearchNotIn(List<Integer> values) {
            addCriterion("SEARCH not in", values, "search");
            return (Criteria) this;
        }

        public Criteria andSearchBetween(Integer value1, Integer value2) {
            addCriterion("SEARCH between", value1, value2, "search");
            return (Criteria) this;
        }

        public Criteria andSearchNotBetween(Integer value1, Integer value2) {
            addCriterion("SEARCH not between", value1, value2, "search");
            return (Criteria) this;
        }

        public Criteria andClicksIsNull() {
            addCriterion("CLICKS is null");
            return (Criteria) this;
        }

        public Criteria andClicksIsNotNull() {
            addCriterion("CLICKS is not null");
            return (Criteria) this;
        }

        public Criteria andClicksEqualTo(Integer value) {
            addCriterion("CLICKS =", value, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksNotEqualTo(Integer value) {
            addCriterion("CLICKS <>", value, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksGreaterThan(Integer value) {
            addCriterion("CLICKS >", value, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksGreaterThanOrEqualTo(Integer value) {
            addCriterion("CLICKS >=", value, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksLessThan(Integer value) {
            addCriterion("CLICKS <", value, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksLessThanOrEqualTo(Integer value) {
            addCriterion("CLICKS <=", value, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksIn(List<Integer> values) {
            addCriterion("CLICKS in", values, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksNotIn(List<Integer> values) {
            addCriterion("CLICKS not in", values, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksBetween(Integer value1, Integer value2) {
            addCriterion("CLICKS between", value1, value2, "clicks");
            return (Criteria) this;
        }

        public Criteria andClicksNotBetween(Integer value1, Integer value2) {
            addCriterion("CLICKS not between", value1, value2, "clicks");
            return (Criteria) this;
        }

        public Criteria andRouteIdIsNull() {
            addCriterion("ROUTE_ID is null");
            return (Criteria) this;
        }

        public Criteria andRouteIdIsNotNull() {
            addCriterion("ROUTE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRouteIdEqualTo(Integer value) {
            addCriterion("ROUTE_ID =", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotEqualTo(Integer value) {
            addCriterion("ROUTE_ID <>", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdGreaterThan(Integer value) {
            addCriterion("ROUTE_ID >", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ROUTE_ID >=", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLessThan(Integer value) {
            addCriterion("ROUTE_ID <", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLessThanOrEqualTo(Integer value) {
            addCriterion("ROUTE_ID <=", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdIn(List<Integer> values) {
            addCriterion("ROUTE_ID in", values, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotIn(List<Integer> values) {
            addCriterion("ROUTE_ID not in", values, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdBetween(Integer value1, Integer value2) {
            addCriterion("ROUTE_ID between", value1, value2, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ROUTE_ID not between", value1, value2, "routeId");
            return (Criteria) this;
        }

        public Criteria andFilesIsNull() {
            addCriterion("FILES is null");
            return (Criteria) this;
        }

        public Criteria andFilesIsNotNull() {
            addCriterion("FILES is not null");
            return (Criteria) this;
        }

        public Criteria andFilesEqualTo(Object value) {
            addCriterion("FILES =", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotEqualTo(Object value) {
            addCriterion("FILES <>", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesGreaterThan(Object value) {
            addCriterion("FILES >", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesGreaterThanOrEqualTo(Object value) {
            addCriterion("FILES >=", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesLessThan(Object value) {
            addCriterion("FILES <", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesLessThanOrEqualTo(Object value) {
            addCriterion("FILES <=", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesIn(List<Object> values) {
            addCriterion("FILES in", values, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotIn(List<Object> values) {
            addCriterion("FILES not in", values, "files");
            return (Criteria) this;
        }

        public Criteria andFilesBetween(Object value1, Object value2) {
            addCriterion("FILES between", value1, value2, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotBetween(Object value1, Object value2) {
            addCriterion("FILES not between", value1, value2, "files");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("CONTENT like", value, "Content");
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