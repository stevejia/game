package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class PigReservationSaveDto{
    @ApiModelProperty(value = "", dataType = "Integer", required = true)
    @NotNull(message = "不能为空")
    private Integer pigId;
    @ApiModelProperty(value = "预约时间", dataType = "Integer")
    private Integer reservationTime;
    @ApiModelProperty(value = "预约状态0未抢到1已抢到", dataType = "Integer", required = true)
    @NotNull(message = "预约状态0未抢到1已抢到不能为空")
    private Integer reservationStatus;
    @ApiModelProperty(value = "用户id", dataType = "Integer", required = true)
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @ApiModelProperty(value = "预约所需福分", dataType = "Integer", required = true)
    @NotNull(message = "预约所需福分不能为空")
    private Integer payPoints;
    @ApiModelProperty(value = "场次", dataType = "Integer", required = true)
    @NotNull(message = "场次不能为空")
    private Integer reservationScene;
    @ApiModelProperty(value = "是否点击开抢", dataType = "Integer")
    private Integer isClickBuy;
}