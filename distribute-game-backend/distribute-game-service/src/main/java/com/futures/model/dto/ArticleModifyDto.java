package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class ArticleModifyDto{
    @ApiModelProperty(value = "文章ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "文章标题", dataType = "String")
    private String title;
    @ApiModelProperty(value = "文章类别", dataType = "Integer")
    private Integer cateId;
    @ApiModelProperty(value = "文章图片", dataType = "String")
    private String photo;
    @ApiModelProperty(value = "文件地址", dataType = "String")
    private String file;
    @ApiModelProperty(value = "文章描述", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "文章关键字", dataType = "String")
    private String keyword;
    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;
    @ApiModelProperty(value = "浏览量", dataType = "Integer")
    private Integer views;
    @ApiModelProperty(value = "文章类型(1:图文，2:视频)", dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "是否推荐", dataType = "Integer")
    private Integer isTui;
    @ApiModelProperty(value = "发布人", dataType = "String")
    private String writer;
    @ApiModelProperty(value = "", dataType = "String")
    private String ip;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    private Integer updateTime;
    @ApiModelProperty(value = "状态", dataType = "Integer")
    private Integer status;
}