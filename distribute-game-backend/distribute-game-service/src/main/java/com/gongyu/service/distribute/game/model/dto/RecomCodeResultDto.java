package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/2 10:22
 */
@Data
public class RecomCodeResultDto {

    @ApiModelProperty(value = "背景图url",dataType = "String")
    private String backImgUrl;

    @ApiModelProperty(value = "邀请码url",dataType = "String")
    private String inviteCodeUrl;
}
