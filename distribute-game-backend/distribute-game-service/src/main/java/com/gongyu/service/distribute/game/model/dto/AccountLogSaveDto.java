package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@ApiModel
public class AccountLogSaveDto {
    @ApiModelProperty(value = "日志id", dataType = "Long", required = true)
    @NotNull(message = "日志id不能为空")
    private Long logId;
    @ApiModelProperty(value = "用户id", dataType = "Integer", required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;
    @ApiModelProperty(value = "用户金额", dataType = "BigDecimal")
    private BigDecimal userMoney;
    @ApiModelProperty(value = "冻结金额", dataType = "BigDecimal")
    private BigDecimal frozenMoney;
    @ApiModelProperty(value = "支付茶籽", dataType = "Integer", required = true)
    @NotNull(message = "支付茶籽不能为空")
    private Integer payPoints;
    @ApiModelProperty(value = "DOGE", dataType = "Integer", required = true)
    @NotNull(message = "DOGE不能为空")
    private Integer dogeMoney;
    @ApiModelProperty(value = "PIG", dataType = "Integer", required = true)
    @NotNull(message = "PIG不能为空")
    private Integer pigCurrency;
    @ApiModelProperty(value = "智能合约收益", dataType = "BigDecimal")
    private BigDecimal contractRevenue;
    @ApiModelProperty(value = "变动时间", dataType = "Integer", required = true)
    @NotNull(message = "变动时间不能为空")
    private Long changeTime;
    @ApiModelProperty(value = "描述", dataType = "String", required = true)
    @NotNull(message = "描述不能为空")
    private String desc;
    @ApiModelProperty(value = "订单编号", dataType = "String")
    private String orderSn;
    @ApiModelProperty(value = "订单id", dataType = "Integer")
    private Integer orderId;
    @ApiModelProperty(value = "1:购买,2:转赠(福分记录),3:充值(福分记录),4:预约/领养(福分记录),5:抢购(福分记录),6:注册(福分记录),7:未抢购成功退回(福分记录),8:智能合约收益(收益财分),9:出售财分,10:管理员操作,11:后台充值,12:团队奖,13推荐奖,15提现pig币,16提现BTC币,17后台充值财分,18后台充值福分,19后台充值doge币,20后台充值pig币,21增加智能合约收益,22增加doge币,23增加pig币", dataType = "Integer", required = true)
    @NotNull(message = "1:购买,2:转赠(福分记录),3:充值(福分记录),4:预约/领养(福分记录),5:抢购(福分记录),6:注册(福分记录),7:未抢购成功退回(福分记录),8:智能合约收益(收益财分),9:出售财分,10:管理员操作,11:后台充值,12:团队奖,13推荐奖,15提现pig币,16提现BTC币,17后台充值财分,18后台充值福分,19后台充值doge币,20后台充值pig币,21增加智能合约收益,22增加doge币,23增加pig币不能为空")
    private Integer type;
    @ApiModelProperty(value = "猪ID", dataType = "Integer")
    private Long pigId;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer laststatus;

    @Tolerate
    public AccountLogSaveDto() {
    }
}