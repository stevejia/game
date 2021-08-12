package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel
public class DistributLevelModifyDto{
    @ApiModelProperty(value = "分销商等级ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer levelId;
    @ApiModelProperty(value = "分销等级类别", dataType = "Integer")
    private Integer levelType;
    @ApiModelProperty(value = "一级佣金比例", dataType = "BigDecimal")
    private BigDecimal rate1;
    @ApiModelProperty(value = "二级佣金比例", dataType = "BigDecimal")
    private BigDecimal rate2;
    @ApiModelProperty(value = "三级佣金比例", dataType = "BigDecimal")
    private BigDecimal rate3;
    @ApiModelProperty(value = "升级条件", dataType = "BigDecimal")
    private BigDecimal orderMoney;
    @ApiModelProperty(value = "", dataType = "String")
    private String levelName;
}