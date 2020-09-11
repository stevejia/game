package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 10:48
 */
@Data
public class ReserveReqDto {


    @ApiModelProperty(value = "分页大小",dataType = "Long")
    private Long size;

    @ApiModelProperty(value = "当前页",dataType = "Long")
    private Long current;
}
