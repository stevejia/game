package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigOrderModifyDto;
import com.futures.model.dto.PigOrderSaveDto;
import com.futures.model.dto.TranComfirmeReqDto;
import com.futures.model.entity.PigOrder;
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