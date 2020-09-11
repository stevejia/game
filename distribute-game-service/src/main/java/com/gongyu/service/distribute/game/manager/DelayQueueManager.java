package com.gongyu.service.distribute.game.manager;

import com.alibaba.fastjson.JSON;
import com.gongyu.service.distribute.game.common.enums.DelayTaskEnum;
import com.gongyu.service.distribute.game.model.DelayTask;
import com.gongyu.service.distribute.game.model.entity.PigGoods;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import com.gongyu.service.distribute.game.service.DelayQueueEventService;
import com.gongyu.service.distribute.game.service.PigReservationService;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 10:54
 */
@Slf4j
@Component
public class DelayQueueManager implements CommandLineRunner {

    private DelayQueue delayQueue = new DelayQueue<>();

    @Autowired
    private DelayQueueEventService delayQueueEventService;
    @Autowired
    private PigReservationService reservationService;
    @Autowired
    private RevealLuckyManager luckyManager;


    /**
     * 加入到延时队列中
     * @param task
     */
    public void put(DelayTask task) {
        log.info("加入延时任务：{}", task);
        delayQueue.put(task);
    }

    /**
     * 删除延时队列中的元素
     * @param task
     */
    public void remove(DelayTask task){
        log.info("删除延时队列元素 task:{}",JSON.toJSONString(task));
        delayQueue.remove(task);
    }

    /**
     * 延时任务执行线程
     */
    private void excuteThread() {
        while (true) {
            try {
                DelayTask task = (DelayTask) delayQueue.take();
                delayQueueEventService.addEvent(task);
            } catch (Exception e){
                log.error("延时队列 excuteThread exception ... ",e);
            }
        }
    }

    @Override
    public void run(String... args) {
        log.info("准备初始化延时队列...");
        Executors.newSingleThreadExecutor().execute(new Thread(this::excuteThread));
        //初始化Redis中数据加入到延时队列中
        Set<String> keys = RedisUtils.getRedisTemplate().keys("task:*");
        List<DelayTask> tasks = RedisUtils.getRedisTemplate().opsForValue().multiGet(keys);
        //将开奖任务场次加入到队列中
        // TODO: 2020/7/9 测试注释 
         for(DelayTask task : tasks){
            //计算延时执行时间

            if(DelayTaskEnum.REVEAL_LUCKY.getCode().intValue() == task.getData().getTaskType().intValue()){
                PigGoods goods = (PigGoods) task.getData().getData();
                //重新计算保存时间
                long exectTime = reservationService.exectTime(goods);
                if(exectTime < 0){
                    log.info(goods.getId() + " " + goods.getGoodsName() + " 开奖时间已过期");
                    log.info("清理请抢购人员缓存：{}",JSON.toJSONString(RedisUtils.get("robProduct:" + goods.getId())));
                    RedisUtils.remove("robProduct:" + goods.getId());
                    RedisUtils.remove("task:" + goods.getId());
                    continue;
                }
                task = luckyManager.convertTaskBase(goods, DelayTaskEnum.REVEAL_LUCKY,exectTime);
                put(task);
            }else if(DelayTaskEnum.CANCEL_ORDER.getCode().intValue() == task.getData().getTaskType().intValue()){
                PigOrder order = (PigOrder)task.getData().getData();
                task = luckyManager.convertTaskBase(order, DelayTaskEnum.CANCEL_ORDER,1800000L);// TODO: 2020/6/23 暂时固定时间 18000
                put(task);
            }
            log.info("加入到队列成功：{}", JSON.toJSONString(task));
        }
        log.info("队列初始化完成...");
    }
}
