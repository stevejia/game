package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
@ApiModel
public class AccountStoreDetailDto {

    @ApiModelProperty(value = "描述", dataType = "String")
    private String describe;
    @ApiModelProperty(value = "茶籽数", dataType = "String")
    private String store;
    @ApiModelProperty(value = "变更时间", dataType = "String")
    private String changeTime;
    @ApiModelProperty(value = "赠送人手机号",dataType = "String")
    private String giverUserPhone;
}
