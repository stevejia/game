package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class UserIdentitySaveDto{
    @ApiModelProperty(value = "用户ID", dataType = "Integer", required = true)
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
    @ApiModelProperty(value = "真实姓名", dataType = "String", required = true)
    @NotNull(message = "真实姓名不能为空")
    private String realName;
    @ApiModelProperty(value = "身份证号", dataType = "String", required = true)
    @NotNull(message = "身份证号不能为空")
    private String identity;
    @ApiModelProperty(value = "0待审核1审核通过-1审核不通过", dataType = "Integer", required = true)
    @NotNull(message = "0待审核1审核通过-1审核不通过不能为空")
    private Integer status;
    @ApiModelProperty(value = "添加时间", dataType = "Integer", required = true)
    @NotNull(message = "添加时间不能为空")
    private Integer addTime;
    @ApiModelProperty(value = "更新时间", dataType = "Integer", required = true)
    @NotNull(message = "更新时间不能为空")
    private Integer updateTime;
}