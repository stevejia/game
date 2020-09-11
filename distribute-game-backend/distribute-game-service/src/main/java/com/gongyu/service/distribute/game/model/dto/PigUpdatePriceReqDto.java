package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/4 18:03
 */
@Data
public class PigUpdatePriceReqDto {

    @ApiModelProperty(value = "精灵实例ID", dataType = "Long", required = true)
    @NotNull(message = "精灵ID不能为空")
    private Long id;

    @ApiModelProperty(value = "金额", dataType = "BigDecimal")
    @NotNull(message = "金额不能为空")
    private BigDecimal price;
}
