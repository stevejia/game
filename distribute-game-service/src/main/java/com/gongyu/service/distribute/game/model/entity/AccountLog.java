package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("zp_account_log")
@Data
@ApiModel
public class AccountLog extends BaseEntity {

    @TableField(exist = false)
    protected Long id;

    @Override
    protected Serializable pkVal() {
        return this.getLogId();
    }

    @ApiModelProperty(value = "日志id", dataType = "Long")
    @TableId(type = IdType.AUTO)
    private Long logId;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Long userId;
    @ApiModelProperty(value = "用户金额", dataType = "BigDecimal")
    private BigDecimal userMoney;
    @ApiModelProperty(value = "冻结金额", dataType = "BigDecimal")
    private BigDecimal frozenMoney;
    @ApiModelProperty(value = "支付积分", dataType = "Integer")
    private Integer payPoints;
    @ApiModelProperty(value = "DOGE", dataType = "Integer")
    private Integer dogeMoney;
    @ApiModelProperty(value = "PIG", dataType = "Integer")
    private Integer pigCurrency;
    @ApiModelProperty(value = "智能合约收益", dataType = "BigDecimal")
    private BigDecimal contractRevenue;
    @ApiModelProperty(value = "变动时间", dataType = "Integer")
    private Long changeTime;
    @ApiModelProperty(value = "手机号", dataType = "String")
    @TableField(exist = false)
    private String mobile;
    @ApiModelProperty(value = "描述", dataType = "String")
    private String descs;
    @ApiModelProperty(value = "订单编号", dataType = "String")
    private String orderSn;
    @ApiModelProperty(value = "订单id", dataType = "Integer")
    private Integer orderId;
    @ApiModelProperty(value = "1:购买,2:转赠(福分记录),3:充值(福分记录),4:预约/领养(福分记录),5:抢购(福分记录),6:注册(福分记录),7:未抢购成功退回(福分记录),8:智能合约收益(收益财分),9:出售财分,10:管理员操作,11:后台充值,12:团队奖,13推荐奖,15提现pig币,16提现BTC币,17后台充值财分,18后台充值福分,19后台充值doge币,20后台充值pig币,21增加智能合约收益,22增加doge币,23增加pig币", dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "猪ID", dataType = "Integer")
    private Long pigId;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer laststatus;


    @ApiModelProperty(value = "赠送者ID",dataType = "Long")
    private Long giverUserId;

    @ApiModelProperty(value = "推广收益",dataType = "BigDecimal")
    @TableField(exist = false)
    private BigDecimal recomIncome;
}