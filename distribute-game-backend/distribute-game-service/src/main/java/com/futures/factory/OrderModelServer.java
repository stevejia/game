package com.futures.factory;

import com.futures.common.enums.OrderTypeEnum;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 17:19
 */
public interface OrderModelServer<T> {

    boolean isSupport(OrderTypeEnum orderTypeEnum);

    void verif(T param);

    void createPig(T param);

    BaseResponse createOrder(T param);
}
