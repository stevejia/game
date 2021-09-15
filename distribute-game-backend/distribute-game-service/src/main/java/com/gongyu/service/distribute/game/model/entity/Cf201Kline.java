package com.gongyu.service.distribute.game.model.entity;

public class Cf201Kline {
    private Integer id;

    private String instrumentid;

    private String localTime;

    private String instrumen1;

    private String tradingday;

    private String startTime;

    private String endTime;

    private String klineTime;

    private Integer period;

    private Double openprice;

    private Double closeprice;

    private Double highestprice;

    private Double lowestprice;

    private Integer volume;

    private Double ao;

    private Double ac;

    private Integer aoMark;

    private Integer acMark;

    private Integer sell;

    private Integer buy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstrumentid() {
        return instrumentid;
    }

    public void setInstrumentid(String instrumentid) {
        this.instrumentid = instrumentid == null ? null : instrumentid.trim();
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime == null ? null : localTime.trim();
    }

    public String getInstrumen1() {
        return instrumen1;
    }

    public void setInstrumen1(String instrumen1) {
        this.instrumen1 = instrumen1 == null ? null : instrumen1.trim();
    }

    public String getTradingday() {
        return tradingday;
    }

    public void setTradingday(String tradingday) {
        this.tradingday = tradingday == null ? null : tradingday.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getKlineTime() {
        return klineTime;
    }

    public void setKlineTime(String klineTime) {
        this.klineTime = klineTime == null ? null : klineTime.trim();
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getOpenprice() {
        return openprice;
    }

    public void setOpenprice(Double openprice) {
        this.openprice = openprice;
    }

    public Double getCloseprice() {
        return closeprice;
    }

    public void setCloseprice(Double closeprice) {
        this.closeprice = closeprice;
    }

    public Double getHighestprice() {
        return highestprice;
    }

    public void setHighestprice(Double highestprice) {
        this.highestprice = highestprice;
    }

    public Double getLowestprice() {
        return lowestprice;
    }

    public void setLowestprice(Double lowestprice) {
        this.lowestprice = lowestprice;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getAo() {
        return ao;
    }

    public void setAo(Double ao) {
        this.ao = ao;
    }

    public Double getAc() {
        return ac;
    }

    public void setAc(Double ac) {
        this.ac = ac;
    }

    public Integer getAoMark() {
        return aoMark;
    }

    public void setAoMark(Integer aoMark) {
        this.aoMark = aoMark;
    }

    public Integer getAcMark() {
        return acMark;
    }

    public void setAcMark(Integer acMark) {
        this.acMark = acMark;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }
}