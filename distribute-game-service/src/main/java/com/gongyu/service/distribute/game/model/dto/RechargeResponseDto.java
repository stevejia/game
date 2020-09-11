package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
@ApiModel
public class RechargeResponseDto {

    @ApiModelProperty(value = "购买数量", dataType = "Long")
    private Float account;
    @ApiModelProperty(value = "付款凭证", dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "交易密码", dataType = "String")
    private String paypwd;
    @ApiModelProperty(value = "充值状态0:待支付 1:充值成功 2:交易关闭", dataType = "Integer")
    private Integer payStatus;
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "时间", dataType = "String")
    private String addTime;
}
