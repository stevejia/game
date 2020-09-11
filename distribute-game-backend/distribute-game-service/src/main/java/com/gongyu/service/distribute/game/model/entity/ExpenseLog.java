package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("zp_expense_log")
@Data
@ApiModel
public class ExpenseLog extends BaseEntity {

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