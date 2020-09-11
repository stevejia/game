package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigMoneyCategoryModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigMoneyCategorySaveDto;
import com.gongyu.service.distribute.game.model.entity.PigMoneyCategory;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigMoneyCategoryService extends CrudService<PigMoneyCategory>{

    IPage<PigMoneyCategory> queryPigMoneyCategory(IPage<PigMoneyCategory> page);

    void savePigMoneyCategory(PigMoneyCategorySaveDto pigMoneyCategorySaveDto);

    void modifyPigMoneyCategory(PigMoneyCategoryModifyDto pigMoneyCategoryModifyDto);
}