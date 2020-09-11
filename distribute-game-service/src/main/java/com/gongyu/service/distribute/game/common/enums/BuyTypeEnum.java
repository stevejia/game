package com.gongyu.service.distribute.game.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/29 16:02
 */
@Getter
@AllArgsConstructor
public enum BuyTypeEnum {

    SYS_HANDSEL("1","系统赠送"),
    BUY_ORDER("2","正常购买"),
    PIG_SPLIT("3","精灵分裂"),
    SELL_INCOM("4","出售推广收益"),

    ;
    String code;

    String desc;


    public static BuyTypeEnum parse(String code){
        return Arrays.stream(BuyTypeEnum.values())
                .filter(item -> item.code.equals(code))
                .findFirst()
                .orElse(BUY_ORDER);
//                .orElseThrow(() -> new BizException("不能被解析的code:" + code));
    }
}
