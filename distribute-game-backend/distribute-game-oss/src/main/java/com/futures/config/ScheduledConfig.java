package com.futures.config;

import com.futures.common.enums.ScheduledTypeEnum;
import com.futures.service.ScheduledCronEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/24 10:04
 */
@Configuration
public class ScheduledConfig {

    @Autowired
    private ScheduledCronEventService cronEventService;

    /**
     * 收益定时
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void incomeCron() {
        cronEventService.addEvent(ScheduledTypeEnum.INCOME);
    }

}
