package com.futures.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigGoodsModifyDto;
import com.futures.model.dto.PigGoodsSaveDto;
import com.futures.model.dto.TodaySalePigSummaryReqDto;
import com.futures.model.entity.PigGoods;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PigGoodsService extends CrudService<PigGoods>{

    IPage<PigGoods> queryPigGoods(IPage<PigGoods> page);

    void savePigGoods(PigGoodsSaveDto pigGoodsSaveDto);

    void modifyPigGoods(PigGoodsModifyDto pigGoodsModifyDto);

    List<PigGoods> queryTodaySalePigSummary(TodaySalePigSummaryReqDto todaySalePigSummaryReqDto);
    
    BaseResponse home();
}