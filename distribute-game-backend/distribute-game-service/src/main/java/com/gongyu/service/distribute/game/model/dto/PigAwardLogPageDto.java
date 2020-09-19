package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/5 17:23
 */
@Data
public class PigAwardLogPageDto extends PigAwardLogModifyDto{

    @ApiModelProperty(value = "场次等级", dataType = "String")
    private String goodsName;

    @ApiModelProperty(value = "预约人数",dataType = "Integer")
    private Integer beforePerson;

    @ApiModelProperty(value = "预约玩家实际开抢人数",dataType = "Integer")
    private Integer clickPerson;

    @ApiModelProperty(value = "实际抢购人数",dataType = "Integer")
    private Integer robPerson;

    @ApiModelProperty(value = "中奖人数",dataType = "Integer")
    private Integer luckyPerson;

    @ApiModelProperty(value = "中奖几率",dataType = "String")
    private String luckyChance;

    @ApiModelProperty(value = "开奖时间",dataType = "Date")
//    @DateTimeFormat(pattern = "YYYY-DD-MM hh:mm:ss")
    private String drawTime;
    
    @ApiModelProperty(value = "当天可售出的产品数量",dataType = "Integer")
    private Integer canSalePig;

}
