package com.futures.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/22 19:32
 */
@Getter
@AllArgsConstructor
public enum TakeQueryEnum {

    TAKE_ING(1,"领养中"),
    TAKEED(2,"已领养"),
    APPEAL(3,"申诉"),
    SPLIT(4,"裂变"),
    ;

    Integer code;

    String desc;
}
