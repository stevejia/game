package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel
public class UserLevelModifyDto{
    @ApiModelProperty(value = "用户等级ID", dataType = "Long", required = true)
    private Long id;
    @ApiModelProperty(value = "表id", dataType = "Integer")
    private Integer levelId;
    @ApiModelProperty(value = "头衔名称", dataType = "String")
    private String levelName;
    @ApiModelProperty(value = "等级必要金额", dataType = "BigDecimal")
    private BigDecimal amount;
    @ApiModelProperty(value = "折扣", dataType = "Integer")
    private Integer discount;
    @ApiModelProperty(value = "头街 描述", dataType = "String")
    private String describes;
    @ApiModelProperty(value = "该会员每次购买商品最低数量", dataType = "Integer")
    private Integer lowestNum;
    @ApiModelProperty(value = "直推人数", dataType = "Integer")
    private Integer straightPush;
    @ApiModelProperty(value = "团队累计收益", dataType = "BigDecimal")
    private BigDecimal teamIncome;
    @ApiModelProperty(value = "一级推广奖", dataType = "BigDecimal")
    private BigDecimal extensionOne;
    @ApiModelProperty(value = "二级推广奖", dataType = "BigDecimal")
    private BigDecimal extensionTow;
    @ApiModelProperty(value = "三级推广奖", dataType = "BigDecimal")
    private BigDecimal extensionThree;
    @ApiModelProperty(value = "团队奖", dataType = "BigDecimal")
    private BigDecimal teamAward;
}