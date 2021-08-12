package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigAwardLogModifyDto;
import com.futures.model.dto.PigAwardLogPageDto;
import com.futures.model.dto.PigAwardLogSaveDto;
import com.futures.model.entity.PigAwardLog;
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