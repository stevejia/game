package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.PigMoneyCategoryMapper;
import com.gongyu.service.distribute.game.model.dto.PigMoneyCategoryModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigMoneyCategorySaveDto;
import com.gongyu.service.distribute.game.model.entity.PigMoneyCategory;
import com.gongyu.service.distribute.game.service.PigMoneyCategoryService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PigMoneyCategoryServiceImpl extends CrudServiceSupport<PigMoneyCategoryMapper, PigMoneyCategory> implements PigMoneyCategoryService  {

    @Override
    public IPage<PigMoneyCategory> queryPigMoneyCategory(IPage<PigMoneyCategory> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePigMoneyCategory(PigMoneyCategorySaveDto pigMoneyCategorySaveDto) {
        PigMoneyCategory pigMoneyCategory = new PigMoneyCategory();
        BeanUtils.copyProperties(pigMoneyCategorySaveDto, pigMoneyCategory);
        pigMoneyCategory.setUptedaTime(DateUtil.getNowDate());
        this.save(pigMoneyCategory);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPigMoneyCategory(PigMoneyCategoryModifyDto pigMoneyCategoryModifyDto) {
        PigMoneyCategory pigMoneyCategory = new PigMoneyCategory();
        BeanUtils.copyProperties(pigMoneyCategoryModifyDto, pigMoneyCategory);
        pigMoneyCategory.setUptedaTime(DateUtil.getNowDate());
        this.updateById(pigMoneyCategory);
    }
}