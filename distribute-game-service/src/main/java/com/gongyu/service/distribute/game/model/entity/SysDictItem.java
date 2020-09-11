package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@TableName("sys_dict_item")
@Data
@ApiModel
public class SysDictItem extends BaseDataEntity {
    @ApiModelProperty(value = "字典编码", dataType = "String")
    private String dictCode;
    @ApiModelProperty(value = "数据项编码", dataType = "String")
    private String itemCode;
    @ApiModelProperty(value = "数据项名称", dataType = "String")
    private String itemName;
    @ApiModelProperty(value = "数据项对应内容", dataType = "String")
    private String itemValue;
    @ApiModelProperty(value = "数据项对应内容参数", dataType = "List")
    @TableField(exist = false)
    private List<String> params;
}