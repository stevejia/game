package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
@ApiModel
public class RechargeRequestDto {

    @ApiModelProperty(value = "购买数量", dataType = "Float", required = true)
    @NotNull(message = "数量不能为空")
    private Float account;
    @ApiModelProperty(value = "付款凭证", dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "交易密码", dataType = "String", required = true)
    @NotNull(message = "交易密码不能为空")
    private String paypwd;
    @ApiModelProperty(value = "手机号", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "用户编号", dataType = "Integer")
    private Integer code;
}
