package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_file")
@Data
@ApiModel
public class File extends BaseEntity {
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