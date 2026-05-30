package com.cmbccd.ulms.db.domain;

import java.util.ArrayList;
import java.util.List;

public class ColumnExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ColumnExample() {
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

        public Criteria andTidIsNull() {
            addCriterion("TID is null");
            return (Criteria) this;
        }

        public Criteria andTidIsNotNull() {
            addCriterion("TID is not null");
            return (Criteria) this;
        }

        public Criteria andTidEqualTo(Integer value) {
            addCriterion("TID =", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotEqualTo(Integer value) {
            addCriterion("TID <>", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidGreaterThan(Integer value) {
            addCriterion("TID >", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidGreaterThanOrEqualTo(Integer value) {
            addCriterion("TID >=", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLessThan(Integer value) {
            addCriterion("TID <", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLessThanOrEqualTo(Integer value) {
            addCriterion("TID <=", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidIn(List<Integer> values) {
            addCriterion("TID in", values, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotIn(List<Integer> values) {
            addCriterion("TID not in", values, "tid");
            return (Criteria) this;
        }

        public Criteria andTidBetween(Integer value1, Integer value2) {
            addCriterion("TID between", value1, value2, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotBetween(Integer value1, Integer value2) {
            addCriterion("TID not between", value1, value2, "tid");
            return (Criteria) this;
        }

        public Criteria andColIsNull() {
            addCriterion("COL is null");
            return (Criteria) this;
        }

        public Criteria andColIsNotNull() {
            addCriterion("COL is not null");
            return (Criteria) this;
        }

        public Criteria andColEqualTo(String value) {
            addCriterion("COL =", value, "col");
            return (Criteria) this;
        }

        public Criteria andColNotEqualTo(String value) {
            addCriterion("COL <>", value, "col");
            return (Criteria) this;
        }

        public Criteria andColGreaterThan(String value) {
            addCriterion("COL >", value, "col");
            return (Criteria) this;
        }

        public Criteria andColGreaterThanOrEqualTo(String value) {
            addCriterion("COL >=", value, "col");
            return (Criteria) this;
        }

        public Criteria andColLessThan(String value) {
            addCriterion("COL <", value, "col");
            return (Criteria) this;
        }

        public Criteria andColLessThanOrEqualTo(String value) {
            addCriterion("COL <=", value, "col");
            return (Criteria) this;
        }

        public Criteria andColLike(String value) {
            addCriterion("COL like", value, "col");
            return (Criteria) this;
        }

        public Criteria andColNotLike(String value) {
            addCriterion("COL not like", value, "col");
            return (Criteria) this;
        }

        public Criteria andColIn(List<String> values) {
            addCriterion("COL in", values, "col");
            return (Criteria) this;
        }

        public Criteria andColNotIn(List<String> values) {
            addCriterion("COL not in", values, "col");
            return (Criteria) this;
        }

        public Criteria andColBetween(String value1, String value2) {
            addCriterion("COL between", value1, value2, "col");
            return (Criteria) this;
        }

        public Criteria andColNotBetween(String value1, String value2) {
            addCriterion("COL not between", value1, value2, "col");
            return (Criteria) this;
        }

        public Criteria andColNameIsNull() {
            addCriterion("COL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andColNameIsNotNull() {
            addCriterion("COL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andColNameEqualTo(String value) {
            addCriterion("COL_NAME =", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameNotEqualTo(String value) {
            addCriterion("COL_NAME <>", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameGreaterThan(String value) {
            addCriterion("COL_NAME >", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameGreaterThanOrEqualTo(String value) {
            addCriterion("COL_NAME >=", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameLessThan(String value) {
            addCriterion("COL_NAME <", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameLessThanOrEqualTo(String value) {
            addCriterion("COL_NAME <=", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameLike(String value) {
            addCriterion("COL_NAME like", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameNotLike(String value) {
            addCriterion("COL_NAME not like", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameIn(List<String> values) {
            addCriterion("COL_NAME in", values, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameNotIn(List<String> values) {
            addCriterion("COL_NAME not in", values, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameBetween(String value1, String value2) {
            addCriterion("COL_NAME between", value1, value2, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameNotBetween(String value1, String value2) {
            addCriterion("COL_NAME not between", value1, value2, "colName");
            return (Criteria) this;
        }

        public Criteria andFormatTypeIsNull() {
            addCriterion("FORMAT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFormatTypeIsNotNull() {
            addCriterion("FORMAT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFormatTypeEqualTo(String value) {
            addCriterion("FORMAT_TYPE =", value, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeNotEqualTo(String value) {
            addCriterion("FORMAT_TYPE <>", value, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeGreaterThan(String value) {
            addCriterion("FORMAT_TYPE >", value, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeGreaterThanOrEqualTo(String value) {
            addCriterion("FORMAT_TYPE >=", value, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeLessThan(String value) {
            addCriterion("FORMAT_TYPE <", value, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeLessThanOrEqualTo(String value) {
            addCriterion("FORMAT_TYPE <=", value, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeLike(String value) {
            addCriterion("FORMAT_TYPE like", value, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeNotLike(String value) {
            addCriterion("FORMAT_TYPE not like", value, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeIn(List<String> values) {
            addCriterion("FORMAT_TYPE in", values, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeNotIn(List<String> values) {
            addCriterion("FORMAT_TYPE not in", values, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeBetween(String value1, String value2) {
            addCriterion("FORMAT_TYPE between", value1, value2, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatTypeNotBetween(String value1, String value2) {
            addCriterion("FORMAT_TYPE not between", value1, value2, "formatType");
            return (Criteria) this;
        }

        public Criteria andFormatSizeIsNull() {
            addCriterion("FORMAT_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andFormatSizeIsNotNull() {
            addCriterion("FORMAT_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andFormatSizeEqualTo(String value) {
            addCriterion("FORMAT_SIZE =", value, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeNotEqualTo(String value) {
            addCriterion("FORMAT_SIZE <>", value, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeGreaterThan(String value) {
            addCriterion("FORMAT_SIZE >", value, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeGreaterThanOrEqualTo(String value) {
            addCriterion("FORMAT_SIZE >=", value, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeLessThan(String value) {
            addCriterion("FORMAT_SIZE <", value, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeLessThanOrEqualTo(String value) {
            addCriterion("FORMAT_SIZE <=", value, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeLike(String value) {
            addCriterion("FORMAT_SIZE like", value, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeNotLike(String value) {
            addCriterion("FORMAT_SIZE not like", value, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeIn(List<String> values) {
            addCriterion("FORMAT_SIZE in", values, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeNotIn(List<String> values) {
            addCriterion("FORMAT_SIZE not in", values, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeBetween(String value1, String value2) {
            addCriterion("FORMAT_SIZE between", value1, value2, "formatSize");
            return (Criteria) this;
        }

        public Criteria andFormatSizeNotBetween(String value1, String value2) {
            addCriterion("FORMAT_SIZE not between", value1, value2, "formatSize");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyIsNull() {
            addCriterion("PRIMARY_KEY is null");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyIsNotNull() {
            addCriterion("PRIMARY_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyEqualTo(String value) {
            addCriterion("PRIMARY_KEY =", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyNotEqualTo(String value) {
            addCriterion("PRIMARY_KEY <>", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyGreaterThan(String value) {
            addCriterion("PRIMARY_KEY >", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyGreaterThanOrEqualTo(String value) {
            addCriterion("PRIMARY_KEY >=", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyLessThan(String value) {
            addCriterion("PRIMARY_KEY <", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyLessThanOrEqualTo(String value) {
            addCriterion("PRIMARY_KEY <=", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyLike(String value) {
            addCriterion("PRIMARY_KEY like", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyNotLike(String value) {
            addCriterion("PRIMARY_KEY not like", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyIn(List<String> values) {
            addCriterion("PRIMARY_KEY in", values, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyNotIn(List<String> values) {
            addCriterion("PRIMARY_KEY not in", values, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyBetween(String value1, String value2) {
            addCriterion("PRIMARY_KEY between", value1, value2, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyNotBetween(String value1, String value2) {
            addCriterion("PRIMARY_KEY not between", value1, value2, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andNotNullIsNull() {
            addCriterion("NOT_NULL is null");
            return (Criteria) this;
        }

        public Criteria andNotNullIsNotNull() {
            addCriterion("NOT_NULL is not null");
            return (Criteria) this;
        }

        public Criteria andNotNullEqualTo(String value) {
            addCriterion("NOT_NULL =", value, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullNotEqualTo(String value) {
            addCriterion("NOT_NULL <>", value, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullGreaterThan(String value) {
            addCriterion("NOT_NULL >", value, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullGreaterThanOrEqualTo(String value) {
            addCriterion("NOT_NULL >=", value, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullLessThan(String value) {
            addCriterion("NOT_NULL <", value, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullLessThanOrEqualTo(String value) {
            addCriterion("NOT_NULL <=", value, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullLike(String value) {
            addCriterion("NOT_NULL like", value, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullNotLike(String value) {
            addCriterion("NOT_NULL not like", value, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullIn(List<String> values) {
            addCriterion("NOT_NULL in", values, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullNotIn(List<String> values) {
            addCriterion("NOT_NULL not in", values, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullBetween(String value1, String value2) {
            addCriterion("NOT_NULL between", value1, value2, "notNull");
            return (Criteria) this;
        }

        public Criteria andNotNullNotBetween(String value1, String value2) {
            addCriterion("NOT_NULL not between", value1, value2, "notNull");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNull() {
            addCriterion("DESCRIBE is null");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNotNull() {
            addCriterion("DESCRIBE is not null");
            return (Criteria) this;
        }

        public Criteria andDescribeEqualTo(String value) {
            addCriterion("DESCRIBE =", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotEqualTo(String value) {
            addCriterion("DESCRIBE <>", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThan(String value) {
            addCriterion("DESCRIBE >", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIBE >=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThan(String value) {
            addCriterion("DESCRIBE <", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThanOrEqualTo(String value) {
            addCriterion("DESCRIBE <=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLike(String value) {
            addCriterion("DESCRIBE like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotLike(String value) {
            addCriterion("DESCRIBE not like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeIn(List<String> values) {
            addCriterion("DESCRIBE in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotIn(List<String> values) {
            addCriterion("DESCRIBE not in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeBetween(String value1, String value2) {
            addCriterion("DESCRIBE between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotBetween(String value1, String value2) {
            addCriterion("DESCRIBE not between", value1, value2, "describe");
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

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("SORT is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("SORT is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("SORT =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("SORT <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("SORT >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("SORT >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("SORT <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("SORT <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("SORT in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("SORT not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("SORT between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("SORT not between", value1, value2, "sort");
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