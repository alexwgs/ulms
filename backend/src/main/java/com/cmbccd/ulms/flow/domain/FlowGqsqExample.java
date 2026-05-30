package com.cmbccd.ulms.flow.domain;

import java.util.ArrayList;
import java.util.List;

public class FlowGqsqExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowGqsqExample() {
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

        public Criteria andDataDateIsNull() {
            addCriterion("DATA_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDataDateIsNotNull() {
            addCriterion("DATA_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDataDateEqualTo(String value) {
            addCriterion("DATA_DATE =", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotEqualTo(String value) {
            addCriterion("DATA_DATE <>", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateGreaterThan(String value) {
            addCriterion("DATA_DATE >", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_DATE >=", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLessThan(String value) {
            addCriterion("DATA_DATE <", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLessThanOrEqualTo(String value) {
            addCriterion("DATA_DATE <=", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLike(String value) {
            addCriterion("DATA_DATE like", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotLike(String value) {
            addCriterion("DATA_DATE not like", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateIn(List<String> values) {
            addCriterion("DATA_DATE in", values, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotIn(List<String> values) {
            addCriterion("DATA_DATE not in", values, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateBetween(String value1, String value2) {
            addCriterion("DATA_DATE between", value1, value2, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotBetween(String value1, String value2) {
            addCriterion("DATA_DATE not between", value1, value2, "dataDate");
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

        public Criteria andExcTimeIsNull() {
            addCriterion("EXC_TIME is null");
            return (Criteria) this;
        }

        public Criteria andExcTimeIsNotNull() {
            addCriterion("EXC_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andExcTimeEqualTo(String value) {
            addCriterion("EXC_TIME =", value, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeNotEqualTo(String value) {
            addCriterion("EXC_TIME <>", value, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeGreaterThan(String value) {
            addCriterion("EXC_TIME >", value, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeGreaterThanOrEqualTo(String value) {
            addCriterion("EXC_TIME >=", value, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeLessThan(String value) {
            addCriterion("EXC_TIME <", value, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeLessThanOrEqualTo(String value) {
            addCriterion("EXC_TIME <=", value, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeLike(String value) {
            addCriterion("EXC_TIME like", value, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeNotLike(String value) {
            addCriterion("EXC_TIME not like", value, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeIn(List<String> values) {
            addCriterion("EXC_TIME in", values, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeNotIn(List<String> values) {
            addCriterion("EXC_TIME not in", values, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeBetween(String value1, String value2) {
            addCriterion("EXC_TIME between", value1, value2, "excTime");
            return (Criteria) this;
        }

        public Criteria andExcTimeNotBetween(String value1, String value2) {
            addCriterion("EXC_TIME not between", value1, value2, "excTime");
            return (Criteria) this;
        }

        public Criteria andAuxReasonIsNull() {
            addCriterion("AUX_REASON is null");
            return (Criteria) this;
        }

        public Criteria andAuxReasonIsNotNull() {
            addCriterion("AUX_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andAuxReasonEqualTo(String value) {
            addCriterion("AUX_REASON =", value, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonNotEqualTo(String value) {
            addCriterion("AUX_REASON <>", value, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonGreaterThan(String value) {
            addCriterion("AUX_REASON >", value, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonGreaterThanOrEqualTo(String value) {
            addCriterion("AUX_REASON >=", value, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonLessThan(String value) {
            addCriterion("AUX_REASON <", value, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonLessThanOrEqualTo(String value) {
            addCriterion("AUX_REASON <=", value, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonLike(String value) {
            addCriterion("AUX_REASON like", value, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonNotLike(String value) {
            addCriterion("AUX_REASON not like", value, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonIn(List<String> values) {
            addCriterion("AUX_REASON in", values, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonNotIn(List<String> values) {
            addCriterion("AUX_REASON not in", values, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonBetween(String value1, String value2) {
            addCriterion("AUX_REASON between", value1, value2, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAuxReasonNotBetween(String value1, String value2) {
            addCriterion("AUX_REASON not between", value1, value2, "auxReason");
            return (Criteria) this;
        }

        public Criteria andAvgTimeIsNull() {
            addCriterion("AVG_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAvgTimeIsNotNull() {
            addCriterion("AVG_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAvgTimeEqualTo(String value) {
            addCriterion("AVG_TIME =", value, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeNotEqualTo(String value) {
            addCriterion("AVG_TIME <>", value, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeGreaterThan(String value) {
            addCriterion("AVG_TIME >", value, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeGreaterThanOrEqualTo(String value) {
            addCriterion("AVG_TIME >=", value, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeLessThan(String value) {
            addCriterion("AVG_TIME <", value, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeLessThanOrEqualTo(String value) {
            addCriterion("AVG_TIME <=", value, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeLike(String value) {
            addCriterion("AVG_TIME like", value, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeNotLike(String value) {
            addCriterion("AVG_TIME not like", value, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeIn(List<String> values) {
            addCriterion("AVG_TIME in", values, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeNotIn(List<String> values) {
            addCriterion("AVG_TIME not in", values, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeBetween(String value1, String value2) {
            addCriterion("AVG_TIME between", value1, value2, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAvgTimeNotBetween(String value1, String value2) {
            addCriterion("AVG_TIME not between", value1, value2, "avgTime");
            return (Criteria) this;
        }

        public Criteria andAuxMemoIsNull() {
            addCriterion("AUX_MEMO is null");
            return (Criteria) this;
        }

        public Criteria andAuxMemoIsNotNull() {
            addCriterion("AUX_MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andAuxMemoEqualTo(String value) {
            addCriterion("AUX_MEMO =", value, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoNotEqualTo(String value) {
            addCriterion("AUX_MEMO <>", value, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoGreaterThan(String value) {
            addCriterion("AUX_MEMO >", value, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoGreaterThanOrEqualTo(String value) {
            addCriterion("AUX_MEMO >=", value, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoLessThan(String value) {
            addCriterion("AUX_MEMO <", value, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoLessThanOrEqualTo(String value) {
            addCriterion("AUX_MEMO <=", value, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoLike(String value) {
            addCriterion("AUX_MEMO like", value, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoNotLike(String value) {
            addCriterion("AUX_MEMO not like", value, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoIn(List<String> values) {
            addCriterion("AUX_MEMO in", values, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoNotIn(List<String> values) {
            addCriterion("AUX_MEMO not in", values, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoBetween(String value1, String value2) {
            addCriterion("AUX_MEMO between", value1, value2, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andAuxMemoNotBetween(String value1, String value2) {
            addCriterion("AUX_MEMO not between", value1, value2, "auxMemo");
            return (Criteria) this;
        }

        public Criteria andEaaPloIsNull() {
            addCriterion("EAA_PLO is null");
            return (Criteria) this;
        }

        public Criteria andEaaPloIsNotNull() {
            addCriterion("EAA_PLO is not null");
            return (Criteria) this;
        }

        public Criteria andEaaPloEqualTo(String value) {
            addCriterion("EAA_PLO =", value, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloNotEqualTo(String value) {
            addCriterion("EAA_PLO <>", value, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloGreaterThan(String value) {
            addCriterion("EAA_PLO >", value, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloGreaterThanOrEqualTo(String value) {
            addCriterion("EAA_PLO >=", value, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloLessThan(String value) {
            addCriterion("EAA_PLO <", value, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloLessThanOrEqualTo(String value) {
            addCriterion("EAA_PLO <=", value, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloLike(String value) {
            addCriterion("EAA_PLO like", value, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloNotLike(String value) {
            addCriterion("EAA_PLO not like", value, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloIn(List<String> values) {
            addCriterion("EAA_PLO in", values, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloNotIn(List<String> values) {
            addCriterion("EAA_PLO not in", values, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloBetween(String value1, String value2) {
            addCriterion("EAA_PLO between", value1, value2, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaPloNotBetween(String value1, String value2) {
            addCriterion("EAA_PLO not between", value1, value2, "eaaPlo");
            return (Criteria) this;
        }

        public Criteria andEaaStatusIsNull() {
            addCriterion("EAA_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andEaaStatusIsNotNull() {
            addCriterion("EAA_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andEaaStatusEqualTo(String value) {
            addCriterion("EAA_STATUS =", value, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusNotEqualTo(String value) {
            addCriterion("EAA_STATUS <>", value, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusGreaterThan(String value) {
            addCriterion("EAA_STATUS >", value, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusGreaterThanOrEqualTo(String value) {
            addCriterion("EAA_STATUS >=", value, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusLessThan(String value) {
            addCriterion("EAA_STATUS <", value, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusLessThanOrEqualTo(String value) {
            addCriterion("EAA_STATUS <=", value, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusLike(String value) {
            addCriterion("EAA_STATUS like", value, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusNotLike(String value) {
            addCriterion("EAA_STATUS not like", value, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusIn(List<String> values) {
            addCriterion("EAA_STATUS in", values, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusNotIn(List<String> values) {
            addCriterion("EAA_STATUS not in", values, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusBetween(String value1, String value2) {
            addCriterion("EAA_STATUS between", value1, value2, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andEaaStatusNotBetween(String value1, String value2) {
            addCriterion("EAA_STATUS not between", value1, value2, "eaaStatus");
            return (Criteria) this;
        }

        public Criteria andDeptNumIsNull() {
            addCriterion("DEPT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andDeptNumIsNotNull() {
            addCriterion("DEPT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNumEqualTo(String value) {
            addCriterion("DEPT_NUM =", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumNotEqualTo(String value) {
            addCriterion("DEPT_NUM <>", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumGreaterThan(String value) {
            addCriterion("DEPT_NUM >", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumGreaterThanOrEqualTo(String value) {
            addCriterion("DEPT_NUM >=", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumLessThan(String value) {
            addCriterion("DEPT_NUM <", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumLessThanOrEqualTo(String value) {
            addCriterion("DEPT_NUM <=", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumLike(String value) {
            addCriterion("DEPT_NUM like", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumNotLike(String value) {
            addCriterion("DEPT_NUM not like", value, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumIn(List<String> values) {
            addCriterion("DEPT_NUM in", values, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumNotIn(List<String> values) {
            addCriterion("DEPT_NUM not in", values, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumBetween(String value1, String value2) {
            addCriterion("DEPT_NUM between", value1, value2, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptNumNotBetween(String value1, String value2) {
            addCriterion("DEPT_NUM not between", value1, value2, "deptNum");
            return (Criteria) this;
        }

        public Criteria andDeptGroupIsNull() {
            addCriterion("DEPT_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andDeptGroupIsNotNull() {
            addCriterion("DEPT_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andDeptGroupEqualTo(String value) {
            addCriterion("DEPT_GROUP =", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupNotEqualTo(String value) {
            addCriterion("DEPT_GROUP <>", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupGreaterThan(String value) {
            addCriterion("DEPT_GROUP >", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupGreaterThanOrEqualTo(String value) {
            addCriterion("DEPT_GROUP >=", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupLessThan(String value) {
            addCriterion("DEPT_GROUP <", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupLessThanOrEqualTo(String value) {
            addCriterion("DEPT_GROUP <=", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupLike(String value) {
            addCriterion("DEPT_GROUP like", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupNotLike(String value) {
            addCriterion("DEPT_GROUP not like", value, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupIn(List<String> values) {
            addCriterion("DEPT_GROUP in", values, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupNotIn(List<String> values) {
            addCriterion("DEPT_GROUP not in", values, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupBetween(String value1, String value2) {
            addCriterion("DEPT_GROUP between", value1, value2, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andDeptGroupNotBetween(String value1, String value2) {
            addCriterion("DEPT_GROUP not between", value1, value2, "deptGroup");
            return (Criteria) this;
        }

        public Criteria andJourStatusIsNull() {
            addCriterion("JOUR_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andJourStatusIsNotNull() {
            addCriterion("JOUR_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andJourStatusEqualTo(String value) {
            addCriterion("JOUR_STATUS =", value, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusNotEqualTo(String value) {
            addCriterion("JOUR_STATUS <>", value, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusGreaterThan(String value) {
            addCriterion("JOUR_STATUS >", value, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusGreaterThanOrEqualTo(String value) {
            addCriterion("JOUR_STATUS >=", value, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusLessThan(String value) {
            addCriterion("JOUR_STATUS <", value, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusLessThanOrEqualTo(String value) {
            addCriterion("JOUR_STATUS <=", value, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusLike(String value) {
            addCriterion("JOUR_STATUS like", value, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusNotLike(String value) {
            addCriterion("JOUR_STATUS not like", value, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusIn(List<String> values) {
            addCriterion("JOUR_STATUS in", values, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusNotIn(List<String> values) {
            addCriterion("JOUR_STATUS not in", values, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusBetween(String value1, String value2) {
            addCriterion("JOUR_STATUS between", value1, value2, "jourStatus");
            return (Criteria) this;
        }

        public Criteria andJourStatusNotBetween(String value1, String value2) {
            addCriterion("JOUR_STATUS not between", value1, value2, "jourStatus");
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

        public Criteria andInDateIsNull() {
            addCriterion("IN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andInDateIsNotNull() {
            addCriterion("IN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andInDateEqualTo(String value) {
            addCriterion("IN_DATE =", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateNotEqualTo(String value) {
            addCriterion("IN_DATE <>", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateGreaterThan(String value) {
            addCriterion("IN_DATE >", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateGreaterThanOrEqualTo(String value) {
            addCriterion("IN_DATE >=", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateLessThan(String value) {
            addCriterion("IN_DATE <", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateLessThanOrEqualTo(String value) {
            addCriterion("IN_DATE <=", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateLike(String value) {
            addCriterion("IN_DATE like", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateNotLike(String value) {
            addCriterion("IN_DATE not like", value, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateIn(List<String> values) {
            addCriterion("IN_DATE in", values, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateNotIn(List<String> values) {
            addCriterion("IN_DATE not in", values, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateBetween(String value1, String value2) {
            addCriterion("IN_DATE between", value1, value2, "inDate");
            return (Criteria) this;
        }

        public Criteria andInDateNotBetween(String value1, String value2) {
            addCriterion("IN_DATE not between", value1, value2, "inDate");
            return (Criteria) this;
        }

        public Criteria andBatchNumIsNull() {
            addCriterion("BATCH_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBatchNumIsNotNull() {
            addCriterion("BATCH_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNumEqualTo(String value) {
            addCriterion("BATCH_NUM =", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotEqualTo(String value) {
            addCriterion("BATCH_NUM <>", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThan(String value) {
            addCriterion("BATCH_NUM >", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_NUM >=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThan(String value) {
            addCriterion("BATCH_NUM <", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThanOrEqualTo(String value) {
            addCriterion("BATCH_NUM <=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLike(String value) {
            addCriterion("BATCH_NUM like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotLike(String value) {
            addCriterion("BATCH_NUM not like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumIn(List<String> values) {
            addCriterion("BATCH_NUM in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotIn(List<String> values) {
            addCriterion("BATCH_NUM not in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumBetween(String value1, String value2) {
            addCriterion("BATCH_NUM between", value1, value2, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotBetween(String value1, String value2) {
            addCriterion("BATCH_NUM not between", value1, value2, "batchNum");
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