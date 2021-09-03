package com.gongyu.application.distribute.game.config;

import com.gongyu.service.distribute.game.common.enums.ScheduledTypeEnum;
import com.gongyu.service.distribute.game.service.ScheduledCronEventService;
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
    @Scheduled(fixedRate = 3000)
    public void incomeCron() {
        cronEventService.addEvent(ScheduledTypeEnum.INCOME);
    }

}
