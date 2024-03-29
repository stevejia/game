package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.mapper.PigGoodsSpellMapper;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigGoodsSpell;
import com.gongyu.service.distribute.game.service.PigGoodsSpellService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PigGoodsSpellServiceImpl extends CrudServiceSupport<PigGoodsSpellMapper, PigGoodsSpell> implements PigGoodsSpellService  {

    @Override
    public IPage<PigGoodsSpell> queryPigGoodsSpell(IPage<PigGoodsSpell> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePigGoodsSpell(PigGoodsSpellSaveDto pigGoodsSpellSaveDto) {
        PigGoodsSpell pigGoodsSpell = new PigGoodsSpell();
        BeanUtils.copyProperties(pigGoodsSpellSaveDto, pigGoodsSpell);
        this.save(pigGoodsSpell);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPigGoodsSpell(PigGoodsSpellModifyDto pigGoodsSpellModifyDto) {
        PigGoodsSpell pigGoodsSpell = new PigGoodsSpell();
        BeanUtils.copyProperties(pigGoodsSpellModifyDto, pigGoodsSpell);
        this.updateById(pigGoodsSpell);
    }
}