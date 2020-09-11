package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class PictureSaveDto{
    @ApiModelProperty(value = "路径", dataType = "String", required = true)
    @NotNull(message = "路径不能为空")
    private String path;
    @ApiModelProperty(value = "图片链接", dataType = "String", required = true)
    @NotNull(message = "图片链接不能为空")
    private String url;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer categoryId;
    @ApiModelProperty(value = "文件md5", dataType = "String", required = true)
    @NotNull(message = "文件md5不能为空")
    private String md5;
    @ApiModelProperty(value = "文件 sha1编码", dataType = "String", required = true)
    @NotNull(message = "文件 sha1编码不能为空")
    private String sha1;
    @ApiModelProperty(value = "状态", dataType = "Integer", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;
    @ApiModelProperty(value = "创建时间", dataType = "Integer", required = true)
    @NotNull(message = "创建时间不能为空")
    private Integer createTime;
    @ApiModelProperty(value = "", dataType = "String")
    private String token;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer system;
}