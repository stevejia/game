package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigGoodsSpell;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigGoodsSpellService extends CrudService<PigGoodsSpell>{

    IPage<PigGoodsSpell> queryPigGoodsSpell(IPage<PigGoodsSpell> page);

    void savePigGoodsSpell(PigGoodsSpellSaveDto pigGoodsSpellSaveDto);

    void modifyPigGoodsSpell(PigGoodsSpellModifyDto pigGoodsSpellModifyDto);
}