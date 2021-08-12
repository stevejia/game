package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SysRoleDto {
    @ApiModelProperty(value = "角色ID", hidden = true)
    private Long id;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;
}