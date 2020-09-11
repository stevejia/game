package com.gongyu.service.distribute.game.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum PhoneCodeTypeEnum {

    REGISTER("REGISTER", "注册"),
    BIND_MOBILE("BIND_MOBILE", "绑定手机号"),
    BIND_THIRD("BIND_THIRD", "绑定第三方登录"),
    LOGIN("LOGIN", "登录"),
    CG_PHONE_OLD("CG_PHONE_OLD", "更换绑定手机-旧手机"),
    CG_PHONE_NEW("CG_PHONE_NEW", "更换绑定手机-新手机"),
    FG_PWD("FG_PWD", "修改密码"),
    FG_PPWD("FG_PPWD", "修改支付密码"),
    MEMBERAUTH("MEMBERAUTH", "身份认证"),
    ADD_MEMBER("ADD_MEMBER", "添加子用户"),
    UNBIND_MOBILE("UNBIND_MOBILE", "解绑手机"),
    VERIFIED_MOBILE("VERIFIED_MOBILE", "实名认证"),
    COLLECT_MONEY("COLLECT_MONEY", "收款方式");
    private String code;
    private String name;

    PhoneCodeTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PhoneCodeTypeEnum getEnum(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (PhoneCodeTypeEnum phoneCodeTypeEnum : values()) {
            if (StringUtils.equals(phoneCodeTypeEnum.getCode(), code)) {
                return phoneCodeTypeEnum;
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
