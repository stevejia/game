package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class PigAppealModifyDto {
    @ApiModelProperty(value = "交易申诉ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "申诉人id", dataType = "Integer")
    private Long userId;
    @ApiModelProperty(value = "订单id", dataType = "Integer")
    private Long orderId;
    @ApiModelProperty(value = "订单编号", dataType = "String")
    private String orderNo;
    @ApiModelProperty(value = "手机号", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "申诉原因", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "订单时间", dataType = "Integer")
    private Integer addTime;
    @ApiModelProperty(value = "", dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "0未审核;1;申诉通过;2 申诉不通过;", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "审核时间", dataType = "Integer")
    private Integer updateTime;
    @ApiModelProperty(value = "申诉人;1:买家;2卖家", dataType = "Integer")
    private Integer complainant;

    @ApiModelProperty(value = "申诉人昵称",dataType = "String")
    private String nickname;
}