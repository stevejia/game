package com.futures.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/28 11:21
 */
@Getter
@AllArgsConstructor
public enum RecomLevelEnum {

    FIRST_LEADER(1,"第一个上级",new BigDecimal("0.06")),
    SECOND_LEADER(2,"第二个上级",new BigDecimal("0.03")),
    THIRD_LEADER(3,"第三个上级",new BigDecimal("0.01")),

    ;

    Integer code;
    String desc;
    /**
     * 推广收益率
     */
    BigDecimal incomeRate;

}
