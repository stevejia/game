package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigGoodsSpellLogModifyDto;
import com.futures.model.dto.PigGoodsSpellLogSaveDto;
import com.futures.model.entity.PigGoodsSpellLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigGoodsSpellLogService extends CrudService<PigGoodsSpellLog>{

    IPage<PigGoodsSpellLog> queryPigGoodsSpellLog(IPage<PigGoodsSpellLog> page);

    void savePigGoodsSpellLog(PigGoodsSpellLogSaveDto pigGoodsSpellLogSaveDto);

    void modifyPigGoodsSpellLog(PigGoodsSpellLogModifyDto pigGoodsSpellLogModifyDto);
}