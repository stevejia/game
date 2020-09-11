package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_user_identity")
@Data
@ApiModel
public class UserIdentity extends BaseEntity {
    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "真实姓名", dataType = "String")
    private String realName;
    @ApiModelProperty(value = "手机号", dataType = "String")
    @TableField(exist = false)
    private String mobile;
    @ApiModelProperty(value = "身份证号", dataType = "String")
    private String identity;
    @ApiModelProperty(value = "0未认证；1认证失败；2认证成功", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "添加时间", dataType = "Integer")
    private Long addTime;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    private Long updateTime;
    @ApiModelProperty(value = "用户昵称",dataType = "String")
    private String nickname;
}