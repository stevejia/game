package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class UserIdentityModifyDto {
    @ApiModelProperty(value = "身份审核ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "真实姓名", dataType = "String")
    private String realName;
    @ApiModelProperty(value = "手机号", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "身份证号", dataType = "String")
    private String identity;
    @ApiModelProperty(value = "0待审核1审核通过-1审核不通过", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "添加时间", dataType = "Integer")
    private Integer addTime;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    private Integer updateTime;
}