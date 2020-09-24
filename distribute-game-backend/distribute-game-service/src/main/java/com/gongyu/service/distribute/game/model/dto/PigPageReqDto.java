package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/4 18:02
 */
@Data
public class PigPageReqDto {

    @ApiModelProperty(value = "用户手机号",dataType = "String")
    private String userPhone;

    @ApiModelProperty(value = "所属人ID",dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "仙子等级",dataType = "Long")
    private Long pigId;

    @ApiModelProperty(value = "状态 0：繁殖中 1 成熟",dataType = "Long")
    private Integer saleStatus;
    
    @ApiModelProperty(value = "是否锁定 0 未锁定 1已锁定", dataType = "Integer")
    private Integer isPigLock;
}
