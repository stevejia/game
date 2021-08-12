package com.futures.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/1 11:54
 */
@Getter
@AllArgsConstructor
public enum PaymentTypeEnum {

    ZFB(1,"支付宝"),
    WX(2,"微信"),
    YHK(3,"银行卡"),
    ;

    private Integer code;
    private String desc;

    public static PaymentTypeEnum parse(Integer code){
        return Arrays.stream(PaymentTypeEnum.values())
                .filter(item -> item.code.intValue() == code.intValue())
                .findFirst()
                .orElseThrow(() -> new BizException("不能被解析的枚举code："+code));
    }
}
