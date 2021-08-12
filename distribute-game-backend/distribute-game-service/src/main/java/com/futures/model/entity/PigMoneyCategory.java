package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@TableName("zp_pig_money_category")
@Data
@ApiModel
public class PigMoneyCategory extends BaseEntity {
    @ApiModelProperty(value = "名称", dataType = "String")
    private String goodsName;
    @ApiModelProperty(value = "最小金额", dataType = "BigDecimal")
    private BigDecimal smallMoney;
    @ApiModelProperty(value = "最大金额", dataType = "BigDecimal")
    private BigDecimal largeMoney;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    private Long uptedaTime;
}