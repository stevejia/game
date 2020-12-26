package com.gongyu.service.distribute.game.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 10:28
 */
@Getter
@AllArgsConstructor
public enum IncomeTypeEnum {

    PAY(1,"购买"),
    GIVE_AWAY(2,"转赠(福分记录)"),
    TOP_UP(3,"充值"),
    RESERVAT(4,"预约/领养(福分记录)"),
    PANIC_BUY(5,"抢购(福分记录)"),
    REGIST(6,"注册(福分记录)"),
    NOT_PANIC_BUY(7,"未抢购成功退回(福分记录)"),
    CONTRACT_INCOM(8,"智能合约收益(收益财分)"),
    SELL_INCOM(9,"出售财分"),
    SYS_action(10,"管理员操作"),
    BACK_TOP_UP(11,"后台充值"),
    TEAM_WILL(12,"团队奖"),
    RECOMM_WILL(13,"推荐奖"),
    PROMOTE(17,"后台充值财分"),
    SYS_POINT(18,"后台充值茶籽"),
    USER_PAY_POINTS(19, "用户当天茶籽");

    ;

    Integer code;

    String desc;


}
