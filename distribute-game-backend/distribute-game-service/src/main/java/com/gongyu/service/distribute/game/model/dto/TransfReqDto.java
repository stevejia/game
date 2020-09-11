package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 13:39
 */
@Data
public class TransfReqDto {

    @ApiModelProperty(value = "查询类型 1:待转让 2：转让中 3：已完成 4：取消/申诉",dataType = "Integer")
    private Integer queryType;

    @ApiModelProperty(value = "分页大小",dataType = "Long")
    private Long size;

    @ApiModelProperty(value = "当前页",dataType = "Long")
    private Long current;
}
