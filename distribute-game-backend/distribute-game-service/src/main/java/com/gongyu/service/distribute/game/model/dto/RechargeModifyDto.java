package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class RechargeModifyDto{
    @ApiModelProperty(value = "用户充值记录ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "", dataType = "Long")
    private Long orderId;
    @ApiModelProperty(value = "会员ID", dataType = "Long")
    private Long userId;
    @ApiModelProperty(value = "会员昵称", dataType = "String")
    private String nickname;
    @ApiModelProperty(value = "充值单号", dataType = "String")
    private String orderSn;
    @ApiModelProperty(value = "充值金额", dataType = "Float")
    private Float account;
    @ApiModelProperty(value = "支付时间", dataType = "Integer")
    private Integer addTime;
    @ApiModelProperty(value = "充值状态0:待支付 1:充值成功 2:交易关闭", dataType = "Integer")
    private Integer payStatus;
    @ApiModelProperty(value = "上传支付凭证", dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer verifierTime;
}