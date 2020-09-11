package com.gongyu.service.distribute.game.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/24 10:37
 */
@Getter
@AllArgsConstructor
public enum ScheduledTypeEnum {

    INCOME(1,"计算收益"),
    ;

    Integer code;

    String desc;
}
