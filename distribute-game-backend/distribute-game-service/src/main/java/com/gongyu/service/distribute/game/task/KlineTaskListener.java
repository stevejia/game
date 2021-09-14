package com.gongyu.service.distribute.game.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @path：com.example.demo.task.MyApplicationRunner.java
 * @className：ScheduledTask.java
 * @description：自启动
 * @author：tanyp
 * @dateTime：2020/7/23 21:37 
 * @editNote：
 */
@Slf4j
@Component
public class KlineTaskListener implements ApplicationRunner {

    @Autowired
    private ScheduledTask scheduledTask;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("================项目启动初始化定时任务====开始===========");
        /**
         * 初始化三个任务：
         * 1、10秒执行一次
         * 2、15秒执行一次
         * 3、20秒执行一次
         */
        List<KlineTask> tasks = Arrays.asList(
        		KlineTask.builder().taskId("10003").expression("*/3 * * * * ?").instrumentId("rb2110").period(30).build()
//        		KlineTask.builder().taskId("10004").expression("*/4 * * * * ?").instrumentId("rb2110").period(60).build(),
//        		KlineTask.builder().taskId("10005").expression("*/5 * * * * ?").instrumentId("rb2110").period(90).build()
        );
        scheduledTask.refreshTask(tasks);
        log.info("================项目启动初始化定时任务====完成==========");
    }
}

