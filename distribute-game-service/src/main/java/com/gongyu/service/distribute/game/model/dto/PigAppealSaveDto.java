package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class PigAppealSaveDto{
    @ApiModelProperty(value = "申诉人id", dataType = "Integer", required = true)
    @NotNull(message = "申诉人id不能为空")
    private Integer userId;
    @ApiModelProperty(value = "订单id", dataType = "Integer", required = true)
    @NotNull(message = "订单id不能为空")
    private Integer orderId;
    @ApiModelProperty(value = "申诉原因", dataType = "String", required = true)
    @NotNull(message = "申诉原因不能为空")
    private String remark;
    @ApiModelProperty(value = "订单时间", dataType = "Integer", required = true)
    @NotNull(message = "订单时间不能为空")
    private Integer addTime;
    @ApiModelProperty(value = "", dataType = "String", required = true)
    @NotNull(message = "不能为空")
    private String imgUrl;
    @ApiModelProperty(value = "0未审核;1;申诉通过;-1申诉不通过;2申诉失效", dataType = "Integer", required = true)
    @NotNull(message = "0未审核;1;申诉通过;-1申诉不通过;2申诉失效不能为空")
    private Integer status;
    @ApiModelProperty(value = "审核时间", dataType = "Integer", required = true)
    @NotNull(message = "审核时间不能为空")
    private Integer updateTime;
    @ApiModelProperty(value = "申诉人;1:买家;2卖家", dataType = "Integer", required = true)
    @NotNull(message = "申诉人;1:买家;2卖家不能为空")
    private Integer complainant;
}