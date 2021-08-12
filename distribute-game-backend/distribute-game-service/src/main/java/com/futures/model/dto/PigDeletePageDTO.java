package com.futures.model.dto;

import com.futures.model.entity.UserExclusivePigDelete;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 16:42
 */
@Data
public class PigDeletePageDTO extends UserExclusivePigDelete {

    @ApiModelProperty(value = "仙子所属用户手机号", dataType = "String")
    private String userPhone;

    @ApiModelProperty(value = "仙子等级", dataType = "String")
    private String goodsName;

    @ApiModelProperty(value = "出售状态",dataType = "String")
    private String saleStatus;
}
