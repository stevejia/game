package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@TableName("zp_user_lock_log")
@Data
@ApiModel
public class UserLockLog extends BaseEntity {

    @TableField(exist = false)
    protected Long id;
    protected Serializable pkVal() {
        return this.getLockId();
    }

    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer lockId;
    @ApiModelProperty(value = "封号的用户ID", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "封号状态：0为未封，1为已封", dataType = "Integer")
    private Integer lockStatus;
    @ApiModelProperty(value = "0为后台操作，1为定时器操作", dataType = "Integer")
    private Integer lockType;
    @ApiModelProperty(value = "封号时间", dataType = "Integer")
    private Integer lockTime;
}