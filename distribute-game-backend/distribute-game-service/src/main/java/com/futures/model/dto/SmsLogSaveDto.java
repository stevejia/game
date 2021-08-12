package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class SmsLogSaveDto{
    @ApiModelProperty(value = "手机号", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "session_id", dataType = "String")
    private String sessionId;
    @ApiModelProperty(value = "发送时间", dataType = "Integer")
    private Integer addTime;
    @ApiModelProperty(value = "验证码", dataType = "String")
    private String code;
    @ApiModelProperty(value = "发送状态,1:成功,0:失败", dataType = "Integer", required = true)
    @NotNull(message = "发送状态,1:成功,0:失败不能为空")
    private Integer status;
    @ApiModelProperty(value = "短信内容", dataType = "String")
    private String msg;
    @ApiModelProperty(value = "发送场景,1:用户注册,2:找回密码,3:客户下单,4:客户支付,5:商家发货,6:身份验证", dataType = "Integer")
    private Integer scene;
    @ApiModelProperty(value = "发送短信异常内容", dataType = "String")
    private String errorMsg;
    @ApiModelProperty(value = "是否已验证", dataType = "Integer")
    private Integer isCheck;
}