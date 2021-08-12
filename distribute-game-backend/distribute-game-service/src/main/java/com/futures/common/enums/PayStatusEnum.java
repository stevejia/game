package com.futures.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 19:43
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    FREEZE(0,"冻结"),
    PROCESSING(1,"交易中"),
    SUCCESS(2,"交易成功"),
    TRAN_TIMEOUT(3,"交易超时"),
    AGAIN_PAY(5,"重新支付"),

    ;
    Integer code;
    String desc;
}
