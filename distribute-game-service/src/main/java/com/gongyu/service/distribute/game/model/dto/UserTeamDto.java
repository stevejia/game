package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
public class UserTeamDto {

    @ApiModelProperty(value = "团队粉丝人数", dataType = "Integer")
    private Integer oneLevel = 0;
    @ApiModelProperty(value = "团队正式用户人数", dataType = "Integer")
    private Integer twoLevel = 0;
    @ApiModelProperty(value = "团队初级合伙人人数", dataType = "Integer")
    private Integer threeLevel = 0;
    @ApiModelProperty(value = "团队中级合伙人人数", dataType = "Integer")
    private Integer fourLevel = 0;
    @ApiModelProperty(value = "团队高级合伙人人数", dataType = "Integer")
    private Integer fiveLevel = 0;
    @ApiModelProperty(value = "团队联合合伙人人数", dataType = "Integer")
    private Integer sixLevel = 0;
}
