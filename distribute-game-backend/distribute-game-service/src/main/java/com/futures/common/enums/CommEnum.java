package com.futures.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 14:21
 */
@Getter
@AllArgsConstructor
public enum CommEnum {

    FALSE(0),
    TRUE(1),
    ;
    Integer code;
}
