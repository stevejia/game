package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel
public class SysUserLogDto {
    @ApiModelProperty(value = "类名", dataType = "String")
    private String className;
    @ApiModelProperty(value = "方法名", dataType = "String")
    private String methodName;
    @ApiModelProperty(value = "方法参数", dataType = "String")
    private String argument;
    @ApiModelProperty(value = "方法描述", dataType = "String")
    private String memo;
    @ApiModelProperty(value = "模块名", dataType = "String")
    private String modelName;
    @ApiModelProperty(value = "IP地址", dataType = "String")
    private String ip;
    @ApiModelProperty(value = "操作时间", dataType = "Date")
    private Date operationTime;
    @ApiModelProperty(value = "日志类型（1：成功 0：异常）", dataType = "String")
    private String flag;
    @ApiModelProperty(value = "用户ID", dataType = "Long")
    private Long userId;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String userName;
    @ApiModelProperty(value = "异常错误信息", dataType = "String")
    private String error;
}