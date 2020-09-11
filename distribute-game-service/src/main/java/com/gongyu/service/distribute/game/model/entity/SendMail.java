package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_send_mail")
@Data
@ApiModel
public class SendMail extends BaseEntity {
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "管理者id", dataType = "Integer")
    private Integer adminId;
    @ApiModelProperty(value = "站内信内容", dataType = "String")
    private String content;
    @ApiModelProperty(value = "发送时间", dataType = "Integer")
    private Long createTime;
    @ApiModelProperty(value = "销毁记录ID", dataType = "Integer")
    private Integer deletePigId;
}