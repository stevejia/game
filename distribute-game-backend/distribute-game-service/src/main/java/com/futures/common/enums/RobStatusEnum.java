package com.futures.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 11:44
 */
@Getter
@AllArgsConstructor
public enum RobStatusEnum {

    NOT_ROBED(0,"未抢到"),
    ROBED(1,"已抢到"),
    ;

    Integer code;

    String desc;

    public static RobStatusEnum parse(Integer code){
        return Arrays.stream(RobStatusEnum.values())
                .filter(item -> item.code == code)
                .findFirst()
                .orElseThrow(() -> new BizException("不能被解析的code:"+code));
    }
}
