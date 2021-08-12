package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigMoneyCategoryModifyDto;
import com.futures.model.dto.PigMoneyCategorySaveDto;
import com.futures.model.entity.PigMoneyCategory;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigMoneyCategoryService extends CrudService<PigMoneyCategory>{

    IPage<PigMoneyCategory> queryPigMoneyCategory(IPage<PigMoneyCategory> page);

    void savePigMoneyCategory(PigMoneyCategorySaveDto pigMoneyCategorySaveDto);

    void modifyPigMoneyCategory(PigMoneyCategoryModifyDto pigMoneyCategoryModifyDto);
}