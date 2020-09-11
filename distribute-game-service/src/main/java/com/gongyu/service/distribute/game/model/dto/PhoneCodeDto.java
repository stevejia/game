package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class PhoneCodeDto {

    @ApiModelProperty(value = "过期时间日期，时间戳格式（参数类型 long）", dataType = "long")
    private Long expiresDate;
    @ApiModelProperty(value = "过期时间，单位秒（参数类型 long）", dataType = "long")
    private Long expiresTime;
    @ApiModelProperty(value = "验证码（开发阶段使用 1234）", dataType = "String")
    private String code;
    @ApiModelProperty(value = "该手机号是否已注册 1-已注册 0-未注册", dataType = "int")
    private Integer beExist;
}
