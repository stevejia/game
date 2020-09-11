package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigOrderModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigOrderSaveDto;
import com.gongyu.service.distribute.game.model.dto.TranComfirmeReqDto;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;


public interface PigOrderService extends CrudService<PigOrder>{

    IPage<PigOrderModifyDto> queryPigOrder(IPage<PigOrderModifyDto> page,PigOrderModifyDto dto);

    void savePigOrder(PigOrderSaveDto pigOrderSaveDto);

    void modifyPigOrder(PigOrderModifyDto pigOrderModifyDto);

    BaseResponse luckStatus(Long userId,Long goodsId);

    BaseResponse orderDetails(String orderNo);


    /**
     * 交易成功确认
     * @param param
     * @return
     */
    BaseResponse tranComfirme(TranComfirmeReqDto param, Long userId);
}