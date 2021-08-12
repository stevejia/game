package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_pig_award_log")
@Data
@ApiModel
public class PigAwardLog extends BaseEntity {
    @ApiModelProperty(value = "", dataType = "String")
    private String joinUserList;
    @ApiModelProperty(value = "", dataType = "String")
    private String awardUserList;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Long pigId;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Long changeTime;
    @ApiModelProperty(value = "", dataType = "String")
    private String pigList;
    @ApiModelProperty(value = "开奖状态 0:已开奖 1:未开奖",dataType = "Integer")
    private Integer openResult;
}