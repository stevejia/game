package com.futures.enums;

import lombok.Getter;

@Getter
public enum UsersLevelEnum {
    ONE(1, "粉丝"),
    TWO(2, "正式用户"),
    THREE(3, "初级合伙人"),
    FOUR(4, "中级合伙人"),
    FIVE(5, "高级合伙人"),
    SIX(6, "联创合伙人");
    private int code;
    private String name;

    UsersLevelEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UsersLevelEnum getEnum(int code) {
        for (UsersLevelEnum inputTypeEnum : values()) {
            if (inputTypeEnum.getCode() == code) {
                return inputTypeEnum;
            }
        }
        return null;
    }

}
