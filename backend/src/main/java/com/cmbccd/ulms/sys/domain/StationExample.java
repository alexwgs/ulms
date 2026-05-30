package com.cmbccd.ulms.sys.domain;

import java.util.ArrayList;
import java.util.List;

public class StationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StationExample() {
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

        public Criteria andFloorNumIsNull() {
            addCriterion("FLOOR_NUM is null");
            return (Criteria) this;
        }

        public Criteria andFloorNumIsNotNull() {
            addCriterion("FLOOR_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andFloorNumEqualTo(String value) {
            addCriterion("FLOOR_NUM =", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumNotEqualTo(String value) {
            addCriterion("FLOOR_NUM <>", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumGreaterThan(String value) {
            addCriterion("FLOOR_NUM >", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumGreaterThanOrEqualTo(String value) {
            addCriterion("FLOOR_NUM >=", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumLessThan(String value) {
            addCriterion("FLOOR_NUM <", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumLessThanOrEqualTo(String value) {
            addCriterion("FLOOR_NUM <=", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumLike(String value) {
            addCriterion("FLOOR_NUM like", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumNotLike(String value) {
            addCriterion("FLOOR_NUM not like", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumIn(List<String> values) {
            addCriterion("FLOOR_NUM in", values, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumNotIn(List<String> values) {
            addCriterion("FLOOR_NUM not in", values, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumBetween(String value1, String value2) {
            addCriterion("FLOOR_NUM between", value1, value2, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumNotBetween(String value1, String value2) {
            addCriterion("FLOOR_NUM not between", value1, value2, "floorNum");
            return (Criteria) this;
        }

        public Criteria andPoiIsNull() {
            addCriterion("POI is null");
            return (Criteria) this;
        }

        public Criteria andPoiIsNotNull() {
            addCriterion("POI is not null");
            return (Criteria) this;
        }

        public Criteria andPoiEqualTo(String value) {
            addCriterion("POI =", value, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiNotEqualTo(String value) {
            addCriterion("POI <>", value, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiGreaterThan(String value) {
            addCriterion("POI >", value, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiGreaterThanOrEqualTo(String value) {
            addCriterion("POI >=", value, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiLessThan(String value) {
            addCriterion("POI <", value, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiLessThanOrEqualTo(String value) {
            addCriterion("POI <=", value, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiLike(String value) {
            addCriterion("POI like", value, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiNotLike(String value) {
            addCriterion("POI not like", value, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiIn(List<String> values) {
            addCriterion("POI in", values, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiNotIn(List<String> values) {
            addCriterion("POI not in", values, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiBetween(String value1, String value2) {
            addCriterion("POI between", value1, value2, "poi");
            return (Criteria) this;
        }

        public Criteria andPoiNotBetween(String value1, String value2) {
            addCriterion("POI not between", value1, value2, "poi");
            return (Criteria) this;
        }

        public Criteria andExtnNumIsNull() {
            addCriterion("EXTN_NUM is null");
            return (Criteria) this;
        }

        public Criteria andExtnNumIsNotNull() {
            addCriterion("EXTN_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andExtnNumEqualTo(String value) {
            addCriterion("EXTN_NUM =", value, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumNotEqualTo(String value) {
            addCriterion("EXTN_NUM <>", value, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumGreaterThan(String value) {
            addCriterion("EXTN_NUM >", value, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumGreaterThanOrEqualTo(String value) {
            addCriterion("EXTN_NUM >=", value, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumLessThan(String value) {
            addCriterion("EXTN_NUM <", value, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumLessThanOrEqualTo(String value) {
            addCriterion("EXTN_NUM <=", value, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumLike(String value) {
            addCriterion("EXTN_NUM like", value, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumNotLike(String value) {
            addCriterion("EXTN_NUM not like", value, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumIn(List<String> values) {
            addCriterion("EXTN_NUM in", values, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumNotIn(List<String> values) {
            addCriterion("EXTN_NUM not in", values, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumBetween(String value1, String value2) {
            addCriterion("EXTN_NUM between", value1, value2, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnNumNotBetween(String value1, String value2) {
            addCriterion("EXTN_NUM not between", value1, value2, "extnNum");
            return (Criteria) this;
        }

        public Criteria andExtnIpIsNull() {
            addCriterion("EXTN_IP is null");
            return (Criteria) this;
        }

        public Criteria andExtnIpIsNotNull() {
            addCriterion("EXTN_IP is not null");
            return (Criteria) this;
        }

        public Criteria andExtnIpEqualTo(String value) {
            addCriterion("EXTN_IP =", value, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpNotEqualTo(String value) {
            addCriterion("EXTN_IP <>", value, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpGreaterThan(String value) {
            addCriterion("EXTN_IP >", value, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpGreaterThanOrEqualTo(String value) {
            addCriterion("EXTN_IP >=", value, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpLessThan(String value) {
            addCriterion("EXTN_IP <", value, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpLessThanOrEqualTo(String value) {
            addCriterion("EXTN_IP <=", value, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpLike(String value) {
            addCriterion("EXTN_IP like", value, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpNotLike(String value) {
            addCriterion("EXTN_IP not like", value, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpIn(List<String> values) {
            addCriterion("EXTN_IP in", values, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpNotIn(List<String> values) {
            addCriterion("EXTN_IP not in", values, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpBetween(String value1, String value2) {
            addCriterion("EXTN_IP between", value1, value2, "extnIp");
            return (Criteria) this;
        }

        public Criteria andExtnIpNotBetween(String value1, String value2) {
            addCriterion("EXTN_IP not between", value1, value2, "extnIp");
            return (Criteria) this;
        }

        public Criteria andPcIpIsNull() {
            addCriterion("PC_IP is null");
            return (Criteria) this;
        }

        public Criteria andPcIpIsNotNull() {
            addCriterion("PC_IP is not null");
            return (Criteria) this;
        }

        public Criteria andPcIpEqualTo(String value) {
            addCriterion("PC_IP =", value, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpNotEqualTo(String value) {
            addCriterion("PC_IP <>", value, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpGreaterThan(String value) {
            addCriterion("PC_IP >", value, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpGreaterThanOrEqualTo(String value) {
            addCriterion("PC_IP >=", value, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpLessThan(String value) {
            addCriterion("PC_IP <", value, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpLessThanOrEqualTo(String value) {
            addCriterion("PC_IP <=", value, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpLike(String value) {
            addCriterion("PC_IP like", value, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpNotLike(String value) {
            addCriterion("PC_IP not like", value, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpIn(List<String> values) {
            addCriterion("PC_IP in", values, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpNotIn(List<String> values) {
            addCriterion("PC_IP not in", values, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpBetween(String value1, String value2) {
            addCriterion("PC_IP between", value1, value2, "pcIp");
            return (Criteria) this;
        }

        public Criteria andPcIpNotBetween(String value1, String value2) {
            addCriterion("PC_IP not between", value1, value2, "pcIp");
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

        public Criteria andPloStatusIsNull() {
            addCriterion("PLO_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPloStatusIsNotNull() {
            addCriterion("PLO_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPloStatusEqualTo(String value) {
            addCriterion("PLO_STATUS =", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusNotEqualTo(String value) {
            addCriterion("PLO_STATUS <>", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusGreaterThan(String value) {
            addCriterion("PLO_STATUS >", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusGreaterThanOrEqualTo(String value) {
            addCriterion("PLO_STATUS >=", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusLessThan(String value) {
            addCriterion("PLO_STATUS <", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusLessThanOrEqualTo(String value) {
            addCriterion("PLO_STATUS <=", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusLike(String value) {
            addCriterion("PLO_STATUS like", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusNotLike(String value) {
            addCriterion("PLO_STATUS not like", value, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusIn(List<String> values) {
            addCriterion("PLO_STATUS in", values, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusNotIn(List<String> values) {
            addCriterion("PLO_STATUS not in", values, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusBetween(String value1, String value2) {
            addCriterion("PLO_STATUS between", value1, value2, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPloStatusNotBetween(String value1, String value2) {
            addCriterion("PLO_STATUS not between", value1, value2, "ploStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusIsNull() {
            addCriterion("POI_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPoiStatusIsNotNull() {
            addCriterion("POI_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPoiStatusEqualTo(String value) {
            addCriterion("POI_STATUS =", value, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusNotEqualTo(String value) {
            addCriterion("POI_STATUS <>", value, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusGreaterThan(String value) {
            addCriterion("POI_STATUS >", value, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusGreaterThanOrEqualTo(String value) {
            addCriterion("POI_STATUS >=", value, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusLessThan(String value) {
            addCriterion("POI_STATUS <", value, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusLessThanOrEqualTo(String value) {
            addCriterion("POI_STATUS <=", value, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusLike(String value) {
            addCriterion("POI_STATUS like", value, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusNotLike(String value) {
            addCriterion("POI_STATUS not like", value, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusIn(List<String> values) {
            addCriterion("POI_STATUS in", values, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusNotIn(List<String> values) {
            addCriterion("POI_STATUS not in", values, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusBetween(String value1, String value2) {
            addCriterion("POI_STATUS between", value1, value2, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andPoiStatusNotBetween(String value1, String value2) {
            addCriterion("POI_STATUS not between", value1, value2, "poiStatus");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("MEMO is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("MEMO =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("MEMO <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("MEMO >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("MEMO >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("MEMO <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("MEMO <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("MEMO like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("MEMO not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("MEMO in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("MEMO not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("MEMO between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("MEMO not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("UPDATE_TIME like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("UPDATE_TIME not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
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