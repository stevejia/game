package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class RechargeSaveDto{
    @ApiModelProperty(value = "", dataType = "Long", required = true)
    @NotNull(message = "不能为空")
    private Long orderId;
    @ApiModelProperty(value = "会员ID", dataType = "Long", required = true)
    @NotNull(message = "会员ID不能为空")
    private Long userId;
    @ApiModelProperty(value = "会员昵称", dataType = "String")
    private String nickname;
    @ApiModelProperty(value = "充值单号", dataType = "String", required = true)
    @NotNull(message = "充值单号不能为空")
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