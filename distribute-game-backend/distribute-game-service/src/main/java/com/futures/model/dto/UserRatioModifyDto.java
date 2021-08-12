package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class UserRatioModifyDto{
    @ApiModelProperty(value = "月分红设置ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "满足的最低金额", dataType = "Integer")
    private Integer lowermoney;
    @ApiModelProperty(value = "满足的最高金额", dataType = "Integer")
    private Integer topmoney;
    @ApiModelProperty(value = "百分比", dataType = "Integer")
    private Integer proportion;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "用户等级", dataType = "Integer")
    private Integer userLevel;
}