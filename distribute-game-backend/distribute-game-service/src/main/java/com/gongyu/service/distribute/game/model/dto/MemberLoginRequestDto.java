package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotNull;

/**
 *
 */
@Data
@Builder
@ApiModel(description = "会员登录请求对象")
public class MemberLoginRequestDto {
    @ApiModelProperty(value = "会员ID")
    private Integer userId;
    @ApiModelProperty(value = "会员Token")
    private String token;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "用户名")
    private String nickname;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码Key")
    private String codeKey;
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "短信验证码")
    private String smsCode;
    @ApiModelProperty(value = "再次输入密码")
    private String oncePassword;
    @ApiModelProperty(value = "邀请人ID")
    private Integer firstLeader;
    @ApiModelProperty(value = "交易密码")
    private String paypwd;
    @ApiModelProperty(value = "再次交易密码")
    private String oncePaypwd;
    @ApiModelProperty(value = "短信类型  REGISTER=注册；LOGIN=登录；FG_PWD=修改密码；FG_PPWD=修改支付密码")
    private String codeType;
    @ApiModelProperty(value = "当前登录密码")
    private String oldPassword;
    @ApiModelProperty(value = "当前操作密码")
    private String oldPayPwd;
    @ApiModelProperty(value = "会员ID")
    private Long uId;
    @ApiModelProperty(value = "操作类型 1=修改密码 2=修改交易密码")
    private Integer operationType;
    @Tolerate
    public MemberLoginRequestDto() {
    }
}
