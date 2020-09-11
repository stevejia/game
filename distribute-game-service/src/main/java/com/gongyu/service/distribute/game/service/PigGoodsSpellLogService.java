package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigGoodsSpellLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigGoodsSpellLogService extends CrudService<PigGoodsSpellLog>{

    IPage<PigGoodsSpellLog> queryPigGoodsSpellLog(IPage<PigGoodsSpellLog> page);

    void savePigGoodsSpellLog(PigGoodsSpellLogSaveDto pigGoodsSpellLogSaveDto);

    void modifyPigGoodsSpellLog(PigGoodsSpellLogModifyDto pigGoodsSpellLogModifyDto);
}