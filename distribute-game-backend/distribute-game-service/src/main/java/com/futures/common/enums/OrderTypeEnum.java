package com.futures.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 15:38
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {

    NORMAL_POSSESSION(0,"正常占领"),
    ACTIVITY(1,"秒杀活动"),
    SELL_REVENUE(2,"出售推广收益"),
    SYS_HANDSEL(3,"系统赠送"),

        ;

    Integer code;
    String desc;

    public static OrderTypeEnum parse(Integer code){
        return Arrays.stream(OrderTypeEnum.values())
                .filter(item -> item.code == code)
                .findFirst()
                .orElseThrow(() -> new BizException("枚举解析异常 code:" + code));
    }
}
