package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@TableName("zp_pig_order")
@Data
@ApiModel
public class PigOrder extends BaseEntity {

    @TableField(exist = false)
    protected Long id;
    protected Serializable pkVal() {
        return this.getOrderId();
    }

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "", dataType = "Integer")
    private Long orderId;
    @ApiModelProperty(value = "生成订单时间", dataType = "Integer")
    private Long establishTime;
    @ApiModelProperty(value = "订单编号", dataType = "String")
    private String pigOrderSn;
    @ApiModelProperty(value = "交易状态,0为冻结，1为交易中，2交易完成 3交易超时 5重新支付", dataType = "Integer")
    private Integer payStatus;
    @ApiModelProperty(value = "出售人", dataType = "Integer")
    private Long sellUserId;
    @ApiModelProperty(value = "购买人", dataType = "Integer")
    private Long purchaseUserId;
    @ApiModelProperty(value = "猪等级", dataType = "Integer")
    private Long pigLevel;
    @ApiModelProperty(value = "猪价格", dataType = "BigDecimal")
    private BigDecimal pigPrice;
    @ApiModelProperty(value = "用户所属猪的ID", dataType = "Integer")
    private Long pigId;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Long userId;
    @ApiModelProperty(value = "申诉时间", dataType = "Integer")
    private Long appealTime;
    @ApiModelProperty(value = "支付凭证", dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "订单结束时间", dataType = "Integer")
    private Long endTime;
    @ApiModelProperty(value = "0为交易  1为抽奖的订单 2为出售财分  3系统赠送星球", dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "凭证单号", dataType = "String")
    private String paynum;
    @ApiModelProperty(value = "卖家确认交易状态 0：未确认 1：已确认",dataType = "Integer")
    private Integer sellConfirmStatus;
    @ApiModelProperty(value = "买家确认交易状态  0：未确认 1：已确认",dataType = "Integer")
    private Integer buyConfirmStatus;
}