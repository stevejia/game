package com.gongyu.service.distribute.game.service;

import com.gongyu.service.distribute.game.event.DelayQueueEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 15:53
 */
@Service
public class DelayQueueEventService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void addEvent(Object data){
        applicationContext.publishEvent(new DelayQueueEvent(data));
    }

}
