package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/22 20:34
 */
@Data
public class AppealReqDto {

    @ApiModelProperty(value = "订单编号",dataType = "String")
    private String orderNo;

    @ApiModelProperty(value = "订单价格",dataType = "BigDecimal")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "申诉人类型 1：买家2:卖家",dataType = "Integer")
    private Integer complainantType;

    @ApiModelProperty(value = "被申诉人",dataType = "String")
    private String beAppealPeople;

    @ApiModelProperty(value = "审核理由",dataType = "String")
    private String appealDesc;

    @ApiModelProperty(value = "申诉图片",dataType = "String")
    private String appealImg;
}
