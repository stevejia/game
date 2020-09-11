package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class PigGoodsSpellModifyDto{
    @ApiModelProperty(value = "商品合成ID", dataType = "Long", required = true)
    private Long id;
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