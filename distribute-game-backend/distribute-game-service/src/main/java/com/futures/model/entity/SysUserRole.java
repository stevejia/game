package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
@TableName("sys_user_role")
public class SysUserRole extends BaseDataEntity {
    @ApiModelProperty(value = "人员ID")
    private Long userId;
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
}