package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ApiModel
public class UserPaymentErrSaveDto{
    @ApiModelProperty(value = "1:支付宝，2:微信，3:银行卡", dataType = "Integer", required = true)
    @NotNull(message = "1:支付宝，2:微信，3:银行卡不能为空")
    private Integer paymentid;
    @ApiModelProperty(value = "账号", dataType = "String", required = true)
    @NotNull(message = "账号不能为空")
    private String account;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "", dataType = "Date")
    private Date dayTime;
}