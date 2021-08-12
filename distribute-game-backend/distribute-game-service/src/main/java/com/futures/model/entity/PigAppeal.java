package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_pig_appeal")
@Data
@ApiModel
public class PigAppeal extends BaseEntity {
    @ApiModelProperty(value = "申诉人id", dataType = "Integer")
    private Long userId;
    @ApiModelProperty(value = "订单id", dataType = "Long")
    private Long orderId;
    @ApiModelProperty(value = "订单编号", dataType = "String")
    @TableField(exist = false)
    private String orderNo;
    @ApiModelProperty(value = "申诉原因", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "手机号", dataType = "String")
    @TableField(exist = false)
    private String mobile;
    @ApiModelProperty(value = "被申诉人手机号", dataType = "String")
    @TableField(exist = false)
    private String otherMobile;
    @ApiModelProperty(value = "订单时间", dataType = "Long")
    private Long addTime;
    @ApiModelProperty(value = "凭证", dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "0未审核;1;申诉通过;2申诉不通过;3申诉失败", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "审核时间", dataType = "Integer")
    private Long updateTime;
    @ApiModelProperty(value = "申诉人;1:买家;2卖家", dataType = "Integer")
    private Integer complainant;
}