package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/4 18:00
 */
@Data
public class FreePigGoodsReqDto {


    @ApiModelProperty(value = "商品价格",dataType = "BigDecimal")
    @NotNull(message = "商品价格不能为空")
    private BigDecimal goodPrice;

    @ApiModelProperty(value = "赠送用户ID",dataType = "Long")
    @NotNull(message = "赠送用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "赠送数量",dataType = "Integer")
    @NotNull(message = "赠送数量不能为空")
    private Integer number;

    @ApiModelProperty(value = "二级交易密码",dataType = "String")
    @NotBlank(message = "二级交易密码不能为空")
    private String twoPassword;
}
