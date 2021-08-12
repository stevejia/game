package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("zp_distribut_level")
@Data
@ApiModel
public class DistributLevel extends BaseEntity {

    @TableField(exist = false)
    protected Long id;
    protected Serializable pkVal() {
        return this.getLevelId();
    }

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