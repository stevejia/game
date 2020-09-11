package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@TableName("zp_user_message")
@Data
@ApiModel
public class UserMessage extends BaseEntity {

    @TableField(exist = false)
    protected Long id;
    protected Serializable pkVal() {
        return this.getRecId();
    }

    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer recId;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "消息id", dataType = "Integer")
    private Integer messageId;
    @ApiModelProperty(value = "系统消息0，活动消息1", dataType = "Integer")
    private Integer category;
    @ApiModelProperty(value = "查看状态：0未查看，1已查看", dataType = "Integer")
    private Integer status;
}