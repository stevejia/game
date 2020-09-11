package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("sys_area")
@Data
@ApiModel
public class SysArea extends BaseDataEntity {
    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "全称", dataType = "String")
    private String fullName;
    @ApiModelProperty(value = "上级地区", dataType = "long")
    private Long parent;
    @ApiModelProperty(value = "树路径", dataType = "String")
    private String treePath;
    @ApiModelProperty(value = "排序", dataType = "int")
    private Integer sort;
    @ApiModelProperty(value = "是否为热门城市 （0：否 1：是）", dataType = "int")
    private Long isHot;
}
