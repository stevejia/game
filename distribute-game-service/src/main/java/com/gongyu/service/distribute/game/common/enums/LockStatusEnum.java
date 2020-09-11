package com.gongyu.service.distribute.game.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 18:56
 */
@Getter
@AllArgsConstructor
public enum LockStatusEnum {

    NOT_LOCK(0,"正常"),
    LOCK(1,"锁定"),

    ;
    Integer code;
    String desc;

    public static LockStatusEnum parse(Integer code){
        return Arrays.stream(LockStatusEnum.values())
                .filter(item -> item.code == code)
                .findFirst()
                .orElseThrow(() -> new BizException("枚举解析异常 code:" + code));
    }
}
