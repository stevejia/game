package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class UserMessageSaveDto{
    @ApiModelProperty(value = "", dataType = "Integer", required = true)
    @NotNull(message = "不能为空")
    private Integer recId;
    @ApiModelProperty(value = "用户id", dataType = "Integer", required = true)
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @ApiModelProperty(value = "消息id", dataType = "Integer", required = true)
    @NotNull(message = "消息id不能为空")
    private Integer messageId;
    @ApiModelProperty(value = "系统消息0，活动消息1", dataType = "Integer", required = true)
    @NotNull(message = "系统消息0，活动消息1不能为空")
    private Integer category;
    @ApiModelProperty(value = "查看状态：0未查看，1已查看", dataType = "Integer", required = true)
    @NotNull(message = "查看状态：0未查看，1已查看不能为空")
    private Integer status;
}