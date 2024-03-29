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
public class UsersQueryDto {

    @ApiModelProperty(value = "手机号", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "用户ID", dataType = "Long")
    private Long id;
}
