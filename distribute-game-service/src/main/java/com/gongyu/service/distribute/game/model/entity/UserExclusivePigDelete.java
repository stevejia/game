package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@TableName("zp_user_exclusive_pig_delete")
@Data
@ApiModel
public class UserExclusivePigDelete extends BaseEntity {
    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private Long userId;
    @ApiModelProperty(value = "当前对应的订单id", dataType = "Integer")
    private Long orderId;
    @ApiModelProperty(value = "猪等级", dataType = "Integer")
    private Long pigId;
    @ApiModelProperty(value = "是否可出售,默认0不可出售，1可出售", dataType = "Integer")
    private Integer isAbleSale;
    @ApiModelProperty(value = "金额", dataType = "BigDecimal")
    private BigDecimal price;
    @ApiModelProperty(value = "收购人ID", dataType = "Integer")
    private Long fromUserId;
    @ApiModelProperty(value = "指定用户ID", dataType = "Integer")
    private Long appointUserId;
    @ApiModelProperty(value = "买入时间", dataType = "Integer")
    private Long buyTime;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Long endTime;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Long delId;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Long deltime;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remark;
}