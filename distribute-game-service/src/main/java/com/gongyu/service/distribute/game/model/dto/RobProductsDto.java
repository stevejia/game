package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 19:14
 */
@Data
public class RobProductsDto {

    @ApiModelProperty(value = "用户ID",dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "精灵ID",dataType = "Long")
    @NotNull(message = "精灵ID不能为空")
    private Long pigId;
}
