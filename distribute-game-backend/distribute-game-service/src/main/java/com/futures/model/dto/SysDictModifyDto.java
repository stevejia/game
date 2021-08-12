package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class SysDictModifyDto {
    @ApiModelProperty(value = "字典ID", dataType = "String", required = true)
    @NotNull(message = "字典ID不能为空")
    private Long id;
    @ApiModelProperty(value = "字典编码", dataType = "String")
    @NotNull(message = "字典编码不能为空")
    private String dictCode;
    @ApiModelProperty(value = "字典名称", dataType = "String")
    @NotNull(message = "字典名称不能为空")
    private String dictName;
}
