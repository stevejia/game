package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel
public class ExpenseLogModifyDto{
    @ApiModelProperty(value = "平台支出记录日ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "操作管理员", dataType = "Integer")
    private Integer adminId;
    @ApiModelProperty(value = "支出金额", dataType = "BigDecimal")
    private BigDecimal money;
    @ApiModelProperty(value = "支出类型0用户提现,1订单退款,2其他", dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "日志记录时间", dataType = "Integer")
    private Integer addtime;
    @ApiModelProperty(value = "业务关联ID", dataType = "Integer")
    private Integer logTypeId;
    @ApiModelProperty(value = "涉及会员id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "涉及商家id", dataType = "Integer")
    private Integer userName;
}