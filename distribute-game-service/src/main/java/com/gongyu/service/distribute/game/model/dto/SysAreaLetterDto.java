package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SysAreaLetterDto {
    @ApiModelProperty(value = "区域ID", dataType = "String")
    private Long areaId;
    @ApiModelProperty(value = "区域名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "拼音首字母", dataType = "String")
    private String firstLetter;
}
