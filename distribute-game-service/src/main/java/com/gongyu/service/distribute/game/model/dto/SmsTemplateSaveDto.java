package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class SmsTemplateSaveDto{
    @ApiModelProperty(value = "自增ID", dataType = "Integer", required = true)
    @NotNull(message = "自增ID不能为空")
    private Integer tplId;
    @ApiModelProperty(value = "短信签名", dataType = "String", required = true)
    @NotNull(message = "短信签名不能为空")
    private String smsSign;
    @ApiModelProperty(value = "短信模板ID", dataType = "String", required = true)
    @NotNull(message = "短信模板ID不能为空")
    private String smsTplCode;
    @ApiModelProperty(value = "发送短信内容", dataType = "String", required = true)
    @NotNull(message = "发送短信内容不能为空")
    private String tplContent;
    @ApiModelProperty(value = "短信发送场景", dataType = "String", required = true)
    @NotNull(message = "短信发送场景不能为空")
    private String sendScene;
    @ApiModelProperty(value = "添加时间", dataType = "Integer", required = true)
    @NotNull(message = "添加时间不能为空")
    private Integer addTime;
}