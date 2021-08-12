package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
public class HomeDto {

    @ApiModelProperty(value = "收款方式", dataType = "Integer")
    private Integer payment = 0;
    @ApiModelProperty(value = "身份审核", dataType = "Integer")
    private Integer userIdentity = 0;
    @ApiModelProperty(value = "交易申诉", dataType = "Integer")
    private Integer appeal = 0;
    @ApiModelProperty(value = "工单处理", dataType = "Integer")
    private Integer workOrder = 0;
    @ApiModelProperty(value = "充值审核", dataType = "Integer")
    private Integer recharge = 0;
    @ApiModelProperty(value = "今日访问会员", dataType = "Integer")
    private Integer todayVisitUsers = 0;
    @ApiModelProperty(value = "今日新增会员", dataType = "Integer")
    private Integer todayAddUsers = 0;
    @ApiModelProperty(value = "今日订单数", dataType = "Integer")
    private Integer todayOrders = 0;
    @ApiModelProperty(value = "会员总数", dataType = "Integer")
    private Integer totalUsers = 0;
    @ApiModelProperty(value = "会员总资产", dataType = "BigDecimal")
    private BigDecimal totalAmount = BigDecimal.ZERO;
    @ApiModelProperty(value = "木材总数", dataType = "Integer")
    private Integer totalProducts = 0;
    @ApiModelProperty(value = "总资产", dataType = "BigDecimal")
    private BigDecimal totalMoney = BigDecimal.ZERO;
    
    @ApiModelProperty(value = "总推广收益", dataType = "BigDecimal")
    private BigDecimal totalRecomIncome = BigDecimal.ZERO;
}
