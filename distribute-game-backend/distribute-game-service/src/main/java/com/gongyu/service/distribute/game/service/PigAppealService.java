package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigAppealModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigAppealSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigAppeal;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigAppealService extends CrudService<PigAppeal> {

    IPage<PigAppeal> queryPigAppeal(IPage<PigAppeal> page, PigAppealModifyDto pigAppealModifyDto);

    void savePigAppeal(PigAppealSaveDto pigAppealSaveDto);

    void modifyPigAppeal(PigAppealModifyDto pigAppealModifyDto);
    void authPigAppeal(PigAppealModifyDto pigAppealModifyDto);
}