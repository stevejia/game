package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_pig_reservation")
@Data
@ApiModel
public class PigReservation extends BaseEntity {
    @ApiModelProperty(value = "", dataType = "Integer")
    private Long pigId;
    @ApiModelProperty(value = "预约时间", dataType = "Integer")
    private Long reservationTime;
    @ApiModelProperty(value = "预约状态0未抢到1已抢到", dataType = "Integer")
    private Integer reservationStatus;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Long userId;
    @ApiModelProperty(value = "预约所需福分", dataType = "Integer")
    private Integer payPoints;
    @ApiModelProperty(value = "场次", dataType = "Integer")
    private Long reservationScene;
    @ApiModelProperty(value = "是否点击开抢", dataType = "Integer")
    private Integer isClickBuy;
}