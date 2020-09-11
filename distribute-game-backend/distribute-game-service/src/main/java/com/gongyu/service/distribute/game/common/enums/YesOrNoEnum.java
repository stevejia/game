package com.gongyu.service.distribute.game.common.enums;

public enum YesOrNoEnum {
    Y("Y", "是"),
    N("N", "否");

    private String code;
    private String name;

    YesOrNoEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static YesOrNoEnum getEnum(String code) {
        for (YesOrNoEnum yesOrNoEnum : values()) {
            if (yesOrNoEnum.getCode().equals(code)) {
                return yesOrNoEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
