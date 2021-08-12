package com.futures.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/24 14:21
 */
@Getter
@AllArgsConstructor
public enum LevelIncomEnum {

    FAN(1,"粉丝",true,true,false,new BigDecimal("0.00")),

    vip(2,"正式会员",true,true,false,new BigDecimal("0.00")),

    ELEMENTAR(3,"初级合伙人",true,true,true,new BigDecimal("0.01")),

    intermed(4,"中级合伙人",true,true,true,new BigDecimal("0.05")),

    high(5,"高级合伙人",true,true,true,new BigDecimal("0.10")),

    BOSS(6,"联创合伙人",true,true,false,new BigDecimal("0.00")),
    ;

    Integer code;

    String desc;

    /**
     * 是否拥有产品收益
     */
    boolean product;

    /**
     * 是否拥有推广收益
     */
    boolean recom;

    /**
     * 是否拥有团队收益
     */
    boolean team;

    /**
     * 团队收益提成率
     */
    BigDecimal teamDrawRate;

    public static LevelIncomEnum parse(Integer code){
        return Arrays.stream(LevelIncomEnum.values())
                .filter(item -> item.code == code)
                .findFirst()
                .orElseThrow(() -> new BizException("不能被解析的code："+code));
    }
}
