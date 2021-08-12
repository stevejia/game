package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
@TableName("sys_menu")
public class SysMenu extends BaseDataEntity {
    @ApiModelProperty(value = "权限名称")
    private String name;
    @ApiModelProperty(value = "菜单链接页面")
    private String hrefLink;
    @ApiModelProperty(value = "请求地址")
    private String urlPath;
    @ApiModelProperty(value = "排序")
    private Integer orders;
    @ApiModelProperty(value = "层级")
    private Integer grade;
    @ApiModelProperty(value = "树路径")
    private String treePath;
    @ApiModelProperty(value = "父级ID")
    private Long parent;
    @ApiModelProperty(value = "权限类型（menu：菜单 btn：按钮）")
    private String type;
    @ApiModelProperty(value = "子权限")
    @TableField(exist = false)
    private List<SysMenu> childAuthorities = new ArrayList<>();
    @ApiModelProperty(value = "是否拥有该权限")
    @TableField(exist = false)
    private Integer isSelected;

}