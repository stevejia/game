package com.futures.controller;

import com.futures.common.enums.OrderTypeEnum;
import com.futures.model.dto.FreePigGoodsReqDto;
import com.futures.service.OrderService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 16:59
 */
@RestController
@RequestMapping("order")
@Api(tags = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "【商品管理】产品赠送", notes = "【商品管理】产品赠送")
    @PostMapping("freePigGoods")
    @SysUserLog(module = "商品管理", action = "产品赠送")
    public BaseResponse freePigGoods(FreePigGoodsReqDto param) {
        return orderService.createOrder(OrderTypeEnum.SYS_HANDSEL,param);
    }
}
