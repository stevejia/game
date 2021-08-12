package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
@TableName("sys_role")
public class SysRole extends BaseDataEntity {
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "角色描述")
    private String description;
    @ApiModelProperty(value = "是否内置（1：是 0：否）",hidden = true)
    private Boolean isSystem;
}