package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class ArticleCateSaveDto{
    @ApiModelProperty(value = "分类名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "排序", dataType = "String")
    private String orderby;
    @ApiModelProperty(value = "封面url", dataType = "String")
    private String coverUrl;
    @ApiModelProperty(value = "图标url", dataType = "String")
    private String iconUrl;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    private Integer updateTime;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer status;
}