package com.gongyu.service.distribute.game.model.entity;

public class Rb2110KlineWithBLOBs extends Rb2110Kline {
    private String aoMark;

    private String acMark;

    public String getAoMark() {
        return aoMark;
    }

    public void setAoMark(String aoMark) {
        this.aoMark = aoMark == null ? null : aoMark.trim();
    }

    public String getAcMark() {
        return acMark;
    }

    public void setAcMark(String acMark) {
        this.acMark = acMark == null ? null : acMark.trim();
    }
}