package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 *
 */
@Data
@Builder
@ApiModel(description = "会员登录返回对象")
public class MemberLoginResponseDto {
    @ApiModelProperty(value = "会员ID")
    private Long userId;
    @ApiModelProperty(value = "会员Token")
    private String token;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "会员编号")
    private Integer code;
    @Tolerate
    public MemberLoginResponseDto() {
    }
}
