package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ApiModel
public class PigOrderSaveDto{
    @ApiModelProperty(value = "", dataType = "Integer", required = true)
    @NotNull(message = "不能为空")
    private Integer orderId;
    @ApiModelProperty(value = "生成订单时间", dataType = "Integer")
    private Integer establishTime;
    @ApiModelProperty(value = "订单编号", dataType = "String")
    private String pigOrderSn;
    @ApiModelProperty(value = "交易状态,0为冻结，1为交易中，2交易完成", dataType = "Integer")
    private Integer payStatus;
    @ApiModelProperty(value = "出售人", dataType = "Integer")
    private Integer sellUserId;
    @ApiModelProperty(value = "购买人", dataType = "Integer")
    private Integer purchaseUserId;
    @ApiModelProperty(value = "猪等级", dataType = "Integer")
    private Integer pigLevel;
    @ApiModelProperty(value = "猪价格", dataType = "BigDecimal")
    private BigDecimal pigPrice;
    @ApiModelProperty(value = "用户所属猪的ID", dataType = "Integer")
    private Integer pigId;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "申诉时间", dataType = "Integer")
    private Integer appealTime;
    @ApiModelProperty(value = "支付凭证", dataType = "String", required = true)
    @NotNull(message = "支付凭证不能为空")
    private String imgUrl;
    @ApiModelProperty(value = "订单结束时间", dataType = "Integer")
    private Integer endTime;
    @ApiModelProperty(value = "0为交易  1为抽奖的订单 2为出售财分  3系统赠送星球", dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "凭证单号", dataType = "String")
    private String paynum;
}