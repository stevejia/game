package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class ConfigModifyDto {
    @ApiModelProperty(value = "配置ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "配置名称", dataType = "String")
    private String configName;
    @ApiModelProperty(value = "配置类型", dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "配置说明", dataType = "String")
    private String title;
    @ApiModelProperty(value = "配置分组", dataType = "Integer")
    private Integer configGroup;
    @ApiModelProperty(value = "配置值", dataType = "String")
    private String extra;
    @ApiModelProperty(value = "配置说明", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    private Integer updateTime;
    @ApiModelProperty(value = "状态", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "配置值", dataType = "String")
    private String configValue;
    @ApiModelProperty(value = "排序", dataType = "Integer")
    private Integer sort;
}