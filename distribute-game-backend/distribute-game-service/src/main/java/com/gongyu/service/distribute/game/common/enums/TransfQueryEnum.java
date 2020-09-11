package com.gongyu.service.distribute.game.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 13:56
 */
@Getter
@AllArgsConstructor
public enum TransfQueryEnum {

    NOT_TRANSF(1,"待转让"),
    TRANSF_ING(2,"转让中"),
    TRANSGED(3,"转让完成"),
    APPEAL(4,"取消申诉"),

    ;

    Integer code;
    String desc;

    public static TransfQueryEnum parse(Integer code){
        return Arrays.stream(TransfQueryEnum.values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElseThrow(() -> new BizException("不能被解析的CODE：" + code));
    }

}
