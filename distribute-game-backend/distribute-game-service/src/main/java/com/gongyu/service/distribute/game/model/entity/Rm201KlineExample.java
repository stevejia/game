package com.gongyu.service.distribute.game.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Rm201KlineExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Rm201KlineExample() {
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

        public Criteria andLocalTimeIsNull() {
            addCriterion("local_time is null");
            return (Criteria) this;
        }

        public Criteria andLocalTimeIsNotNull() {
            addCriterion("local_time is not null");
            return (Criteria) this;
        }

        public Criteria andLocalTimeEqualTo(String value) {
            addCriterion("local_time =", value, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeNotEqualTo(String value) {
            addCriterion("local_time <>", value, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeGreaterThan(String value) {
            addCriterion("local_time >", value, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeGreaterThanOrEqualTo(String value) {
            addCriterion("local_time >=", value, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeLessThan(String value) {
            addCriterion("local_time <", value, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeLessThanOrEqualTo(String value) {
            addCriterion("local_time <=", value, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeLike(String value) {
            addCriterion("local_time like", value, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeNotLike(String value) {
            addCriterion("local_time not like", value, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeIn(List<String> values) {
            addCriterion("local_time in", values, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeNotIn(List<String> values) {
            addCriterion("local_time not in", values, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeBetween(String value1, String value2) {
            addCriterion("local_time between", value1, value2, "localTime");
            return (Criteria) this;
        }

        public Criteria andLocalTimeNotBetween(String value1, String value2) {
            addCriterion("local_time not between", value1, value2, "localTime");
            return (Criteria) this;
        }

        public Criteria andInstrumen1IsNull() {
            addCriterion("Instrumen1 is null");
            return (Criteria) this;
        }

        public Criteria andInstrumen1IsNotNull() {
            addCriterion("Instrumen1 is not null");
            return (Criteria) this;
        }

        public Criteria andInstrumen1EqualTo(String value) {
            addCriterion("Instrumen1 =", value, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1NotEqualTo(String value) {
            addCriterion("Instrumen1 <>", value, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1GreaterThan(String value) {
            addCriterion("Instrumen1 >", value, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1GreaterThanOrEqualTo(String value) {
            addCriterion("Instrumen1 >=", value, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1LessThan(String value) {
            addCriterion("Instrumen1 <", value, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1LessThanOrEqualTo(String value) {
            addCriterion("Instrumen1 <=", value, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1Like(String value) {
            addCriterion("Instrumen1 like", value, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1NotLike(String value) {
            addCriterion("Instrumen1 not like", value, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1In(List<String> values) {
            addCriterion("Instrumen1 in", values, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1NotIn(List<String> values) {
            addCriterion("Instrumen1 not in", values, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1Between(String value1, String value2) {
            addCriterion("Instrumen1 between", value1, value2, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andInstrumen1NotBetween(String value1, String value2) {
            addCriterion("Instrumen1 not between", value1, value2, "instrumen1");
            return (Criteria) this;
        }

        public Criteria andTradingdayIsNull() {
            addCriterion("TradingDay is null");
            return (Criteria) this;
        }

        public Criteria andTradingdayIsNotNull() {
            addCriterion("TradingDay is not null");
            return (Criteria) this;
        }

        public Criteria andTradingdayEqualTo(String value) {
            addCriterion("TradingDay =", value, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayNotEqualTo(String value) {
            addCriterion("TradingDay <>", value, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayGreaterThan(String value) {
            addCriterion("TradingDay >", value, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayGreaterThanOrEqualTo(String value) {
            addCriterion("TradingDay >=", value, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayLessThan(String value) {
            addCriterion("TradingDay <", value, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayLessThanOrEqualTo(String value) {
            addCriterion("TradingDay <=", value, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayLike(String value) {
            addCriterion("TradingDay like", value, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayNotLike(String value) {
            addCriterion("TradingDay not like", value, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayIn(List<String> values) {
            addCriterion("TradingDay in", values, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayNotIn(List<String> values) {
            addCriterion("TradingDay not in", values, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayBetween(String value1, String value2) {
            addCriterion("TradingDay between", value1, value2, "tradingday");
            return (Criteria) this;
        }

        public Criteria andTradingdayNotBetween(String value1, String value2) {
            addCriterion("TradingDay not between", value1, value2, "tradingday");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(String value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(String value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(String value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(String value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(String value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLike(String value) {
            addCriterion("start_time like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotLike(String value) {
            addCriterion("start_time not like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<String> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<String> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(String value1, String value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(String value1, String value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeIsNull() {
            addCriterion("kline_time is null");
            return (Criteria) this;
        }

        public Criteria andKlineTimeIsNotNull() {
            addCriterion("kline_time is not null");
            return (Criteria) this;
        }

        public Criteria andKlineTimeEqualTo(String value) {
            addCriterion("kline_time =", value, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeNotEqualTo(String value) {
            addCriterion("kline_time <>", value, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeGreaterThan(String value) {
            addCriterion("kline_time >", value, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeGreaterThanOrEqualTo(String value) {
            addCriterion("kline_time >=", value, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeLessThan(String value) {
            addCriterion("kline_time <", value, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeLessThanOrEqualTo(String value) {
            addCriterion("kline_time <=", value, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeLike(String value) {
            addCriterion("kline_time like", value, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeNotLike(String value) {
            addCriterion("kline_time not like", value, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeIn(List<String> values) {
            addCriterion("kline_time in", values, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeNotIn(List<String> values) {
            addCriterion("kline_time not in", values, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeBetween(String value1, String value2) {
            addCriterion("kline_time between", value1, value2, "klineTime");
            return (Criteria) this;
        }

        public Criteria andKlineTimeNotBetween(String value1, String value2) {
            addCriterion("kline_time not between", value1, value2, "klineTime");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNull() {
            addCriterion("period is null");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNotNull() {
            addCriterion("period is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEqualTo(Integer value) {
            addCriterion("period =", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotEqualTo(Integer value) {
            addCriterion("period <>", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThan(Integer value) {
            addCriterion("period >", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("period >=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThan(Integer value) {
            addCriterion("period <", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("period <=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodIn(List<Integer> values) {
            addCriterion("period in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotIn(List<Integer> values) {
            addCriterion("period not in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodBetween(Integer value1, Integer value2) {
            addCriterion("period between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("period not between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andOpenpriceIsNull() {
            addCriterion("OpenPrice is null");
            return (Criteria) this;
        }

        public Criteria andOpenpriceIsNotNull() {
            addCriterion("OpenPrice is not null");
            return (Criteria) this;
        }

        public Criteria andOpenpriceEqualTo(Double value) {
            addCriterion("OpenPrice =", value, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceNotEqualTo(Double value) {
            addCriterion("OpenPrice <>", value, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceGreaterThan(Double value) {
            addCriterion("OpenPrice >", value, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceGreaterThanOrEqualTo(Double value) {
            addCriterion("OpenPrice >=", value, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceLessThan(Double value) {
            addCriterion("OpenPrice <", value, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceLessThanOrEqualTo(Double value) {
            addCriterion("OpenPrice <=", value, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceIn(List<Double> values) {
            addCriterion("OpenPrice in", values, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceNotIn(List<Double> values) {
            addCriterion("OpenPrice not in", values, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceBetween(Double value1, Double value2) {
            addCriterion("OpenPrice between", value1, value2, "openprice");
            return (Criteria) this;
        }

        public Criteria andOpenpriceNotBetween(Double value1, Double value2) {
            addCriterion("OpenPrice not between", value1, value2, "openprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceIsNull() {
            addCriterion("ClosePrice is null");
            return (Criteria) this;
        }

        public Criteria andClosepriceIsNotNull() {
            addCriterion("ClosePrice is not null");
            return (Criteria) this;
        }

        public Criteria andClosepriceEqualTo(Double value) {
            addCriterion("ClosePrice =", value, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceNotEqualTo(Double value) {
            addCriterion("ClosePrice <>", value, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceGreaterThan(Double value) {
            addCriterion("ClosePrice >", value, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceGreaterThanOrEqualTo(Double value) {
            addCriterion("ClosePrice >=", value, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceLessThan(Double value) {
            addCriterion("ClosePrice <", value, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceLessThanOrEqualTo(Double value) {
            addCriterion("ClosePrice <=", value, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceIn(List<Double> values) {
            addCriterion("ClosePrice in", values, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceNotIn(List<Double> values) {
            addCriterion("ClosePrice not in", values, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceBetween(Double value1, Double value2) {
            addCriterion("ClosePrice between", value1, value2, "closeprice");
            return (Criteria) this;
        }

        public Criteria andClosepriceNotBetween(Double value1, Double value2) {
            addCriterion("ClosePrice not between", value1, value2, "closeprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceIsNull() {
            addCriterion("HighestPrice is null");
            return (Criteria) this;
        }

        public Criteria andHighestpriceIsNotNull() {
            addCriterion("HighestPrice is not null");
            return (Criteria) this;
        }

        public Criteria andHighestpriceEqualTo(Double value) {
            addCriterion("HighestPrice =", value, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceNotEqualTo(Double value) {
            addCriterion("HighestPrice <>", value, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceGreaterThan(Double value) {
            addCriterion("HighestPrice >", value, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceGreaterThanOrEqualTo(Double value) {
            addCriterion("HighestPrice >=", value, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceLessThan(Double value) {
            addCriterion("HighestPrice <", value, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceLessThanOrEqualTo(Double value) {
            addCriterion("HighestPrice <=", value, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceIn(List<Double> values) {
            addCriterion("HighestPrice in", values, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceNotIn(List<Double> values) {
            addCriterion("HighestPrice not in", values, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceBetween(Double value1, Double value2) {
            addCriterion("HighestPrice between", value1, value2, "highestprice");
            return (Criteria) this;
        }

        public Criteria andHighestpriceNotBetween(Double value1, Double value2) {
            addCriterion("HighestPrice not between", value1, value2, "highestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceIsNull() {
            addCriterion("LowestPrice is null");
            return (Criteria) this;
        }

        public Criteria andLowestpriceIsNotNull() {
            addCriterion("LowestPrice is not null");
            return (Criteria) this;
        }

        public Criteria andLowestpriceEqualTo(Double value) {
            addCriterion("LowestPrice =", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceNotEqualTo(Double value) {
            addCriterion("LowestPrice <>", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceGreaterThan(Double value) {
            addCriterion("LowestPrice >", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceGreaterThanOrEqualTo(Double value) {
            addCriterion("LowestPrice >=", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceLessThan(Double value) {
            addCriterion("LowestPrice <", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceLessThanOrEqualTo(Double value) {
            addCriterion("LowestPrice <=", value, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceIn(List<Double> values) {
            addCriterion("LowestPrice in", values, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceNotIn(List<Double> values) {
            addCriterion("LowestPrice not in", values, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceBetween(Double value1, Double value2) {
            addCriterion("LowestPrice between", value1, value2, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andLowestpriceNotBetween(Double value1, Double value2) {
            addCriterion("LowestPrice not between", value1, value2, "lowestprice");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNull() {
            addCriterion("Volume is null");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNotNull() {
            addCriterion("Volume is not null");
            return (Criteria) this;
        }

        public Criteria andVolumeEqualTo(Integer value) {
            addCriterion("Volume =", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotEqualTo(Integer value) {
            addCriterion("Volume <>", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThan(Integer value) {
            addCriterion("Volume >", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThanOrEqualTo(Integer value) {
            addCriterion("Volume >=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThan(Integer value) {
            addCriterion("Volume <", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThanOrEqualTo(Integer value) {
            addCriterion("Volume <=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeIn(List<Integer> values) {
            addCriterion("Volume in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotIn(List<Integer> values) {
            addCriterion("Volume not in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeBetween(Integer value1, Integer value2) {
            addCriterion("Volume between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotBetween(Integer value1, Integer value2) {
            addCriterion("Volume not between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andAoIsNull() {
            addCriterion("AO is null");
            return (Criteria) this;
        }

        public Criteria andAoIsNotNull() {
            addCriterion("AO is not null");
            return (Criteria) this;
        }

        public Criteria andAoEqualTo(Double value) {
            addCriterion("AO =", value, "ao");
            return (Criteria) this;
        }

        public Criteria andAoNotEqualTo(Double value) {
            addCriterion("AO <>", value, "ao");
            return (Criteria) this;
        }

        public Criteria andAoGreaterThan(Double value) {
            addCriterion("AO >", value, "ao");
            return (Criteria) this;
        }

        public Criteria andAoGreaterThanOrEqualTo(Double value) {
            addCriterion("AO >=", value, "ao");
            return (Criteria) this;
        }

        public Criteria andAoLessThan(Double value) {
            addCriterion("AO <", value, "ao");
            return (Criteria) this;
        }

        public Criteria andAoLessThanOrEqualTo(Double value) {
            addCriterion("AO <=", value, "ao");
            return (Criteria) this;
        }

        public Criteria andAoIn(List<Double> values) {
            addCriterion("AO in", values, "ao");
            return (Criteria) this;
        }

        public Criteria andAoNotIn(List<Double> values) {
            addCriterion("AO not in", values, "ao");
            return (Criteria) this;
        }

        public Criteria andAoBetween(Double value1, Double value2) {
            addCriterion("AO between", value1, value2, "ao");
            return (Criteria) this;
        }

        public Criteria andAoNotBetween(Double value1, Double value2) {
            addCriterion("AO not between", value1, value2, "ao");
            return (Criteria) this;
        }

        public Criteria andAcIsNull() {
            addCriterion("AC is null");
            return (Criteria) this;
        }

        public Criteria andAcIsNotNull() {
            addCriterion("AC is not null");
            return (Criteria) this;
        }

        public Criteria andAcEqualTo(Double value) {
            addCriterion("AC =", value, "ac");
            return (Criteria) this;
        }

        public Criteria andAcNotEqualTo(Double value) {
            addCriterion("AC <>", value, "ac");
            return (Criteria) this;
        }

        public Criteria andAcGreaterThan(Double value) {
            addCriterion("AC >", value, "ac");
            return (Criteria) this;
        }

        public Criteria andAcGreaterThanOrEqualTo(Double value) {
            addCriterion("AC >=", value, "ac");
            return (Criteria) this;
        }

        public Criteria andAcLessThan(Double value) {
            addCriterion("AC <", value, "ac");
            return (Criteria) this;
        }

        public Criteria andAcLessThanOrEqualTo(Double value) {
            addCriterion("AC <=", value, "ac");
            return (Criteria) this;
        }

        public Criteria andAcIn(List<Double> values) {
            addCriterion("AC in", values, "ac");
            return (Criteria) this;
        }

        public Criteria andAcNotIn(List<Double> values) {
            addCriterion("AC not in", values, "ac");
            return (Criteria) this;
        }

        public Criteria andAcBetween(Double value1, Double value2) {
            addCriterion("AC between", value1, value2, "ac");
            return (Criteria) this;
        }

        public Criteria andAcNotBetween(Double value1, Double value2) {
            addCriterion("AC not between", value1, value2, "ac");
            return (Criteria) this;
        }

        public Criteria andAoMarkIsNull() {
            addCriterion("AO_Mark is null");
            return (Criteria) this;
        }

        public Criteria andAoMarkIsNotNull() {
            addCriterion("AO_Mark is not null");
            return (Criteria) this;
        }

        public Criteria andAoMarkEqualTo(Integer value) {
            addCriterion("AO_Mark =", value, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkNotEqualTo(Integer value) {
            addCriterion("AO_Mark <>", value, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkGreaterThan(Integer value) {
            addCriterion("AO_Mark >", value, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkGreaterThanOrEqualTo(Integer value) {
            addCriterion("AO_Mark >=", value, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkLessThan(Integer value) {
            addCriterion("AO_Mark <", value, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkLessThanOrEqualTo(Integer value) {
            addCriterion("AO_Mark <=", value, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkIn(List<Integer> values) {
            addCriterion("AO_Mark in", values, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkNotIn(List<Integer> values) {
            addCriterion("AO_Mark not in", values, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkBetween(Integer value1, Integer value2) {
            addCriterion("AO_Mark between", value1, value2, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAoMarkNotBetween(Integer value1, Integer value2) {
            addCriterion("AO_Mark not between", value1, value2, "aoMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkIsNull() {
            addCriterion("AC_Mark is null");
            return (Criteria) this;
        }

        public Criteria andAcMarkIsNotNull() {
            addCriterion("AC_Mark is not null");
            return (Criteria) this;
        }

        public Criteria andAcMarkEqualTo(Integer value) {
            addCriterion("AC_Mark =", value, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkNotEqualTo(Integer value) {
            addCriterion("AC_Mark <>", value, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkGreaterThan(Integer value) {
            addCriterion("AC_Mark >", value, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkGreaterThanOrEqualTo(Integer value) {
            addCriterion("AC_Mark >=", value, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkLessThan(Integer value) {
            addCriterion("AC_Mark <", value, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkLessThanOrEqualTo(Integer value) {
            addCriterion("AC_Mark <=", value, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkIn(List<Integer> values) {
            addCriterion("AC_Mark in", values, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkNotIn(List<Integer> values) {
            addCriterion("AC_Mark not in", values, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkBetween(Integer value1, Integer value2) {
            addCriterion("AC_Mark between", value1, value2, "acMark");
            return (Criteria) this;
        }

        public Criteria andAcMarkNotBetween(Integer value1, Integer value2) {
            addCriterion("AC_Mark not between", value1, value2, "acMark");
            return (Criteria) this;
        }

        public Criteria andSellIsNull() {
            addCriterion("sell is null");
            return (Criteria) this;
        }

        public Criteria andSellIsNotNull() {
            addCriterion("sell is not null");
            return (Criteria) this;
        }

        public Criteria andSellEqualTo(Integer value) {
            addCriterion("sell =", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellNotEqualTo(Integer value) {
            addCriterion("sell <>", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellGreaterThan(Integer value) {
            addCriterion("sell >", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellGreaterThanOrEqualTo(Integer value) {
            addCriterion("sell >=", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellLessThan(Integer value) {
            addCriterion("sell <", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellLessThanOrEqualTo(Integer value) {
            addCriterion("sell <=", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellIn(List<Integer> values) {
            addCriterion("sell in", values, "sell");
            return (Criteria) this;
        }

        public Criteria andSellNotIn(List<Integer> values) {
            addCriterion("sell not in", values, "sell");
            return (Criteria) this;
        }

        public Criteria andSellBetween(Integer value1, Integer value2) {
            addCriterion("sell between", value1, value2, "sell");
            return (Criteria) this;
        }

        public Criteria andSellNotBetween(Integer value1, Integer value2) {
            addCriterion("sell not between", value1, value2, "sell");
            return (Criteria) this;
        }

        public Criteria andBuyIsNull() {
            addCriterion("buy is null");
            return (Criteria) this;
        }

        public Criteria andBuyIsNotNull() {
            addCriterion("buy is not null");
            return (Criteria) this;
        }

        public Criteria andBuyEqualTo(Integer value) {
            addCriterion("buy =", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyNotEqualTo(Integer value) {
            addCriterion("buy <>", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyGreaterThan(Integer value) {
            addCriterion("buy >", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyGreaterThanOrEqualTo(Integer value) {
            addCriterion("buy >=", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyLessThan(Integer value) {
            addCriterion("buy <", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyLessThanOrEqualTo(Integer value) {
            addCriterion("buy <=", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyIn(List<Integer> values) {
            addCriterion("buy in", values, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyNotIn(List<Integer> values) {
            addCriterion("buy not in", values, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyBetween(Integer value1, Integer value2) {
            addCriterion("buy between", value1, value2, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyNotBetween(Integer value1, Integer value2) {
            addCriterion("buy not between", value1, value2, "buy");
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