package com.futures.factory.orderModel;

import com.futures.common.enums.OrderTypeEnum;
import com.futures.factory.OrderModelServer;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

/**
 * 默认处理器
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 17:27
 */
@Slf4j
@Service
public class DefaultOrderProcess implements OrderModelServer<T> {

    @Override
    public boolean isSupport(OrderTypeEnum orderTypeEnum) {
        return false;
    }

    @Override
    public void verif(T param) {
        log.info("默认处理器 DefaultOrderModel verif ...");
    }

    @Override
    public void createPig(T param) {
        log.info("默认处理器 DefaultOrderModel createPig ...");
    }

    @Override
    public BaseResponse createOrder(T param) {
        return BaseResponse.error("不支持");
    }
}
