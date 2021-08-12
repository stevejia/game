package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class SendMailSaveDto{
    @ApiModelProperty(value = "用户id", dataType = "Integer", required = true)
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @ApiModelProperty(value = "管理者id", dataType = "Integer", required = true)
    @NotNull(message = "管理者id不能为空")
    private Integer adminId;
    @ApiModelProperty(value = "站内信内容", dataType = "String", required = true)
    @NotNull(message = "站内信内容不能为空")
    private String content;
    @ApiModelProperty(value = "发送时间", dataType = "Integer", required = true)
    @NotNull(message = "发送时间不能为空")
    private Integer createTime;
    @ApiModelProperty(value = "销毁记录ID", dataType = "Integer")
    private Integer deletePigId;
}