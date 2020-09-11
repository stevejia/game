package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel
public class UserPaymentErrModifyDto{
    @ApiModelProperty(value = "用户收款支付方ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "1:支付宝，2:微信，3:银行卡", dataType = "Integer")
    private Integer paymentid;
    @ApiModelProperty(value = "账号", dataType = "String")
    private String account;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "", dataType = "Date")
    private Date dayTime;
}