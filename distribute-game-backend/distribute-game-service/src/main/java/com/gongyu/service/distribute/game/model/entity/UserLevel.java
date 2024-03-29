package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@TableName("zp_user_level")
@Data
@ApiModel
public class UserLevel extends BaseEntity {

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