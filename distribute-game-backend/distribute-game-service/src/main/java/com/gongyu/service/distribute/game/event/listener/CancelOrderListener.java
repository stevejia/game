package com.gongyu.service.distribute.game.event.listener;

import com.alibaba.fastjson.JSON;
import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.event.DelayQueueEvent;
import com.gongyu.service.distribute.game.manager.DelayQueueManager;
import com.gongyu.service.distribute.game.manager.PigOrderManager;
import com.gongyu.service.distribute.game.manager.RevealLuckyManager;
import com.gongyu.service.distribute.game.manager.UserExclusivePigManager;
import com.gongyu.service.distribute.game.model.DelayTask;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePig;
import com.gongyu.service.distribute.game.model.entity.Users;
import com.gongyu.service.distribute.game.service.UsersService;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 订单超时取消订单监听器
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 17:53
 */
@Slf4j
@Service
public class CancelOrderListener implements ApplicationListener<DelayQueueEvent> {

    @Autowired
    private Redisson redisson;
    @Autowired
    private PigOrderManager orderManager;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DelayQueueManager queueManager;
    @Autowired
    private RevealLuckyManager luckyManager;
    @Autowired
    private UserExclusivePigManager pigManager;

    @Async("commonExecutor")
    @Override
    public void onApplicationEvent(DelayQueueEvent delayQueueEvent) {
        PigOrder order = null;
        PigOrder taskOrder = null;
        DelayTask task = (DelayTask)delayQueueEvent.getSource();
        RLock lock = redisson.getLock("cancelOrderListener");
        if(task.getData().getTaskType() == DelayTaskEnum.CANCEL_ORDER.getCode()){
            log.info("订单超时取消订单监听器...delayQueueEvent:{}",JSON.toJSONString(delayQueueEvent));
            taskOrder = (PigOrder)task.getData().getData();
            order = orderManager.getByOrderNo(taskOrder.getPigOrderSn());

            try{
                if(PayStatusEnum.FREEZE.getCode().intValue() == order.getPayStatus().intValue()){
                    log.info("订单超时取消订单监听器 onApplicationEvent 该订单已冻结 order:{}",JSON.toJSONString(order));
                    queueManager.remove(task);
                    //删除老的延时取消订单，并重新计算延时时间加入一份新的取消订单的延时任务
                    task = luckyManager.convertTaskBase(order, DelayTaskEnum.CANCEL_ORDER,7200000L);// TODO: 2020/6/23 测试时间
                    queueManager.put(task);
                    RedisUtils.set("task:order-"+order.getPigOrderSn(),task,7200000L);
                }else{
                    if(lock.tryLock()){
                        try{
                            log.info("订单超时取消订单监听器：{}", JSON.toJSONString(task));
                            //避免分布式场景重复消费
                            if(null != RedisUtils.get("task:order-"+order.getPigOrderSn())){
                                this.processTask(order);
                            }
                            return;
                        }finally {
                            if(null != lock){
                                lock.unlock();
                            }
                            //清除订单信息
                            if(null != order){
                                log.info("订单超时取消订单逻辑处理完成...");
                                RedisUtils.remove("task:order-"+order.getPigOrderSn());
                            }
                        }
                    }
                }
            }catch (Exception e){
                log.error("订单超时取消订单逻辑处理异常...",e);
                log.error("订单超时取消订单逻辑处理 order:{]",JSON.toJSONString(order));
                //清除订单信息
                if(null == order){
                    RedisUtils.remove("task:order-"+taskOrder.getPigOrderSn());
                }
            }


        }
    }

    public void processTask(PigOrder order){
        order.setEndTime(DateUtil.getNowDate());
        UserExclusivePig pig = pigManager.findById(order.getPigId());
        //买方已付款
        if(PayStatusEnum.SUCCESS.getCode() == order.getPayStatus()){
        	//买方已付款 未确认时
        	if(order.getSellConfirmStatus() == CommEnum.FALSE.getCode()) {
        		order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                order.setSellConfirmStatus(CommEnum.TRUE.getCode());
                pig.setIsAbleSale(SaleStatusEnum.FALSE.getCode());
                pig.setAppointUserId(null);
                pig.setOrderId(order.getOrderId());
                pig.setIsPigLock(LockStatusEnum.NOT_LOCK.getCode());
                pigManager.update(pig);
                //木材归属方获取一枚pig币
                Long buyUserId = order.getPurchaseUserId();
                Users users = usersService.getById(buyUserId);
                users.setPigCurrency(users.getPigCurrency() + 1);
                usersService.updateById(users);
        	}
        }else{
            order.setPayStatus(PayStatusEnum.TRAN_TIMEOUT.getCode());

            pig.setIsAbleSale(SaleStatusEnum.FALSE.getCode());
            pig.setAppointUserId(null);
            pig.setOrderId(order.getOrderId());
            pig.setIsPigLock(LockStatusEnum.LOCK.getCode());
            pigManager.update(pig);

            UserExclusivePig newPig = new UserExclusivePig();
            BeanUtils.copyProperties(pig,newPig);
            newPig.setIsPigLock(LockStatusEnum.NOT_LOCK.getCode());
            newPig.setIsAbleSale(SaleStatusEnum.TRUE.getCode());
            newPig.setOrderId(null);
            newPig.setId(null);
            pigManager.insert(newPig);
        }
        orderManager.updateIgnoreNull(order);
    }
}
