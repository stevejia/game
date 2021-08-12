package com.futures.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/8 11:30
 */
@Getter
@AllArgsConstructor
public enum AuthStatusEnum {

    NOT_AUTH(0,"未认证"),
    AUTH_FAIL(1,"认证失败"),
    AUTH_SUCCESS(2,"认证成功"),

        ;

    private Integer code;
    private String desc;
}
