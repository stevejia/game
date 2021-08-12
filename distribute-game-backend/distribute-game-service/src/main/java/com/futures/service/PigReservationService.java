package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigReservationModifyDto;
import com.futures.model.dto.PigReservationSaveDto;
import com.futures.model.dto.ReservatDto;
import com.futures.model.dto.RobProductsDto;
import com.futures.model.entity.PigGoods;
import com.futures.model.entity.PigReservation;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.util.List;
import java.util.Set;


public interface PigReservationService extends CrudService<PigReservation>{

    IPage<PigReservationModifyDto> queryPigReservation(IPage<PigReservationModifyDto> page,PigReservationModifyDto dto);
    
    
    List<PigReservationModifyDto> queryAllPigReservation(PigReservationModifyDto dto);
    

    void savePigReservation(PigReservationSaveDto pigReservationSaveDto);

    void modifyPigReservation(PigReservationModifyDto pigReservationModifyDto);

    BaseResponse reservat(ReservatDto param);

    BaseResponse robProducts(RobProductsDto param);

    List<PigReservation> luckStatus(Set<Long> users, List<Long>luckUsers, List<PigReservation> reservats);

    long exectTime(PigGoods goods);
}