package com.gongyu.service.distribute.game.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/28 15:50
 */
@Getter
@AllArgsConstructor
public enum PigStatusEnum {

    PRACTICE(1,"修炼中"),
    RESERVA(2,"预约"),
    OPEN_UP(3,"开抢"),
    RESERVAED(4,"已预约"),
    NOT_PUBLIC(5,"暂未开放"),
    WAIT_CONTRACT(6,"待领养"),


    ;
    ;
    Integer code;
    String desc;
}
