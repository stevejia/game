package com.futures.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ApiModel
public class PigMoneyCategoryModifyDto{
    @ApiModelProperty(value = "商品价值区间ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "名称", dataType = "String")
    private String goodsName;
    @ApiModelProperty(value = "最小金额", dataType = "BigDecimal")
    private BigDecimal smallMoney;
    @ApiModelProperty(value = "最大金额", dataType = "BigDecimal")
    private BigDecimal largeMoney;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date uptedaTime;
}