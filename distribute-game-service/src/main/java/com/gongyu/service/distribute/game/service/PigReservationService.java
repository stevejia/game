package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigReservationModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigReservationSaveDto;
import com.gongyu.service.distribute.game.model.dto.ReservatDto;
import com.gongyu.service.distribute.game.model.dto.RobProductsDto;
import com.gongyu.service.distribute.game.model.entity.PigGoods;
import com.gongyu.service.distribute.game.model.entity.PigReservation;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.util.List;
import java.util.Set;


public interface PigReservationService extends CrudService<PigReservation>{

    IPage<PigReservationModifyDto> queryPigReservation(IPage<PigReservationModifyDto> page,PigReservationModifyDto dto);

    void savePigReservation(PigReservationSaveDto pigReservationSaveDto);

    void modifyPigReservation(PigReservationModifyDto pigReservationModifyDto);

    BaseResponse reservat(ReservatDto param);

    BaseResponse robProducts(RobProductsDto param);

    List<PigReservation> luckStatus(Set<Long> users, List<Long>luckUsers, List<PigReservation> reservats);

    long exectTime(PigGoods goods);
}