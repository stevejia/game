package com.futures.event.listener;

import com.alibaba.fastjson.JSON;
import com.futures.common.enums.*;
import com.futures.event.DelayQueueEvent;
import com.futures.model.DelayTask;
import com.futures.model.entity.*;
import com.futures.service.*;
import com.futures.utils.RedisUtils2;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 处理开奖监听者
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 16:02
 */
@Slf4j
@Service
public class RevealLuckListener implements ApplicationListener<DelayQueueEvent> {

    @Autowired
    private Redisson redisson;
    @Autowired
    private OrderService orderServicel;

    @Async("commonExecutor")
    @Override
    public void onApplicationEvent(DelayQueueEvent delayQueueEvent) {
        PigGoods goods = null;
        DelayTask task = (DelayTask)delayQueueEvent.getSource();
        RLock lock = redisson.getLock("revealLuckListener");
        if(task.getData().getTaskType() == DelayTaskEnum.REVEAL_LUCKY.getCode()){
            log.info("开奖处理监听者...delayQueueEvent:{}",JSON.toJSONString(delayQueueEvent));
            lock.lock();
            try{
                log.info("执行开奖延时任务：{}", JSON.toJSONString(task));
                goods = (PigGoods)task.getData().getData();
                //避免分布式场景重复消费
                if(null != RedisUtils.get("task:" + goods.getId())){
                    orderServicel.processTask(goods);
                }
                return;
            } catch (Exception e){
                log.info("处理开奖监听者 onApplicationEvent throw Exception ... ",e);
                throw new BizException("处理开奖监听者处理异常");
            } finally{
                //清除本次开奖信息
                log.info("开奖逻辑处理完成...task:{}",JSON.toJSONString(RedisUtils.get("task:" + goods.getId())));
//                RedisUtils.remove("robProduct:" + goods.getId());
                RedisUtils2.removeBatch("robProduct:");
                RedisUtils.remove("task:" + goods.getId());
                lock.unlock();
            }
        }
    }
}
