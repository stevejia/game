package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@TableName("zp_sms_template")
@Data
@ApiModel
public class SmsTemplate extends BaseEntity {

    @TableField(exist = false)
    protected Long id;
    protected Serializable pkVal() {
        return this.getTplId();
    }

    @ApiModelProperty(value = "自增ID", dataType = "Integer")
    private Integer tplId;
    @ApiModelProperty(value = "短信签名", dataType = "String")
    private String smsSign;
    @ApiModelProperty(value = "短信模板ID", dataType = "String")
    private String smsTplCode;
    @ApiModelProperty(value = "发送短信内容", dataType = "String")
    private String tplContent;
    @ApiModelProperty(value = "短信发送场景", dataType = "String")
    private String sendScene;
    @ApiModelProperty(value = "添加时间", dataType = "Integer")
    private Integer addTime;
}