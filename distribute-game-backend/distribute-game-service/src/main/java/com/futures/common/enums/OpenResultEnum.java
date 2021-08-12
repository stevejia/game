package com.futures.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 17:31
 */
@Getter
@AllArgsConstructor
public enum OpenResultEnum {

    NOT_OPEN(0,"未开奖"),
    OEPN(1,"已开奖"),
    ;

    Integer code;
    String desc;
}
