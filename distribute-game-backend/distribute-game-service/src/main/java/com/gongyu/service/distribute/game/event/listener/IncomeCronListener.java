package com.gongyu.service.distribute.game.event.listener;

import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.event.ScheduledCronEvent;
import com.gongyu.service.distribute.game.service.IncomeService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 计算收益监听器
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/24 10:30
 */
@Slf4j
@Service
public class IncomeCronListener implements ApplicationListener<ScheduledCronEvent> {

    @Autowired
    private Redisson redission;
    @Autowired
    private IncomeService incomeService;

    @Async("commonExecutor")
    @Override
    public void onApplicationEvent(ScheduledCronEvent scheduledCronEvent) {
        log.info("IncomeCronListener onApplicationEvent start...");
        ScheduledTypeEnum scheduledTypeEnum = (ScheduledTypeEnum) scheduledCronEvent.getSource();
//        RLock lock = redission.getLock("IncomeCronListener");
//        if(scheduledTypeEnum.equals(ScheduledTypeEnum.INCOME)){
//            try{
//                if(lock.tryLock()){
//                    incomeService.processIncome();
//                    log.info("IncomeCronListener onApplicationEvent end...");
//                }
//            }finally {
//                if(lock != null && lock.isHeldByCurrentThread()){
//                    lock.unlock();
//                }
//            }
//        }
    }

}
