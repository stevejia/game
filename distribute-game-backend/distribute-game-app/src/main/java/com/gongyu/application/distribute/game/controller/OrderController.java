package com.gongyu.application.distribute.game.controller;

import com.alibaba.fastjson.JSON;
import com.gongyu.service.distribute.game.common.enums.OrderTypeEnum;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import com.gongyu.service.distribute.game.service.OrderService;
import com.gongyu.service.distribute.game.service.PigOrderService;
import com.gongyu.service.distribute.game.service.PigReservationService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/11 18:01
 */
@Slf4j
@RestController
@Api(tags = "订单交易管理")
@RequestMapping("order")
public class OrderController {

    @Autowired
    private PigReservationService reservationService;
    @Autowired
    private PigOrderService orderService;

    @Autowired
    private OrderService createOrderService;

    @ApiOperation(value = "预约",response = BaseResponse.class)
    @PostMapping("reservat")
    public BaseResponse reservat(@Valid ReservatDto param){
        param.setUserId(WebUtils.getCurrentUserId());
        return reservationService.reservat(param);
    }

    @ApiOperation(value = "抢产品",response = BaseResponse.class)
    @PostMapping("robProducts")
    public BaseResponse robProducts(@Valid RobProductsDto param){
        log.info("robProducts 抢产品 req param:{}", JSON.toJSONString(param));
        param.setUserId(WebUtils.getCurrentUserId());
        BaseResponse resp = reservationService.robProducts(param);
        log.info("robProducts 抢产品 result resp:{}", JSON.toJSONString(resp));

        return resp;
    }

    @ApiOperation(value = "开奖状态",response = PigOrder.class)
    @PostMapping("luckStatus")
    public BaseResponse luckStatus(Long goodsId){
        return orderService.luckStatus(WebUtils.getCurrentUserId(), goodsId);
    }

    @ApiOperation(value = "获取订单详情",response = OrderDetailsDto.class)
    @PostMapping("orderDetails")
    public BaseResponse orderDetails(String orderNo){
        return orderService.orderDetails(orderNo);
    }

    @ApiOperation(value = "下单",response = BaseResponse.class)
    @PostMapping("payOrder")
    public BaseResponse payOrder(@Valid PayOrderReqDto param){
        return createOrderService.createOrder(OrderTypeEnum.NORMAL_POSSESSION, param);
    }

    @ApiOperation(value = "交易成功确认", response = BaseResponse.class)
    @PostMapping("tranComfirme")
    public BaseResponse tranComfirme(@Valid TranComfirmeReqDto param) {
        return orderService.tranComfirme(param, WebUtils.getCurrentUserId());
    }
}
