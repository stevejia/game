package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.mapper.PigGoodsSpellLogMapper;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigGoodsSpellLog;
import com.gongyu.service.distribute.game.service.PigGoodsSpellLogService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PigGoodsSpellLogServiceImpl extends CrudServiceSupport<PigGoodsSpellLogMapper, PigGoodsSpellLog> implements PigGoodsSpellLogService  {

    @Override
    public IPage<PigGoodsSpellLog> queryPigGoodsSpellLog(IPage<PigGoodsSpellLog> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePigGoodsSpellLog(PigGoodsSpellLogSaveDto pigGoodsSpellLogSaveDto) {
        PigGoodsSpellLog pigGoodsSpellLog = new PigGoodsSpellLog();
        BeanUtils.copyProperties(pigGoodsSpellLogSaveDto, pigGoodsSpellLog);
        this.save(pigGoodsSpellLog);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPigGoodsSpellLog(PigGoodsSpellLogModifyDto pigGoodsSpellLogModifyDto) {
        PigGoodsSpellLog pigGoodsSpellLog = new PigGoodsSpellLog();
        BeanUtils.copyProperties(pigGoodsSpellLogModifyDto, pigGoodsSpellLog);
        this.updateById(pigGoodsSpellLog);
    }
}