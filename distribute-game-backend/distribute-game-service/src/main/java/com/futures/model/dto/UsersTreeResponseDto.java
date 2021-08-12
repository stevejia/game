package com.futures.model.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Builder
@Data
@ApiModel
public class UsersTreeResponseDto {
    @ApiModelProperty(value = "表id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "会员等级", dataType = "Integer")
    private String level;
    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer counts;
    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String nickname;

    @ApiModelProperty(value = "资产", dataType = "BigDecimal")
    private BigDecimal assets;
    
    @ApiModelProperty(value = "有效产品数量", dataType = "Integer")
    private Integer prodCount;
    
    @ApiModelProperty(value = "3天抢购次数", dataType = "Integer")
    private Integer threeDaysRub;
    
    @ApiModelProperty(value = "7天抢购次数", dataType = "Integer")
    private Integer sevenDaysRub;
    @Tolerate
    public UsersTreeResponseDto() {
    }

}