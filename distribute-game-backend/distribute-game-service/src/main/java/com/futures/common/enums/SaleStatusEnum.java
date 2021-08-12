package com.futures.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 出售状态 繁殖中的不可出售
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 14:33
 */
@Getter
@AllArgsConstructor
public enum SaleStatusEnum {

    FALSE(0,"不可出售","繁殖中","修炼中"),
    TRUE(1,"可出售","成熟","成熟"),
    ;

    Integer code;
    String desc;
    String status;
    String goodsStatus;

    public static SaleStatusEnum parse(Integer code){
        return Arrays.stream(SaleStatusEnum.values())
                .filter(item -> item.code == code.intValue())
                .findFirst()
                .orElseThrow(()-> new BizException("枚举解析异常 code=" + code));
    }

}
