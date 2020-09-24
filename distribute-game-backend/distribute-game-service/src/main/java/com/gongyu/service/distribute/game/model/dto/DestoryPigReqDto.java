package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/4 17:59
 */
@Data
public class DestoryPigReqDto {

    @ApiModelProperty(value = "木材实例的ID")
    @NotNull(message = "木材实例ID不能为空")
    private Long id;

    @ApiModelProperty(value = "备注")
    private String remark;
}
