package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SysBtnDto {
    @ApiModelProperty(value = "按钮权限名称")
    private String btnName;
    @ApiModelProperty(value = "是否选中")
    private boolean selected;
    @ApiModelProperty(value = "是否选中（1：是 0：否）")
    private int isSelect;
}
