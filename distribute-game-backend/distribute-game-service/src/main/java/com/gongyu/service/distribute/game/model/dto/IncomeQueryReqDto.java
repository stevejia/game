package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/28 18:29
 */
@Data
public class IncomeQueryReqDto {

    @ApiModelProperty(value = "查询收入类型 1:累计收入 2：推荐收入",dataType = "Integer")
    @NotNull(message = "查询类型不能为空")
    private Integer type;
}
