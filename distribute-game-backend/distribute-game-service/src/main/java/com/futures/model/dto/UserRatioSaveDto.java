package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class UserRatioSaveDto{
    @ApiModelProperty(value = "满足的最低金额", dataType = "Integer", required = true)
    @NotNull(message = "满足的最低金额不能为空")
    private Integer lowermoney;
    @ApiModelProperty(value = "满足的最高金额", dataType = "Integer", required = true)
    @NotNull(message = "满足的最高金额不能为空")
    private Integer topmoney;
    @ApiModelProperty(value = "百分比", dataType = "Integer", required = true)
    @NotNull(message = "百分比不能为空")
    private Integer proportion;
    @ApiModelProperty(value = "创建时间", dataType = "Integer", required = true)
    @NotNull(message = "创建时间不能为空")
    private Integer createTime;
    @ApiModelProperty(value = "用户等级", dataType = "Integer", required = true)
    @NotNull(message = "用户等级不能为空")
    private Integer userLevel;
}