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

@TableName("zp_recharge")
@Data
@ApiModel
public class Recharge extends BaseEntity {

    @TableField(exist = false)
    protected Long id;
    protected Serializable pkVal() {
        return this.getOrderId();
    }

    @TableId(value="order_id",type = IdType.AUTO)
    @ApiModelProperty(value = "", dataType = "Long")
    private Long orderId;
    @ApiModelProperty(value = "会员ID", dataType = "Long")
    private Long userId;
    @ApiModelProperty(value = "会员昵称", dataType = "String")
    private String nickname;
    @ApiModelProperty(value = "充值单号", dataType = "String")
    private String orderSn;
    @ApiModelProperty(value = "充值金额", dataType = "Float")
    private Float account;
    @ApiModelProperty(value = "支付时间", dataType = "Integer")
    private Long addTime;
    @ApiModelProperty(value = "充值状态0:待审核 1:审核成功呢 2:审核失败", dataType = "Integer")
    private Integer payStatus;
    @ApiModelProperty(value = "上传支付凭证", dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "修改时间", dataType = "Integer")
    private Long verifierTime;
}