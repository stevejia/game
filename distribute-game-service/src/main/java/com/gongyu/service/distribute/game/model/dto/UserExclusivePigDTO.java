package com.gongyu.service.distribute.game.model.dto;

import com.gongyu.service.distribute.game.model.entity.UserExclusivePig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 17:48
 */
@Data
public class UserExclusivePigDTO extends UserExclusivePig {

    @ApiModelProperty(value = "仙子所属用户手机号", dataType = "String")
    private String userPhone;

    @ApiModelProperty(value = "仙子等级", dataType = "String")
    private String goodsName;

    @ApiModelProperty(value = "出售状态",dataType = "String")
    private String saleStatus;

    @ApiModelProperty(value = "精灵状态",dataType = "String")
    private String isPigLockStatus;

    @ApiModelProperty(value = "产品类型",dataType = "String")
    private String goodsType;

}
