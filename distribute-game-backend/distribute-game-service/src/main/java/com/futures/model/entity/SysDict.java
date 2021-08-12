package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("sys_dict")
@Data
@ApiModel
public class SysDict extends BaseDataEntity {
    @ApiModelProperty(value = "字典编码", dataType = "String")
    private String dictCode;
    @ApiModelProperty(value = "字典名称", dataType = "String")
    private String dictName;
}