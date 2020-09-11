package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class UserPaymentLogModifyDto{
    @ApiModelProperty(value = "用户收款支付方ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "1:支付宝，2:微信，3:银行卡", dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "账号", dataType = "String")
    private String account;
    @ApiModelProperty(value = "收款人", dataType = "String")
    private String name;
    @ApiModelProperty(value = "二维url", dataType = "String")
    private String qrcodeUrl;
    @ApiModelProperty(value = "开户银行", dataType = "String")
    private String bankName;
    @ApiModelProperty(value = "支行", dataType = "String")
    private String branchName;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "", dataType = "String")
    private String paysalt;
    @ApiModelProperty(value = "0 为未审核  1为已经审核 -1 审核不通过", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "操作的id", dataType = "Integer")
    private Integer paymentId;
    @ApiModelProperty(value = "", dataType = "String")
    private String action;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer upTime;
}