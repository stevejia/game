package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    @ApiModelProperty(value = "菜单权限ID")
    private Long menuId;
}