package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_pig_goods_spell")
@Data
@ApiModel
public class PigGoodsSpell extends BaseEntity {
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer pigId;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer pigOrder;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer addtime;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer isSpell;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "合成时间", dataType = "Integer")
    private Integer isSpellTime;
}