package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class PigAwardLogSaveDto{
    @ApiModelProperty(value = "", dataType = "String", required = true)
    @NotNull(message = "不能为空")
    private String joinUserList;
    @ApiModelProperty(value = "", dataType = "String", required = true)
    @NotNull(message = "不能为空")
    private String awardUserList;
    @ApiModelProperty(value = "", dataType = "Integer", required = true)
    @NotNull(message = "不能为空")
    private Integer pigId;
    @ApiModelProperty(value = "", dataType = "Integer", required = true)
    @NotNull(message = "不能为空")
    private Integer changeTime;
    @ApiModelProperty(value = "", dataType = "String", required = true)
    @NotNull(message = "不能为空")
    private String pigList;
}