package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SysMenuCacheDto {
    @ApiModelProperty(value = "权限ID")
    private Long id;
    @ApiModelProperty(value = "请求地址")
    private String urlPath;
}