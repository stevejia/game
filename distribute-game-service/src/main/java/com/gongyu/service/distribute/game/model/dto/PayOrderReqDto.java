package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/12 15:20
 */
@Data
public class PayOrderReqDto {

    @ApiModelProperty(value = "订单号",dataType = "String")
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "付款凭证Url",dataType = "String")
    @NotBlank(message = "付款凭证URL不能为空")
    private String payCertUrl;

    @ApiModelProperty(value = "支付密码",dataType = "String")
    @NotBlank(message = "支付密码为空")
    private String payPwd;
}
