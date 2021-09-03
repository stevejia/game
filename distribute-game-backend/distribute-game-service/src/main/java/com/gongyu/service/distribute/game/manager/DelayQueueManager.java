package com.gongyu.service.distribute.game.manager;

import com.alibaba.fastjson.JSON;
import com.gongyu.service.distribute.game.common.enums.DelayTaskEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.event.KlineTaskEvent;
import com.gongyu.service.distribute.game.model.DelayTask;
import com.gongyu.service.distribute.game.model.TaskBase;
import com.gongyu.service.distribute.game.model.entity.PigGoods;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import com.gongyu.service.distribute.game.service.DelayQueueEventService;
import com.gongyu.service.distribute.game.service.PigReservationService;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        List<DelayTask> tasks = this.initTasks();
        Executors.newSingleThreadExecutor().execute(new Thread(this::excuteThread));
        
        
         for(DelayTask task : tasks){
            //计算延时执行时间

            log.info("333333");
            log.info("加入到队列成功：{}", JSON.toJSONString(task));
        }
        log.info("队列初始化完成...");
    }
    
    private List<DelayTask> initTasks(){
    	//mock instrument
    	List<TestInstrumentsClass> instrumentList = new ArrayList<TestInstrumentsClass>();
    	TestInstrumentsClass instrument = new TestInstrumentsClass();
    	instrument.setInstrumentId("rb2110");
    	instrument.setPeriod(30);
    	instrumentList.add(instrument);
    	List<DelayTask> tasks = new ArrayList<DelayTask>();
    	for(TestInstrumentsClass item : instrumentList) {
    		KlineTaskEvent event = new KlineTaskEvent();
    		event.setInstrumentId(item.getInstrumentId());
    		event.setPeriod(item.getPeriod());
    	}
    	return tasks;
    }
    
    public DelayTask convertDelayTask(TaskBase taskBase,Long exectTime){
        DelayTask task = new DelayTask(taskBase,exectTime);
        return task;
    }

    public DelayTask convertTaskBase(Object o,Long exectTime){
        TaskBase base = new TaskBase();
        base.setIdentifier(String.valueOf(DateUtil.getNowDate()));
        base.setData(o);
        DelayTask task = convertDelayTask(base,exectTime);
        return task;
    }
    
    
    @Data
    public class TestInstrumentsClass{
    	private String instrumentId;
    	
    	private Integer period;
    }
}
