package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.AuthRechargeDto;
import com.gongyu.service.distribute.game.model.dto.RechargeDto;
import com.gongyu.service.distribute.game.model.dto.RechargeModifyDto;
import com.gongyu.service.distribute.game.model.dto.RechargeSaveDto;
import com.gongyu.service.distribute.game.model.entity.Recharge;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import javax.validation.Valid;

public interface RechargeService extends CrudService<Recharge>{

    IPage<RechargeDto> queryRecharge(IPage<RechargeDto> page,RechargeDto dto);

    void saveRecharge(RechargeSaveDto rechargeSaveDto);

    void modifyRecharge(RechargeModifyDto rechargeModifyDto);

    BaseResponse auth(AuthRechargeDto param);
}