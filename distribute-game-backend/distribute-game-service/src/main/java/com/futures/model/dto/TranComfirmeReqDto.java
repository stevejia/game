package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 15:50
 */
@Data
public class TranComfirmeReqDto {

    @ApiModelProperty(value = "订单编号",dataType = "String")
    @NotBlank
    private String orderNo;

}
