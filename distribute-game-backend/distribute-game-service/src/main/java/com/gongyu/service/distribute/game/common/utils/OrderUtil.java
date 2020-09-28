package com.gongyu.service.distribute.game.common.utils;

import com.gongyu.snowcloud.framework.util.DateUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 20:29
 */
@Component
public class OrderUtil {

    @Resource
    private Redisson redisson;

    /**
     * 生成订单编号
     * @return
     */
    public String grenOrderNo(){
        RLock rLock = redisson.getLock("grenOrderNo");
        try{
            rLock.lock();
            String orderNo = DateUtils.format(new Date(), DateUtils.DEFAULT_LONG_DATE_FORMAT + "SSS");
            Long intOrderNo = Long.parseLong(orderNo) * 100;
            Random r = new Random();
            int i = r.nextInt(100000);
            intOrderNo +=i;
//            orderNo = orderNo + i;
            return Long.toString(intOrderNo);
        }finally {
            rLock.unlock();
        }

    }

}
