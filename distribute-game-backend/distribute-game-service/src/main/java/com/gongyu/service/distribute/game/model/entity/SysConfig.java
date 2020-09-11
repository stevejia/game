package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
@TableName("sys_config")
public class SysConfig extends BaseDataEntity {
    @ApiModelProperty(value = "配置键")
    private String configKey;
    @ApiModelProperty(value = "配置值")
    private String configValue;
    @ApiModelProperty(value = "备注")
    private String remark;

}
