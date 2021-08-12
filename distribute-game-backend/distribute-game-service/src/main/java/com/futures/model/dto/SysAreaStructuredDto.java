package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
public class SysAreaStructuredDto {
    @ApiModelProperty(value = "区域编码（区域ID）", dataType = "String")
    private Long code;
    @ApiModelProperty(value = "区域名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "子区域", dataType = "List")
    private List<SysAreaStructuredDto> sub;
}
