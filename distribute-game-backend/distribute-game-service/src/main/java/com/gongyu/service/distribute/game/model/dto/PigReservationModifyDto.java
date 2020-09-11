package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class PigReservationModifyDto{
    @ApiModelProperty(value = "预约记录ID", dataType = "Long")
    private Long id;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer pigId;
    @ApiModelProperty(value = "预约时间", dataType = "Integer")
    private Integer reservationTime;
    @ApiModelProperty(value = "预约状态0未抢到1已抢到", dataType = "Integer")
    private Integer reservationStatus;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "预约所需福分", dataType = "Integer")
    private Integer payPoints;
    @ApiModelProperty(value = "场次", dataType = "Integer")
    private Integer reservationScene;
    @ApiModelProperty(value = "是否点击开抢", dataType = "Integer")
    private Integer isClickBuy;

    @ApiModelProperty(value = "预约人手机号", dataType = "String")
    private String userPhone;

    @ApiModelProperty(value = "仙子等级", dataType = "String")
    private String goodsName;

}