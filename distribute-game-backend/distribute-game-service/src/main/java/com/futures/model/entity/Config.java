package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_config")
@Data
@ApiModel
public class Config extends BaseEntity {
    @ApiModelProperty(value = "配置名称", dataType = "String")
    private String configName;
    @ApiModelProperty(value = "配置类型", dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "配置说明 1输入框 2单选 3图片 4富文本 5链接 6文件", dataType = "String")
    private String title;
    @ApiModelProperty(value = "配置分组", dataType = "Integer")
    private Integer configGroup;
    @ApiModelProperty(value = "配置值", dataType = "String")
    private String extra;
    @ApiModelProperty(value = "配置说明", dataType = "String")
    private String remark;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Long createTime;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    private Long updateTime;
    @ApiModelProperty(value = "状态", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "配置值 0关 1开", dataType = "String")
    private String configValue;
    @ApiModelProperty(value = "排序", dataType = "Integer")
    private Integer sort;
}