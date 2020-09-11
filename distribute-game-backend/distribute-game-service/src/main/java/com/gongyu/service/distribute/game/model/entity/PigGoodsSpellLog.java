package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@TableName("zp_pig_goods_spell_log")
@Data
@ApiModel
public class PigGoodsSpellLog extends BaseEntity {
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