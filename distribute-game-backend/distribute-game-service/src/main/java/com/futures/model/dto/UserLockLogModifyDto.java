package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class UserLockLogModifyDto{
    @ApiModelProperty(value = "用户封号记ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "", dataType = "Integer")
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