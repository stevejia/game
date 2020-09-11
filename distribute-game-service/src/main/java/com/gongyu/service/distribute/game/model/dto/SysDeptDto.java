package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SysDeptDto {
    @ApiModelProperty(value = "部门ID", hidden = true)
    private Long id;
    @ApiModelProperty(value = "上级部门ID，一级部门为0")
    private Long parentId;
    @ApiModelProperty(value = "部门名称")
    private String name;
    @ApiModelProperty(value = "排序")
    private Integer sort;
}