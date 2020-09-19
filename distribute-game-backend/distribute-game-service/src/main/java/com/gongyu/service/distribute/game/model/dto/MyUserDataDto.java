package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/18 18:43
 */
@Data
public class MyUserDataDto {

    @ApiModelProperty(value = "用户ID",dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "昵称",dataType = "String")
    private String userNmae;

    @ApiModelProperty(value = "头像url",dataType = "String")
    private String avatarUrl;

    @ApiModelProperty(value = "手机号",dataType = "String")
    private String mobile;

    @ApiModelProperty(value = "积分",dataType = "Integer")
    private Integer points;

    @ApiModelProperty(value = "用户等级",dataType = "String")
    private String levelName;

    @ApiModelProperty(value = "总资产",dataType = "BigDecimal")
    private BigDecimal asset;

    @ApiModelProperty(value = "累计收入",dataType = "BigDecimal")
    private BigDecimal accumulIncome;

    @ApiModelProperty(value = "推荐收入",dataType = "BigDecimal")
    private BigDecimal recomIncome;
    
    @ApiModelProperty(value = "用户编号",dataType = "Integer")
    private Integer code;


}
