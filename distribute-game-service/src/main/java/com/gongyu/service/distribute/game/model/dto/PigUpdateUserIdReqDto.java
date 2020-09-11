package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/4 18:03
 */
@Data
public class PigUpdateUserIdReqDto {

    @ApiModelProperty(value = "精灵实例ID", dataType = "Long", required = true)
    @NotNull(message = "精灵ID不能为空")
    private Long id;

    @ApiModelProperty(value = "指定用户ID", dataType = "Long")
    @NotNull(message = "指定用户ID不能为空")
    private Long appointUserId;
}
