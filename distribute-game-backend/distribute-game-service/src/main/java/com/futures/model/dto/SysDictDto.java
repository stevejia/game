package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
public class SysDictDto {
    @ApiModelProperty(value = "字典编码", dataType = "String")
    private String dictCode;
    @ApiModelProperty(value = "字典名称", dataType = "String")
    private String dictName;
    @ApiModelProperty(value = "字典明细子项", dataType = "SysDictItemDto")
    private List<SysDictItemDto> sysDictItemList;
}
