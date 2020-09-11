package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Builder
@Data
@ApiModel
public class UsersTreeResponseDto {
    @ApiModelProperty(value = "表id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "会员等级", dataType = "Integer")
    private String level;
    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer counts;
    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String nickname;

    @Tolerate
    public UsersTreeResponseDto() {
    }

}