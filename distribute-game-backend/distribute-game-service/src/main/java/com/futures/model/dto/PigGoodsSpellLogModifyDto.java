package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel
public class PigGoodsSpellLogModifyDto{
    @ApiModelProperty(value = "商品合成日志ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "", dataType = "String")
    private String pigIdStr;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer addtime;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "", dataType = "BigDecimal")
    private BigDecimal money;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer isPay;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer payTime;
}