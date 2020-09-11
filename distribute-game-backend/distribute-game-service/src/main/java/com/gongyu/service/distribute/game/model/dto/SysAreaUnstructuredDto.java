package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SysAreaUnstructuredDto {
    @ApiModelProperty(value = "区域ID", dataType = "String")
    private String areaId;
    @ApiModelProperty(value = "父ID", dataType = "String")
    private String parentId;
    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "排序", dataType = "String")
    private String sort;
}
