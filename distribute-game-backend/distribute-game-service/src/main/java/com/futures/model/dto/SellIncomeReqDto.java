package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/30 19:27
 */
@Data
public class SellIncomeReqDto {

    @ApiModelProperty(value = "出售推荐收益数量",dataType = "BigDecimal")
    @NotNull(message = "出售推荐收益数量不能为空")
    private BigDecimal sellNum;

    @ApiModelProperty(value = "交易密码",dataType = "String")
    @NotBlank(message = "交易密码")
    private String payPwd;


}
