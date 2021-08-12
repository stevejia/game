package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
public class UserTeamLevelDto {

    @ApiModelProperty(value = "等级", dataType = "Integer")
    private Integer level;
    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer number;

}
