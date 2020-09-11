package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class FileSaveDto{
    @ApiModelProperty(value = "原始文件名", dataType = "String", required = true)
    @NotNull(message = "原始文件名不能为空")
    private String name;
    @ApiModelProperty(value = "保存名称", dataType = "String", required = true)
    @NotNull(message = "保存名称不能为空")
    private String savename;
    @ApiModelProperty(value = "文件保存路径", dataType = "String", required = true)
    @NotNull(message = "文件保存路径不能为空")
    private String savepath;
    @ApiModelProperty(value = "文件后缀", dataType = "String", required = true)
    @NotNull(message = "文件后缀不能为空")
    private String ext;
    @ApiModelProperty(value = "文件mime类型", dataType = "String", required = true)
    @NotNull(message = "文件mime类型不能为空")
    private String mime;
    @ApiModelProperty(value = "文件大小", dataType = "Integer", required = true)
    @NotNull(message = "文件大小不能为空")
    private Integer size;
    @ApiModelProperty(value = "文件md5", dataType = "String")
    private String md5;
    @ApiModelProperty(value = "文件 sha1编码", dataType = "String", required = true)
    @NotNull(message = "文件 sha1编码不能为空")
    private String sha1;
    @ApiModelProperty(value = "文件保存位置", dataType = "Integer", required = true)
    @NotNull(message = "文件保存位置不能为空")
    private Integer location;
    @ApiModelProperty(value = "上传时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "路径", dataType = "String", required = true)
    @NotNull(message = "路径不能为空")
    private String path;
}