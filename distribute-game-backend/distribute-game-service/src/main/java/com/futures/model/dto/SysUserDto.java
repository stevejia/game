package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class SysUserDto {
    @ApiModelProperty(value = "主键ID")
    private Long id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "部门ID")
    private Long deptId;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "头像")
    private String headImg;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "二级密码")
    private String twoLevelPassword;
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    @ApiModelProperty(value = "是否激活（1：是 0：否）")
    private int isEnabled;
    @ApiModelProperty(value = "是否锁定（1：是 0：否）")
    private int isLocked;
    @ApiModelProperty(value = "用户访问key")
    private String key;
}
