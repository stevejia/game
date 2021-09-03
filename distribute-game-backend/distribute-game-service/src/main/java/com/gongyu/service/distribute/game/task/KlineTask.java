package com.gongyu.service.distribute.game.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KlineTask {
	/**
     * 任务id
     */
    private String taskId;
    
    private Integer period;
    
    private String instrumentId;

    /**
     * 任务执行规则时间
     */
    private String expression;
}
