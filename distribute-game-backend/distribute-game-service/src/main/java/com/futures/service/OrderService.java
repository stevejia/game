package com.futures.service;

import com.futures.common.enums.OrderTypeEnum;
import com.futures.model.entity.PigGoods;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 17:01
 */
public interface OrderService<T> {

    BaseResponse createOrder(OrderTypeEnum orderTypeEnum,T param);

    void processTask(PigGoods goods);
}
