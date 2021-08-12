package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigAppealModifyDto;
import com.futures.model.dto.PigAppealSaveDto;
import com.futures.model.entity.PigAppeal;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigAppealService extends CrudService<PigAppeal> {

    IPage<PigAppeal> queryPigAppeal(IPage<PigAppeal> page, PigAppealModifyDto pigAppealModifyDto);

    void savePigAppeal(PigAppealSaveDto pigAppealSaveDto);

    void modifyPigAppeal(PigAppealModifyDto pigAppealModifyDto);
    void authPigAppeal(PigAppealModifyDto pigAppealModifyDto);
}