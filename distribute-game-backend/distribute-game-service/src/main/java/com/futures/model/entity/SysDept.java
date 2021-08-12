package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
@TableName("sys_dept")
public class SysDept extends BaseDataEntity {
    @ApiModelProperty(value = "上级部门ID，一级部门为0")
    private Long parentId;
    @ApiModelProperty(value = "部门名称")
    private String name;
    @ApiModelProperty(value = "排序")
    private Integer rank;
    @ApiModelProperty(value = "二级部门集合")
    @TableField(exist = false)
    private List<SysDept> deptList;
}