package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@TableName("zp_user_payment_err")
@Data
@ApiModel
public class UserPaymentErr extends BaseEntity {
    @ApiModelProperty(value = "1:支付宝，2:微信，3:银行卡", dataType = "Integer")
    private Integer paymentid;
    @ApiModelProperty(value = "账号", dataType = "String")
    private String account;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "", dataType = "Date")
    private Date dayTime;
}