package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigGoodsModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigGoods;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigGoodsService extends CrudService<PigGoods>{

    IPage<PigGoods> queryPigGoods(IPage<PigGoods> page);

    void savePigGoods(PigGoodsSaveDto pigGoodsSaveDto);

    void modifyPigGoods(PigGoodsModifyDto pigGoodsModifyDto);

    BaseResponse home();
}