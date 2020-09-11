package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class ConfigResponseDto {
    @ApiModelProperty(value = "配置名称", dataType = "String", required = true)
    private String configName;
    @ApiModelProperty(value = "配置说明", dataType = "String", required = true)
    private String title;
    @ApiModelProperty(value = "配置说明", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "配置值", dataType = "String")
    private String configValue;
}