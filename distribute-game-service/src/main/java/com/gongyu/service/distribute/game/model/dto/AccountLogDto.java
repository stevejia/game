package com.gongyu.service.distribute.game.model.dto;

import com.gongyu.service.distribute.game.model.entity.AccountLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
public class AccountLogDto extends AccountLog {

    @ApiModelProperty(value = "收入类型",dataType = "String")
    private String incomTypeName;
}
