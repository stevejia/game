package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 11:13
 */
@Data
public class ReserveResultDto {

    @ApiModelProperty(value = "英雄名称",dataType = "String")
    private String pigName;

    @ApiModelProperty(value = "预约时间 yyyy-mm-dd hh:mm:ss",dataType = "String")
    private String reserveDate;

    @ApiModelProperty(value = "抢购状态",dataType = "String")
    private String robStatus;

    @ApiModelProperty(value = "花费茶籽",dataType = "Integer")
    private Integer payPoints;
}
