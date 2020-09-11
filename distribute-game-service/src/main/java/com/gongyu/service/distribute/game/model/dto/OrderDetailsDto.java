package com.gongyu.service.distribute.game.model.dto;

import com.gongyu.service.distribute.game.model.entity.UserPayment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/11 19:13
 */
@Data
public class OrderDetailsDto {

    @ApiModelProperty(value = "订单时间",dataType = "Date")
    private Date orderTime;

    @ApiModelProperty(value = "转让方",dataType = "Stirng")
    private String transferName;

    @ApiModelProperty(value = "转让手机号",dataType = "String")
    private String transferMobile;

    @ApiModelProperty(value = "领养方",dataType = "String")
    private String adoptName;

    @ApiModelProperty(value = "领养方手机号",dataType = "String")
    private String adoptMobile;

    @ApiModelProperty(value = "金额",dataType = "BigDecimal")
    private BigDecimal money;

    @ApiModelProperty(value = "支付方式",dataType = "UserPaymentLog")
    private List<UserPayment> payments;

    @ApiModelProperty(value = "支付凭证",dataType = "String")
    private String payImageUrl;

}
