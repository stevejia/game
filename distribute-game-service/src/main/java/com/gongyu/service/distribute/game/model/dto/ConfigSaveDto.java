package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class ConfigSaveDto{
    @ApiModelProperty(value = "配置名称", dataType = "String", required = true)
    @NotNull(message = "配置名称不能为空")
    private String configName;
    @ApiModelProperty(value = "配置类型", dataType = "Integer", required = true)
    @NotNull(message = "配置类型不能为空")
    private Integer type;
    @ApiModelProperty(value = "配置说明", dataType = "String", required = true)
    @NotNull(message = "配置说明不能为空")
    private String title;
    @ApiModelProperty(value = "配置分组", dataType = "Integer", required = true)
    @NotNull(message = "配置分组不能为空")
    private Integer configGroup;
    @ApiModelProperty(value = "配置值", dataType = "String", required = true)
    @NotNull(message = "配置值不能为空")
    private String extra;
    @ApiModelProperty(value = "配置说明", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "创建时间", dataType = "Integer", required = true)
    @NotNull(message = "创建时间不能为空")
    private Integer createTime;
    @ApiModelProperty(value = "更新时间", dataType = "Integer", required = true)
    @NotNull(message = "更新时间不能为空")
    private Integer updateTime;
    @ApiModelProperty(value = "状态", dataType = "Integer", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;
    @ApiModelProperty(value = "配置值", dataType = "String")
    private String configValue;
    @ApiModelProperty(value = "排序", dataType = "Integer", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sort;
}