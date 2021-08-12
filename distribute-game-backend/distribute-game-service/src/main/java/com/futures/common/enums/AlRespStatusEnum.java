package com.futures.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/8 11:36
 */
@Getter
@AllArgsConstructor
public enum AlRespStatusEnum {

    NOT_PUSH(0,"未请求"),
    PUSH_ING(1,"请求中"),
    PUSH_SUCCESS(2,"请求成功"),
    PUSH_FAIL(3,"请求失败"),
    RESP_SUCCESS(4,"返回成功"),
    RESP_FAIL(5,"返回失败"),
    ;

    private Integer code;
    private String desc;
}
