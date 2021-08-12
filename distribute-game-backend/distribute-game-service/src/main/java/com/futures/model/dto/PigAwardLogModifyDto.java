package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class PigAwardLogModifyDto{
    @ApiModelProperty(value = "奖励日志ID", dataType = "Long")
    private Long id;
    @ApiModelProperty(value = "参与者ID", dataType = "String")
    private String joinUserList;
    @ApiModelProperty(value = "中奖者ID", dataType = "String")
    private String awardUserList;
    @ApiModelProperty(value = "级别", dataType = "Integer")
    private Integer pigId;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer changeTime;
    @ApiModelProperty(value = "", dataType = "String")
    private String pigList;
}