package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigGoodsSpellModifyDto;
import com.futures.model.dto.PigGoodsSpellSaveDto;
import com.futures.model.entity.PigGoodsSpell;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigGoodsSpellService extends CrudService<PigGoodsSpell>{

    IPage<PigGoodsSpell> queryPigGoodsSpell(IPage<PigGoodsSpell> page);

    void savePigGoodsSpell(PigGoodsSpellSaveDto pigGoodsSpellSaveDto);

    void modifyPigGoodsSpell(PigGoodsSpellModifyDto pigGoodsSpellModifyDto);
}