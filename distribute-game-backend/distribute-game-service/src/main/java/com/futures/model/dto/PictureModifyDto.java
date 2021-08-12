package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class PictureModifyDto{
    @ApiModelProperty(value = "图片ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "路径", dataType = "String")
    private String path;
    @ApiModelProperty(value = "图片链接", dataType = "String")
    private String url;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer categoryId;
    @ApiModelProperty(value = "文件md5", dataType = "String")
    private String md5;
    @ApiModelProperty(value = "文件 sha1编码", dataType = "String")
    private String sha1;
    @ApiModelProperty(value = "状态", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "", dataType = "String")
    private String token;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer system;
}