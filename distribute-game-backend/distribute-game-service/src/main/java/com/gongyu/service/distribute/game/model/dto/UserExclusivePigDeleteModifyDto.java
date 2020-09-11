package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel
public class UserExclusivePigDeleteModifyDto{
    @ApiModelProperty(value = "实例删除ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "当前对应的订单id", dataType = "Integer")
    private Integer orderId;
    @ApiModelProperty(value = "猪等级", dataType = "Integer")
    private Integer pigId;
    @ApiModelProperty(value = "是否可出售,默认0不可出售，1可出售", dataType = "Integer")
    private Integer isAbleSale;
    @ApiModelProperty(value = "金额", dataType = "BigDecimal")
    private BigDecimal price;
    @ApiModelProperty(value = "收购人ID", dataType = "Integer")
    private Integer fromUserId;
    @ApiModelProperty(value = "指定用户ID", dataType = "Integer")
    private Integer appointUserId;
    @ApiModelProperty(value = "买入时间", dataType = "Integer")
    private Integer buyTime;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer endTime;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer delId;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer deltime;
}