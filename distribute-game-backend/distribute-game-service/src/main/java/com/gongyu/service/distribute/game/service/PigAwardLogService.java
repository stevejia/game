package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogPageDto;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigAwardLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.util.List;
import java.util.Set;

public interface PigAwardLogService extends CrudService<PigAwardLog>{

    IPage<PigAwardLogPageDto> queryPigAwardLog(IPage<PigAwardLogPageDto> page,PigAwardLogPageDto dto);
    
    List<PigAwardLogPageDto> queryPigGoodsSummary(PigAwardLogPageDto dto);

    void savePigAwardLog(PigAwardLogSaveDto pigAwardLogSaveDto);

    void modifyPigAwardLog(PigAwardLogModifyDto pigAwardLogModifyDto);

    void handleAwardLog(PigAwardLog awardLog, Set<Long> joinUsers, Set<Long> awardUsers);
}