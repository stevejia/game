package com.gongyu.service.distribute.game.model.dto;

import com.gongyu.service.distribute.game.model.entity.UserExclusivePigDel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 19:57
 */
@Data
public class UserExclusivePigDelDTO extends UserExclusivePigDel {

    @ApiModelProperty(value = "仙子所属用户手机号", dataType = "String")
    private String userPhone;

    @ApiModelProperty(value = "仙子等级", dataType = "String")
    private String goodsName;

    @ApiModelProperty(value = "出售状态",dataType = "String")
    private String saleStatus;

    @ApiModelProperty(value = "分裂时间",dataType = "Long")
    private Long splitTime;
}
