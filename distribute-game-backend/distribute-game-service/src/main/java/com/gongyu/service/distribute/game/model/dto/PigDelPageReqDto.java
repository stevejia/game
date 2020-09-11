package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/4 18:01
 */
@Data
public class PigDelPageReqDto {

    @ApiModelProperty(value = "用户手机号",dataType = "String")
    private String userPhone;

    @ApiModelProperty(value = "仙子等级",dataType = "Long")
    private Long pigId;

    @ApiModelProperty(value = "裂变仙子ID",dataType = "Long")
    private Long delId;
}
