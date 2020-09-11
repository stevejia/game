package com.gongyu.service.distribute.game.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 15:55
 */
public class DelayQueueEvent extends ApplicationEvent {

    public DelayQueueEvent(Object source) {
        super(source);
    }

}
