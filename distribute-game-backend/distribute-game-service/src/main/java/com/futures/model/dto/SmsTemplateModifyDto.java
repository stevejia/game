package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SmsTemplateModifyDto{
    @ApiModelProperty(value = "短信模板ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "自增ID", dataType = "Integer")
    private Integer tplId;
    @ApiModelProperty(value = "短信签名", dataType = "String")
    private String smsSign;
    @ApiModelProperty(value = "短信模板ID", dataType = "String")
    private String smsTplCode;
    @ApiModelProperty(value = "发送短信内容", dataType = "String")
    private String tplContent;
    @ApiModelProperty(value = "短信发送场景", dataType = "String")
    private String sendScene;
    @ApiModelProperty(value = "添加时间", dataType = "Integer")
    private Integer addTime;
}