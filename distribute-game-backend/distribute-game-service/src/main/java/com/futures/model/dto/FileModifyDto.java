package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class FileModifyDto{
    @ApiModelProperty(value = "文件ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "原始文件名", dataType = "String")
    private String name;
    @ApiModelProperty(value = "保存名称", dataType = "String")
    private String savename;
    @ApiModelProperty(value = "文件保存路径", dataType = "String")
    private String savepath;
    @ApiModelProperty(value = "文件后缀", dataType = "String")
    private String ext;
    @ApiModelProperty(value = "文件mime类型", dataType = "String")
    private String mime;
    @ApiModelProperty(value = "文件大小", dataType = "Integer")
    private Integer size;
    @ApiModelProperty(value = "文件md5", dataType = "String")
    private String md5;
    @ApiModelProperty(value = "文件 sha1编码", dataType = "String")
    private String sha1;
    @ApiModelProperty(value = "文件保存位置", dataType = "Integer")
    private Integer location;
    @ApiModelProperty(value = "上传时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "路径", dataType = "String")
    private String path;
}