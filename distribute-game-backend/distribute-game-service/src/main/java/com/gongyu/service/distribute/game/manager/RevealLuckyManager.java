package com.gongyu.service.distribute.game.manager;

import com.gongyu.service.distribute.game.common.enums.DelayTaskEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.model.DelayTask;
import com.gongyu.service.distribute.game.model.TaskBase;
import org.springframework.stereotype.Service;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 11:15
 */
@Service
public class RevealLuckyManager {

    public DelayTask convertDelayTask(TaskBase taskBase,Long exectTime){
        DelayTask task = new DelayTask(taskBase,exectTime);
        return task;
    }

    public DelayTask convertTaskBase(Object o,DelayTaskEnum taskEnum,Long exectTime){
        TaskBase base = new TaskBase();
        base.setIdentifier(String.valueOf(DateUtil.getNowDate()));
        base.setData(o);
        base.setTaskType(taskEnum.getCode());
        DelayTask task = convertDelayTask(base,exectTime);
        return task;
    }
}
