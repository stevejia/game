package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/4 18:04
 */
@Data
public class PrizeTodayReqDto {

    @ApiModelProperty(value = "用户手机号",dataType = "String")
    private String userPhone;

    @ApiModelProperty(value = "出售人ID",dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "仙子等级",dataType = "Long")
    private Long pigId;
}
