package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/22 16:38
 */
@Data
public class AdoptReqDto {

    @ApiModelProperty(value = "查询类型 1:领养中 2：已领养3：取消/申诉4：已裂变",dataType = "Integer")
    private Integer queryType;

}
