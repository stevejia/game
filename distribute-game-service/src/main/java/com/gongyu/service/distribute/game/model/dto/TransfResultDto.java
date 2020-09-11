package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 14:32
 */
@Data
public class TransfResultDto {

    @ApiModelProperty(value = "英雄编号-订单号",dataType = "String")
    private String orderNo;

    @ApiModelProperty(value = "精灵名称",dataType = "String")
    private String pigName;

    @ApiModelProperty(value = "精灵图片url",dataType = "String")
    private String pigUrl;

    @ApiModelProperty(value = "最小价格",dataType = "BigDecimal")
    private BigDecimal smallPrice;

    @ApiModelProperty(value = "最大价格",dataType = "BigDecimal")
    private BigDecimal largePrice;

    @ApiModelProperty(value = "合约天数",dataType = "Integer")
    private Integer contractDay;

    @ApiModelProperty(value = "合约收益率",dataType = "BigDecimal")
    private BigDecimal contractRate;

    @ApiModelProperty(value = "转让时间",dataType = "String")
    private String contractTime;

    @ApiModelProperty(value = "订单状态: 交易状态,0为冻结，1为交易中，2交易完成 3交易超时 5重新支付",dataType = "Integer")
    private Integer orderStatus;

    @ApiModelProperty(value = "买家确认状态0：未确认 1：已确认")
    private Integer buyComfirm;

    @ApiModelProperty(value = "卖家确认状态0：未确认 1：已确认")
    private Integer sellComfirm;

    @ApiModelProperty(value = "申诉理由",dataType = "String")
    private String appealDesc;

    @ApiModelProperty(value = "申诉时间YYY-MM-DD HH:mm:ss",dataType = "String")
    private String appealDate;

    @ApiModelProperty(value = "领养方name",dataType = "String")
    private String buyName;


    @ApiModelProperty(value = "付款凭证",dataType = "String")
    private String payImageUrl;

    @ApiModelProperty(value = "精灵价格",dataType = "BigDecimal")
    private BigDecimal goodsPrice;

}
