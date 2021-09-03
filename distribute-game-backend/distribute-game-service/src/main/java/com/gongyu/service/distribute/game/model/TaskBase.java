package com.gongyu.service.distribute.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/9 10:49
 */
@Data
@AllArgsConstructor
public class TaskBase implements Serializable {

    /**
     * 任务ID
     */
    private String identifier;

    /**
     * 1 开奖
     */
    private Integer taskType;
    
    /**
     * task instrumentId 用于标识合约
     */
    private String instrumentId;
    
    /**
     * task period 用于标识 周期
     */
    private Integer period;
    /**
     * 执行数据
     */
    private Object data;

    public TaskBase() {
    }
}
