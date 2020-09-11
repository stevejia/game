package com.gongyu.service.distribute.game.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 11:22
 */
@Getter
@AllArgsConstructor
public enum DelayTaskEnum {

    REVEAL_LUCKY(1,"开奖"),
    CANCEL_ORDER(2,"取消订单"),

    ;

    Integer code;
    String desc;
}
