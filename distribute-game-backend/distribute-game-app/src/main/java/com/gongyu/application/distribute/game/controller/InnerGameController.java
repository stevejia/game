package com.gongyu.application.distribute.game.controller;

import com.alibaba.fastjson.JSON;
import com.gongyu.service.distribute.game.common.enums.ScheduledTypeEnum;
import com.gongyu.service.distribute.game.manager.DelayQueueManager;
import com.gongyu.service.distribute.game.model.DelayTask;
import com.gongyu.service.distribute.game.service.ScheduledCronEventService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/28 20:22
 */
@Slf4j
@RestController
@RequestMapping("inner")
@Api(tags = "手动补偿")
public class InnerGameController {

    @Autowired
    private ScheduledCronEventService executorService;
    @Autowired
    private DelayQueueManager queueManager;
    // TODO: 2020/6/28 开奖补偿

    @RequestMapping("openLuck")
    public BaseResponse openLuck(){
        String s = "{\"data\":{\"data\":{\"adoptiveEnergy\":40,\"contractDays\":5,\"dogeMoney\":1,\"endTime\":33960000,\"gameOpenType\":0,\"gameResetTime\":1589644801,\"goodsName\":\"铁男\",\"id\":24,\"images\":\"http://bucket-gongyu.oss-cn-hangzhou.aliyuncs.com/root/4133a2fb79b24ca1ac3aa19e2f981311.png\",\"images2\":\"https://cgfoss.oss-cn-hangzhou.aliyuncs.com/angular/b39b617574364e04a323c6c40e1dbf85.jpg\",\"incomeRatio\":15.00,\"isDisplay\":1,\"isLock\":1,\"isSpell\":0,\"largePrice\":1500.01,\"moneyCategoryId\":4,\"pigCurrency\":10,\"probability\":30,\"reservation\":25,\"resetTime\":0,\"secondEndTime\":-28800000,\"secondStartTime\":-28800000,\"smallPrice\":900.02,\"splitNum\":0,\"startTime\":33840000,\"todayIsOpen\":1},\"identifier\":\"1594200245\",\"taskType\":1},\"expire\":1594201408000}";
        DelayTask task = JSON.parseObject(s, DelayTask.class);
        queueManager.put(task);
        return BaseResponse.success();
    }

    // TODO: 2020/6/28 收益计算补偿

    @RequestMapping("open")
    public BaseResponse open(){
        executorService.addEvent(ScheduledTypeEnum.INCOME);
        return BaseResponse.success();
    }
}
