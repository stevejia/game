package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 10:43
 */
@Data
public class AuthRechargeDto {

    @ApiModelProperty(value = "充值记录ID",dataType = "Long")
    @NotNull(message = "充值记录id不能为空")
    private Long id;

    @ApiModelProperty(value = "审核状态 0 待审核 1审核通过 2审核失败",dataType = "Integer")
    @NotNull(message = "审核状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "审核备注")
    private String remark;
}
