package com.gongyu.service.distribute.game.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gongyu.service.distribute.game.model.entity.Recharge;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/5 20:49
 */
@Data
public class RechargeDto extends Recharge {

    @ApiModelProperty(value = "昵称",dataType = "String")
    private String nickName;

    @ApiModelProperty(value = "用户手机号",dataType = "String")
    private String userPhone;

    @ApiModelProperty(value = "查询时间",dataType = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd hh:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private Date queryDate;
}
