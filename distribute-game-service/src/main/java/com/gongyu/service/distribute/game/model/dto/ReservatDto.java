package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 16:23
 */
@Data
public class ReservatDto {

    @ApiModelProperty(value = "预约商品ID",dataType = "Long")
    @NotNull(message = "预约商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "用户ID",dataType = "Long")
    private Long userId;
}
