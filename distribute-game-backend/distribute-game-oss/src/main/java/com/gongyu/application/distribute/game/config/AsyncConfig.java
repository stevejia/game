package com.gongyu.application.distribute.game.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 10:08
 */
@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {


    private int corePoolSize = 10;


    private int maxPoolSize = 300;


    private int queueCapacity = 10;

    @Bean(name = "commonExecutor")
    public Executor commonExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix("comExecutor-");
        threadPoolTaskExecutor.setAwaitTerminationSeconds(30);

        //如果线程数不够，就在当前线程执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
