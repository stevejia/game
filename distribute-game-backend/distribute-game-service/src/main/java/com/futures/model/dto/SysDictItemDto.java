package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SysDictItemDto {
    @ApiModelProperty(value = "数据项编码", dataType = "String")
    private String itemCode;
    @ApiModelProperty(value = "数据项名称", dataType = "String")
    private String itemName;
    @ApiModelProperty(value = "数据项对应内容", dataType = "String")
    private String itemValue;
}
