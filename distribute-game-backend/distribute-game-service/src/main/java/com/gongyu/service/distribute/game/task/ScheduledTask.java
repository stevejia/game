package com.gongyu.service.distribute.game.task;


import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gongyu.service.distribute.game.service.KlineService;

import lombok.extern.slf4j.Slf4j;

/**
 * @path：com.example.demo.task.ScheduledTask.java
 * @className：ScheduledTask.java
 * @description：定时任务
 * @author：tanyp
 * @dateTime：2020/7/23 21:37 
 * @editNote：
 */
@Slf4j
@Component
public class ScheduledTask implements SchedulingConfigurer {
	
	@Autowired
	private KlineService klineService;
	
    private volatile ScheduledTaskRegistrar registrar;

    private final ConcurrentHashMap<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, CronTask> cronTasks = new ConcurrentHashMap<>();

    /**
     * 默认启动10个线程
     */
    private static final Integer DEFAULT_THREAD_POOL = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        registrar.setScheduler(Executors.newScheduledThreadPool(DEFAULT_THREAD_POOL));
        this.registrar = registrar;
    }

    @PreDestroy
    public void destroy() {
        this.registrar.destroy();
    }

    /**
     * @methodName：refreshTask
     * @description：初始化任务
     * 1、从数据库获取执行任务的集合【TxTask】
     * 2、通过调用 【refresh】 方法刷新任务列表
     * 3、每次数据库中的任务发生变化后重新执行【1、2】
     * @author：tanyp
     * @dateTime：2020/7/23 21:37
     * @Params： [tasks]
     * @Return： void
     * @editNote：
     */
    public void refreshTask(List<KlineTask> tasks) {
        // 删除已经取消任务
        scheduledFutures.keySet().forEach(key -> {
            if (Objects.isNull(tasks) || tasks.size() == 0) {
                scheduledFutures.get(key).cancel(false);
                scheduledFutures.remove(key);
                cronTasks.remove(key);
                return;
            }
            tasks.forEach(task -> {
                if (!Objects.equals(key, task.getTaskId())) {
                    scheduledFutures.get(key).cancel(false);
                    scheduledFutures.remove(key);
                    cronTasks.remove(key);
                    return;
                }
            });
        });

        // 添加新任务、更改执行规则任务
        tasks.forEach(txTask -> {
            String expression = txTask.getExpression();
            // 任务表达式为空则跳过
            if (StringUtils.isEmpty(expression)) {
                return;
            }

            // 任务已存在并且表达式未发生变化则跳过
            if (scheduledFutures.containsKey(txTask.getTaskId()) && cronTasks.get(txTask.getTaskId()).getExpression().equals(expression)) {
                return;
            }

            // 任务执行时间发生了变化，则删除该任务
            if (scheduledFutures.containsKey(txTask.getTaskId())) {
                scheduledFutures.get(txTask.getTaskId()).cancel(false);
                scheduledFutures.remove(txTask.getTaskId());
                cronTasks.remove(txTask.getTaskId());
            }
            CronTask task = new CronTask(new Runnable() {
                @Override
                public void run() {
                    // 执行业务逻辑
                    try { 
                        log.info("执行单个任务，任务ID【{}】合约【{}】周期【{}】 执行规则【{}】", txTask.getTaskId(), txTask.getInstrumentId(), txTask.getPeriod(), txTask.getExpression());
                        klineService.processKlineTD(txTask.getInstrumentId(), txTask.getPeriod());
                        System.out.println("==========================执行任务=============================");
                    } catch (Exception e) {
                        log.error("执行发送消息任务异常，异常信息：{}", e);
                    }
                }
            }, expression);
            ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
            cronTasks.put(txTask.getTaskId(), task);
            scheduledFutures.put(txTask.getTaskId(), future);
        });
    }

}

