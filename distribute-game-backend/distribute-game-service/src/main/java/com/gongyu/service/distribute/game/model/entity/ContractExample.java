package com.gongyu.service.distribute.game.model.entity;

import java.util.ArrayList;
import java.util.List;

public class ContractExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ContractExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andInstrumentidIsNull() {
            addCriterion("InstrumentID is null");
            return (Criteria) this;
        }

        public Criteria andInstrumentidIsNotNull() {
            addCriterion("InstrumentID is not null");
            return (Criteria) this;
        }

        public Criteria andInstrumentidEqualTo(String value) {
            addCriterion("InstrumentID =", value, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidNotEqualTo(String value) {
            addCriterion("InstrumentID <>", value, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidGreaterThan(String value) {
            addCriterion("InstrumentID >", value, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidGreaterThanOrEqualTo(String value) {
            addCriterion("InstrumentID >=", value, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidLessThan(String value) {
            addCriterion("InstrumentID <", value, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidLessThanOrEqualTo(String value) {
            addCriterion("InstrumentID <=", value, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidLike(String value) {
            addCriterion("InstrumentID like", value, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidNotLike(String value) {
            addCriterion("InstrumentID not like", value, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidIn(List<String> values) {
            addCriterion("InstrumentID in", values, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidNotIn(List<String> values) {
            addCriterion("InstrumentID not in", values, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidBetween(String value1, String value2) {
            addCriterion("InstrumentID between", value1, value2, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentidNotBetween(String value1, String value2) {
            addCriterion("InstrumentID not between", value1, value2, "instrumentid");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameIsNull() {
            addCriterion("InstrumentName is null");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameIsNotNull() {
            addCriterion("InstrumentName is not null");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameEqualTo(String value) {
            addCriterion("InstrumentName =", value, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameNotEqualTo(String value) {
            addCriterion("InstrumentName <>", value, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameGreaterThan(String value) {
            addCriterion("InstrumentName >", value, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameGreaterThanOrEqualTo(String value) {
            addCriterion("InstrumentName >=", value, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameLessThan(String value) {
            addCriterion("InstrumentName <", value, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameLessThanOrEqualTo(String value) {
            addCriterion("InstrumentName <=", value, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameLike(String value) {
            addCriterion("InstrumentName like", value, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameNotLike(String value) {
            addCriterion("InstrumentName not like", value, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameIn(List<String> values) {
            addCriterion("InstrumentName in", values, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameNotIn(List<String> values) {
            addCriterion("InstrumentName not in", values, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameBetween(String value1, String value2) {
            addCriterion("InstrumentName between", value1, value2, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andInstrumentnameNotBetween(String value1, String value2) {
            addCriterion("InstrumentName not between", value1, value2, "instrumentname");
            return (Criteria) this;
        }

        public Criteria andAmTimeIsNull() {
            addCriterion("AM_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAmTimeIsNotNull() {
            addCriterion("AM_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAmTimeEqualTo(String value) {
            addCriterion("AM_TIME =", value, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeNotEqualTo(String value) {
            addCriterion("AM_TIME <>", value, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeGreaterThan(String value) {
            addCriterion("AM_TIME >", value, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeGreaterThanOrEqualTo(String value) {
            addCriterion("AM_TIME >=", value, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeLessThan(String value) {
            addCriterion("AM_TIME <", value, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeLessThanOrEqualTo(String value) {
            addCriterion("AM_TIME <=", value, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeLike(String value) {
            addCriterion("AM_TIME like", value, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeNotLike(String value) {
            addCriterion("AM_TIME not like", value, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeIn(List<String> values) {
            addCriterion("AM_TIME in", values, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeNotIn(List<String> values) {
            addCriterion("AM_TIME not in", values, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeBetween(String value1, String value2) {
            addCriterion("AM_TIME between", value1, value2, "amTime");
            return (Criteria) this;
        }

        public Criteria andAmTimeNotBetween(String value1, String value2) {
            addCriterion("AM_TIME not between", value1, value2, "amTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeIsNull() {
            addCriterion("PM_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPmTimeIsNotNull() {
            addCriterion("PM_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPmTimeEqualTo(String value) {
            addCriterion("PM_TIME =", value, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeNotEqualTo(String value) {
            addCriterion("PM_TIME <>", value, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeGreaterThan(String value) {
            addCriterion("PM_TIME >", value, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeGreaterThanOrEqualTo(String value) {
            addCriterion("PM_TIME >=", value, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeLessThan(String value) {
            addCriterion("PM_TIME <", value, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeLessThanOrEqualTo(String value) {
            addCriterion("PM_TIME <=", value, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeLike(String value) {
            addCriterion("PM_TIME like", value, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeNotLike(String value) {
            addCriterion("PM_TIME not like", value, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeIn(List<String> values) {
            addCriterion("PM_TIME in", values, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeNotIn(List<String> values) {
            addCriterion("PM_TIME not in", values, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeBetween(String value1, String value2) {
            addCriterion("PM_TIME between", value1, value2, "pmTime");
            return (Criteria) this;
        }

        public Criteria andPmTimeNotBetween(String value1, String value2) {
            addCriterion("PM_TIME not between", value1, value2, "pmTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeIsNull() {
            addCriterion("NIGHT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andNightTimeIsNotNull() {
            addCriterion("NIGHT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andNightTimeEqualTo(String value) {
            addCriterion("NIGHT_TIME =", value, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeNotEqualTo(String value) {
            addCriterion("NIGHT_TIME <>", value, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeGreaterThan(String value) {
            addCriterion("NIGHT_TIME >", value, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeGreaterThanOrEqualTo(String value) {
            addCriterion("NIGHT_TIME >=", value, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeLessThan(String value) {
            addCriterion("NIGHT_TIME <", value, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeLessThanOrEqualTo(String value) {
            addCriterion("NIGHT_TIME <=", value, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeLike(String value) {
            addCriterion("NIGHT_TIME like", value, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeNotLike(String value) {
            addCriterion("NIGHT_TIME not like", value, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeIn(List<String> values) {
            addCriterion("NIGHT_TIME in", values, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeNotIn(List<String> values) {
            addCriterion("NIGHT_TIME not in", values, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeBetween(String value1, String value2) {
            addCriterion("NIGHT_TIME between", value1, value2, "nightTime");
            return (Criteria) this;
        }

        public Criteria andNightTimeNotBetween(String value1, String value2) {
            addCriterion("NIGHT_TIME not between", value1, value2, "nightTime");
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