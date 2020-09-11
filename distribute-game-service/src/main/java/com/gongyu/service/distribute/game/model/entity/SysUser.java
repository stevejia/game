package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
@TableName("sys_user")
public class SysUser extends BaseDataEntity {
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "密码",hidden = true)
    private String password;
    @ApiModelProperty(value = "二级密码")
    private String twoLevelPassword;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "头像")
    private String headImg;
    @ApiModelProperty(value = "部门ID")
    private Long deptId;
    @ApiModelProperty(value = "部门名称")
    @TableField(exist = false)
    private String deptName;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "是否激活（1：是 0：否）")
    private int isEnabled;
    @ApiModelProperty(value = "是否锁定（1：是 0：否）")
    private int isLocked;
    @ApiModelProperty(value = "锁定日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lockedDate;
    @ApiModelProperty(value = "最后登录日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginDate;
    @ApiModelProperty(value = "连续登录失败次数")
    private Integer loginFailureCount;
    @ApiModelProperty(value = "最后登录IP")
    private String loginIp;
    @ApiModelProperty(value = "最后登录UA")
    private String userAgent;
    @ApiModelProperty(value = "最后登录session key")
    private String loginSessionKey;
    @ApiModelProperty(value = "角色ID")
    @TableField(exist = false)
    private Long roleId;
    @ApiModelProperty(value = "角色名称")
    @TableField(exist = false)
    private String roleName;

}