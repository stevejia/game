package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class GraphVerifyCodeDto {
    @ApiModelProperty(value = "验证码类型")
    private String codeKey;
    @ApiModelProperty(value = "验证码")
    private String codeValue;
}
