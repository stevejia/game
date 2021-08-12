package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SendMailModifyDto{
    @ApiModelProperty(value = "管理员发送站内信ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "管理者id", dataType = "Integer")
    private Integer adminId;
    @ApiModelProperty(value = "站内信内容", dataType = "String")
    private String content;
    @ApiModelProperty(value = "发送时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "销毁记录ID", dataType = "Integer")
    private Integer deletePigId;
}