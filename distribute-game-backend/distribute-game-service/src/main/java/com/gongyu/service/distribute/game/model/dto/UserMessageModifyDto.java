package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class UserMessageModifyDto{
    @ApiModelProperty(value = "用户消息ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer recId;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "消息id", dataType = "Integer")
    private Integer messageId;
    @ApiModelProperty(value = "系统消息0，活动消息1", dataType = "Integer")
    private Integer category;
    @ApiModelProperty(value = "查看状态：0未查看，1已查看", dataType = "Integer")
    private Integer status;
}