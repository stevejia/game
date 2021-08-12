package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class UserLockLogSaveDto{
    @ApiModelProperty(value = "", dataType = "Integer", required = true)
    @NotNull(message = "不能为空")
    private Integer lockId;
    @ApiModelProperty(value = "封号的用户ID", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "封号状态：0为未封，1为已封", dataType = "Integer")
    private Integer lockStatus;
    @ApiModelProperty(value = "0为后台操作，1为定时器操作", dataType = "Integer")
    private Integer lockType;
    @ApiModelProperty(value = "封号时间", dataType = "Integer")
    private Integer lockTime;
}