package com.futures.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 15:55
 */
public class ScheduledCronEvent extends ApplicationEvent {

    public ScheduledCronEvent(Object source) {
        super(source);
    }

}
