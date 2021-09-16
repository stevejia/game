package com.gongyu.service.distribute.game.model.entity;

public class Contract {
    private Integer id;

    private String instrumentid;

    private String instrumentname;

    private String amTime;

    private String pmTime;

    private String nightTime;

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

    public String getInstrumentname() {
        return instrumentname;
    }

    public void setInstrumentname(String instrumentname) {
        this.instrumentname = instrumentname == null ? null : instrumentname.trim();
    }

    public String getAmTime() {
        return amTime;
    }

    public void setAmTime(String amTime) {
        this.amTime = amTime == null ? null : amTime.trim();
    }

    public String getPmTime() {
        return pmTime;
    }

    public void setPmTime(String pmTime) {
        this.pmTime = pmTime == null ? null : pmTime.trim();
    }

    public String getNightTime() {
        return nightTime;
    }

    public void setNightTime(String nightTime) {
        this.nightTime = nightTime == null ? null : nightTime.trim();
    }
}