package com.gongyu.service.distribute.game.event.listener;

import com.alibaba.fastjson.JSON;
import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.event.DelayQueueEvent;
import com.gongyu.service.distribute.game.model.DelayTask;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.service.distribute.game.utils.RedisUtils2;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 处理开奖监听者
 * 
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 16:02
 */
@Slf4j
@Service
public class KlineListener implements ApplicationListener<DelayQueueEvent> {

	@Autowired
	private Redisson redisson;
	@Autowired
	private OrderService orderServicel;

	@Async("commonExecutor")
	@Override
	public void onApplicationEvent(DelayQueueEvent delayQueueEvent) {
		DelayTask task = (DelayTask) delayQueueEvent.getSource();
		RLock lock = redisson.getLock("klineListener");

		lock.lock();
		Gson gson = new Gson();
		log.info(gson.toJson(task.getData().getData()));
		lock.unlock();
	}
}
