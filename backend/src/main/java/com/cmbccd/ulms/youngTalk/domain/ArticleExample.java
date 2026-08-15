package com.cmbccd.ulms.youngTalk.domain;

import java.util.ArrayList;
import java.util.List;

public class ArticleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticleExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIsNull() {
            addCriterion("ARTICAL_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIsNotNull() {
            addCriterion("ARTICAL_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andArticleTypeEqualTo(Integer value) {
            addCriterion("ARTICAL_TYPE =", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotEqualTo(Integer value) {
            addCriterion("ARTICAL_TYPE <>", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeGreaterThan(Integer value) {
            addCriterion("ARTICAL_TYPE >", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ARTICAL_TYPE >=", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeLessThan(Integer value) {
            addCriterion("ARTICAL_TYPE <", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ARTICAL_TYPE <=", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIn(List<Integer> values) {
            addCriterion("ARTICAL_TYPE in", values, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotIn(List<Integer> values) {
            addCriterion("ARTICAL_TYPE not in", values, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeBetween(Integer value1, Integer value2) {
            addCriterion("ARTICAL_TYPE between", value1, value2, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ARTICAL_TYPE not between", value1, value2, "articleType");
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

        public Criteria andCategoryEqualTo(Integer value) {
            addCriterion("CATEGORY =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(Integer value) {
            addCriterion("CATEGORY <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(Integer value) {
            addCriterion("CATEGORY >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("CATEGORY >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(Integer value) {
            addCriterion("CATEGORY <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(Integer value) {
            addCriterion("CATEGORY <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<Integer> values) {
            addCriterion("CATEGORY in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<Integer> values) {
            addCriterion("CATEGORY not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(Integer value1, Integer value2) {
            addCriterion("CATEGORY between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(Integer value1, Integer value2) {
            addCriterion("CATEGORY not between", value1, value2, "category");
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

        public Criteria andRespDeptIsNull() {
            addCriterion("RESP_DEPT is null");
            return (Criteria) this;
        }

        public Criteria andRespDeptIsNotNull() {
            addCriterion("RESP_DEPT is not null");
            return (Criteria) this;
        }

        public Criteria andRespDeptEqualTo(String value) {
            addCriterion("RESP_DEPT =", value, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptNotEqualTo(String value) {
            addCriterion("RESP_DEPT <>", value, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptGreaterThan(String value) {
            addCriterion("RESP_DEPT >", value, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptGreaterThanOrEqualTo(String value) {
            addCriterion("RESP_DEPT >=", value, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptLessThan(String value) {
            addCriterion("RESP_DEPT <", value, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptLessThanOrEqualTo(String value) {
            addCriterion("RESP_DEPT <=", value, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptLike(String value) {
            addCriterion("RESP_DEPT like", value, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptNotLike(String value) {
            addCriterion("RESP_DEPT not like", value, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptIn(List<String> values) {
            addCriterion("RESP_DEPT in", values, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptNotIn(List<String> values) {
            addCriterion("RESP_DEPT not in", values, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptBetween(String value1, String value2) {
            addCriterion("RESP_DEPT between", value1, value2, "respDept");
            return (Criteria) this;
        }

        public Criteria andRespDeptNotBetween(String value1, String value2) {
            addCriterion("RESP_DEPT not between", value1, value2, "respDept");
            return (Criteria) this;
        }

        public Criteria andPubUserIsNull() {
            addCriterion("PUB_USER is null");
            return (Criteria) this;
        }

        public Criteria andPubUserIsNotNull() {
            addCriterion("PUB_USER is not null");
            return (Criteria) this;
        }

        public Criteria andPubUserEqualTo(String value) {
            addCriterion("PUB_USER =", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserNotEqualTo(String value) {
            addCriterion("PUB_USER <>", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserGreaterThan(String value) {
            addCriterion("PUB_USER >", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserGreaterThanOrEqualTo(String value) {
            addCriterion("PUB_USER >=", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserLessThan(String value) {
            addCriterion("PUB_USER <", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserLessThanOrEqualTo(String value) {
            addCriterion("PUB_USER <=", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserLike(String value) {
            addCriterion("PUB_USER like", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserNotLike(String value) {
            addCriterion("PUB_USER not like", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserIn(List<String> values) {
            addCriterion("PUB_USER in", values, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserNotIn(List<String> values) {
            addCriterion("PUB_USER not in", values, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserBetween(String value1, String value2) {
            addCriterion("PUB_USER between", value1, value2, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserNotBetween(String value1, String value2) {
            addCriterion("PUB_USER not between", value1, value2, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubDateIsNull() {
            addCriterion("PUB_DATE is null");
            return (Criteria) this;
        }

        public Criteria andPubDateIsNotNull() {
            addCriterion("PUB_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andPubDateEqualTo(String value) {
            addCriterion("PUB_DATE =", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotEqualTo(String value) {
            addCriterion("PUB_DATE <>", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateGreaterThan(String value) {
            addCriterion("PUB_DATE >", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateGreaterThanOrEqualTo(String value) {
            addCriterion("PUB_DATE >=", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateLessThan(String value) {
            addCriterion("PUB_DATE <", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateLessThanOrEqualTo(String value) {
            addCriterion("PUB_DATE <=", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateLike(String value) {
            addCriterion("PUB_DATE like", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotLike(String value) {
            addCriterion("PUB_DATE not like", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateIn(List<String> values) {
            addCriterion("PUB_DATE in", values, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotIn(List<String> values) {
            addCriterion("PUB_DATE not in", values, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateBetween(String value1, String value2) {
            addCriterion("PUB_DATE between", value1, value2, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotBetween(String value1, String value2) {
            addCriterion("PUB_DATE not between", value1, value2, "pubDate");
            return (Criteria) this;
        }

        public Criteria andCompTypeIsNull() {
            addCriterion("COMP_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCompTypeIsNotNull() {
            addCriterion("COMP_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCompTypeEqualTo(Integer value) {
            addCriterion("COMP_TYPE =", value, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeNotEqualTo(Integer value) {
            addCriterion("COMP_TYPE <>", value, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeGreaterThan(Integer value) {
            addCriterion("COMP_TYPE >", value, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("COMP_TYPE >=", value, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeLessThan(Integer value) {
            addCriterion("COMP_TYPE <", value, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeLessThanOrEqualTo(Integer value) {
            addCriterion("COMP_TYPE <=", value, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeIn(List<Integer> values) {
            addCriterion("COMP_TYPE in", values, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeNotIn(List<Integer> values) {
            addCriterion("COMP_TYPE not in", values, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeBetween(Integer value1, Integer value2) {
            addCriterion("COMP_TYPE between", value1, value2, "compType");
            return (Criteria) this;
        }

        public Criteria andCompTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("COMP_TYPE not between", value1, value2, "compType");
            return (Criteria) this;
        }

        public Criteria andCompDateIsNull() {
            addCriterion("COMP_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCompDateIsNotNull() {
            addCriterion("COMP_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCompDateEqualTo(String value) {
            addCriterion("COMP_DATE =", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateNotEqualTo(String value) {
            addCriterion("COMP_DATE <>", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateGreaterThan(String value) {
            addCriterion("COMP_DATE >", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateGreaterThanOrEqualTo(String value) {
            addCriterion("COMP_DATE >=", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateLessThan(String value) {
            addCriterion("COMP_DATE <", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateLessThanOrEqualTo(String value) {
            addCriterion("COMP_DATE <=", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateLike(String value) {
            addCriterion("COMP_DATE like", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateNotLike(String value) {
            addCriterion("COMP_DATE not like", value, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateIn(List<String> values) {
            addCriterion("COMP_DATE in", values, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateNotIn(List<String> values) {
            addCriterion("COMP_DATE not in", values, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateBetween(String value1, String value2) {
            addCriterion("COMP_DATE between", value1, value2, "compDate");
            return (Criteria) this;
        }

        public Criteria andCompDateNotBetween(String value1, String value2) {
            addCriterion("COMP_DATE not between", value1, value2, "compDate");
            return (Criteria) this;
        }

        public Criteria andTopFlagIsNull() {
            addCriterion("TOP_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andTopFlagIsNotNull() {
            addCriterion("TOP_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andTopFlagEqualTo(Integer value) {
            addCriterion("TOP_FLAG =", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagNotEqualTo(Integer value) {
            addCriterion("TOP_FLAG <>", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagGreaterThan(Integer value) {
            addCriterion("TOP_FLAG >", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("TOP_FLAG >=", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagLessThan(Integer value) {
            addCriterion("TOP_FLAG <", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagLessThanOrEqualTo(Integer value) {
            addCriterion("TOP_FLAG <=", value, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagIn(List<Integer> values) {
            addCriterion("TOP_FLAG in", values, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagNotIn(List<Integer> values) {
            addCriterion("TOP_FLAG not in", values, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagBetween(Integer value1, Integer value2) {
            addCriterion("TOP_FLAG between", value1, value2, "topFlag");
            return (Criteria) this;
        }

        public Criteria andTopFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("TOP_FLAG not between", value1, value2, "topFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagIsNull() {
            addCriterion("ELITE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andEliteFlagIsNotNull() {
            addCriterion("ELITE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andEliteFlagEqualTo(Integer value) {
            addCriterion("ELITE_FLAG =", value, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagNotEqualTo(Integer value) {
            addCriterion("ELITE_FLAG <>", value, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagGreaterThan(Integer value) {
            addCriterion("ELITE_FLAG >", value, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("ELITE_FLAG >=", value, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagLessThan(Integer value) {
            addCriterion("ELITE_FLAG <", value, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagLessThanOrEqualTo(Integer value) {
            addCriterion("ELITE_FLAG <=", value, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagIn(List<Integer> values) {
            addCriterion("ELITE_FLAG in", values, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagNotIn(List<Integer> values) {
            addCriterion("ELITE_FLAG not in", values, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagBetween(Integer value1, Integer value2) {
            addCriterion("ELITE_FLAG between", value1, value2, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andEliteFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("ELITE_FLAG not between", value1, value2, "eliteFlag");
            return (Criteria) this;
        }

        public Criteria andOnStageIsNull() {
            addCriterion("ON_STAGE is null");
            return (Criteria) this;
        }

        public Criteria andOnStageIsNotNull() {
            addCriterion("ON_STAGE is not null");
            return (Criteria) this;
        }

        public Criteria andOnStageEqualTo(Integer value) {
            addCriterion("ON_STAGE =", value, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageNotEqualTo(Integer value) {
            addCriterion("ON_STAGE <>", value, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageGreaterThan(Integer value) {
            addCriterion("ON_STAGE >", value, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageGreaterThanOrEqualTo(Integer value) {
            addCriterion("ON_STAGE >=", value, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageLessThan(Integer value) {
            addCriterion("ON_STAGE <", value, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageLessThanOrEqualTo(Integer value) {
            addCriterion("ON_STAGE <=", value, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageIn(List<Integer> values) {
            addCriterion("ON_STAGE in", values, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageNotIn(List<Integer> values) {
            addCriterion("ON_STAGE not in", values, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageBetween(Integer value1, Integer value2) {
            addCriterion("ON_STAGE between", value1, value2, "onStage");
            return (Criteria) this;
        }

        public Criteria andOnStageNotBetween(Integer value1, Integer value2) {
            addCriterion("ON_STAGE not between", value1, value2, "onStage");
            return (Criteria) this;
        }

        public Criteria andViewNumIsNull() {
            addCriterion("VIEW_NUM is null");
            return (Criteria) this;
        }

        public Criteria andViewNumIsNotNull() {
            addCriterion("VIEW_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andViewNumEqualTo(Integer value) {
            addCriterion("VIEW_NUM =", value, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumNotEqualTo(Integer value) {
            addCriterion("VIEW_NUM <>", value, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumGreaterThan(Integer value) {
            addCriterion("VIEW_NUM >", value, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("VIEW_NUM >=", value, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumLessThan(Integer value) {
            addCriterion("VIEW_NUM <", value, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumLessThanOrEqualTo(Integer value) {
            addCriterion("VIEW_NUM <=", value, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumIn(List<Integer> values) {
            addCriterion("VIEW_NUM in", values, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumNotIn(List<Integer> values) {
            addCriterion("VIEW_NUM not in", values, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumBetween(Integer value1, Integer value2) {
            addCriterion("VIEW_NUM between", value1, value2, "viewNum");
            return (Criteria) this;
        }

        public Criteria andViewNumNotBetween(Integer value1, Integer value2) {
            addCriterion("VIEW_NUM not between", value1, value2, "viewNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumIsNull() {
            addCriterion("COLLECT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andCollectNumIsNotNull() {
            addCriterion("COLLECT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andCollectNumEqualTo(Integer value) {
            addCriterion("COLLECT_NUM =", value, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumNotEqualTo(Integer value) {
            addCriterion("COLLECT_NUM <>", value, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumGreaterThan(Integer value) {
            addCriterion("COLLECT_NUM >", value, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("COLLECT_NUM >=", value, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumLessThan(Integer value) {
            addCriterion("COLLECT_NUM <", value, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumLessThanOrEqualTo(Integer value) {
            addCriterion("COLLECT_NUM <=", value, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumIn(List<Integer> values) {
            addCriterion("COLLECT_NUM in", values, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumNotIn(List<Integer> values) {
            addCriterion("COLLECT_NUM not in", values, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumBetween(Integer value1, Integer value2) {
            addCriterion("COLLECT_NUM between", value1, value2, "collectNum");
            return (Criteria) this;
        }

        public Criteria andCollectNumNotBetween(Integer value1, Integer value2) {
            addCriterion("COLLECT_NUM not between", value1, value2, "collectNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumIsNull() {
            addCriterion("REPLY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andReplyNumIsNotNull() {
            addCriterion("REPLY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andReplyNumEqualTo(Integer value) {
            addCriterion("REPLY_NUM =", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotEqualTo(Integer value) {
            addCriterion("REPLY_NUM <>", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumGreaterThan(Integer value) {
            addCriterion("REPLY_NUM >", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("REPLY_NUM >=", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumLessThan(Integer value) {
            addCriterion("REPLY_NUM <", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumLessThanOrEqualTo(Integer value) {
            addCriterion("REPLY_NUM <=", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumIn(List<Integer> values) {
            addCriterion("REPLY_NUM in", values, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotIn(List<Integer> values) {
            addCriterion("REPLY_NUM not in", values, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumBetween(Integer value1, Integer value2) {
            addCriterion("REPLY_NUM between", value1, value2, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("REPLY_NUM not between", value1, value2, "replyNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumIsNull() {
            addCriterion("LIKE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andLikeNumIsNotNull() {
            addCriterion("LIKE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andLikeNumEqualTo(Integer value) {
            addCriterion("LIKE_NUM =", value, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumNotEqualTo(Integer value) {
            addCriterion("LIKE_NUM <>", value, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumGreaterThan(Integer value) {
            addCriterion("LIKE_NUM >", value, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("LIKE_NUM >=", value, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumLessThan(Integer value) {
            addCriterion("LIKE_NUM <", value, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumLessThanOrEqualTo(Integer value) {
            addCriterion("LIKE_NUM <=", value, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumIn(List<Integer> values) {
            addCriterion("LIKE_NUM in", values, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumNotIn(List<Integer> values) {
            addCriterion("LIKE_NUM not in", values, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumBetween(Integer value1, Integer value2) {
            addCriterion("LIKE_NUM between", value1, value2, "likeNum");
            return (Criteria) this;
        }

        public Criteria andLikeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("LIKE_NUM not between", value1, value2, "likeNum");
            return (Criteria) this;
        }

        public Criteria andCoverIsNull() {
            addCriterion("COVER is null");
            return (Criteria) this;
        }

        public Criteria andCoverIsNotNull() {
            addCriterion("COVER is not null");
            return (Criteria) this;
        }

        public Criteria andCoverEqualTo(String value) {
            addCriterion("COVER =", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotEqualTo(String value) {
            addCriterion("COVER <>", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverGreaterThan(String value) {
            addCriterion("COVER >", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverGreaterThanOrEqualTo(String value) {
            addCriterion("COVER >=", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLessThan(String value) {
            addCriterion("COVER <", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLessThanOrEqualTo(String value) {
            addCriterion("COVER <=", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLike(String value) {
            addCriterion("COVER like", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotLike(String value) {
            addCriterion("COVER not like", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverIn(List<String> values) {
            addCriterion("COVER in", values, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotIn(List<String> values) {
            addCriterion("COVER not in", values, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverBetween(String value1, String value2) {
            addCriterion("COVER between", value1, value2, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotBetween(String value1, String value2) {
            addCriterion("COVER not between", value1, value2, "cover");
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

        public Criteria andFilesEqualTo(String value) {
            addCriterion("FILES =", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotEqualTo(String value) {
            addCriterion("FILES <>", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesGreaterThan(String value) {
            addCriterion("FILES >", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesGreaterThanOrEqualTo(String value) {
            addCriterion("FILES >=", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesLessThan(String value) {
            addCriterion("FILES <", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesLessThanOrEqualTo(String value) {
            addCriterion("FILES <=", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesLike(String value) {
            addCriterion("FILES like", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotLike(String value) {
            addCriterion("FILES not like", value, "files");
            return (Criteria) this;
        }

        public Criteria andFilesIn(List<String> values) {
            addCriterion("FILES in", values, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotIn(List<String> values) {
            addCriterion("FILES not in", values, "files");
            return (Criteria) this;
        }

        public Criteria andFilesBetween(String value1, String value2) {
            addCriterion("FILES between", value1, value2, "files");
            return (Criteria) this;
        }

        public Criteria andFilesNotBetween(String value1, String value2) {
            addCriterion("FILES not between", value1, value2, "files");
            return (Criteria) this;
        }

        public Criteria andCompGradeIsNull() {
            addCriterion("COMP_GRADE is null");
            return (Criteria) this;
        }

        public Criteria andCompGradeIsNotNull() {
            addCriterion("COMP_GRADE is not null");
            return (Criteria) this;
        }

        public Criteria andCompGradeEqualTo(Integer value) {
            addCriterion("COMP_GRADE =", value, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeNotEqualTo(Integer value) {
            addCriterion("COMP_GRADE <>", value, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeGreaterThan(Integer value) {
            addCriterion("COMP_GRADE >", value, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeGreaterThanOrEqualTo(Integer value) {
            addCriterion("COMP_GRADE >=", value, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeLessThan(Integer value) {
            addCriterion("COMP_GRADE <", value, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeLessThanOrEqualTo(Integer value) {
            addCriterion("COMP_GRADE <=", value, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeIn(List<Integer> values) {
            addCriterion("COMP_GRADE in", values, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeNotIn(List<Integer> values) {
            addCriterion("COMP_GRADE not in", values, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeBetween(Integer value1, Integer value2) {
            addCriterion("COMP_GRADE between", value1, value2, "compGrade");
            return (Criteria) this;
        }

        public Criteria andCompGradeNotBetween(Integer value1, Integer value2) {
            addCriterion("COMP_GRADE not between", value1, value2, "compGrade");
            return (Criteria) this;
        }

        public Criteria andRespStatusIsNull() {
            addCriterion("RESP_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andRespStatusIsNotNull() {
            addCriterion("RESP_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRespStatusEqualTo(Integer value) {
            addCriterion("RESP_STATUS =", value, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusNotEqualTo(Integer value) {
            addCriterion("RESP_STATUS <>", value, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusGreaterThan(Integer value) {
            addCriterion("RESP_STATUS >", value, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("RESP_STATUS >=", value, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusLessThan(Integer value) {
            addCriterion("RESP_STATUS <", value, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusLessThanOrEqualTo(Integer value) {
            addCriterion("RESP_STATUS <=", value, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusIn(List<Integer> values) {
            addCriterion("RESP_STATUS in", values, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusNotIn(List<Integer> values) {
            addCriterion("RESP_STATUS not in", values, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusBetween(Integer value1, Integer value2) {
            addCriterion("RESP_STATUS between", value1, value2, "respStatus");
            return (Criteria) this;
        }

        public Criteria andRespStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("RESP_STATUS not between", value1, value2, "respStatus");
            return (Criteria) this;
        }

        public Criteria andAnonFlagIsNull() {
            addCriterion("ANON_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andAnonFlagIsNotNull() {
            addCriterion("ANON_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andAnonFlagEqualTo(Integer value) {
            addCriterion("ANON_FLAG =", value, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagNotEqualTo(Integer value) {
            addCriterion("ANON_FLAG <>", value, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagGreaterThan(Integer value) {
            addCriterion("ANON_FLAG >", value, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("ANON_FLAG >=", value, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagLessThan(Integer value) {
            addCriterion("ANON_FLAG <", value, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagLessThanOrEqualTo(Integer value) {
            addCriterion("ANON_FLAG <=", value, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagIn(List<Integer> values) {
            addCriterion("ANON_FLAG in", values, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagNotIn(List<Integer> values) {
            addCriterion("ANON_FLAG not in", values, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagBetween(Integer value1, Integer value2) {
            addCriterion("ANON_FLAG between", value1, value2, "anonFlag");
            return (Criteria) this;
        }

        public Criteria andAnonFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("ANON_FLAG not between", value1, value2, "anonFlag");
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