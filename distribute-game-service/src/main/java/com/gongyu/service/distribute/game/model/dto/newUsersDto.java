package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
public class newUsersDto {

    @ApiModelProperty(value = "日期", dataType = "String")
    private String dates;
    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer number;

}
