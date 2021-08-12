package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class UserPaymentSaveDto{
    @ApiModelProperty(value = "1:支付宝，2:微信，3:银行卡", dataType = "Integer", required = true)
    @NotNull(message = "1:支付宝，2:微信，3:银行卡不能为空")
    private Integer type;
    @ApiModelProperty(value = "账号", dataType = "String", required = true)
    @NotNull(message = "账号不能为空")
    private String account;
    @ApiModelProperty(value = "收款人", dataType = "String", required = true)
    @NotNull(message = "收款人不能为空")
    private String name;
    @ApiModelProperty(value = "二维url", dataType = "String")
    private String qrcodeUrl;
    @ApiModelProperty(value = "开户银行", dataType = "String")
    private String bankName;
    @ApiModelProperty(value = "支行", dataType = "String")
    private String branchName;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "用户id", dataType = "Integer", required = true)
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @ApiModelProperty(value = "手机号码", dataType = "String", required = true)
    @NotNull(message = "手机号码不能为空")
    private String mobile;
    @ApiModelProperty(value = "", dataType = "String")
    private String paysalt;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer status;
}